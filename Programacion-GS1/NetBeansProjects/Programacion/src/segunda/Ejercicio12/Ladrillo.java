
package segunda.Ejercicio12;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ladrillo extends Rectangle{
    public static final int ANCHURA = 28;
    public static final int ALTURA = 10;
    public Color color;

    public Ladrillo(Color color, int posX, int posY) {
        super(posX, posY, ANCHURA,ALTURA);
        this.color = color;
    }
    
    public void dibujar(Graphics gg){
        gg.setColor(color);
        gg.fillRect(x,y,width,height);
    }
    
}
