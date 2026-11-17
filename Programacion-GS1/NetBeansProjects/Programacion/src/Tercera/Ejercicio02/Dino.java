
package Tercera.Ejercicio02;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Dino extends Rectangle{
    int velY = -4;
    public Dino(){
        super(20,256,18,43);
    }
    public void paint(Graphics g){
        g.setColor(Color.red);
        g.drawRect(x, y, width, height);
    }
    
    public void saltar1px(){
        this.y += velY;
    }
    public void bajar1px(){
        this.y += -velY;
    }
    
}
