package Tercera.Ejercicio05;


import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Blackjack extends Applet{
    public static final int NUMCARTAS = 52;
    public static final int CPP = 13;
    Image imagenes[];
    Graphics noseve;
    Image imagen;
    Baraja baraja;
    Mano crupier, jugador;
    Button boton1,boton2;
    
    @Override
    public void init() {
        imagen = this.createImage(700,500);
        noseve = imagen.getGraphics();
        imagenes = new Image[NUMCARTAS];
        String palos[] = {"_of_clubs.png","_of_diamonds.png", "_of_hearts.png","_of_spades.png"};
        for(int i = 0; i < NUMCARTAS; i++){
            imagenes[i] = getImage(getCodeBase(),"Tercera/Ejercicio05/Cartas/" + ((i%CPP+1) + palos[i / CPP]));
        }
        baraja = new Baraja(imagenes);
        crupier = new Mano(10);
        jugador = new Mano(300);
        crupier.anadirCarta(baraja.sacarCarta());
        
        jugador.anadirCarta(baraja.sacarCarta());
        jugador.anadirCarta(baraja.sacarCarta());
        
        this.setLayout(new BorderLayout());
        Panel panelBajo = new Panel();
        boton1 = new Button("Hit me!");
        boton2 = new Button("Stand");
        panelBajo.add(boton1);
        panelBajo.add(boton2);
        
        this.add("South", panelBajo);
               
        this.setSize(400,500);
    }

    
    public void update(Graphics g){
        paint(g);
    }
    
public void paint(Graphics g){
    noseve.setColor(Color.GREEN); 
    noseve.fillRect(0, 0, 400, 500);

        crupier.paint(noseve, this);
        jugador.paint(noseve, this);
    
    g.drawImage(imagen, 0, 0, this);
}

public boolean action(Event e, Object obj){
    if(e.target instanceof Button){
        if(e.arg.equals("Hit me!")){
            if(!jugador.busted()){
                jugador.anadirCarta(baraja.sacarCarta());
            } 
            if(jugador.busted()){
                while(crupier.menosDe17()){
                    crupier.anadirCarta(baraja.sacarCarta());
                    repaint();
                }
            }
           
            
        } else {
            while(crupier.menosDe17()){
                crupier.anadirCarta(baraja.sacarCarta());
                repaint();
            }
        }
    }
    return true;
}


}

