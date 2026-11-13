    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Willi
 */
public class NodoHuffman implements Comparable<NodoHuffman> {

    private int valor;              // 0 a 255 si es hoja, -1 si es nodo interno
    private long frecuencia;
    private NodoHuffman izquierdo;
    private NodoHuffman derecho;
    private int orden;

    // Constructor de hoja
    public NodoHuffman(int valor, long frecuencia) {
        this.valor = valor;
        this.frecuencia = frecuencia;
    }

    // Constructor de nodo interno: la frecuencia es la suma de los hijos
    public NodoHuffman(NodoHuffman izquierdo, NodoHuffman derecho) {
        this.valor = -1;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.frecuencia = izquierdo.frecuencia + derecho.frecuencia;
    }

    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    public int getValor() {
        return valor;
    }

    public long getFrecuencia() {
        return frecuencia;
    }

    public NodoHuffman getIzquierdo() {
        return izquierdo;
    }

    public NodoHuffman getDerecho() {
        return derecho;
    }
    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    @Override
    public int compareTo(NodoHuffman otro) {
        int resultado = Long.compare(this.frecuencia, otro.frecuencia);
        if (resultado == 0) {
            resultado = Integer.compare(this.orden, otro.orden);
        }
        return resultado;
    }
}

