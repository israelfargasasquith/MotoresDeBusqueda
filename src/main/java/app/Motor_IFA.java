/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package app;

import modelo.Parseador;

/**
 *
 * @author israe
 */
public class Motor_IFA {

    public static void main(String[] args) {
        Parseador parser = new Parseador();
        parser.seleccionarFichero(); //MEDcasiALL son una pequeña parte para la prueba de indexar 10 solo
        try {
            parser.parsear();
            //parser.borrar(""); //¿sera una opcion en el menu?
        } catch (Exception ex) {
            System.out.println("Error al parsear: "+ex.getMessage());
            System.exit(1);
        }
    }
}
