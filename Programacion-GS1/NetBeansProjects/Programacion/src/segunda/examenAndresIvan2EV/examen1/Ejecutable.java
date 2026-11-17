
package segunda.examenAndresIvan2EV.examen1;


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
    Thread animacion;
    Graphics noseve;
    Image imagen;
    ArrayList<Bloque> bloques;
    Color color[];
    int contador = 0;
    int cuenta = 0;
    int segundos = 0;
    boolean gameOver = false;
   
    
    
    @Override
    public void init() {
        bloques = new ArrayList<Bloque>();
        color = new Color[10];
        color[0]= Color.BLUE;
        color[1]= Color.CYAN;
        color[2]= Color.DARK_GRAY;
        color[3]= Color.GREEN;
        color[4]= Color.LIGHT_GRAY;
        color[5]= Color.MAGENTA;
        color[6]= Color.ORANGE;
        color[7]= Color.PINK;
        color[8]= Color.RED;
        color[9]= Color.WHITE;
        
        for(int i = 0; i < 120; i = i + 12){
            for(int j = 0; j < 60; j = j + 6){
                bloques.add(new Bloque(i,j,color[i/12]));               
                }
            }
        
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
        noseve.setColor(Color.WHITE);
        noseve.drawString("tiempo: "+segundos, 10, 280);

        if(gameOver == true){
            noseve.setColor(Color.RED);
            noseve.drawString("GAME OVER",100,150);
        }

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                bloques.get(i*10+j).paint(noseve);
                }
            }
        
        g.drawImage(imagen,0,0,this);
        }
    
    
    @Override
    public void run() { //muy importante esta parte
        do{       
            if(gameOver == false){
                cuenta++;
                if(cuenta >= 50){
                    cuenta = 0;
                    segundos++;
                }
            }

            repaint();
            try {
                Thread.sleep(20); // como un task.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Ejecutable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while(true);
    }
    public boolean mouseDown(Event e, int x, int y){
        for(int i = 0; i < bloques.size(); i++){
            if(bloques.get(i).encontrado(x,y)){
                bloques.get(i).visible = false;
            }
        }
        int quedan = 0;
        for(int i = 0; i < bloques.size(); i++){
            if(bloques.get(i).visible == true){
                quedan++;
            }
        }
        if(quedan == 0){
            gameOver = true;
        }
    return true;
}

}
    
    



