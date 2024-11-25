/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package app;

import modelo.Consultas;
import modelo.Parseador;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

/**
 *
 * @author israe
 */
public class Motor_IFA {

    public static void main(String[] args) {
        SolrClient client =  new HttpSolrClient.Builder("http://localhost:8983/solr").build();
        Parseador parser = new Parseador(client);
        Consultas consultas = new Consultas(client);
        //parser.seleccionarFichero(); //MEDcasiALL son una pequeña parte para la prueba de indexar 10 solo
        try {
            consultas.consultar();
            
            //parser.borrar("*");
            //parser.parsear();
        } catch (Exception ex) {
            System.out.println("Error al parsear: "+ex.getMessage());
            System.exit(1);
        }
    }
}
