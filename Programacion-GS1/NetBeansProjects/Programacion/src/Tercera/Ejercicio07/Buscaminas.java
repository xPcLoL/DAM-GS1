package Tercera.Ejercicio07;


import Tercera.Ejercicio06.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Buscaminas extends Applet{
    public static final int DIM = 10;
    Image mina;
    Image reverso;
    Graphics noseve;
    Image imagen;
    Casilla casillas[][];
    
    
    @Override
    public void init() {
        imagen = this.createImage(700,500);
        noseve = imagen.getGraphics();
        
        mina = getImage(getCodeBase(), "Tercera/Ejercicio07/mina.png");
        reverso = getImage(getCodeBase(), "Tercera/Ejercicio07/casilla.png");
        casillas = new Casilla[DIM][DIM];
        
        for(int i = 0; i < casillas.length; i++){
            for(int j = 0; j < casillas[i].length; j++){
                casillas[i][j] = new Casilla(reverso,j*Casilla.TAM, 100+i*Casilla.TAM);
            }
        }
        ArrayList<Integer> numeros = new ArrayList<Integer>();
        for(int i = 0; i < DIM*DIM; i++){
            numeros.add(new Integer(i));
        }
        Collections.shuffle(numeros); //desordena los elementos de la lista
        for(int i = 0; i < DIM; i++){
            int aux = numeros.get(i)%10;
            casillas[(numeros.get(i)-aux)/10][aux].setMina(mina);
        }
        
    for (int i = 0; i < DIM; i++) {
        for (int j = 0; j < DIM; j++) {
            if (casillas[i][j].getMina() == null) {            
                for (int o = i - 1; o <= i + 1; o++) {
                    for (int u = j - 1; u <= j + 1; u++) {                    
                        if (o >= 0 && o < DIM && u >= 0 && u < DIM) {                        
                            if (casillas[o][u].getMina() != null) {
                                casillas[i][j].setMinas_alrededor(casillas[i][j].getMinas_alrededor() + 1);
                            }
                        }
                    }
                }
            }
        }
    }
        this.setSize(400,500);
    }

    
    public void update(Graphics g){
        paint(g);
    }
@Override
public void paint(Graphics g) {
    noseve.setColor(Color.LIGHT_GRAY); 
    noseve.fillRect(0, 0, 400, 500);
    
    for(int i = 0; i < casillas.length; i++){
        for(int j = 0; j < casillas[i].length; j++){
            casillas[i][j].paint(noseve, this);
        }
    }
    
    g.drawImage(imagen, 0, 0, this);
}

public boolean mouseDown(Event ev, int x, int y){
    
    for(int i = 0; i < casillas.length; i++){
        for(int j = 0; j < casillas[i].length; j++){
            if(casillas[i][j].contains(new Point(x,y))){
                casillas[i][j].setTapada(false);
                repaint();
            }
        }
    }
    
    return true;
}

}

