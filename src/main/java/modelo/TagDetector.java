/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author israe
 */
public class TagDetector {

    private HashMap tags;
    private ArrayList<ArrayList> everyTag;
    private ArrayList<String> administrationIterations;
    private ArrayList<String> volumeIterations;
    private ArrayList<String> therapeutic_procedureIterations;
    private ArrayList<String> sign_symptomIterations;
    private ArrayList<String> sexIterations;
    private ArrayList<String> severityIterations;
    private ArrayList<String> personal_backgroundIterations;
    private ArrayList<String> nonbiological_locationIterations;
    private ArrayList<String> medicationIterations;
    private ArrayList<String> lab_valueIterations;
    private ArrayList<String> historyIterations;
    private ArrayList<String> family_historyIterations;
    private ArrayList<String> dosageIterations;
    private ArrayList<String> distanceIterations;
    private ArrayList<String> diagnosis_procedureIterations;
    private ArrayList<String> detailed_descriptionIterations;
    private ArrayList<String> dateIterations;
    private ArrayList<String> biological_structureIterations;
    private ArrayList<String> biological_attributeIterations;
    private ArrayList<String> areaIterations;
    private ArrayList<String> ageIterations;
    private ArrayList<String> disease_disorderIterations;
    private ArrayList<String> conferenceIterations;
    private ArrayList<String> clinical_eventIterations;
    private ArrayList<String> durationIterations;

    public TagDetector() {
        //Leer desde el fichero que genere el script que identifique el numero de tags diferentes que hay (TODO)
        tags = new HashMap();
        tags.put("<ADMINISTRATION>", 1);
        tags.put("<VOLUME>", 2);
        tags.put("<THERAPEUTIC_PROCEDURE>", 3);
        tags.put("<SIGN_SYMPTOM>", 4);
        tags.put("<SEX>", 5);
        tags.put("<SEVERITY>", 6);
        tags.put("<PERSONAL_BACKGROUND>", 7);
        tags.put("<NONBIOLOGICAL_LOCATION>", 8);
        tags.put("<MEDICATION>", 9);
        tags.put("<LAB_VALUE>", 10);
        tags.put("<HISTORY>", 11);
        tags.put("<FAMILY_HISTORY>", 12);
        tags.put("<DOSAGE>", 13);
        tags.put("<DURATION>", 14);
        tags.put("<DISTANCE>", 15);
        tags.put("<DISEASE_DISORDER>", 16);
        tags.put("<DIAGNOSTIC_PROCEDURE>", 17);
        tags.put("<DETAILED_DESCRIPTION>", 18);
        tags.put("<DATE>", 19);
        tags.put("<COREFERENCE>", 20);
        tags.put("<CLINICAL_EVENT>", 21);
        tags.put("<BIOLOGICAL_STRUCTURE>", 22);
        tags.put("<BIOLOGICAL_ATTRIBUTE>", 23);
        tags.put("<AREA>", 24);
        tags.put("<AGE>", 25);

        this.administrationIterations = new ArrayList<>();
        this.ageIterations = new ArrayList<>();
        this.areaIterations = new ArrayList<>();
        this.biological_attributeIterations = new ArrayList<>();
        this.biological_structureIterations = new ArrayList<>();
        this.clinical_eventIterations = new ArrayList<>();
        this.conferenceIterations = new ArrayList<>();
        this.disease_disorderIterations = new ArrayList<>();
        this.dateIterations = new ArrayList<>();
        this.detailed_descriptionIterations = new ArrayList<>();
        this.diagnosis_procedureIterations = new ArrayList<>();
        this.distanceIterations = new ArrayList<>();
        this.dosageIterations = new ArrayList<>();
        this.durationIterations = new ArrayList<>();
        this.family_historyIterations = new ArrayList<>();
        this.historyIterations = new ArrayList<>();
        this.lab_valueIterations = new ArrayList<>();
        this.medicationIterations = new ArrayList<>();
        this.nonbiological_locationIterations = new ArrayList<>();
        this.personal_backgroundIterations = new ArrayList<>();
        this.severityIterations = new ArrayList<>();
        this.sexIterations = new ArrayList<>();
        this.sign_symptomIterations = new ArrayList<>();
        this.therapeutic_procedureIterations = new ArrayList<>();
        this.volumeIterations = new ArrayList<>();

        this.everyTag = new ArrayList<>();

        this.everyTag.add(administrationIterations);
        this.everyTag.add(volumeIterations);
        this.everyTag.add(therapeutic_procedureIterations);
        this.everyTag.add(sign_symptomIterations);
        this.everyTag.add(sexIterations);
        this.everyTag.add(severityIterations);
        this.everyTag.add(personal_backgroundIterations);
        this.everyTag.add(nonbiological_locationIterations);
        this.everyTag.add(medicationIterations);
        this.everyTag.add(lab_valueIterations);
        this.everyTag.add(historyIterations);
        this.everyTag.add(family_historyIterations);
        this.everyTag.add(dosageIterations);
        this.everyTag.add(durationIterations);
        this.everyTag.add(distanceIterations);
        this.everyTag.add(disease_disorderIterations);
        this.everyTag.add(diagnosis_procedureIterations);
        this.everyTag.add(detailed_descriptionIterations);
        this.everyTag.add(dateIterations);
        this.everyTag.add(conferenceIterations);
        this.everyTag.add(clinical_eventIterations);
        this.everyTag.add(biological_structureIterations);
        this.everyTag.add(biological_attributeIterations);
        this.everyTag.add(areaIterations);
        this.everyTag.add(ageIterations);

    }

    public boolean identified(String line) {

        String splittedLine[] = line.split("\\s+");
        int valueOfMap = -1;
        for (String string : splittedLine) {
            if (tags.get(string) != null) {
                valueOfMap = (int) tags.get(string);
                everyTag.get(valueOfMap-1).add(string);

            }
        }
        return false;
    }

}
