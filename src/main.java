/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import controlador.ControladorHuffman;
import javax.swing.SwingUtilities;
import vista.VentanaPrincipal;
/**
 *
 * @author Willi
 */


public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ControladorHuffman controlador = new ControladorHuffman();
            VentanaPrincipal ventana = new VentanaPrincipal(controlador);
            ventana.setVisible(true);
        });
    }
}