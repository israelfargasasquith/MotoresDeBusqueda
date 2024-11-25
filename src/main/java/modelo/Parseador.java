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
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
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

    /**
     * *
     * TODO: Añadir esta funcionalidad como vista extra en el GUI Cambiar el
     * String fileName por el fichero a procesar en si mismo *
     */
    public void seleccionarFichero() {
        JFileChooser tmp = new JFileChooser();
        tmp.setCurrentDirectory(new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "corpusData"));
        int aproved = tmp.showDialog(null, JFileChooser.APPROVE_SELECTION);
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

    public void borrar(String query) throws SolrServerException, IOException { //TODO: Mover este metodo a Consultas.class

        SolrClient client = new HttpSolrClient.Builder("http://localhost:8983/solr/MedColection").build();
        client.deleteByQuery(query);
        client.commit();
        System.out.println("Todos los documentos borrados");
    }

    public ArrayList<String> parsearConsultas(int nConsultas, int nPalabras) throws FileNotFoundException, IOException {
        ArrayList<String> consultas = new ArrayList<>();
        this.seleccionarFichero();
        if (parsingFile != null) { //TODO Hacer refactor de este metodo y el siguiente
            BufferedReader br = new BufferedReader(new FileReader(parsingFile));
            String line;
            StringBuilder wholeQuery = new StringBuilder();
            int queryCount = 0;
            while ((line = br.readLine()) != null && queryCount < nConsultas) {
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
                        doc.addField(".I", line);
                        doc.addField("texto", wholePar);
                        this.addDocument(doc);
                        wholePar.setLength(0);
                    }
                    documentCount++;
                    System.out.println("***********************************************\nStarted writing Documento" + (documentCount) + ".txt");
                    continue;
                } else if (line.startsWith(".W")) {
                    continue;
                } else {
                    System.out.println(line);
                    wholePar.append(line.strip()).append(" ");
                }
            }

        }

    }

}
