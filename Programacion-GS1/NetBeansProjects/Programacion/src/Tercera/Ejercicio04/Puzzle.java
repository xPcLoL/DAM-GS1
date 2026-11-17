package Tercera.Ejercicio04;


import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Puzzle extends Applet{
    Graphics noseve;
    Image imagen;
    public static final int PIEZAS = 25;
    public static final int FILAS = 5;
    Image imagenes[];
    Pieza[] piezas;
    Pieza actual;
    Rectangle tablero[][];
    
    @Override
    public void init() {
        imagenes = new Image[PIEZAS];
        piezas = new Pieza[PIEZAS];
        for(int i = 0; i < PIEZAS; i++){
            imagenes[i] = getImage(getCodeBase(),"Tercera/Ejercicio04/imagenes/" + (i+1) + ".png");
            piezas[i] = new Pieza(imagenes[i],i);
        }
        tablero = new Rectangle[FILAS][FILAS];
        for(int i = 0; i < FILAS; i++){
            for(int j = 0; j < FILAS; j++){
                tablero[i][j] = new Rectangle(Pieza.DIMENSION*j+10,Pieza.DIMENSION*i+10, Pieza.DIMENSION,Pieza.DIMENSION);
            }
        }
        imagen = this.createImage(700,500);
        noseve = imagen.getGraphics();
        this.setSize(700,500);
    }

    
    public void update(Graphics g){
        paint(g);
    }
    
public void paint(Graphics g){
    noseve.setColor(Color.WHITE); 
    noseve.fillRect(0, 0, 700, 500);
    
    noseve.setColor(Color.BLACK);
    noseve.fillRect(10, 10, 300, 300); 

    for(int i = 0; i < PIEZAS; i++){
        piezas[i].paint(noseve, this);
    }
    noseve.setColor(Color.RED);
    for(int i = 0; i < FILAS; i++){
            for(int j = 0; j < FILAS; j++){
                noseve.drawRect(tablero[i][j].x,tablero[i][j].y, tablero[i][j].width,tablero[i][j].height);
            }
        }
    g.drawImage(imagen, 0, 0, this);
}

public boolean mouseDown(Event e, int x, int y){
    for(int i = 0; i < PIEZAS; i++){
        if(piezas[i].contains(x,y)){
            actual = piezas[i];
            return true;
        }
    }
    return true;
}

public boolean mouseDrag(Event e, int x, int y){
    if(actual != null){
        actual.mover(x, y);
    }
    repaint();

    return true;
}
public boolean mouseUp(Event e, int x, int y){
    for(int i = 0; i < FILAS; i++){
            for(int j = 0; j < FILAS; j++){
                if((actual.intersects(tablero[i][j])) && (actual.posicion == i*5+j)){
                    actual.setColocado(true);
                    actual.ajustar(tablero[i][j].x,tablero[i][j].y);
                    repaint();
                }
            }
    }
    actual = null;
    return true;
}

}

