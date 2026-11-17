
package segunda.examenAndresIvan2EV.examen1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bloque extends Rectangle{
    Color color;
    boolean visible = true;

    
    public Bloque(int x, int y, Color color){
        this.x = x;
        this.y = y;
        this.width = 10;
        this.height = 5;
        this.color = color;
        
    }
    

    public void paint(Graphics gg){
        if(visible == true){
            gg.setColor(color);
            gg.fillRect(x, y, width, height);
        }
    }
    
    public boolean encontrado(int posX, int posY){
        return this.contains(posX, posY);
    }


    
    
}
