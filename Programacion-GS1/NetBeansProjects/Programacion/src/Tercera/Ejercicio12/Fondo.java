
package Tercera.Ejercicio12;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class Fondo {
    Image imagen;
    int x,y;
    int anchura, altura;
    
    public Fondo(Image imagen, int x, int y, int anchura, int altura){
        this.imagen = imagen;
        this.x = x;
        this.y = y;
        this.anchura = anchura;
        this.altura = altura;
    }
    
    public void mover(){
        if(x < -1200){
            x = 0;
        }else{
        this.x -= 20;
    
        }
    }
    public void paint(Graphics g, Applet a){
        g.drawImage(imagen, x, y, anchura, altura, a);
    }
    
    
}
