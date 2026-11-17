
package Tercera.Examen3ev;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Escalera extends Rectangle{
    private Image imagen;
    public Escalera(int x, int y, Image imagen){
        super(x,y,50,190);
        this.imagen = imagen;
    }
    
    public void paint(Graphics g, Applet a){
        g.drawImage(imagen, x, y, width, height, a);
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }
    
}
