
package Examen_Andres_Ivan;

public class Examen2 {
    public static void main(String arg[]){
        String frase = "en un lugar de la mancha de cuyo nombre no quiero acordarme ";
        
        int inicio = 0;
        int fin = frase.indexOf(" ");
        
        while(fin != -1){
            boolean tieneE = false;
            for(int i = inicio; i < fin;i++){
                if(frase.charAt(i)=='e'){                  
                    tieneE = true;
                    i = fin;
                }
            }
            if(tieneE){
                frase = frase.substring(0,inicio).concat(frase.substring(fin+1));
                inicio = 0;
                fin = frase.indexOf(" ");
            }else{
                inicio = fin+1;
                fin = frase.indexOf(" ",inicio); 
            }
        }
        System.out.println(frase);

    }
}
