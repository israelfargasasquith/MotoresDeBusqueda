/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.script.ScriptException;

/**
 *
 * @author israe
 */
public class ControladorJython {
    
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
