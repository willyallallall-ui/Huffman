/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Willi
 */

public class Detalle {

    public static final int MAX_SIMBOLOS = 20;   // símbolos que se muestran en la cadena de códigos
    public static final int MAX_BYTES = 40;      // bytes que se muestran como máximo

    // 'a' si es imprimible, 0x0A en hexadecimal si no
    public static String simbolo(int valor) {
        if (valor >= 32 && valor <= 126) {
            return "'" + (char) valor + "'";
        }
        return "0x" + String.format("%02X", valor);
    }

    // Bytes en decimal (0 a 255) entre desde y hasta
    public static String bytes(byte[] datos, int desde, int hasta) {
        StringBuilder sb = new StringBuilder();
        int limite = Math.min(hasta, desde + MAX_BYTES);
        for (int i = desde; i < limite; i++) {
            sb.append(datos[i] & 0xFF).append(" ");
        }
        if (hasta > limite) {
            sb.append("...");
        }
        return sb.toString();
    }

    // Bits de cada byte de 8 en 8; en el último solo los significativos
    public static String bits(byte[] datos, int desde, int hasta, int bitsSignificativos) {
        StringBuilder sb = new StringBuilder();
        int limite = Math.min(hasta, desde + MAX_BYTES);

        for (int i = desde; i < limite; i++) {
            String bits = String.format("%8s", Integer.toBinaryString(datos[i] & 0xFF)).replace(' ', '0');
            if (i == hasta - 1) {
                bits = bits.substring(0, bitsSignificativos);
            }
            sb.append(bits).append(" ");
        }
        if (hasta > limite) {
            sb.append("...");
        }
        return sb.toString();
    }
}