package segunda.examenAndresIvan2EV.examen2;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ejecutable extends Applet implements Runnable{
    Thread animacion;
    Graphics noseve;
    Image imagen;
    ArrayList<Circulo> circulos;
    Random r;
    
    @Override
    public void init(){
        circulos = new ArrayList<Circulo>();
        circulos.add(new Circulo(50, 50, 200, 2, 3));
        
        imagen = this.createImage(300, 300);
        noseve = imagen.getGraphics();
        this.setSize(300, 300);
    }
    
    @Override
    public void start(){
        animacion = new Thread(this);
        animacion.start();
    }
    
    public void update(Graphics g){
        paint(g);
    }
    
    public void paint(Graphics g){
        noseve.setColor(Color.BLACK);
        noseve.fillRect(0, 0, 300, 300);
        
        for(int i = 0; i < circulos.size(); i++){
            circulos.get(i).paint(noseve);
        }
        
        g.drawImage(imagen, 0, 0, this);
    }
    
        
    @Override
    public void run(){
        do{
            for(int i = 0; i < circulos.size(); i++){
                circulos.get(i).mover();
            }
            repaint();
            try{
                Thread.sleep(20);
            } catch(InterruptedException ex){
                Logger.getLogger(Ejecutable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while(true);
    }
    public boolean mouseDown(Event e, int x, int y){
        int termina = 0;
        for(int i = 0; i < circulos.size(); i++){
            if(circulos.get(i).encontrado(x,y)){
                int mitad = circulos.get(i).ancho/2;
                int x2 = circulos.get(i).x;
                int y2 = circulos.get(i).y;
                circulos.remove(i);
                circulos.add(new Circulo(x2, y2, mitad, 3, 2));
                circulos.add(new Circulo(x2, y2, mitad, -3, -2));

                termina = 1;
            }
            if(termina == 1){
                i = circulos.size();
            }
        }
        return true;
    }

}

    
    



