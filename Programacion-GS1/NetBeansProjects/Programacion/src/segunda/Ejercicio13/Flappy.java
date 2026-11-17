
package segunda.Ejercicio13;





import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Flappy extends Applet implements Runnable{
    public static final int TIEMPO = 20;
    Thread animacion;
    Graphics noseve;
    Image imagen;
    Pajaro pajaro;
    int contador = 0;
    ArrayList<Columna> columnas;
    boolean termina = false;
    
    
    @Override
    public void init() {
        pajaro = new Pajaro();
        columnas = new ArrayList<Columna>();
        columnas.add(new Columna());
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
        for(int i = 0; i < columnas.size(); i++){
            columnas.get(i).paint(noseve);
        }
        pajaro.paint(noseve);
        if(termina){
            noseve.drawString("GAME OVER", 120,140);
        }
        g.drawImage(imagen,0,0,this);
        
    }
    
    @Override
    public void run() { //muy importante esta parte
        do{       
            pajaro.update();
            for(int i = 0; i < columnas.size(); i++){
            columnas.get(i).update();
                if((columnas.get(0).rect1.x < -Columna.ANCHURA)){
                    columnas.remove(0);
                }
            } 
            contador += 15;
            if(contador > 2000){
                columnas.add(new Columna());
                contador = 0;
            }
            termina = pajaro.colision(columnas.get(0));
            if(termina){
                repaint();
                animacion.stop();
            }
            repaint();
            try {
                Thread.sleep(TIEMPO); // como un task.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Flappy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while(true);
    }
    @Override
    public boolean keyDown(Event e, int tecla){
        if(e.key == 32){
            pajaro.salto();
        }
        return true;
    }
    public boolean mouseDown(Event e,int x, int y){
        
        return true;
    }
}


