/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import java.io.File;
import java.io.IOException;
import modelo.Compresor;
import modelo.Descompresor;

/**
 *
 * @author Willi
 */

public class ControladorHuffman {

    private File carpetaData = new File("data");
    private Compresor compresor = new Compresor();
    private Descompresor descompresor = new Descompresor();

    // La Vista lo usa para abrir el JFileChooser en esta carpeta
    public File getCarpetaData() {
        crearCarpetaData();
        return carpetaData;
    }

    private void crearCarpetaData() {
        if (!carpetaData.exists()) {
            carpetaData.mkdirs();
        }
    }

    public String comprimir(File archivo) throws IOException {
        validarArchivo(archivo);
        crearCarpetaData();

        File destino = new File(carpetaData, archivo.getName() + ".pkz");
        compresor.comprimir(archivo, destino);


        long original = archivo.length();
        long comprimido = destino.length();
        double factor = 0;
        if (original > 0) {
            factor = (1.0 - (double) comprimido / original) * 100;
        }

        return "Archivo comprimido: " + destino.getPath()
                + "\nTamaño original: " + original + " bytes"
                + "\nTamaño comprimido: " + comprimido + " bytes"
                + "\nFactor de compresión: " + String.format("%.1f", factor) + "%";
    }

    public String descomprimir(File archivo) throws IOException {
        validarArchivo(archivo);

        String nombre = archivo.getName();
        if (!nombre.toLowerCase().endsWith(".pkz")) {
            throw new IOException("El archivo seleccionado no tiene extensión .pkz");
        }
        crearCarpetaData();

        // "anita.txt.pkz" -> "descomprimido_anita.txt"
        String nombreOriginal = nombre.substring(0, nombre.length() - 4);
        File destino = new File(carpetaData, "descomprimido_" + nombreOriginal);
        descompresor.descomprimir(archivo, destino);

        return "Archivo descomprimido: " + destino.getPath()
                + "\nTamaño: " + destino.length() + " bytes";
    }

    private void validarArchivo(File archivo) throws IOException {

        if (archivo == null || !archivo.isFile()) {
            throw new IOException("Debe seleccionar un archivo válido");
        }
    }
}