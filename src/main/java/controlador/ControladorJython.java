/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.script.ScriptException;

/**
 *
 * @author israe
 */
public class ControladorJython {
    
    
    public String queryEnricher(String entrada) throws IOException{
        String query = "\"63 year old woman with history of CAD presented to ER\"";

        String pythonInterpreter = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "resources" + File.separator + "env" + File.separator + "Scripts" + File.separator + "python.exe"; // Adjust path for your OS
        String enrichScript = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "resources" + File.separator + "queryEnricher.py";
        String pipTest = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "resources" + File.separator + "pip list";
        ProcessBuilder pb = new ProcessBuilder(pythonInterpreter, enrichScript, query);
        Process process = pb.start();

        // Read output
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        System.out.println("Output: " + output.toString().trim());
        
        return output.toString().trim();
    }
    
    public void llamandoTest() throws FileNotFoundException, ScriptException, IOException{
           String query = "hola";

        // Call external Python script
        ProcessBuilder pb = new ProcessBuilder("python", 
                "C:\\Users\\israe\\Documents\\Ner Model\\queryEnricher.py", query);
        Process process = pb.start();

        // Read output
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        System.out.println("Output: " + output.toString().trim());
    }
    
}
