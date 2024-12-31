from transformers import AutoTokenizer, AutoModelForTokenClassification
from transformers import pipeline

# Cargar el modelo y el tokenizador preentrenados
tokenizer = AutoTokenizer.from_pretrained("Clinical-AI-Apollo/Medical-NER")
model = AutoModelForTokenClassification.from_pretrained("Clinical-AI-Apollo/Medical-NER")

# Crear un pipeline para el reconocimiento de entidades nombradas
nlp = pipeline("ner", model=model, tokenizer=tokenizer)


def enrich_text_with_ner(text):
    # Obtener los resultados del reconocimiento de entidades nombradas
    ner_results = nlp(text)

    # Ordenar las entidades por sus posiciones de inicio
    ner_results = sorted(ner_results, key=lambda x: x['start'])

    # Inicializar una lista para almacenar las entidades combinadas
    combined_entities = []

    # Combinar entidades contiguas del mismo tipo
    for entity in ner_results:
        entity_type = entity['entity'].split('-')[-1]  # Obtener el tipo de entidad sin el prefijo B- o I-
        if combined_entities and entity_type == combined_entities[-1]['entity']:
            combined_entities[-1]['end'] = entity['end']
            combined_entities[-1]['word'] += text[entity['start']:entity['end']]
        else:
            combined_entities.append({
                'entity': entity_type,
                'start': entity['start'],
                'end': entity['end'],
                'word': text[entity['start']:entity['end']]
            })

    # Inicializar una cadena vacía para almacenar el texto enriquecido
    enriched_text = ""

    # Inicializar una variable para rastrear la última posición en el texto
    last_position = 0

    # Iterar sobre las entidades combinadas y enriquecer el texto
    for entity in combined_entities:
        # Agregar el texto antes de la entidad
        enriched_text += text[last_position:entity['start']]

        # Agregar la entidad con etiquetas
        enriched_text += f"<{entity['entity']}>{entity['word']}</{entity['entity']}>"

        # Actualizar la última posición
        last_position = entity['end']

    # Agregar el texto restante después de la última entidad
    enriched_text += text[last_position:]

    return enriched_text

# Texto de ejemplo
#example_text = "the investigations reported here offer a model for further research into possible intrauterine factors in the pathogenesis of hyaline membrane disease"

# Enriquecer el texto con etiquetas de NER y mostrar el resultado
#enriched_text = enrich_text_with_ner(example_text)
#print(enriched_text)