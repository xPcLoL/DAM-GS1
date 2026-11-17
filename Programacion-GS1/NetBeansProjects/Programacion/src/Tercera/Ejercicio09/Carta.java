
package Tercera.Ejercicio09;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Carta extends Rectangle{
    public static final int ANCHURA = 70;
    public static final int ALTURA = 120;
    public static final int ROJO = 1;
    public static final int NEGRO = 0;
    public static final int PICAS = 0;
    public static final int ROMBOS = 1;
    public static final int CORAZONES = 2;
    public static final int TREBOLES = 3;
    Image imagen;
    private int color;
    private int valor;
    private int palo;
    
    public Carta(Image imagen, int color, int valor, int palo){
        super(-200,-200, ANCHURA, ALTURA);
        this.imagen = imagen;
        this.color = color;
        this.valor = valor;
        this.palo = palo;
    }
    public void paint(Graphics g, Applet a){
        g.drawImage(imagen, x, y,ANCHURA,ALTURA, a);
    }
    
    public void actualizar(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getPalo() {
        return palo;
    }

    public void setPalo(int palo) {
        this.palo = palo;
    }
    
}
