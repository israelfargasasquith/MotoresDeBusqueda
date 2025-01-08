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
    

    public TagDetector() { //Leer desde el fichero que genere el script que identifique el numero de tags diferentes que hay (TODO)
        tags = new HashMap();
        tags.put("<ADMINISTRATION>",1);
        tags.put("<VOLUME>",2);
        tags.put("<THERAPEUTIC_PROCEDURE>",3);
        tags.put("<SIGN_SYMPTOM>",4);
        tags.put("<SEX>",5);
        tags.put("<SEVERITY>",6);
        tags.put("<PERSONAL_BACKGROUND>",7);
        tags.put("<NONBIOLOGICAL_LOCATION>",8);
        tags.put("<MEDICATION>",9);
        tags.put("<LAB_VALUE>",10);
        tags.put("<HISTORY>",11);
        tags.put("<FAMILY_HISTORY>",12);
        tags.put("<DOSAGE>",13);
        tags.put("<DURATION>",14);
        tags.put("<DISTANCE>",15);
        tags.put("<DISEASE_DISORDER>",16);
        tags.put("<DIAGNOSTIC_PROCEDURE>",17);
        tags.put("<DETAILED_DESCRIPTION>",18);
        tags.put("<DATE>",19);
        tags.put("<COREFERENCE>",20);
        tags.put("<CLINICAL_EVENT>",21);
        tags.put("<BIOLOGICAL_STRUCTURE>",22);
        tags.put("<BIOLOGICAL_ATTRIBUTE>",23);
        tags.put("<AREA>",24);
        tags.put("<AGE>",25);
    }
    
    public boolean identified(String line){
        return false; sadasd
    }

}
