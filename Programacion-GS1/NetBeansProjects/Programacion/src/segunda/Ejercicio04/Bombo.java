
package segunda.Ejercicio04;

import java.util.Random;

public class Bombo {
    int[] bolas;
    int numBolas = 48;
    Random aleatorio;

    public Bombo() {
        this.bolas = new int [numBolas];
        
        for(int i = 0; i < bolas.length;i++){
            bolas[i] = i+1;
        }
        
        this.aleatorio = new Random();
        
    }
    
    public void sacaBola(int [][] matriz){
        int contador = 0;
        int indice;
        for(int i = 0; i < matriz.length;i++){
            for(int j = 0; j < matriz[i].length;j++){
                indice = aleatorio.nextInt(numBolas-contador);
                matriz[i][j] = bolas[indice];
                bolas[indice] = bolas[numBolas-contador-1];
                contador++;
            }
        }
    }
    
    public int sacaBolaUnica(){
        int numAleatorio = aleatorio.nextInt(numBolas);
        int bola = bolas[numAleatorio];
        bolas[numAleatorio] = bolas[numBolas-1];
        numBolas--;
        return bola;
    }
    
    
    
}
