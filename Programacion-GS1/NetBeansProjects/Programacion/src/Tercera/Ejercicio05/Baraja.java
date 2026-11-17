
package Tercera.Ejercicio05;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;

public class Baraja {
    ArrayList<Carta> cartas;
    
    public Baraja(Image imagenes[]){
        cartas = new ArrayList<Carta>();
        for(int i = 0; i < Blackjack.NUMCARTAS; i++){
            cartas.add(new Carta(imagenes[i], (i%Blackjack.CPP+1)));
        }
        Collections.shuffle(cartas); //desordena los elementos de una lista
    }
    
    public Carta sacarCarta(){
        Carta carta = cartas.get(0);
        cartas.remove(0);
        return carta;
    }
}
