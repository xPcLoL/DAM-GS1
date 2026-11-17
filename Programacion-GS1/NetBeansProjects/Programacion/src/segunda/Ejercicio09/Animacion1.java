
package segunda.Ejercicio09;


import java.awt.Button;
import java.awt.Event;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Animacion1 extends Frame implements Runnable{
    Thread animacion;
    int actual = 0;
    String frames[] = {"*","**","***","****","*****","******","*******","********","*********","**********","***********","************","***********","**********","*********","********","*******","******","*****","****","***","**","*"};
    public static void main(String arg[]) throws Exception{
        Animacion1 app = new Animacion1();
    }
    
    public Animacion1(){
        super("Animación Sencilla");

        
        
        this.pack();
        this.setSize(400,400);
        this.setVisible(true);
        
        animacion = new Thread(this);
        animacion.start();
        
    }
    
    public boolean handleEvent(Event ev){
        
        if(ev.id == Event.WINDOW_DESTROY){
            System.exit(0);
            return true;
        }
        return false;
    }
    public void paint(Graphics g){
        g.drawString(frames[actual], 80, 100);
        
    }
    @Override
    public void run() { //muy importante esta parte
        do{   
            repaint();
            actual=(actual+1)%frames.length; //esta instruccion es interesante para hacer un bucle en un vector
            try {
                Thread.sleep(100); // como un task.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Animacion1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while(true);
    }
}


