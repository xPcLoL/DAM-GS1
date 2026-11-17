
package segunda.Ejercicio01;

public class Programa {
    public static void main(String arg[]){
        
        Persona p1,p2,p3;
        p2 = new Persona("Jacinto","Cementerio");
        p1 = new Persona("Y3023677L","Andres Sebastian",1.78,"Calle La Caza 7","50002");
        p3 = new Persona("Y3023677L","Andres Sebastian",1.78,"Calle La Caza 7","50002");
        
        p1.mostrar();
        p3.mostrar();
        
        // direccion es private por lo que no puedo hacer esto -> p3.direccion = "Calle La Prima Angelica 10";
        p3.mostrar();
        
        p3.setDireccion("Calle del maquinista de la general");
        p3.mostrar();
        
    }
    
    
}
