package Tercera.Ejercicio12;


import Tercera.Ejercicio11.*;
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

public class JuegoCoche extends Applet implements Runnable{  

    Graphics noseve;
    Image imagen;
    Thread animacion;
    
    int tiempo = 400;
    int contador = 0;
    int cont_ruedas = 1;
    
    Fondo fondo;
    Fondo coche;
    Fondo ruedas[];
    
    Image imagenes[];
    
    Pedal acelerar;
    Pedal freno;
    

    
    @Override
    public void init() {
        imagen = this.createImage(700,800);
        noseve = imagen.getGraphics();
        
        imagenes = new Image[3];
        for(int i = 0; i < imagenes.length; i++){
            imagenes[i] = getImage(getCodeBase(), "Tercera/Ejercicio12/rueda"+ (i+1) +".png");
        }
        acelerar = new Pedal(getImage(getCodeBase(), "Tercera/Ejercicio12/pedal_acelerar.png"),550,600);
        freno = new Pedal(getImage(getCodeBase(), "Tercera/Ejercicio12/pedal_frenar.png"),450,600);
        
        ruedas = new Fondo[2];
        ruedas[0] = new Fondo(getImage(getCodeBase(), "Tercera/Ejercicio12/rueda1.png"),50,700,80,80);
        ruedas[1] = new Fondo(getImage(getCodeBase(), "Tercera/Ejercicio12/rueda1.png"),140,700,80,80);
        
        fondo = new Fondo(getImage(getCodeBase(), "Tercera/Ejercicio12/fondociudad.jpg"),0,0,2000,800);
        coche = new Fondo(getImage(getCodeBase(),"Tercera/Ejercicio12/coche.png"), 40, 600, 200,200);
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
 
    fondo.paint(noseve, this);
    coche.paint(noseve, this);
    ruedas[0].paint(noseve, this);
    ruedas[1].paint(noseve, this);
    noseve.setColor(Color.BLACK);
    
    acelerar.paint(noseve, this, "Acelerar");
    freno.paint(noseve, this, "Frenar");

    g.drawImage(imagen, 0, 0, this);
}

@Override
    public void run() {
        do{
            contador++;
            if(contador == 5){
                for(int i = 0; i < ruedas.length; i++){
                    if(cont_ruedas == imagenes.length){
                        ruedas[i].imagen = imagenes[0];
                    }else{
                        ruedas[i].imagen = imagenes[cont_ruedas];
                    }       
                }
                contador = 0;
                if(cont_ruedas == imagenes.length){
                    cont_ruedas = 1;
                }else{
                    cont_ruedas++;
                }
            }
            fondo.mover();
            
            repaint();
            try {
                Thread.sleep(tiempo);
            } catch (InterruptedException ex) {
            };
        }while(true);
    }

@Override
public boolean mouseDown(Event ev, int x, int y) {
    if(acelerar.contains(x, y)){
        if(tiempo > 30)
            tiempo -= 20;
    }
    if(freno.contains(x, y)){
        tiempo += 20;
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
}

