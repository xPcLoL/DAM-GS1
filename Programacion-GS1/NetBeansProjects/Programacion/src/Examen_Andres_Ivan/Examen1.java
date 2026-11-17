
package Examen_Andres_Ivan;

public class Examen1 {
    public static void main(String arg[]){
        int vector[] = {3,3,4,1,3,1,5,3,5,1};
        int vectorAux[]={0,0,0,0,0};
        
        for(int i = 0; i < vector.length;i++){
            vectorAux[vector[i]-1]++;
        }
        for(int j = 0; j < vectorAux.length;j++){
            System.out.printf(" El numero %d ha salido %d veces\n",j+1,vectorAux[j]);
        }
    }
}
