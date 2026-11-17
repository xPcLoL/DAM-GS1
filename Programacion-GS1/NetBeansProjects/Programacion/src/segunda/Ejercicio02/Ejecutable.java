
package segunda.Ejercicio02;

public class Ejecutable {
    public static void main(String arg[]){
        Mono a1;
        Perro a2;
        a1 = new Mono("Gorila",false,1,"12/07/2025","Angola",500.4,true,'M');
        a2 = new Perro("labrador","Motos",2,"23/08/2023","España",21.2,true,'M');
        
        a1.hablar();
        a2.hablar();    
    }
}
