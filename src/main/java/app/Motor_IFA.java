/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package app;

import controlador.ControladorPrincipal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author israe
 */
public class Motor_IFA {

    //TODO Organizar las clases consultas y parser
    public static void main(String[] args) throws IOException {
        ControladorPrincipal cp = new ControladorPrincipal();
        cp.menuPrincipal();
        System.exit(0);
    }
}
