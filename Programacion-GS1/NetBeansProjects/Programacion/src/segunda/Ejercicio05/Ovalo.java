
package segunda.Ejercicio05;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ovalo {
    int altura;
    int anchura;
    int posX;
    int posY;
    Color color;
    Random r;
    
    public Ovalo(){
        this.r = new Random();
        inicializar();
    }

    public void inicializar() {
        posX = r.nextInt(200);
        posY = r.nextInt(200);
        this.anchura = r.nextInt(100);
        this.altura = r.nextInt(100);
        this.color = new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256));
    }
    
    public void dibujar(Graphics graficos){
        graficos.setColor(this.color);
        graficos.fillOval(posX, posY, anchura, altura);
    }
    
}


