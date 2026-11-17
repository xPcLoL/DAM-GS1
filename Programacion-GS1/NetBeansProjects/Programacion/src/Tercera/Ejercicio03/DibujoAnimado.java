
package Tercera.Ejercicio03;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class DibujoAnimado {
    Image[] imagenes;
    int indice = 0;
    
    public DibujoAnimado(Image[] imagenes){
        this.imagenes = imagenes;
    }
    
    public void paint(Graphics g, Applet applet){
        g.drawImage(imagenes[indice], 0, 0, 200, 250, applet);
    }
    
    public void actualizar(){
        indice = (indice +1) % imagenes.length;
    }
}
