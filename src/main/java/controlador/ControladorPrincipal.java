/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import vista.VistaPrincipal;

/**
 *
 * @author israe
 */
public class ControladorPrincipal implements ActionListener {
    
    private final Scanner keyboard;
    private Parseador parser;
    private Consultas consultas;
    private GeneradorTrec_File generadorFicheroTrec;
    private boolean borrado;
    
    private VistaPrincipal vistaPrincipal;
    private int nPalabras;
    private int nFilas;
    
    public ControladorPrincipal() {
        this.keyboard = new Scanner(System.in);
        lanzarSolr();
        SolrClient client = new HttpSolrClient.Builder("http://localhost:8983/solr/").build();
        parser = new Parseador(client);
        consultas = new Consultas(client);
        generadorFicheroTrec = new GeneradorTrec_File();
        
        vistaPrincipal = new VistaPrincipal();
        vistaPrincipal.setVisible(false);
        vistaPrincipal.setLocationRelativeTo(null);
        addActionListeners();
        
        nPalabras = 0;
        nFilas = 15;
    }
    private void lanzarSolr(){
        String pathSolr = "C:File.separatorUsersFile.separatorisraeFile.separatorDesktopFile.separatorsolr-8.11.4File.separatorbin";
        String command = "";
        
        ProcessBuilder lanzador
        
    }
    public void lanzarApp() {
        vistaPrincipal.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String consulta = "";
        switch (e.getActionCommand()) {
            case "Buscar":
                consulta = vistaPrincipal.fieldBuscador.getText();
                consultas.buscar(consulta, nFilas, nPalabras);
                break;
            default:
                System.out.println("Error al añadir los ActionListeners");
        }
    }
    
    private void addActionListeners() {
        vistaPrincipal.botonBuscar.addActionListener(this);
        vistaPrincipal.menuOpcionesCorpus.addActionListener(this);
        vistaPrincipal.menuOpcionesQueries.addActionListener(this);
    }
    
    public void menuPrincipal() {
        int opc = -1;
        int nPalabras, nFilas, nConsultas;
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
                    System.out.println("Cuantas palabras por consulta (0 para toda la frase)?");
                    nPalabras = keyboard.nextInt();
                    System.out.println("Cuantas filas de resultados quiere mostrar (0 para todas)?");
                    nFilas = keyboard.nextInt();
                    try {
                        generadorFicheroTrec.generarFicheroConsultas(consultas.consultasTrec(nPalabras, nFilas));
                    } catch (IOException ex) {
                        System.out.println("Error consultasTrec :" + ex.getMessage());
                    }
                }
                break;
                
                default:
                    System.out.println("No existe esa opcion");
            }
        } while (opc != 0);
        System.exit(0);
    }
    
}
