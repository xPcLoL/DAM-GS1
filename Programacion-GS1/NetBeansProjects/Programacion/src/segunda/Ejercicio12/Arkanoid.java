
package segunda.Ejercicio12;




import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Arkanoid extends Applet implements Runnable{
    public static final int FILAS = 5;
    public static final int COLUMNAS = 50;
    public static final int DERECHA = 0;
    public static final int NODERECHA = 1;
    Thread animacion;
    Graphics noseve;
    Image imagen;
    ArrayList<Ladrillo> ladrillos;
    Raqueta raqueta;
    Pelota pelota;
    
    
    
    

    @Override
    public void init() {
        Color colores[] = {Color.CYAN, Color.YELLOW, Color.ORANGE, Color.GREEN, Color.RED};
        ladrillos = new ArrayList<Ladrillo>();
        int x = 0;
        int y = 10;
        for(int i = 0; i < FILAS; i++){
            for(int j = 0; j < COLUMNAS; j++){
                ladrillos.add(new Ladrillo(colores[i],x,y));
                x = x + Ladrillo.ANCHURA + 2;
            }
            y = y + Ladrillo.ALTURA + 2;
            x = 0;
        }
        raqueta = new Raqueta();
        pelota = new Pelota();
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
        
        for(int i = 0; i < ladrillos.size(); i++){
            ladrillos.get(i).dibujar(noseve);
        }
        raqueta.paint(noseve);
        pelota.paint(noseve);
        g.drawImage(imagen,0,0,this);
        
    }
    
    @Override
    public void run() { //muy importante esta parte
        do{              
            pelota.update(raqueta,ladrillos,pelota);
            repaint();
            try {
                Thread.sleep(10); // como un task.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Arkanoid.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while(true);
    }
    
    public boolean keyDown(Event e, int tecla){
        if(tecla ==1006){
            raqueta.setX(NODERECHA);
        }
        if(tecla ==1007){
            raqueta.setX(DERECHA);
        }
        return true;
    }
    public boolean mouseDown(Event e,int x, int y){
        pelota.x = 150;
        pelota.y = 200;
        return true;
    }
}


