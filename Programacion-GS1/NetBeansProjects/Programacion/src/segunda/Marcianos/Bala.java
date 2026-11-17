
package segunda.Marcianos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bala extends Rectangle{
    
    public static final int ANCHURA = 10;
    public static final int ALTURA = 10;
    public static final int VELY = 15;
    
    int velY;
    
    public Bala(int x, int y){
        super(x,y,ANCHURA,ALTURA);
        this.velY = VELY;
    }
    
    public void paint(Graphics g){
       g.setColor(Color.YELLOW);
       g.fillRect(this.x,this.y,ANCHURA,ALTURA);
    }
    
    public void mover(){
        this.y -= VELY;
    }
    
}
