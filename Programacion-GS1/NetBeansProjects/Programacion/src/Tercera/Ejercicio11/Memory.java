package Tercera.Ejercicio11;


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
import java.util.Random;

public class Memory extends Applet implements Runnable{  
    public static final int TIEMPO = 1000;
    public static final int TAM = 4;
    int cuentaSegundos = 0;
    Graphics noseve;
    Image imagen;
    Thread animacion;
    Image imagenes[];
    Image reverso;
    Casilla casillas[][];
    Casilla cUno;
    Casilla cDos;
    int contador = 0;
    boolean jugando = false;
    
    @Override
    public void init() {
        imagen = this.createImage(700,800);
        noseve = imagen.getGraphics();
        imagenes = new Image[TAM*2];
        casillas = new Casilla[TAM][TAM];
        reverso = getImage(getCodeBase(),"Tercera/Ejercicio11/reverso.png");
        for(int i = 0; i < TAM*2; i++){
            imagenes[i] = getImage(getCodeBase(), "Tercera/Ejercicio11/img"+(i+1)+".png");
        }
        
        for(int o = 0; o < casillas.length; o++){
            for(int u = 0; u < casillas[o].length; u++){
                casillas[o][u] = new Casilla(reverso,imagenes[((o*TAM)+u)%(TAM*2)],100+Casilla.TAM*u, 100+Casilla.TAM*o);
            }
        }
        desordenar(casillas);
        

        

        this.setSize(700,800);
    }
    
    public void start(){
        animacion = new Thread(this);
        animacion.start();
    }

    public void update(Graphics g){
        paint(g);
    }
@Override
public void paint(Graphics g) {
    noseve.setColor(Color.GREEN); 
    noseve.fillRect(0, 0, 700, 800);
    
    for(int i = 0; i < casillas.length; i++){
        for(int o = 0; o < casillas[i].length; o++){
            casillas[i][o].paint(noseve, this);
        }
    }
    
    g.drawImage(imagen, 0, 0, this);
}

@Override
    public void run() {
        do{
            cuentaSegundos += TIEMPO;
            if((cuentaSegundos > contador+2000)){
                cUno.setVisible(false);
                cDos.setVisible(false);
                cUno = null;
                cDos = null;
                contador = 99999999;
                jugando = false;
            }
            
            repaint();
            try {
                Thread.sleep(TIEMPO);
            } catch (InterruptedException ex) {
            };
        }while(true);
    }

@Override
public boolean mouseDown(Event ev, int x, int y) {
    if(!jugando){
    for(int i = 0; i < casillas.length; i ++){
        for(int j = 0; j < casillas[i].length; j++){
            if(casillas[i][j].contains(x, y)){
                if( cUno == null){
                    cUno = casillas[i][j];
                    casillas[i][j].setVisible(true);
                    repaint();
                    break;
                } else{
                    cDos = casillas[i][j];
                    casillas[i][j].setVisible(true);
                    if(cDos.getImagen() == cUno.getImagen()){
                        cUno = null;
                        cDos = null;
                    } else{
                        contador = cuentaSegundos;
                        jugando = true;
                    }
                    repaint();
                    break;
                }
            }
        }
    }
    }
    return true;
}

public boolean mouseDrag(Event ev, int x, int y){
    return true;
}
public boolean mouseUp(Event ev, int x, int y){

    return true;
}
public boolean keyDown(Event ev, int tecla){
    return true;
}

public void desordenar(Casilla casillas[][]) {
    ArrayList<Casilla> lista = new ArrayList<>();
    for (int i = 0; i < TAM; i++) {
        for (int j = 0; j < TAM; j++) {
            lista.add(casillas[i][j]);
        }
    }

    Collections.shuffle(lista);

    int contador = 0;
    for (int i = 0; i < TAM; i++) {
        for (int j = 0; j < TAM; j++) {
            casillas[i][j] = lista.get(contador++);

            casillas[i][j].x = (100 + Casilla.TAM * j);
            casillas[i][j].y =(100 + Casilla.TAM * i);
        }
    }
}

    
}

