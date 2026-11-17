
package Tercera.Ejercicio10;

import java.applet.Applet;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class Serpiente {
    ArrayList<Anillo> anillos;
    
    public Serpiente(int x, int y, Image imagen, int direccion){
        this.anillos = new ArrayList<Anillo>();
        for(int i = 0; i < 10; i++){
            anillos.add(new Anillo(imagen,x-i*Anillo.TAM,y,direccion));
        }
    }
    
    public void paint(Graphics g, Applet a){
        for(int i = 0; i < anillos.size(); i++){
            anillos.get(i).paint(g, a);
        }
    }
    
    public void actualizar(){
        for (int i = 0; i < anillos.size(); i++) {
        anillos.get(i).update();
    }
    for(int i = anillos.size() - 1; i > 0; i--){
        anillos.get(i).setDireccion(anillos.get(i-1).getDireccion());
    }

    
    }
    
    public Anillo primerAnillo(){
        return this.anillos.get(0);
    }
    public Anillo ultimoAnillo(){
        return this.anillos.get(this.anillos.size()-1);
    }
    
    public void cambiarDireccion(int direccion){
        primerAnillo().setDireccion(direccion); 
    }
    
    public void insertarAnillo(Image casilla){
        Anillo ultimo = ultimoAnillo();
        int direccion = ultimo.getDireccion();
        
        switch(direccion){
            case Event.UP:
                anillos.add(new Anillo(casilla,ultimo.x ,ultimo.y + Anillo.TAM , direccion));
                break;
            case Event.DOWN:
                anillos.add(new Anillo(casilla,ultimo.x ,ultimo.y - Anillo.TAM , direccion));
                break;
            case Event.LEFT:
                anillos.add(new Anillo(casilla,ultimo.x + Anillo.TAM ,ultimo.y , direccion));
                break;
            case Event.RIGHT:
                anillos.add(new Anillo(casilla,ultimo.x - Anillo.TAM ,ultimo.y , direccion));
        }
    }
}
