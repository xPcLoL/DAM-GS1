package Tercera.Ejercicio03;


import segunda.Esqueletos.*;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Caminar extends Applet implements Runnable{
    Thread animacion;
    Graphics noseve;
    Image imagen;
    Image[][] fotogramas;
    int pasos = 0;
    DibujoAnimado[] da;
    int dibujo = 0;
    
    
    
    @Override
    public void init() {
        fotogramas = new Image[3][4];
        String[] lugares = {"Guerrillero/g","Hampon/h","Vaquero/v"};
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                        System.out.println("Buscando en: " + getCodeBase());

                fotogramas[i][j] = getImage(getCodeBase(),"Tercera/Ejercicio03/" + lugares[i] + (j+1) + ".gif");
            }
        }     
        da = new DibujoAnimado[3];
        da[0] = new DibujoAnimado(fotogramas[0]);
        da[1] = new DibujoAnimado(fotogramas[1]);
        da[2] = new DibujoAnimado(fotogramas[2]);
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
        da[dibujo].paint(noseve, this);
        g.drawImage(imagen,0,0,this);
        }
    
    
    @Override
    public void run() { //muy importante esta parte
        do{       
            da[dibujo].actualizar();
            repaint();
            try {
                Thread.sleep(150); // como un task.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Caminar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while(true);
    }
    public boolean keyDown(Event e, int tecla) {
    
    switch ((char)e.key) {
        case 'g':
        case 'G':
            dibujo = 0;
            break;
        case 'h':
        case 'H':
            dibujo = 1;
            break;
        case 'v':
        case 'V':
            dibujo = 2;
            break;
        default:
            break;
    }
    return true;
}

}

