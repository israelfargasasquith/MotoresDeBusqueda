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
    private Consultas consul;

    public ControladorPrincipal() {
        this.keyboard = new Scanner(System.in);
        SolrClient client = new HttpSolrClient.Builder("http://localhost:8983/solr/").build();
        parser = new Parseador(client);
        consul = new Consultas(client);

    }

    public void menuPrincipal() {
        int opc = -1;
        do {
            System.out.println("Saludos, bienvenido al motor IFA, Que desea?");
            System.out.println("1.- Borrar el core MedColection");
            System.out.println("2.- Parsear el corpus Med (se creara el core MedColection a partir de Med.all o MedcasiAll)");
            System.out.println("3.- Recorrer el fichero de consultas");
            System.out.println("0.- Salir");
            opc = keyboard.nextInt();
            switch (opc) {
                case 0:

                    break;
                case 1: {
                    try {
                        consul.borrar("*");
                    } catch (SolrServerException ex) {
                        System.out.println("Error SolrServer al llamar al metodo borrar: " + ex.getMessage());
                    } catch (IOException ex) {
                        System.out.println("Error IO al llamar al metodo borrar: " + ex.getMessage());
                    }
                }
                break;

                case 2: {
                    try {
                        parser.parsear();
                    } catch (Exception ex) {
                        System.out.println("Error al llamar al metodo parsear: " + ex.getMessage());
                    }
                }

                break;

                case 3:
                    int nPalabras,
                     nConsultas;
                    System.out.println("Cuantas consultas quiere procesar?");
                    nConsultas = keyboard.nextInt();
                    System.out.println("Cuantas palabras por consulta (0 para toda la frase)?");
                    nPalabras = keyboard.nextInt();
                     {
                        try {
                            consul.consultar(nConsultas, nPalabras);
                        } catch (IOException ex) {
                            System.out.println("Error al llamar al metodo consultar: " + ex.getMessage());
                        }
                    }
                    break;

                default:
                    System.out.println("No existe esa opcion");
            }
        } while (opc != 0);
    }

}
