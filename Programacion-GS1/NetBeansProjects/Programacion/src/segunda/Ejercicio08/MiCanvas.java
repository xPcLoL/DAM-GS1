
package segunda.Ejercicio08;

import java.awt.Canvas;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.util.ArrayList;
import java.util.List;

public class MiCanvas extends Canvas{
    private int tipo;
    DosPuntos actual;
    List<DosPuntos> lista; // <"nombre de la clase de los objetos de la clase list">

    public MiCanvas(int tipo) {
        super();
        lista = new ArrayList<DosPuntos>();
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public void paint(Graphics g) {
        for(int i = 0; i < lista.size();i++){
            lista.get(i).dibujar(g);    
        }
        if(actual != null){
            actual.dibujar(g);  
        }
    } 
    
    public boolean mouseDown(Event ev, int x, int y){
        actual = new DosPuntos(x,y,tipo);
        return true;
    }
    public boolean mouseDrag(Event ev, int x, int y){
        if(actual!=null){
            actual.posFinalX = x;
            actual.posFinalY =y;
            repaint();
        }    
        return true;
    }
    public boolean mouseUp(Event ev, int x, int y){
        lista.add(actual);
        actual = null;
        return true;
    }
}
