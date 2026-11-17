
package segunda.Ejercicio03;

public class Nif {
    public int dni;
    public char letra;
    //final es para constantes
    public final char tabla[] = {'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'};
    
    
    //Fijate bien donde van los throw para excepciones y eso
    public Nif(int numero, char letra) throws Exception {
        this.dni = numero;
        if(letra != tabla[dni%tabla.length]){
            throw new Exception("Has calculado mal la letra");
        }
        this.letra = letra;
    }
    
    public Nif(int numero){
        this.dni = numero;
        this.letra = tabla[numero%tabla.length];
    }
    
    public void mostrar(){
        System.out.printf("%d-%c\n", dni,letra);
    }
    
}
