
package Examen_Andres_Ivan;

public class Examen4 {
    public static void main(String arg[]){
        char frase[] = {'E', 'n', ' ', 'u', 'n', ' ', 'l', 'u', 'g', 'a', 'r', ' ', 'd', 'e', ' ', 'L', 'a', ' ', 'M', 'a',
'n', 'c', 'h', 'a'};
        char frase2[] = new char[frase.length];
        int contador = 0;
        
        for(int i = 0; i < frase.length;i++){
            if(!esVocal(frase[i])){
                frase2[contador] = frase[i];
                System.out.printf("%c",frase2[contador]);
                contador++;               
            }
        }
    }
    public static boolean esVocal(char carac){
        switch(carac){
                case 'A': case 'E': case 'I': case 'O': case 'U':
                    return true;
                   
                case 'a': case 'e': case 'i': case 'o': case 'u':
                    return true;
                    
                default:
                    return false;
            }
    }
}
