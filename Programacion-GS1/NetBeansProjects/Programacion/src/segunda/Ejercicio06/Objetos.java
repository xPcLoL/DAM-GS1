
package segunda.Ejercicio06;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Event;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Panel;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;

public class Objetos extends Frame{
    TextArea textArea;
    public static void main(String arg[]){
        Objetos app = new Objetos();
    }
    
    public Objetos(){
        super("Trabajando con objetos de java");
        
        setup();
        
        this.pack();
        this.setSize(400,400);
        this.setVisible(true);
    }

    public void setup(){
        Panel principal = new Panel();
        principal.setLayout(new GridLayout(3,3));
        Panel paneles[][] = new Panel [3][3];
        for(int i = 0; i < 3;i++){
            for(int j = 0; j < 3; j++){
                paneles[i][j] = new Panel();
            }
        }
        
        paneles[0][0].add(new Label("Campo de texto:"));
        paneles[0][0].add(new MiTextField("",15));
                
        textArea = new TextArea(5,10);
        textArea.setBackground(Color.yellow);
        
        String opciones[] = {"Sí", "No", "Quizás"};
        
       
        
        paneles[0][1].add(textArea);
        
        paneles[0][2].add(new MiBoton("Borrar!", textArea));
        
        paneles[1][0].add(new MiChoice(opciones,textArea));
        
        paneles[1][1].add(new MiCanvas());
        
        
        
        
        principal.add(paneles[0][0]);
        principal.add(paneles[0][1]);
        principal.add(paneles[0][2]);
        principal.add(paneles[1][0]);
        principal.add(paneles[1][1]);
        
        this.add("Center",principal);
    }
    
    public boolean handleEvent(Event ev){
        if(ev.id == Event.WINDOW_DESTROY){
            System.exit(0);
            return true;
        }
        return false;
    }
}

class MiTextField extends TextField{
//Esto es una subclase que creamos de la clase TextField, una mejora que nosotros hacemos
    public MiTextField(String string, int i){
        super(string, i);
        
    }
    public boolean action(Event ev, Object objeto){
            String texto = this.getText();
            this.setText(texto.toUpperCase());
            return true; 
    }
    
}

class MiBoton extends Button{
    TextArea texto;
    MiBoton(String string, TextArea texto){
        super(string);
        this.texto = texto;
    }
    
    public boolean action(Event ev, Object objeto){
        texto.setText(" ");
        return true;
    }
    
}

class MiChoice extends Choice{
    TextArea texto;
    public MiChoice(String opciones[], TextArea texto){
        super();
        for(int i = 0; i < opciones.length; i++){
            this.add(opciones[i]);
        }
        this.texto = texto;
    }
    
    public boolean action(Event ev, Object objeto){
        String textoo = this.getSelectedItem();
        texto.setText(textoo);
        return true;
    }
    
    
}


class MiCanvas extends Canvas{
    int posX, posY;
    public MiCanvas(){
        super();
        this.setSize(75,75);
        this.setBackground(Color.yellow);
        setForeground(Color.RED);
        this.setVisible(true);
        
    }
    
    public void paint(Graphics g){
        g.fillRect(posX, posY, 5, 5);
    }
    
    public boolean mouseDown(Event ev, int posX, int posY){
        this.posX = posX;
        this.posY = posY;
        repaint(); //importante poner esto cuh; gronkler
        return true;
    }
}
