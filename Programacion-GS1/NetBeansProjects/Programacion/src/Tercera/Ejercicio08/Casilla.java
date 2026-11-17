
package Tercera.Ejercicio08;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Casilla extends Rectangle{
    public static final int TAM = 30*2;
    int valor;
    Color color;
    
    
    public Casilla(int valor, Color color, int x, int y){
        super(x,y, TAM,TAM);
        this.valor = valor;
        this.color = color;
    }
    
    public void paint(Graphics g){
        Font fuenteNueva = new Font("Times New Roman", Font.PLAIN, 24);
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.WHITE);
        g.drawRect(x, y, width, height);
        g.setFont(fuenteNueva);
        if(valor < 10)
            g.drawString(""+valor, x+12*2, y+18*2);
        else 
            g.drawString(""+valor, x+9*2, y+18*2);
    }
}
