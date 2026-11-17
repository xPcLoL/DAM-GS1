
package segunda.Ejercicio12;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Pelota extends Rectangle{
    int velX, velY;

    public Pelota() {
        super(150,200,9,9);
        velX = -3;
        velY = -2;
    }
    public void paint(Graphics gg){
        gg.setColor(Color.WHITE);
        gg.fillOval(x,y,width,height);
    }
    
    public void update(Raqueta raqueta, ArrayList<Ladrillo> ladrillos, Pelota pelota){
        this.x += this.velX;
        this.y += this.velY;
        if(this.x < 0 || this.x > 291){
            this.velX = -this.velX;
        } 
        if(this.y < 0){
            this.velY = -this.velY;
        }
        if(this.intersects(raqueta)){
                this.velY = -this.velY;
            }
            for(int i = 0; i < ladrillos.size();i++){
                if(this.intersects(ladrillos.get(i))){
                    this.velY = -this.velY;
                    ladrillos.remove(i);
                    break;
                }
            }
    }
    
}
