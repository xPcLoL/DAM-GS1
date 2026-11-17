
package Tercera.Ejercicio12;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Pedal extends Rectangle{
    public static final int TAM = 120;
    Image imagen;
    
    
    public Pedal(Image imagen, int x, int y){
        super(x,y,TAM,TAM);
        this.imagen = imagen;
    }
    
    public void paint(Graphics g, Applet a, String s){
        g.drawImage(imagen, x, y, TAM, TAM, a);
        g.drawString(s, x+20, y-20);
    }
}
