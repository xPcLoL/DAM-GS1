
package segunda.Ejercicio08;

import java.awt.Graphics;

public class DosPuntos {
    public static final int LINEA = 0;
    public static final int OVALO = 1;
    public static final int RECTANGULO = 2;   
    
    
    int posInicialX;
    int posFinalX;
    int posInicialY;
    int posFinalY;
    
    int tipo;

    public DosPuntos(int posInicialX, int posInicialY, int posFinalX, int posFinalY, int tipo) {
        this.posInicialX = posInicialX;
        this.posFinalX = posFinalX;
        this.posInicialY = posInicialY;
        this.posFinalY = posFinalY;
        this.tipo = tipo;
    }
    
    public DosPuntos(int posInicioX, int posInicioY, int tipo){
        this(posInicioX,posInicioY,posInicioX,posInicioY,tipo);
    }
    public void dibujar(Graphics gg){
        int anchura, altura;
        anchura = Math.abs(posFinalX - posInicialX);
        altura = Math.abs(posFinalY - posInicialY);
        switch(tipo){
            case LINEA :
                gg.drawLine(posInicialX, posInicialY, posFinalX, posFinalY);
                break;
            case OVALO :
                gg.drawOval(posInicialX, posInicialY, anchura, altura);
                break;
            case RECTANGULO: 
                gg.drawRect(posInicialX, posInicialY, anchura, altura);
                break;
        }
    }
}
