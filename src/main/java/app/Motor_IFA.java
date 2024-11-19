/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package app;

import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Parseador;

/**
 *
 * @author israe
 */
public class Motor_IFA {

    public static void main(String[] args) {
        Parseador parser = new Parseador();
        parser.seleccionarFichero();
        try {
            parser.parsear();
        } catch (Exception ex) {
            System.out.println("Error al parsear: "+ex.getMessage());
            System.exit(1);
        }
    }
}
