
package Tercera.Ejercicio02;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Obstaculo extends Rectangle{
    int velX = 4;
    public Obstaculo(){
        super(299,300,(int)(Math.random()*17+5),(int)(Math.random()*30+10));
        this.y = y - height;
    }
    public void paint(Graphics g){
        g.setColor(Color.green);
        g.fillRect(x, y, width, height);
    }
    public void mover(){
        this.x -= velX;
    }
    
}
