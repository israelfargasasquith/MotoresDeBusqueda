/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Consultas;
import modelo.GeneradorTrec_File;
import modelo.Parseador;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

/**
 *
 * @author israe
 */
public class ControladorPrincipal {

    private final Scanner keyboard;
    private Parseador parser;
    private Consultas consultas;
    private GeneradorTrec_File generadorFicheroTrec;
    private boolean borrado;

    public ControladorPrincipal() {
        this.keyboard = new Scanner(System.in);
        SolrClient client = new HttpSolrClient.Builder("http://localhost:8983/solr/").build();
        parser = new Parseador(client);
        consultas = new Consultas(client);
        generadorFicheroTrec = new GeneradorTrec_File();
    }

    public void menuPrincipal() {
        int opc = -1;
        do {
            System.out.println("Saludos, bienvenido al motor IFA, Que desea?");
            System.out.println("1.- Borrar el core MedColection");
            System.out.println("2.- Parsear el corpus Med (se creara el core MedColection a partir de Med.ALL)");
            System.out.println("3.- Recorrer el fichero de consultas");
            System.out.println("4.- Seleccionar el fichero de consultas (por defecto MED.QRY");
            System.out.println("5.- Seleccionar el fichero para formar el corpus (por defecto MED.ALL)");
            System.out.println("6.- Generar fichero trec_solr_file");

            System.out.println("0.- Salir");
            opc = keyboard.nextInt();
            switch (opc) {
                case 0:

                    break;
                case 1:
                    try {
                        consultas.borrar("*");
                    } catch (SolrServerException ex) {
                        System.out.println("Error SolrServer al llamar al metodo borrar: " + ex.getMessage());
                    } catch (IOException ex) {
                        System.out.println("Error IO al llamar al metodo borrar: " + ex.getMessage());
                    }

                    break;

                case 2:
                    try {
                        parser.parsearCorpus();
                    } catch (Exception ex) {
                        System.out.println("Error al llamar al metodo parsear: " + ex.getMessage());
                    }

                    break;

                case 3:
                    int nPalabras,
                     nFilas,
                     nConsultas;
                    System.out.println("Cuantas consultas quiere procesar?");
                    nConsultas = keyboard.nextInt();
                    System.out.println("Cuantas palabras por consulta (0 para toda la frase)?");
                    nPalabras = keyboard.nextInt();
                    System.out.println("Cuantas filas de resultados quiere mostrar (0 para todas)?");
                    nFilas = keyboard.nextInt();

                     {
                        try {
                            consultas.consultar(nConsultas, nPalabras, nFilas);
                        } catch (IOException ex) {
                            System.out.println("Error al llamar al metodo consultar: " + ex.getMessage());
                        }
                    }
                    break;

                case 4:
                    parser.seleccionarFicheroConsultas();
                    break;

                case 5:
                    parser.seleccionarFicheroCorpus();
                    break;
                case 6: {
                    try {
                        generadorFicheroTrec.generarFicheroConsultas(consultas.consultasTrec());
                    } catch (IOException ex) {
                        System.out.println("Error consultasTrec :" + ex.getMessage());
                    }
                }
                break;

                default:
                    System.out.println("No existe esa opcion");
            }
        } while (opc != 0);
    }

}
