
package Tercera.Ejercicio05;

import java.applet.Applet;
import java.awt.Graphics;
import java.util.ArrayList;

public class Mano {
    ArrayList<Carta> cartas;
    int posY;
    
    public Mano(int posY){
        cartas = new ArrayList<Carta>();
        this.posY = posY;
    }
    
    public void paint(Graphics g, Applet a){
        for(int i = 0; i < cartas.size(); i++){
            cartas.get(i).setPosX(i*50+50);
            cartas.get(i).setPosY(posY);
            cartas.get(i).paint(g,a);
        }
    }
    
    public void anadirCarta(Carta carta){
        cartas.add(carta);
    }
    
    //falta metodo puntuacion que calcula cuantos puntos tiene la mano
    
    public int puntuacion(){
        int puntuacion = 0;
        boolean hayAs = false;
        
        for(Carta carta:this.cartas){
            puntuacion += carta.valor;
            if(carta.valor == 1){
                hayAs = true;
            }
        }
        if(puntuacion <= 11 && hayAs){
            puntuacion+=10;
        }
        return puntuacion;
    }
    //falta metodo busted, que tienes mas de 21; booleano
    
    public boolean busted(){
        return puntuacion() > 21;
    }
    //falta metodo menor que 17; booleano
    
    public boolean menosDe17(){
        return puntuacion() < 17;
    }
}
