
package Tercera.Examen3ev;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Jugador extends Rectangle{
    private Image imagen;
    private int vel = 5;
    private int direccion = 1; //Direccion 1 es derecha, direccion 2 es izquierda, direccion 3 es arriba y direccion 4 es abajo
    
    public Jugador(Image imagen,int x, int y){
        super(x,y,30,5);
        this.imagen = imagen;
    }
    
    public void paint(Graphics g, Applet a){
        g.setColor(Color.red);
        g.fillRect(x, y, width, height);
        g.drawImage(imagen, x, y-55, width, height+50, a);
    }
    
    public void mover(int direccion){
        if(x<700 && x>0)
        switch(direccion){
            case 1 :
                x += vel;
                break;
            case 2 : 
                x -= vel;
                break;
            case 3 : 
                y -= vel;
                break;
            case 4 : 
                y += vel;
                break;
        }
        if(x>670){
            x = 670;
        }
        if(x<10){
            x = 10;
        }
        
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public int getVel() {
        return vel;
    }

    public void setVel(int vel) {
        this.vel = vel;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }
    
}
