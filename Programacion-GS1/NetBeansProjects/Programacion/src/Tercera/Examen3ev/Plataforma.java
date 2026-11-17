
package Tercera.Examen3ev;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Plataforma extends Rectangle{
    public Plataforma(int y){
        super(0,y,800,30);
    }
    
    public void paint(Graphics g){
        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(x, y, width, height);
    }
}
