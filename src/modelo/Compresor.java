/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
/**
 *
 * @author Willi
 */


public class Compresor {

    public void comprimir(File origen, File destino) throws IOException {
        byte[] datos = Files.readAllBytes(origen.toPath());

        OutputStream salida = new BufferedOutputStream(new FileOutputStream(destino));

        // Archivo vacío: el .pkz es solo un byte con 0 nodos internos
        if (datos.length == 0) {
            salida.write(0);
            salida.close();
            return;
        }

        ArbolHuffman arbol = new ArbolHuffman();
        arbol.contarFrecuencias(datos);
        arbol.construirArbol();
        arbol.generarCodigos();
        String[] codigos = arbol.getCodigos();

        // Convertir cada byte a su código y empaquetar los bits de 8 en 8
        ByteArrayOutputStream cuerpo = new ByteArrayOutputStream();

        int actual = 0;          // byte que se está armando
        int bitsEnActual = 0;    // cuántos bits lleva

        for (byte b : datos) {
            String codigo = codigos[b & 0xFF];
            for (int i = 0; i < codigo.length(); i++) {
                actual = actual << 1;
                if (codigo.charAt(i) == '1') {
                    actual = actual | 1;
                }
                bitsEnActual++;
                if (bitsEnActual == 8) {
                    cuerpo.write(actual);
                    actual = 0;
                    bitsEnActual = 0;
                }
            }
        }

        // Último byte incompleto: se rellena con ceros a la derecha
        int bitsSignificativos = 8;
        if (bitsEnActual > 0) {
            bitsSignificativos = bitsEnActual;
            actual = actual << (8 - bitsEnActual);
            cuerpo.write(actual);
        }

        // Estructura del .pkz
        salida.write(arbol.contarNodosInternos());   // 1. nodos internos
        salida.write(arbol.serializarArbol());       // 2. árbol
        salida.write(bitsSignificativos);            // 3. bits del último byte
        salida.write(cuerpo.toByteArray());          // 4. datos comprimidos

        salida.close();
    }
}