
package segunda.Ejercicio11;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Pelota extends Rectangle{    
    Color color;
    Color colores[] = {Color.RED,Color.YELLOW,Color.BLUE,Color.PINK,Color.MAGENTA,Color.WHITE,Color.ORANGE};
    int velX,velY;
    
    public Pelota(){
        super((int)(Math.random()*250),(int)(Math.random()*250),0,0);
        int radio = (int)(Math.random()*30+20);
        this.setSize(radio,radio);
        this.color = colores[(int)(Math.random()*colores.length)];
        velX = (int)(Math.random()*11-5);
        velY = (int)(Math.random()*11-5);
    }
    public void paint(Graphics gg){
        gg.setColor(color);
        gg.fillOval(x,y,width,height);
    }
    public void actualizar(){
        this.x = x + velX;
        this.y = y + velY;
        if((x <= 0)||(x>=(300-width))){
            velX = -velX;  
        }
        if((y <= 0)||(y>=(300-height))){
            velY = -velY; 
        }
    }
}
