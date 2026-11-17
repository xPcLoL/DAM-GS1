
package Tercera.Ejercicio04;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Pieza extends Rectangle{
    public static final int DIMENSION = 60;
    Image imagen;
    int posicion;
    private boolean colocado = false;
    
    public Pieza(Image imagen,int posicion){
        super((int)(Math.random()*300 +350),(int)(Math.random()*450),DIMENSION,DIMENSION);
        this.imagen = imagen;
        this.posicion = posicion;
    }
    
    public void paint(Graphics g, Applet applet){
        g.drawImage(imagen, x, y, width, height, applet);
    }

    public boolean isColocado() {
        return colocado;
    }

    public void setColocado(boolean colocado) {
        this.colocado = colocado;
    }
    public void mover(int x, int y){
        if(!colocado){
        this.x = x - (DIMENSION/2);
        this.y = y - (DIMENSION/2);
        }
        }
    public void ajustar(int x,int y){
        this.x = x;
        this.y = y;
    }
}
