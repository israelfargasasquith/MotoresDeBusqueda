import re
from CallingNer import *
with open("MED.ALL", 'r') as infile, open("ENRICHED_MED.ALL", 'w') as outfile:
    regex = r"^\.I\s\d+$|^\.W$"
    counter = 1
    text = []

    for line in infile:
        if re.match(regex, line):
            if text:
                processed = enrich_text_with_ner(" ".join(text))
                outfile.write(f".I {counter}\n.W\n{processed}\n")
                counter += 1
                text = []

            if line.startswith(".I"):
                continue
        else:
            text.append(line.strip())
            text.append("\n")

    if text:
        processed = enrich_text_with_ner(" ".join(text))
        outfile.write(f".I {counter}\n.W\n{processed}\n")

