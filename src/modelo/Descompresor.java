/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
/**
 *
 * @author Willi
 */



public class Descompresor {

    private int posicion;   // por dónde va leyendo el arreglo de bytes

    public void descomprimir(File origen, File destino) throws IOException {
        byte[] datos = Files.readAllBytes(origen.toPath());

        if (datos.length == 0) {
            throw new IOException("El archivo .pkz está vacío");
        }

        int nodosInternos = datos[0] & 0xFF;

        // .pkz de un archivo vacío: solo trae el 0
        if (nodosInternos == 0) {
            Files.write(destino.toPath(), new byte[0]);
            return;
        }

        // Cada nodo ocupa 2 bytes y hay (nodosInternos * 2 + 1) nodos
        int tamanoArbol = (nodosInternos * 2 + 1) * 2;
        if (datos.length < 1 + tamanoArbol + 2) {
            throw new IOException("El archivo no tiene un formato .pkz válido");
        }


        // 1. Reconstruir el árbol
        posicion = 1;
        NodoHuffman raiz;
        try {
            raiz = leerNodo(datos);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IOException("El archivo no tiene un formato .pkz válido");
        }
        if (posicion != 1 + tamanoArbol) {
            throw new IOException("El archivo no tiene un formato .pkz válido");
        }

        // 2. Bits significativos del último byte
        int bitsSignificativos = datos[posicion] & 0xFF;
        posicion++;
        if (bitsSignificativos < 1 || bitsSignificativos > 8) {
            throw new IOException("El archivo no tiene un formato .pkz válido");
        }

        // 3. Recorrer los bits y recuperar los bytes originales
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        NodoHuffman actual = raiz;

        for (int i = posicion; i < datos.length; i++) {
            int bitsEnEsteByte = 8;
            if (i == datos.length - 1) {
                bitsEnEsteByte = bitsSignificativos;   // el último byte puede venir incompleto
            }
            for (int j = 7; j > 7 - bitsEnEsteByte; j--) {
                int bit = (datos[i] >> j) & 1;
                if (bit == 0) {
                    actual = actual.getIzquierdo();

                } else {
                    actual = actual.getDerecho();
                }
                if (actual.esHoja()) {
                    salida.write(actual.getValor());
                    actual = raiz;
                }
            }
        }

        Files.write(destino.toPath(), salida.toByteArray());
    }

    // Lee un nodo en preorden: (1, valor) es hoja, (0, 0) es nodo interno
    private NodoHuffman leerNodo(byte[] datos) {
        int marca = datos[posicion] & 0xFF;
        int valor = datos[posicion + 1] & 0xFF;
        posicion += 2;

        if (marca == 1) {
            return new NodoHuffman(valor, 0);
        }
        NodoHuffman izquierdo = leerNodo(datos);
        NodoHuffman derecho = leerNodo(datos);
        return new NodoHuffman(izquierdo, derecho);
    }
}