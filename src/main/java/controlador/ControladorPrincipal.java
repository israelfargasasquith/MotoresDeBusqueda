/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import modelo.Consultas;
import modelo.GeneradorTrec_File;
import modelo.Parseador;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import vista.VistaModalAjustesCorpus;
import vista.VistaModalAjustesQuery;
import vista.VistaModalEvaluacion;
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
    // private boolean borrado;

    private VistaPrincipal vistaPrincipal;
    private int nPalabras;
    private int nFilas;

    private VistaModalAjustesQuery vistaAjustesQueries;
    private VistaModalAjustesCorpus vistaAjustesCorpus;
    private VistaModalEvaluacion vistaEvaluacion;

    public ControladorPrincipal() {
        this.keyboard = new Scanner(System.in);
        // lanzarSolr(); // WIP
        SolrClient client = new HttpSolrClient.Builder("http://localhost:8983/solr/").build();
        parser = new Parseador(client);
        consultas = new Consultas(client);
        generadorFicheroTrec = new GeneradorTrec_File();

        vistaPrincipal = new VistaPrincipal();
        vistaPrincipal.scrollResultados.getViewport().setView(vistaPrincipal.textResultados);
        vistaPrincipal.textResultados.setEditable(false);
        vistaPrincipal.scrollResultados.createVerticalScrollBar();
        vistaPrincipal.setVisible(false);
        vistaPrincipal.setLocationRelativeTo(null);

        vistaAjustesQueries = new VistaModalAjustesQuery(vistaPrincipal, true);
        vistaAjustesQueries.setVisible(false);
        vistaAjustesQueries.setLocationRelativeTo(null);

        vistaAjustesCorpus = new VistaModalAjustesCorpus(vistaPrincipal, true);
        vistaAjustesCorpus.setVisible(false);
        vistaAjustesCorpus.setLocationRelativeTo(null);
        vistaAjustesCorpus.labelCorpus.setText("MedColection");

        vistaEvaluacion = new VistaModalEvaluacion(vistaPrincipal, true);
        vistaEvaluacion.setVisible(false);
        vistaEvaluacion.setLocationRelativeTo(null);

        addActionListeners();

        nPalabras = 0;
        nFilas = 15;
    }

    /* private void lanzarSolr() {
        String pathSolr = "C:" + File.separator + "Users" + File.separator + "israe" + File.separator + "Desktop" + File.separator + "solr-8.11.4" + File.separator + "bin" + File.separator + "solr";
        String commandArrancar = "start";

        ProcessBuilder lanzador = new ProcessBuilder();

        lanzador.command(pathSolr, commandArrancar);

        try {
            lanzador.start();
        } catch (IOException ex) {
            System.out.println("Error al lanzar Solrj: " + ex.getMessage());
        }
    } //No funcional aun */
    public void lanzarApp() {
        vistaPrincipal.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SimpleAttributeSet boldSet = new SimpleAttributeSet();
        StyleConstants.setBold(boldSet, true);
        StyleConstants.setFontSize(boldSet, 13);
        StyleConstants.setUnderline(boldSet, true);

        String consulta = "";
        switch (e.getActionCommand()) {
            case "Buscar":
                vistaPrincipal.textResultados.setText("");
                consulta = vistaPrincipal.fieldBuscador.getText();
                SolrDocumentList docs = consultas.buscar(consulta, nFilas, nPalabras);
                StyledDocument docMostrar = vistaPrincipal.textResultados.getStyledDocument();
                for (SolrDocument doc : docs) {
                    try {
                        docMostrar.insertString(docMostrar.getLength(), "I: " + doc.get("I").toString() + " Score: " + doc.get("score").toString() + "\n", boldSet);
                    } catch (BadLocationException ex) {
                        System.out.println("Error: " + ex.getLocalizedMessage());
                    }
                    try {
                        this.formatear(doc.get("texto").toString(), docMostrar);
                    } catch (BadLocationException ex) {
                        System.out.println("Error: " + ex.getLocalizedMessage());
                    }
                }
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        vistaPrincipal.scrollResultados.getVerticalScrollBar().setValue(0);
                    }
                }
                );

                break;
            case "Mostrar Opciones Queries":
                vistaAjustesQueries.labelNFilas.setText(
                        "" + nFilas);
                vistaAjustesQueries.labelNPalabras.setText(
                        "" + nPalabras);
                vistaAjustesQueries.setVisible(
                        true);
                vistaAjustesQueries.setModal(
                        true);

                break;
            case "ActualizarNFilas":
                nFilas = Integer.parseInt(vistaAjustesQueries.textNFilas.getText());

                vistaAjustesQueries.labelNFilas.setText(
                        "" + nFilas);
                break;
            case "ActualizarNPalabras":
                nPalabras = Integer.parseInt(vistaAjustesQueries.textNPalabras.getText());

                vistaAjustesQueries.labelNPalabras.setText(
                        "" + nPalabras);
                break;
            case "Mostrar Opciones Corpus":
                vistaAjustesCorpus.setVisible(
                        true);
                vistaAjustesCorpus.setModal(
                        true);
                break;
            case "BuscarFichero":
                parser.seleccionarFicheroCorpus();

                break;
            case "BorrarCore":
                try {
                    consultas.borrar("*");
                } catch (SolrServerException ex) {
                    System.out.println("Error al borrar el core: " + ex.getMessage());
                } catch (IOException ex) {
                    System.out.println("Error IO al borrar el core: " + ex.getMessage());
                }

                break;

            case "ParsearCore":
                try {
                    parser.parsearCorpus();
                } catch (Exception ex) {
                    System.out.println("Error al parsear el core: " + ex.getMessage());
                }

                break;

            case "MedColecction":
                System.out.println(
                        "Seleccionado el MedColection");
                vistaAjustesCorpus.labelCorpus.setText(
                        "MedColection");
                parser.setCore(
                        "MedColection");
                break;

            case "EnrichedMed":
                System.out.println(
                        "Seleccionado el enrichedMed");
                vistaAjustesCorpus.labelCorpus.setText(
                        "enrichedMed");
                parser.setCore(
                        "enrichedMed");
                break;

            case "Mostrar Opciones Evaluacion":
                vistaEvaluacion.setVisible(
                        true);
                vistaEvaluacion.setModal(
                        true);
                break;

            case "GenerarEvaluacion":
                String path = null;

                try {
                    path = generadorFicheroTrec.generarFicheroConsultas(consultas.consultasTrec(nPalabras, nFilas));
                } catch (IOException ex) {
                    System.out.println("Error al generar el fichero de evaluacion: " + ex.getMessage());
                }

                String content = null;

                try {
                    content = Files.readString(Paths.get(path));
                } catch (IOException ex) {
                    System.out.println("Error al generar el fichero de evaluacion visto por pantalla: " + ex.getMessage());
                }

                JTextArea textArea = new JTextArea(content);

                textArea.setEditable(
                        false);
                JScrollPane scrollPane = new JScrollPane(textArea);

                JOptionPane.showMessageDialog(
                        null, scrollPane, "Evaluation Results", JOptionPane.INFORMATION_MESSAGE);

                break;

            default:
                System.out.println(
                        "Error al añadir los ActionListeners");
        }
    }

    private void formatear(String textoCompleto, StyledDocument doc) throws BadLocationException {

        SimpleAttributeSet plainSet = new SimpleAttributeSet();
        StyleConstants.setFontSize(plainSet, 12);
        String[] splitted = textoCompleto.split("\\s+");
        int n = 0;
        for (String string : splitted) {
            if (n > 13) {
                doc.insertString(doc.getLength(), "\n", plainSet);
                n = 0;
            } else {
                doc.insertString(doc.getLength(), " " + string, plainSet);
                n++;
            }
        }
        doc.insertString(doc.getLength(), "\n", plainSet);

    }

    private void addActionListeners() {
        vistaPrincipal.botonBuscar.addActionListener(this);
        vistaPrincipal.menuOpcionesCorpus.addActionListener(this);
        vistaPrincipal.menuOpcionesQueries.addActionListener(this);
        vistaPrincipal.menuEvaluacion.addActionListener(this);

        vistaAjustesQueries.actualizarNFilas.addActionListener(this);
        vistaAjustesQueries.actualizarNPalabras.addActionListener(this);

        vistaAjustesCorpus.buttomBorrarCore.addActionListener(this);
        vistaAjustesCorpus.buttomBuscarFichero.addActionListener(this);
        vistaAjustesCorpus.buttomParsearCore.addActionListener(this);
        vistaAjustesCorpus.radioEnriched.addActionListener(this);
        vistaAjustesCorpus.radioMedColecction.addActionListener(this);

        vistaEvaluacion.buttomEvaluar.addActionListener(this);

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
