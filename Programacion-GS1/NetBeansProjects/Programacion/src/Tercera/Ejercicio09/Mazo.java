
package Tercera.Ejercicio09;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class Mazo extends Rectangle{
    private ArrayList<Carta> cartas;
    
    public Mazo(int posx, int posy, int anchura, int altura){
        super(posx,posy,anchura,altura);
        cartas = new ArrayList<Carta>();
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(ArrayList<Carta> cartas) {
        this.cartas = cartas;
    }
    
    public void paint(Graphics g, Applet a){
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        for(int i = 0; i < cartas.size(); i++){
            cartas.get(i).paint(g,a);
        }
    }
    public Carta extraer(){
        return cartas.get(cartas.size()-1);
    }
    public void eliminar(){
        cartas.remove(cartas.size()-1);
    }
    public abstract boolean añadir(Carta carta); //nigga    
    public abstract void recolocar();
    
}
