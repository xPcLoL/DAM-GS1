package Tercera.Ejercicio02;


import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JuegoDino extends Applet implements Runnable{
    Thread animacion;
    Graphics noseve;
    Image imagen;
    Dino dino;
    boolean estaSaltando = false;
    int contador = 0;
    int contador2 = 0;
    ArrayList<Obstaculo> obstaculos;
    
    
    @Override
    public void init() {
        
        obstaculos = new ArrayList<Obstaculo>();
        dino = new Dino();
        imagen = this.createImage(300,300);
        noseve = imagen.getGraphics();
        this.setSize(300,300);
        
        
    }

    @Override
    public void start() {
        animacion = new Thread(this);
        animacion.start();
    }
    public void update(Graphics g){
        paint(g);
    }
    
    public void paint(Graphics g){
        
        noseve.setColor(Color.BLACK);
        noseve.fillRect(0, 0, 300, 300);
        dino.paint(noseve);
        for(int o = 0; o < obstaculos.size(); o++){
            obstaculos.get(o).paint(noseve);
        }
        
        g.drawImage(imagen,0,0,this);
        }
    
    
    @Override
    public void run() { //muy importante esta parte
        do{       
            contador2++;
            if(contador2 == 40){
                obstaculos.add(new Obstaculo());
                contador2 = 0;
            }
            for(int j = 0; j < obstaculos.size(); j++){
                obstaculos.get(j).mover();
            }
            if(estaSaltando && contador < 15){
                dino.saltar1px();
                contador++;
            }
            if(contador >= 15 && contador < 30){
                dino.bajar1px();
                contador++;
            } 
            if(contador == 30){
                contador = 0;
                estaSaltando = false;
            }
            for(int i = 0; i < obstaculos.size(); i++){
                if(obstaculos.get(i).intersects(dino))
                    animacion.stop();
                if(obstaculos.get(i).x < 0)
                    obstaculos.remove(i);
            }
            repaint();
            try {
                Thread.sleep(20); // como un task.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(JuegoDino.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while(true);
    }
    public boolean keyDown(Event e, int tecla){
        if(e.key == 32){
            estaSaltando = true;
            return true;
        }
        return true;
    }

}

