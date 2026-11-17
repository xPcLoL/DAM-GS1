package segunda.Esqueletos;


import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class esqueleto_juego extends Applet implements Runnable{
    Thread animacion;
    Graphics noseve;
    Image imagen;
    
   
    
    
    @Override
    public void init() {
        
        
        
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
        
        g.drawImage(imagen,0,0,this);
        }
    
    
    @Override
    public void run() { //muy importante esta parte
        do{       
            
            repaint();
            try {
                Thread.sleep(20); // como un task.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(esqueleto_juego.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while(true);
    }

}

