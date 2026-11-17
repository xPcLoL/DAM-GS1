
package segunda.Marcianos;

import java.awt.Color;
import java.awt.Graphics;

public class Gun {
    public static final int VELX = 20;
    int width;
    int height;
    int x;
    int y;
    
    public Gun(){
        width = 30;
        height = 30;
        x = 120;
        y = 260;
    }
    
    public void paint(Graphics g){
        g.setColor(Color.red);
        g.drawRect(x, y, width, height);
    }
    
    public void update(boolean derecha){
        if (derecha){
            if (x < 300 - width) {
                this.x += VELX;
            }
        } else { 
            if (x > 0) { 
                this.x -= VELX;
            }
        }
    }
}
