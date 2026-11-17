package Tercera.Examen3ev;



import java.applet.Applet;

import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;


public class SpaceLadder extends Applet implements Runnable{  

    Graphics noseve;
    Image imagen;
    Thread animacion;
    
    Jugador jugador;
    int direccion = 1; //direccion 1 derecha, direccion 2 izquierda y direccion 3 escalera
    Image imagenesDerecha[];
    Image imagenesIzquierda[];
    Image imagenesEscalera[];
    Plataforma plataformas[];
    Escalera escaleras[];
    int contadorAnimacion = 0;

    

    
    @Override
    public void init() {
        imagen = this.createImage(700,800);
        noseve = imagen.getGraphics();
        imagenesDerecha = new Image[4];
        imagenesIzquierda = new Image[4];
        imagenesEscalera = new Image[4];
        plataformas = new Plataforma[3];
        escaleras = new Escalera[2];
        for(int q = 0; q < imagenesDerecha.length; q++){
            imagenesDerecha[q] = getImage(getCodeBase(), "Tercera/Examen3ev/derecha_" + (q+1) + ".png");
        }
        for(int w = 0; w < imagenesIzquierda.length; w++){
            imagenesIzquierda[w] = getImage(getCodeBase(), "Tercera/Examen3ev/izquierda_" + (w+1) + ".png");
        }
        for(int e = 0; e < imagenesEscalera.length; e++){
            imagenesEscalera[e] = getImage(getCodeBase(), "Tercera/Examen3ev/escalera_" + (e+1) + ".png");
        }
        for(int i = 0; i < plataformas.length; i++){
            plataformas[i] = new Plataforma(160*i+120);
        }
        jugador = new Jugador(getImage(getCodeBase(), "Tercera/Examen3ev/Derecha_1.png"),120,130);
            escaleras[0] = new Escalera(120, 120, getImage(getCodeBase(), "Tercera/Examen3ev/Escalera.png"));
            escaleras[1] = new Escalera(420, 280, getImage(getCodeBase(), "Tercera/Examen3ev/Escalera.png"));
        
        
        
        
        this.setSize(700,800);
    }
    
    public void start(){
        animacion = new Thread(this);
        animacion.start();
    }

    public void update(Graphics g){
        paint(g);
    }
@Override
public void paint(Graphics g) {
    noseve.setColor(Color.GREEN); 
    noseve.fillRect(0, 0, 700, 800);
    
    for(int i = 0; i < plataformas.length; i++){
        plataformas[i].paint(noseve);
    }
    for(int j = 0; j < escaleras.length; j++){
        escaleras[j].paint(noseve, this);
    }
    jugador.paint(noseve, this);

    g.drawImage(imagen, 0, 0, this);
}

@Override
    public void run() {
        do{
            if(jugador.intersects(escaleras[0])){
                if((jugador.y > escaleras[0].y+6)&&jugador.y < escaleras[0].y+escaleras[0].height-6){
                    jugador.mover(jugador.getDireccion());
                }else {
                    for(int o = 0; o < plataformas.length; o++){
                        if(jugador.intersects(plataformas[o])){
                            jugador.y = plataformas[o].y+plataformas[o].height/2-2;
                        }
                    }
                }
            }else{
        
                if(jugador.intersects(escaleras[1])){
                    if((jugador.y > escaleras[1].y+6)&&jugador.y < escaleras[1].y+escaleras[1].height-6){
                        jugador.mover(jugador.getDireccion());
                    }else {
                        for(int o = 0; o < plataformas.length; o++){
                            if(jugador.intersects(plataformas[o])){
                                jugador.y = plataformas[o].y+plataformas[o].height/2-2;
                            }
                        }
                    }
                }else{
                    jugador.mover(jugador.getDireccion());
                }
            }
 
            switch(direccion){
                case 1:
                    jugador.setImagen(imagenesDerecha[contadorAnimacion]);
                    contadorAnimacion++;
                    if(contadorAnimacion == imagenesDerecha.length)
                        contadorAnimacion = 0;
                    break;
                case 2: 
                    jugador.setImagen(imagenesIzquierda[contadorAnimacion]);
                    contadorAnimacion++;
                    if(contadorAnimacion == imagenesIzquierda.length)
                        contadorAnimacion = 0;
                    break;
                case 3:
                    jugador.setImagen(imagenesEscalera[contadorAnimacion]);
                    contadorAnimacion++;
                    if(contadorAnimacion == imagenesEscalera.length)
                        contadorAnimacion = 0;
                    break;
            }
           
            repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
            };
        }while(true);
    }

@Override
public boolean keyDown(Event ev, int tecla){
    for(int j = 0; j < plataformas.length; j++){
        if(jugador.intersects(plataformas[j])){
            if(tecla == Event.LEFT){
                direccion = 2;
                jugador.setDireccion(2);
            }
            if(tecla == Event.RIGHT){
                direccion = 1;
                jugador.setDireccion(1);
            }
        }    
    }
    for(int i = 0; i < escaleras.length; i++){
        if(jugador.intersects(escaleras[i])){
            if(tecla == Event.UP){
                direccion = 3;
                jugador.setDireccion(3);
            }
            if(tecla == Event.DOWN){
                direccion = 3;
                jugador.setDireccion(4);
            }
        }
    }
        
    return true;
}  
}

