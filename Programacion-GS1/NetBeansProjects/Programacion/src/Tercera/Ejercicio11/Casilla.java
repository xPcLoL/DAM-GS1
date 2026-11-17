
package Tercera.Ejercicio11;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Casilla extends Rectangle{
    public static final int TAM = 100;
    private Image reverso; 
    Image imagen;
    private boolean visible;
    
    public Casilla(Image reverso, Image imagen, int x, int y){
        super(x,y,TAM,TAM);
        this.reverso = reverso;
        this.imagen = imagen;
        visible = false;
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public void paint(Graphics g, Applet a){
        if(visible){
            g.drawImage(imagen, x, y, width, height, a);
        } else {
            g.drawImage(reverso, x, y, width, height, a);
        }
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }
}
