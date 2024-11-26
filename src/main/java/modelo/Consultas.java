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
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
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

    public void borrar(String query) throws SolrServerException, IOException {

        SolrClient client = new HttpSolrClient.Builder("http://localhost:8983/solr/MedColection").build();
        client.deleteByQuery(query);
        client.commit();
        System.out.println("Todos los documentos borrados");
    }

    public void consultar(int nConsultas, int nPalabras) throws IOException {
        ArrayList<SolrQuery> queries = new ArrayList<>();
        Parseador p = new Parseador(client);
        ArrayList<String> consultas = p.parsearConsultas(nConsultas, nPalabras);
        for (String consulta : consultas) {

            queries.add(new SolrQuery().setQuery("texto:" + consultas.get(0)).setRows(5).setFields("I,score,texto").addSort("score", SolrQuery.ORDER.desc));
        }

        ArrayList<QueryResponse> rsp = new ArrayList<>();
        QueryResponse tmp = null;

        try {
            for (SolrQuery query : queries) {
                tmp = client.query("MedColection", query);
                rsp.add(tmp);
            }
            System.out.println("Query lanzada");
        } catch (SolrServerException ex) {
            System.out.println("Error server en query: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error IO en query: " + ex.getMessage());
        }
        SolrDocumentList docs = null;
        for (QueryResponse queryResponse : rsp) {
            docs = queryResponse.getResults();
            System.out.println("***********************************\nMostramos los documentos recogidos");
            for (SolrDocument doc : docs) {
                System.out.println(doc);
            }

        }
    }

}
