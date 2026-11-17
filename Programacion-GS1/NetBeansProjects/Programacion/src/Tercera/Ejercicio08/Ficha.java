
package Tercera.Ejercicio08;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Ficha extends Rectangle{
    public static final int TAM = 60;
    int valor;
    Image imagen;
    private ArrayList<Integer> numerosApostados;
    
    public Ficha(int x, int y, int valor, Image imagen){
        super(x,y,TAM,TAM);
        this.valor = valor;
        this.imagen = imagen;
        numerosApostados = new ArrayList<Integer>();
    }
    
    public void paint(Graphics g, Applet a){
        g.drawImage(imagen, x, y,TAM,TAM, a);
    }

    public ArrayList<Integer> getNumerosApostados() {
        return numerosApostados;
    }

    public void setNumerosApostados(ArrayList<Integer> numerosApostados) {
        this.numerosApostados = numerosApostados;
    }
    
    public void actualizarPos(int x, int y){
        this.x = x - (TAM/2);
        this.y = y - (TAM/2);
    }
    
    public void cargarNumerosApostados(Casilla casillas[][]){
        this.numerosApostados.clear();
        for(int i = 0; i < casillas.length; i++){
            for(int j = 0; j < casillas[i].length; j++){
                if(this.intersects(casillas[i][j])){
                    this.numerosApostados.add(casillas[i][j].valor);
                }
            }
        }
    }
}
