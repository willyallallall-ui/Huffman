/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.ControladorHuffman;
import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author Willi
 */


public class VentanaPrincipal extends JFrame {

    private ControladorHuffman controlador;
    private JButton botonComprimir;
    private JButton botonDescomprimir;

    public VentanaPrincipal(ControladorHuffman controlador) {
        this.controlador = controlador;

        setTitle("Compresor Huffman (.pkz)");
        setSize(430, 140);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);      // centrada en la pantalla
        setLayout(new BorderLayout());

        JLabel etiqueta = new JLabel("Comprima o descomprima cualquier tipo de archivo", JLabel.CENTER);
        etiqueta.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));

        botonComprimir = new JButton("Comprimir");
        botonDescomprimir = new JButton("Descomprimir");
        botonComprimir.addActionListener(e -> comprimir());
        botonDescomprimir.addActionListener(e -> descomprimir());

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonComprimir);
        panelBotones.add(botonDescomprimir);

        add(etiqueta, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
    }

    private void comprimir() {
        JFileChooser selector = new JFileChooser(controlador.getCarpetaData().getAbsoluteFile());
        selector.setDialogTitle("Seleccione el archivo a comprimir");

        if (selector.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                String mensaje = controlador.comprimir(selector.getSelectedFile());
                JOptionPane.showMessageDialog(this, mensaje, "Compresión", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void descomprimir() {
        JFileChooser selector = new JFileChooser(controlador.getCarpetaData().getAbsoluteFile());
        selector.setDialogTitle("Seleccione el archivo .pkz a descomprimir");
        selector.setFileFilter(new FileNameExtensionFilter("Archivos comprimidos (*.pkz)", "pkz"));

        if (selector.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                String mensaje = controlador.descomprimir(selector.getSelectedFile());
                JOptionPane.showMessageDialog(this, mensaje, "Descompresión", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}