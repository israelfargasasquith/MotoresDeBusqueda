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

    public ArrayList<RespuestaTrec> consultasTrec() throws IOException {
        ArrayList<SolrQuery> queries = new ArrayList<>();
        ArrayList<RespuestaTrec> respuestasList = new ArrayList<>();
        Parseador p = new Parseador(client);
        ArrayList<String> consultas = p.parsearConsultas(30, 5);

        for (String consulta : consultas) {
            queries.add(new SolrQuery().setQuery("texto:" + consulta).setRows(1033).setFields("I,score").addSort("score", SolrQuery.ORDER.desc));
        }

        ArrayList<QueryResponse> rsp = new ArrayList<>();
        QueryResponse tmp = null;

        try {

            for (SolrQuery query : queries) {
                tmp = client.query("MedColection", query);
                rsp.add(tmp);
            }

        } catch (SolrServerException ex) {
            System.out.println("Error server en query: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error IO en query: " + ex.getMessage());
        }
        SolrDocumentList docs = null;
        RespuestaTrec respuesta = null;
        int nConsulta = 1;
        int nRanking = 0;
        for (QueryResponse queryResponse : rsp) {
            docs = queryResponse.getResults();
            System.out.println("***********\nMostramos los documentos recogidos y los anadimos al array para trec\n***********");
            System.out.println("NumFound: " + docs.getNumFound());
            for (SolrDocument doc : docs) {
                //System.out.println(doc.toString());
                //     Consulta Q0  documento   ranking score     EQUIPO is the format of the file
                //i.e: 1        Q0  3392        0       0.017277  ETSI
                String iTmp = doc.get("I").toString().substring(1, doc.get("I").toString().length() - 1);
                respuesta = new RespuestaTrec("" + nConsulta, iTmp, "" + nRanking, doc.get("score").toString());
                respuestasList.add(respuesta);
                nRanking++;
            }
            nConsulta++;
            nRanking=0;
        }

        return respuestasList;
    }

    public void consultar(int nConsultas, int nPalabras, int nFilas) throws IOException {
        ArrayList<SolrQuery> queries = new ArrayList<>();
        Parseador p = new Parseador(client);
        ArrayList<String> consultas = p.parsearConsultas(nConsultas, nPalabras);
        for (String consulta : consultas) {

            queries.add(new SolrQuery().setQuery("texto:" + consultas.get(0)).setRows(nFilas).setFields("I,score").addSort("score", SolrQuery.ORDER.desc)); //añadir campo texto despues
        }

        ArrayList<QueryResponse> rsp = new ArrayList<>();
        QueryResponse tmp = null;

        try {
            for (SolrQuery query : queries) {
                tmp = client.query("MedColection", query);
                rsp.add(tmp);
                System.out.println("**************************************" + "\nQuery lanzada: " + query.getQuery() + "\n**************************************");
            }

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
