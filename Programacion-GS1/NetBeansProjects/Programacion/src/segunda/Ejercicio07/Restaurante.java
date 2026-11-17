
package segunda.Ejercicio07;

import java.awt.Choice;
import java.awt.Event;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.TextField;

public class Restaurante extends Frame{
    String comidas[] = {"Desayuno", "Almuerzo", "Cena"};
    String platos[][] = {{"Leche","Café","Huevos","Tostadas","Mermelada","Bacon"},
                         {"Paella","Sopa","Macarrones","Ensalada","Filete","Pescado","Albondigas","Hamburguesa"},
                         {"Tortilla","Sopa","Hamburguesa","Ensalada","Pollo","Bocadillo","Sandwich"}};
    Choice chComida;
    List[] listasPlatos;
    List presentar;
    TextField texto;
    
    public static void main(String arg[]){
        Restaurante app = new Restaurante();
        
    }
    
    public Restaurante(){
        super("Restaurante Saleasiano");
        
        setup();
        
        this.pack();
        this.setSize(275,275);
        this.setVisible(true);
    }
    
    public void setup(){
        this.add("North", new Label("Elige tu comanda", Label.CENTER));
        this.chComida = new Choice();
        
        for(int i = 0; i < comidas.length;i++){
            chComida.add(comidas[i]);
        }
        this.add(chComida);
        listasPlatos = new List[comidas.length];
        for(int j = 0; j < listasPlatos.length; j++){
            listasPlatos[j] = new List(5, true); //int numero_filas_que_se_ven, boolean permite_seleccion_multiple
            for(int k = 0; k < platos[j].length; k++){
                listasPlatos[j].add(platos[j][k]);
            }
        }
        presentar = listasPlatos[0];
        this.add("East",presentar);
        texto = new TextField(comidas[0] + ":", 40);
        this.add("South", texto);
        
    }
    
    public boolean handleEvent(Event ev){
        if(ev.id == Event.WINDOW_DESTROY){
            System.exit(0);
            return true;
        } else if(ev.id == Event.ACTION_EVENT){
            if(ev.target instanceof Choice){
                this.remove(presentar);
                presentar = listasPlatos[chComida.getSelectedIndex()];
                this.add("East",presentar);
                this.setVisible(true);
                texto.setText(chComida.getSelectedItem() + ":");
            }
        } else if ((ev.id == Event.LIST_SELECT)||(ev.id == Event.LIST_DESELECT)){
            String frase = chComida.getSelectedItem() + ": ";
            String elecciones []= presentar.getSelectedItems();
            for(int i = 0; i < elecciones.length;i++){
                frase = frase.concat(" ").concat(elecciones[i]);
            }
            texto.setText(frase);
        }
        return false;
    }
}
