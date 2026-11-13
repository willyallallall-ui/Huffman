/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.util.PriorityQueue;
import java.io.ByteArrayOutputStream;
/**
 *
 * @author Willi
 */
public class ArbolHuffman {

    private long[] frecuencias = new long[256];
    private NodoHuffman raiz;

    public void contarFrecuencias(byte[] datos) {
        frecuencias = new long[256];
        for (byte b : datos) {
            frecuencias[b & 0xFF]++;
        }
    }

    public void construirArbol() {
        PriorityQueue<NodoHuffman> cola = new PriorityQueue<>();
        int orden = 0;

        // Las hojas entran en orden ASCII (0 a 255)
        for (int i = 0; i < 256; i++) {
            if (frecuencias[i] > 0) {
                NodoHuffman hoja = new NodoHuffman(i, frecuencias[i]);
                hoja.setOrden(orden++);
                cola.add(hoja);
            }
        }

        if (cola.isEmpty()) {
            raiz = null;
            return;
        }

        // Un solo byte distinto: hoja de relleno con frecuencia 0
        if (cola.size() == 1) {
            int unico = cola.peek().getValor();
            NodoHuffman relleno = new NodoHuffman((unico + 1) % 256, 0);
            relleno.setOrden(orden++);
            cola.add(relleno);
        }

        // Cada nodo nuevo recibe un orden mayor que todos los anteriores
        while (cola.size() > 1) {
            NodoHuffman izquierdo = cola.poll();
            NodoHuffman derecho = cola.poll();
            NodoHuffman padre = new NodoHuffman(izquierdo, derecho);
            padre.setOrden(orden++);
            cola.add(padre);
        }

        raiz = cola.poll();
    }

    public long[] getFrecuencias() {
        return frecuencias;
    }

    public NodoHuffman getRaiz() {
        return raiz;
    }
     // codigos[i] = código en bits (como texto de 0 y 1) del byte con valor i
    private String[] codigos = new String[256];

    public void generarCodigos() {
        codigos = new String[256];
        if (raiz != null) {
            recorrer(raiz, "");
        }
    }

    private void recorrer(NodoHuffman nodo, String camino) {
        if (nodo.esHoja()) {
            codigos[nodo.getValor()] = camino;
        } else {
            recorrer(nodo.getIzquierdo(), camino + "0");
            recorrer(nodo.getDerecho(), camino + "1");
        }
    }

    public String[] getCodigos() {
        return codigos;
    }
        // Árbol en preorden, 2 bytes por nodo:
    // hoja -> (1, valor del byte)    nodo interno -> (0, 0)
    public byte[] serializarArbol() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        if (raiz != null) {
            escribirNodo(raiz, salida);
        }
        return salida.toByteArray();
    }

    private void escribirNodo(NodoHuffman nodo, ByteArrayOutputStream salida) {
        if (nodo.esHoja()) {
            salida.write(1);
            salida.write(nodo.getValor());
        } else {
            salida.write(0);
            salida.write(0);
            escribirNodo(nodo.getIzquierdo(), salida);
            escribirNodo(nodo.getDerecho(), salida);
        }
    }

    // Primer byte del .pkz: cantidad de nodos internos (hojas - 1)
    public int contarNodosInternos() {
        return contarInternos(raiz);
    }

    private int contarInternos(NodoHuffman nodo) {
        if (nodo == null || nodo.esHoja()) {
            return 0;
        }
        return 1 + contarInternos(nodo.getIzquierdo()) + contarInternos(nodo.getDerecho());

    }
    
}