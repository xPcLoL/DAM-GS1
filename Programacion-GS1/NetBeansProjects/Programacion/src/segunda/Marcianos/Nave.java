
package segunda.Marcianos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Nave extends Rectangle{
    
    public static final int ANCHURA = 30;
    public static final int ALTURA = 20;
    Color color;
    int velX;
    
    public Nave(Color color, int velX){
        super((int)(Math.random() * 180)+1,(int)(Math.random() * 180)+1,ANCHURA,ALTURA);
        
        this.color = color;
        this.velX = velX;
    }
    
    public void paint(Graphics g){
        g.setColor(this.color);
        g.fillRect(x, y, ANCHURA, ALTURA);
    }
    
    public void mover(){
        this.x += this.velX;
        if(this.x >= 300 - ANCHURA || this.x <= 0){
            this.velX *= -1;
        }
    }
}
