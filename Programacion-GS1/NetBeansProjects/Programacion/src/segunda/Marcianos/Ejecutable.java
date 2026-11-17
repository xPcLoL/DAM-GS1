package segunda.Marcianos;


import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ejecutable extends Applet implements Runnable{
    public static final int TIEMPO = 20;
    Thread animacion;
    Graphics noseve;
    Image imagen;
    ArrayList<Nave> naves;
    Gun gun;
    int contador = 0;
    ArrayList<Bala> balas;
    Color colores[] = {Color.BLUE, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED};
   
    
    
    @Override
    public void init() {
        
        naves = new ArrayList<Nave>();
        
        for(int i = 0; i < 30; i++){
            naves.add(new Nave(colores[(int)(Math.random()*4)],(int)(Math.random()*10+1)));
        }
        gun = new Gun();
        balas = new ArrayList<Bala>();
        
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
        
        gun.paint(noseve);
        for(int j = 0; j < naves.size(); j++){
            naves.get(j).paint(noseve);
        }
        for(int i = 0; i < balas.size(); i++){
                balas.get(i).paint(noseve);
        }
        
        g.drawImage(imagen,0,0,this);
        }
    
    
    @Override
    public void run() { //muy importante esta parte
        do{      
            for(int i = 0; i < balas.size(); i++){
                balas.get(i).mover();
                if(balas.get(i).y < 0){
                    balas.remove(i);
                    break;  
                }
                for(int p = 0; p < naves.size(); p++){
                    if(balas.get(i).intersects(naves.get(p))){
                        naves.remove(p);
                        break;
                    }
                }
            }
            for(int o = 0; o < naves.size(); o++){
                naves.get(o).mover();
            }
            contador += 1;
            if(contador == 20){
                contador = 0;
                naves.add(new Nave(colores[(int)(Math.random()*4)],(int)(Math.random()*10+1)));
            }
            repaint();
            try {
                Thread.sleep(TIEMPO); // como un task.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Ejecutable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while(true);
    }
    
    @Override
    public boolean keyDown(Event e, int tecla){
        if(e.key == Event.RIGHT){
            gun.update(true);
            return true;
        }
        if(e.key == Event.LEFT){
            gun.update(false);
            return true;
        }
        if(e.key == 32){
            balas.add(new Bala(gun.x + gun.width/2-Bala.ANCHURA/2, gun.y));
        }
        return true;
    }
    
    

}
