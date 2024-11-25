/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

/**
 *
 * @author israe
 */
public class Consultas {

    private SolrClient client;

    public Consultas(SolrClient client) {
        this.client = client;
    }

    public void consultar() throws IOException {
        SolrQuery query = new SolrQuery();
        Parseador p = new Parseador(client);
        ArrayList<String> consultas = p.parsearConsultas(1, 5);
        System.out.println("Consulta a lanzar: "+consultas.get(0));
        query.setQuery("texto:"+consultas.get(0)).setRows(5).setFields("* score").addSort("score", SolrQuery.ORDER.desc);
        QueryResponse rsp = null;
        try {
            rsp = client.query("MedColection", query);
            System.out.println("Query lanzada");
        } catch (SolrServerException ex) {
            System.out.println("Error server en query: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error IO en query: " + ex.getMessage());
        }
        SolrDocumentList docs = rsp.getResults();
        System.out.println("***********************************\nMostramos los documentos recogidos");
        for (int i = 0; i < docs.size(); ++i) {
            System.out.println(docs.get(i));
        }
    }

}
