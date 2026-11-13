/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Random;
import modelo.Compresor;
import modelo.Descompresor;
/**
 *
 * @author Willi
 */
import controlador.ControladorHuffman;
import vista.VentanaPrincipal;

public class Prueba {
    public static void main(String[] args) {
        new VentanaPrincipal(new ControladorHuffman()).setVisible(true);
    }
}