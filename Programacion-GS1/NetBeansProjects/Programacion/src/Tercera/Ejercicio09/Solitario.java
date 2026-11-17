package Tercera.Ejercicio09;

import Tercera.Ejercicio08.*;
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

public class Solitario extends Applet{
    public static final int NUM_CARTAS = 52;
    public static final int CPP = 13;
    Image imagenes[];
    Image reverso;
    
    Graphics noseve;
    Image imagen;
    Baraja baraja;
    MazoSecundario mazoS;
    Rectangle rect;
    MazoPalo[] mazosPalo;
    Rectangle rects[];

    Carta activa;    
    
    @Override
    public void init() {
        imagen = this.createImage(700,800);
        noseve = imagen.getGraphics();
        imagenes = new Image[NUM_CARTAS];
        rect = new Rectangle(20, 20,Carta.ANCHURA,Carta.ALTURA);
        rects = new Rectangle[4];
        mazosPalo = new MazoPalo[4];
        
        for(int u = 0; u < mazosPalo.length; u++){
            mazosPalo[u] = new MazoPalo(250+(u*100));
            rects[u] = new Rectangle(250+(u*100), MazoPalo.POSY, Carta.ANCHURA, Carta.ALTURA);
        }
        
        String palos[] = {"_of_clubs.png","_of_diamonds.png", "_of_hearts.png","_of_spades.png"};
        for(int i = 0; i < NUM_CARTAS; i++){
            imagenes[i] = getImage(getCodeBase(),"Tercera/Ejercicio09/Cartas/" + ((i%CPP+1) + palos[i / CPP]));
        }
        reverso = getImage(getCodeBase(),"Tercera/Ejercicio09/Cartas/reverso.png");
        baraja = new Baraja(imagenes);
        mazoS = new MazoSecundario();

        this.setSize(700,800);
    }

    public void update(Graphics g){
        paint(g);
    }
@Override
public void paint(Graphics g) {
    noseve.setColor(Color.GREEN); 
    noseve.fillRect(0, 0, 700, 800);
    
    noseve.drawImage(reverso, 20, 20,Carta.ANCHURA,Carta.ALTURA, this);
    mazoS.paint(noseve, this);
    for(int u = 0; u < mazosPalo.length; u++){
        mazosPalo[u].paint(noseve, this);
    }

    g.drawImage(imagen, 0, 0, this);
}
@Override
public boolean mouseDown(Event ev, int x, int y) {
    if (rect.contains(x, y)) {
        mazoS.añadir(baraja.sacarCarta());
        mazoS.recolocar();
    }
    if(mazoS.contains(x, y)){
        activa = mazoS.extraer();
    }
    repaint();
    return true;
}

public boolean mouseDrag(Event ev, int x, int y){
    if(activa != null){
        activa.actualizar(x-(Carta.ANCHURA/2), y-(Carta.ANCHURA/2));
        repaint();
    }
    return true;
}
public boolean mouseUp(Event ev, int x, int y){
    if(activa != null)
        for(int i = 0; i < mazosPalo.length; i++){
            if(activa.intersects(mazosPalo[i])){
                if(mazosPalo[i].añadir(activa)){
                    mazoS.eliminar();
                    mazosPalo[i].recolocar();
                    break;
                }
            }
        }
    mazoS.recolocar();
    activa = null;
    repaint();
    return true;
}
/*
public boolean handleEvent(Event ev){

    return true;
}
*/


}

