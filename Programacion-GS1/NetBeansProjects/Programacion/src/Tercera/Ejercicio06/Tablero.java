package Tercera.Ejercicio06;


import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablero extends Applet{
    public static final int TAM = 5;
    Image imagenes[][];
    Lugar lugares[][];
    Graphics noseve;
    Image imagen;
    Point blanco;
    AudioClip error,acierto,exito;
    
    @Override
    public void init() {
        imagen = this.createImage(700,500);
        noseve = imagen.getGraphics();
        try{
            error = getAudioClip(new URL(getCodeBase(),"Tercera/Ejercicio05/botonesysonidos/sonidos/error.wav"));
            acierto = getAudioClip(new URL(getCodeBase(),"Tercera/Ejercicio05/botonesysonidos/sonidos/correct.wav"));
            exito = getAudioClip(new URL(getCodeBase(),"Tercera/Ejercicio05/botonesysonidos/sonidos/exito.wav"));
        
        
        }catch(MalformedURLException e){};
        imagenes = new Image[TAM][TAM];
        lugares = new Lugar[TAM][TAM];
        for(int i = 0; i < TAM; i++){
            for(int j = 0; j < TAM; j++){
                imagenes[i][j] = getImage(getCodeBase(), "Tercera/Ejercicio06/botonesysonidos/botones/" + ((i*TAM)+j+1) + ".gif");
                lugares[i][j] = new Lugar(imagenes[i][j], (i*TAM)+j);
            }
        }
            blanco = new Point(TAM-1,TAM-1);
            
        
        
               
        this.setSize(250,300);
    }

    
    public void update(Graphics g){
        paint(g);
    }
    
public void paint(Graphics g) {
    // 1. Limpiamos el fondo
    noseve.setColor(Color.GREEN); 
    noseve.fillRect(0, 0, 700, 500); // Usa el tamaño que definiste en el buffer
    
    // 2. Dibujamos todas las piezas recorriendo la matriz
    for (int i = 0; i < TAM; i++) {
        for (int j = 0; j < TAM; j++) {
            if (lugares[i][j] != null) {
                // Llamamos al paint de Lugar pasándole la fila (i) y columna (j)
                lugares[i][j].paint(noseve, this, i, j);
            }
        }
    }
    
    // 3. Volcamos el buffer a la pantalla
    g.drawImage(imagen, 0, 0, this);
}

public boolean mover(Point click){
    Point desplazamiento, hasta;
    desplazamiento = new Point(delta(click.x,blanco.x), delta(click.y,blanco.y));
    if((desplazamiento.x == 0)&&(desplazamiento.y == 0))
        return false;
    if((desplazamiento.x != 0)&&(desplazamiento.y != 0))
        return false;
    hasta = new Point(click.x + desplazamiento.x,click.y + desplazamiento.y);
    if(!((blanco.x == hasta.x)&&(blanco.y == hasta.y)))
        mover(hasta);
    lugares[blanco.x][blanco.y].setImagen(lugares[click.x][click.y].getImagen());
    lugares[blanco.x][blanco.y].setValor(lugares[click.x][click.y].getValor());
    lugares[click.x][click.y].setImagen(null);
    lugares[click.x][click.y].setValor(0);
    blanco = click;
    return true;
}

public int delta(int a, int b){
    if(a == b) return 0;
    else return((b-a)/Math.abs(b-a));
}

public boolean mouseDown(Event ev, int x, int y){
    Point click;
    click = new Point(y/Lugar.DIM, x/Lugar.DIM);
    if(mover(click)){
        acierto.play();
        repaint();
    }
    else 
        error.play();
    return true;
}
}

