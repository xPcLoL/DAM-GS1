
package Tercera.Ejercicio10;

import java.applet.Applet;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Anillo extends Rectangle{
    public static final int TAM = 15;
    private int direccion;
    Image imagen;
    
    public Anillo(Image imagen, int x, int y, int direccion){
        super(x,y,TAM,TAM);
        this.imagen = imagen;
        this.direccion = direccion;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }
    
    public void paint(Graphics g, Applet a){
        g.drawImage(imagen,x,y,width,height,a);
    }
    public void update(){
        switch (direccion){
            case Event.UP:
                y -= TAM;
                break;
            case Event.DOWN:
                y += TAM;
                break;
            case Event.LEFT:
                x -= TAM;
                break;
            case Event.RIGHT:
                x += TAM;
                break;
        }
    }
            
}
