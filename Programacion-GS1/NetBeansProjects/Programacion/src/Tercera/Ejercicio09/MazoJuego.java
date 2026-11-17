
package Tercera.Ejercicio09;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class MazoJuego extends Mazo{

    public MazoJuego(int posx) {
        super(posx, 150, Carta.ANCHURA, Carta.ALTURA);
    }
    
    
    @Override
    public void paint(Graphics g, Applet a){
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        for(int i = 0; i < getCartas().size(); i++){
            getCartas().get(i).paint(g, a);
        }
    }

    @Override
    public boolean añadir(Carta carta) {
        getCartas().add(carta);
        return true;
    }
    

    @Override
    public void recolocar() {

    }
    
}
