
package segunda.Ejercicio12;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Raqueta extends Rectangle{
    public static final int VELX = 5;

    public Raqueta() {
        super(120,270,60,10);
    }
    
    public void paint(Graphics gg){
        gg.setColor(Color.WHITE);
        gg.fillRect(x,y,width,height);
    }
    
    public void setX(int derecha){
        if(derecha == Arkanoid.DERECHA){
            this.x += VELX;
        } else{
            this.x -= VELX;
        }
        if(x < 0) x = 0;
        if(x > 240) x = 240;
    }
}
