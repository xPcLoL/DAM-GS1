package Tercera.Ejercicio10;


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

public class Snake extends Applet implements Runnable{   
    public static final int TIEMPO = 100;
    Graphics noseve;
    Image imagen;
    Image imgCasilla;
    Image imgManzana;
    Serpiente serpiente;
    Thread animacion;
    ArrayList<Anillo> manzanas;
    int cuentaSegundos = 0;
    
    
    @Override
    public void init() {
        imagen = this.createImage(700,800);
        noseve = imagen.getGraphics();
        
        imgCasilla = getImage(getCodeBase(),"Tercera/Ejercicio10/casilla.png");
        imgManzana = getImage(getCodeBase(),"Tercera/Ejercicio10/manzana.png");

        serpiente = new Serpiente(300,300,imgCasilla,Event.RIGHT); 
        manzanas = new ArrayList<Anillo>();
        
        for(int i = 0; i < 10; i++){
            manzanas.add(new Anillo(imgManzana,(int)(Math.random()*600),(int)(Math.random()*600),0));
        }
        

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
    
    serpiente.paint(noseve, this);
    for(Anillo manzana : manzanas){
        manzana.paint(noseve, this);
    }

    g.drawImage(imagen, 0, 0, this);
}

@Override
    public void run() {
        do{
            cuentaSegundos+=TIEMPO;
            if(cuentaSegundos > 5000){
                manzanas.add(new Anillo(imgManzana,(int)(Math.random()*600),(int)(Math.random()*600),0));
                cuentaSegundos = 0;
            }
            serpiente.actualizar();
            
            for(int i = 0; i < manzanas.size(); i++){
                if(serpiente.primerAnillo().intersects(manzanas.get(i))){
                    serpiente.insertarAnillo(imgCasilla);
                    manzanas.remove(manzanas.get(i));
                    break;
                }
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
    return true;
}

public boolean mouseDrag(Event ev, int x, int y){
    return true;
}
public boolean mouseUp(Event ev, int x, int y){

    return true;
}
public boolean keyDown(Event ev, int tecla){
    serpiente.cambiarDireccion(tecla);
    return true;
}

    
}

