package segunda.examenAndresIvan2EV.examen2;

import java.awt.Color;
import java.awt.Graphics;

public class Circulo {
    int x;
    int y;
    int ancho;
    int velX;
    int velY;
    
    public Circulo(int x, int y, int ancho, int velX, int velY){
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.velX = velX;
        this.velY = velY;
    }
    
    public void paint(Graphics g){
        g.setColor(Color.RED);
        g.fillOval(x,y,ancho,ancho);
    }
    
    public void mover(){
        x = x + velX;
        y = y + velY;
        if((x < 0) || ((x + ancho) > 300)){
            velX = -velX;
        }
        if((y < 0) || ((y + ancho) > 300)){
            velY = -velY;
        }
    }
    
    public boolean encontrado(int posX, int posY){
        if((posX >= x) && (posX <= x + ancho) && (posY >= y) && (posY <= (y + ancho))){
            return true;
        }
        return false;
    }
}
