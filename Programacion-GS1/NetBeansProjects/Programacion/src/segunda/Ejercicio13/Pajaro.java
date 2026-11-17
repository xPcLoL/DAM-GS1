
package segunda.Ejercicio13;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Pajaro extends Rectangle{
    public static final int GRAVEDAD = 2;
    public static final int SALTO = -45;

    public Pajaro() {
        super(10,0,15,15);
    }
    public void paint(Graphics gg){
        gg.setColor(Color.YELLOW);
        gg.fillRect(x,y,width,height);
    }
    public void update(){
        this.y += GRAVEDAD;
    }
    public void salto(){
        this.y += SALTO;
    }
    public boolean colision(Columna columna){
        if(columna.rect1.intersects(this)||columna.rect2.intersects(this)){
            return true;
        }
        return false;
    }
}
