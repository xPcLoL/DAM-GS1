
package segunda.Ejercicio11;



import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Jugando extends Applet implements Runnable{
    Thread animacion;
    Graphics noseve;
    Image imagen;
    ArrayList<Pelota> pelotas;

    @Override
    public void init() {
        imagen = this.createImage(300,300);
        noseve = imagen.getGraphics();
        this.setSize(300,300);
        pelotas = new ArrayList<Pelota>();
        for(int i = 0; i < 10; i++){
            pelotas.add(new Pelota());
        }
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
        for(int i = 0; i < pelotas.size(); i++){
            pelotas.get(i).paint(noseve);
        }
        g.drawImage(imagen,0,0,this);    
    }
    
    @Override
    public void run() { //muy importante esta parte
        do{  
            for(int i = 0; i < pelotas.size(); i++){
                pelotas.get(i).actualizar();
            }
            
            repaint();
            try {
                Thread.sleep(50); // como un task.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Jugando.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while(true);
    }
    
    public boolean mouseDown(Event e, int x, int y){
        for(int i = 0; i < pelotas.size();i++){
            if(pelotas.get(i).contains(x, y)){
                pelotas.remove(i);
                break;
            }
        }
        return true;  
    }
}


