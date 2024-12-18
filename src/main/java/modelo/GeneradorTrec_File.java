/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author israe
 */
public class GeneradorTrec_File {

    private String filePath;
    private File trecDoc;
    private FileWriter escritor;

    public GeneradorTrec_File() {
        this.filePath = "" + System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "generatedOutput" + File.separator;
        trecDoc = new File(filePath + "trec_solr_file");
    }

    public String generarFicheroConsultas(ArrayList<RespuestaTrec> respuestas) {
        //Consulta Q0 documento ranking score EQUIPO is the format of the file

        try {
            trecDoc.createNewFile();
        } catch (IOException ex) { //mejorar este manejo de excepciones
            System.out.println("Error al generar el fichero trec: " + ex.getMessage());
        }
        try {
            escritor = new FileWriter(trecDoc);
        } catch (IOException ex) {
            System.out.println("Error al generar el fichero trec escritor: " + ex.getMessage());
        }

        for (RespuestaTrec respuesta : respuestas) {
            try {
                escritor.write(respuesta.toString() + "\n");
            } catch (IOException ex) {
                System.out.println("Error al generar el fichero trec bucle for: " + ex.getMessage());
            }
        }

        try {
            escritor.close();
        } catch (IOException ex) {
            System.out.println("Error al generar el fichero trece escritor cerrar: " + ex.getMessage());
        }

       return this.evaluador();
    }

    private String evaluador() {
        String exePath = filePath + "treceval.exe";
        String relPath = filePath + "MED_rel.TREC";
        String solrPath = filePath + "trec_solr_file";

        File output = new File(filePath + "evaluation.txt");

        ProcessBuilder evaluation = new ProcessBuilder();
        evaluation.command(exePath, relPath, solrPath);
        evaluation.redirectOutput(output);
        try {
            Process process = evaluation.start();
            int exitCode = process.waitFor();
            System.out.println("Process exited with code: " + exitCode);

            /*  BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            System.out.println("Process exited with code: " + exitCode);*/
        } catch (IOException ex) {
            System.out.println("Error IO en el proceso de evaluacion: " + ex.getMessage());
        } catch (InterruptedException ex) {
            System.out.println("Error de interrupcion en el proceso de evaluacion: " + ex.getMessage());
        }
        return output.getAbsolutePath();
    }

}
