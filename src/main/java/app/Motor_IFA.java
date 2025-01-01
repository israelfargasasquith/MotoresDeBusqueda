/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author israe
 */
public class Motor_IFA {

    //TODO Organizar las clases consultas y parser
    public static void main(String[] args) throws IOException {

        String query = "\"63 year old woman with history of CAD presented to ER\"";

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
        
        
        
//        try {
//            StringWriter writer = new StringWriter();
//            ScriptContext context = new SimpleScriptContext();
//            context.setWriter(writer);
//
//            ScriptEngineManager manager = new ScriptEngineManager();
//            ScriptEngine engine = manager.getEngineByName("python");
//            engine.eval(new FileReader("C:\\Users\\israe\\Documents\\Ner Model\\python example.py  hola"), context);
//            System.out.println("Output: " + writer.toString().trim());
//        } catch (IOException | ScriptException ex) {
//            System.out.println("Error: " + ex.getMessage());
//        }
//ControladorPrincipal cp = new ControladorPrincipal();
        //cp.menuPrincipal();
        //cp.lanzarApp();
    }
//}
