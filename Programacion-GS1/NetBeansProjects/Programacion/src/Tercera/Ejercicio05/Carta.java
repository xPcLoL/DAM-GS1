
package Tercera.Ejercicio05;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class Carta {
    public static final int ANCHURA = 100;
    public static final int ALTURA = 150;
    private int posX,posY;
    Image imagen;
    int valor;
    
    public Carta(Image img, int valor){
        if(valor > 10){
            this.valor = 10;
        }else{
           this.valor = valor; 
        }
        //valor = (v > 10)?10:valor; operador condicional
        this.imagen = img;
    }
    
    public void paint(Graphics g, Applet apt){
        g.drawImage(imagen, posX, posY,ANCHURA,ALTURA, apt);
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    
}
