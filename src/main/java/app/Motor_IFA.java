/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author israe
 */
public class Motor_IFA {

    public static void main(String[] args) throws IOException {

        String query = "\"63 year old woman with history of CAD presented to ER\"";

        String pythonInterpreter = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "resources" + File.separator + "env" + File.separator + "Scripts" + File.separator + "python.exe"; // Adjust path for your OS
        String enrichScript = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "resources" + File.separator + "queryEnricher.py";
        String pipTest = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "resources" + File.separator + "pip list";
        //ProcessBuilder pb = new ProcessBuilder("python", enrichScript,query);
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
    }

}
