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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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

    private File parsingCorpusFile;
    private File parsingQueryFile;
    private SolrClient client;
    private Map<String, String> tokenExcluidos;

    public Parseador(SolrClient client) {
        parsingCorpusFile = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "corpusData" + File.separator + "MED.ALL");
        parsingQueryFile = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "corpusData" + File.separator + "MED.QRY");
        this.client = client;
        this.tokenExcluidos = new HashMap<>();
        String punctuation = "(),.-_?¿¡!\\{}[]:;\"<>|/@#$%^&*~`";
        for (char c : punctuation.toCharArray()) {
            tokenExcluidos.put(String.valueOf(c), "-");
        }
    }

    public void seleccionarFicheroCorpus() {
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);
        frame.setVisible(true); //solamente para que el filechoser salga por encima de la consola...
        frame.setLocationRelativeTo(null);
        JFileChooser tmp = new JFileChooser();
        tmp.setCurrentDirectory(new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "corpusData"));
        int aproved = tmp.showDialog(frame, JFileChooser.APPROVE_SELECTION);
        frame.dispose();
        if (aproved == JFileChooser.APPROVE_OPTION) {
            parsingCorpusFile = tmp.getSelectedFile();
        } else {
            System.out.println("Pues no hago nada.");
        }

    }

    public void seleccionarFicheroConsultas() {
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);
        frame.setVisible(true); //solamente para que el filechoser salga por encima de la consola...
        frame.setLocationRelativeTo(null);
        JFileChooser tmp = new JFileChooser();
        tmp.setCurrentDirectory(new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "corpusData"));
        int aproved = tmp.showDialog(frame, JFileChooser.APPROVE_SELECTION);
        frame.dispose();
        if (aproved == JFileChooser.APPROVE_OPTION) {
            parsingQueryFile = tmp.getSelectedFile();
        } else {
            System.out.println("Pues no hago nada.");
        }

    }

    private void addDocument(ArrayList<SolrInputDocument> docList) {

        try {
            for (SolrInputDocument solrInputDocument : docList) {
                client.add("MedColection", solrInputDocument);
                client.commit("MedColection");
            }

        } catch (SolrServerException ex) {
            System.out.println("Error solrServer: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error IO: " + ex.getMessage());
        }
    }

    public ArrayList<String> parsearConsultas(int nConsultas, int nPalabras) throws FileNotFoundException, IOException {
        if (parsingQueryFile != null) {

            BufferedReader br = new BufferedReader(new FileReader(parsingQueryFile));
            String line;
            StringBuilder wholeQuery = new StringBuilder();
            int queryCount = 0;
            ArrayList<String> consultas = new ArrayList<>();
            String tmp;
            String appendable = "";
            while ((line = br.readLine()) != null && queryCount < nConsultas) {
                if (line.startsWith(".I")) {
                    if (wholeQuery.length() > 0) {
                        if (nPalabras <= 0) {
                            String splitWhole[] = wholeQuery.toString().split("\\s+");
                            String wholeTogether = splitWhole[0];
                            for (int i = 1; i < splitWhole.length; i++) {
                                wholeTogether += "+" + splitWhole[i];
                            }
                            consultas.add(wholeTogether);
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
                        wholeQuery.setLength(0);
                        queryCount++;
                    }
                } else if (line.startsWith(".W")) {
                } else {
                    tmp = line.strip();
                    for (char c : tmp.toCharArray()) {
                        if (this.tokenExcluidos.get(String.valueOf(c)) == null) {
                            appendable += String.valueOf(c);
                        }
                    }

                    wholeQuery.append(appendable);
                    appendable = "";
                }
            }

            if (wholeQuery.length() > 0 && queryCount < nConsultas) {
                if (nPalabras <= 0) {
                    String splitWhole[] = wholeQuery.toString().split("\\s+");
                    String wholeTogether = splitWhole[0];
                    for (int i=1;i<splitWhole.length;i++) {
                        wholeTogether += "+" + splitWhole[i];
                    }
                    consultas.add(wholeTogether);
                } else {
                    String[] splitted = wholeQuery.toString().split("\\s+");
                    StringBuilder queryString = new StringBuilder();

                    for (int i = 0; i < Math.min(nPalabras, splitted.length); i++) {
                        if (i > 0) {
                            queryString.append("+");
                        }
                        queryString.append(splitted[i]);
                    }
                    appendable = "";
                    tmp = queryString.toString();
                    for (char c : tmp.toCharArray()) {
                        if (this.tokenExcluidos.get(String.valueOf(c)) == null) {
                            appendable += String.valueOf(c);
                        }
                    }

                    consultas.add(appendable);
                }
            }

            br.close();
            return consultas;
        } else {
            return null;
        }

    }

    public void parsearCorpus() throws Exception {
        if (parsingCorpusFile != null) {
            BufferedReader br = new BufferedReader(new FileReader(parsingCorpusFile));
            String line;
            StringBuilder wholePar = new StringBuilder();
            ArrayList<SolrInputDocument> docList = new ArrayList<>();
            SolrInputDocument tmp;
            String ridoffI = null;
            int fixingI;
            int documentCount = 0;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(".I")) {
                    if (wholePar.length() > 0) {
                        tmp = new SolrInputDocument();
                        tmp.addField("id", UUID.randomUUID().toString());
                        ridoffI = line.substring(2).strip();
                        fixingI = Integer.parseInt(ridoffI);
                        fixingI--;
                        ridoffI = "" + fixingI;
                        tmp.addField("I", ridoffI);
                        tmp.addField("texto", wholePar.toString());
                        docList.add(tmp);
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
                doc.addField("I", "" + 1033);
                doc.addField("texto", wholePar.toString());
                docList.add(doc);
                System.out.println("***********************************************\nFinished writing Documento" + documentCount + ".txt");
            }
            this.addDocument(docList);
        }

    }

}
