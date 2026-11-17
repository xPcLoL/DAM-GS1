package Tercera.Ejercicio08;

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

public class Ruleta extends Applet{
    public static final int COLUMNAS = 12;
    public static final int FILAS = 3;
    
    Graphics noseve;
    Image imagen;
    Color color;
    
    Casilla casillas[][];
    Ficha fichas[] = new Ficha[10];
    Ficha activa;
    ArrayList<Integer> numeros;
    Panel panel = new Panel();
    int vueltas = 0;
    
    
    Button boton = new Button("Jugar");
    
    
    @Override
    public void init() {
        imagen = this.createImage(1300,800);
        noseve = imagen.getGraphics();
        casillas = new Casilla[12][3];
        panel.add(boton);
        this.add("South",panel);
        int valores[] = {1,5,10,25,50,100,500,1000,5000,10000};
        
        for(int o = 0; o < fichas.length; o++){
            fichas[o] = new Ficha(40+Ficha.TAM*o, 340,valores[o],getImage(getCodeBase(), "Tercera/Ejercicio08/fichas/ficha"+(o+1)+".png"));
        }
        
        //se puede hacer con una lista de Integer, que es la clase de los enteros, ya que int es tipo de dato no clase
        
        for(int i = 0; i < COLUMNAS; i++){
            for(int j = 0; j < FILAS; j++){
                int numero = j+i*3+1;
                if((numero % 2 ) == 0){
                    color = Color.BLACK;
                }else{
                    color = Color.RED;
                }
                if((numero == 11) || (numero == 13) || (numero == 15) ||(numero == 17) || (numero == 29) || (numero == 31) || (numero == 33) || (numero == 35)){
                    color = Color.BLACK;
                }
                if((numero == 12)||(numero == 16)||(numero == 18)||(numero == 14) || (numero == 30) || (numero == 32) || (numero == 34) || (numero == 36)){
                    color = Color.RED;
                }
                
                casillas[i][j] = new Casilla(numero,color,Casilla.TAM*i+40, 260 - Casilla.TAM*j);
            }
        }
        this.setSize(1300,800);
    }

    public void update(Graphics g){
        paint(g);
    }
@Override
public void paint(Graphics g) {
    noseve.setColor(Color.LIGHT_GRAY); 
    noseve.fillRect(0, 0, 1300, 800);
    
    for(int i = 0; i < COLUMNAS; i++){
        for(int j = 0; j < FILAS; j++){
            casillas[i][j].paint(noseve);
        }
    }
    for(int o = 0; o < fichas.length; o++){
        fichas[o].paint(noseve, this);
    }
    
    
    g.drawImage(imagen, 0, 0, this);
}

public boolean mouseDown(Event ev, int x, int y){
    for(int i = 0; i < fichas.length; i++){
        if(fichas[i].contains(x, y)){
            activa = fichas[i];
            return true;
        }
    }
    return true;
}

public boolean mouseDrag(Event ev, int x, int y){
    if(activa != null){
       activa.actualizarPos(x, y);
       repaint();
    }
    return true;
}
public boolean mouseUp(Event ev, int x, int y){
    if(activa != null){
        activa.cargarNumerosApostados(casillas);
        activa = null;
        
    }
    return true;
}

public boolean handleEvent(Event ev){
    if(ev.id == Event.ACTION_EVENT){
            if(ev.target instanceof Button)
                if(ev.arg.equals("Jugar")){
                    numeros.add((int)Math.random()*36+1);
                    for(int i = 0; i < casillas.length; i++){
                        for(int j = 0; i < casillas[i].length; j++){
                            if(casillas[i][j].valor == numeros.get(vueltas)){
                                System.out.println("salio");
                                noseve.setColor(Color.RED);
                                noseve.drawString(""+numeros.get(vueltas), 500, 200);
                                vueltas++;
                                return true;
                            }
                        }
                    }
                    return true;
                }
    }
    return true;
}



}

