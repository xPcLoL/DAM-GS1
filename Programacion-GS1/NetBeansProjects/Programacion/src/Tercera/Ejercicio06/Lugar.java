
package Tercera.Ejercicio06;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class Lugar {
    // DIM es el tamaño en píxeles de cada pieza (48x48)
    public static final int DIM = 48;
    
    private Image imagen;
    private int valor; // El valor original/correcto de la pieza para comprobar si ganó

    public Lugar(Image imagen, int valor) {
        this.imagen = imagen;
        this.valor = valor;
    }

    // Getters y Setters
    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

   
    public void paint(Graphics g, Applet a, int fila, int col) {
        // Multiplicamos la fila y columna por la dimensión para obtener los píxeles
        // Nota: x es columna, y es fila
        if (imagen != null) {
            g.drawImage(imagen, col * DIM, fila * DIM, DIM, DIM, a);
        }
    }
}