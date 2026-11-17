
package Tercera.Ejercicio07;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashSet;

public class Casilla extends Rectangle{
    public static final int TAM = 17;
    private Image mina;
    Image reverso;
    private boolean tapada;
    private int minas_alrededor;
    
    public Casilla(Image reverso, int x, int y){
        super(x,y,TAM,TAM);
        this.reverso = reverso;
        tapada = false;
        minas_alrededor = 0;
    }

    public Image getMina() {
        return mina;
    }

    public void setMina(Image mina) {
        this.mina = mina;
    }

    public boolean isTapada() {
        return tapada;
    }

    public void setTapada(boolean tapada) {
        this.tapada = tapada;
    }

    public int getMinas_alrededor() {
        return minas_alrededor;
    }

    public void setMinas_alrededor(int minas_alrededor) {
        this.minas_alrededor = minas_alrededor;
    }
    
    
    public void paint(Graphics g, Applet a) {
    if (tapada) {
        g.drawImage(reverso, x, y, TAM, TAM, a); 
    } else {
        if (mina != null) {
            g.drawImage(mina, x, y, TAM, TAM, a);
        } else {
            g.setColor(Color.BLACK);
            g.drawString("" + minas_alrededor, x + 4, y + 13);
        }
    }
    g.setColor(Color.DARK_GRAY);
    g.drawRect(x, y, width, height);
}
}
