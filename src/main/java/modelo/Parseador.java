/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;

/**
 *
 * @author israe
 */
public class Parseador {

    private File parsingFile;
    private SolrClient client;

    public Parseador(SolrClient client) {
        parsingFile = null;
        this.client = client;
    }

    public void seleccionarFichero() {
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);
        frame.setVisible(true); //solamente para que el filechoser salga por encima de la consola...
        frame.setLocationRelativeTo(null);
        JFileChooser tmp = new JFileChooser();
        tmp.setCurrentDirectory(new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "corpusData"));
        int aproved = tmp.showDialog(frame, JFileChooser.APPROVE_SELECTION);
        frame.dispose();
        if (aproved == JFileChooser.APPROVE_OPTION) {
            parsingFile = tmp.getSelectedFile();
        } else {
            System.out.println("Pues no hago nada.");
        }

    }

    private void addDocument(SolrInputDocument doc) {

        try {
            client.add("MedColection", doc);
            client.commit("MedColection");
        } catch (SolrServerException ex) {
            System.out.println("Error solrServer: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error IO: " + ex.getMessage());
        }
    }

    public ArrayList<String> parsearConsultas(int nConsultas, int nPalabras) throws FileNotFoundException, IOException {
        ArrayList<String> consultas = new ArrayList<>();
        this.seleccionarFichero();
        if (parsingFile != null) { //TODO Hacer refactor de este metodo y el siguiente
            BufferedReader br = new BufferedReader(new FileReader(parsingFile));
            String line;
            StringBuilder wholeQuery = new StringBuilder();
            int queryCount = 0;
            while ((line = br.readLine()) != null && queryCount < nConsultas) { //Hay que añadir alguna forma de controlar que se hagan todas las queries
                if (line.startsWith(".I")) {
                    if (wholeQuery.length() > 0) {
                        if (nPalabras <= 0) { //toda la palabra
                            consultas.add(wholeQuery.toString());
                            wholeQuery.setLength(0);
                        } else {
                            String[] splitted = wholeQuery.toString().split("\\s+");
                            int i = 0;
                            String queryString = "";
                            for (String string : splitted) {
                                if (i >= nPalabras) {
                                    break;
                                }
                                if (i == 0) {
                                    queryString = string;
                                } else {
                                    queryString += "+" + string;
                                }
                                i++;
                            }
                            consultas.add(queryString);
                        }
                        queryCount++;
                    }
                    System.out.println("***********************************************\nStarted writing Query" + (queryCount));
                } else if (line.startsWith(".W")) {
                } else {
                    System.out.println(line);
                    wholeQuery.append(line.strip()).append(" ");
                }
            }
        }
        return consultas;
    }

    public void parsear() throws Exception {
        this.seleccionarFichero();
        if (parsingFile != null) {
            BufferedReader br = new BufferedReader(new FileReader(parsingFile));
            String line;
            StringBuilder wholePar = new StringBuilder();
            int documentCount = 0;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(".I")) {
                    if (wholePar.length() > 0) {
                        SolrInputDocument doc = new SolrInputDocument();
                        doc.addField("id", UUID.randomUUID().toString());
                        doc.addField("I", line);
                        doc.addField("texto", wholePar);
                        this.addDocument(doc);
                        wholePar.setLength(0);
                    }
                    documentCount++;
                    System.out.println("***********************************************\nStarted writing Documento" + (documentCount) + ".txt");
                } else if (line.startsWith(".W")) {
                } else {
                    System.out.println(line);
                    wholePar.append(line.strip()).append(" ");
                }
            }
            if (wholePar.length() > 0) { //Ultimo documento de la lista
                SolrInputDocument doc = new SolrInputDocument();
                doc.addField("id", UUID.randomUUID().toString());
                doc.addField("I", "Documento" + documentCount);
                doc.addField("texto", wholePar.toString());
                this.addDocument(doc);
                System.out.println("***********************************************\nFinished writing Documento" + documentCount + ".txt");
            }

        }

    }

}
