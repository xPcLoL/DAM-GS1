
package Examen_Andres_Ivan;

public class Examen5 {
    public static void main(String arg[]){
        int numero = 666661;
        System.out.printf("El numero %d tiene un total de %d cifras pares\n",numero,parCifras(numero));
    }
    public static int parCifras(int numero){
        if(numero <= 0){
            return numero;
        } else {
            if(numero%2 == 0){
                return parCifras(numero/10)+1;
            }else{
                return parCifras(numero/10);
            }
        }
    }
}
