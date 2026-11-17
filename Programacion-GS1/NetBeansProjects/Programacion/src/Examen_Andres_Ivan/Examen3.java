
package Examen_Andres_Ivan;

public class Examen3 {
    public static void main(String arg[]){
        int matriz[][] = {{1,2,3,4},
                          {5,6,7,8},
                          {9,10,11,12},
                          {13,14,15,16}};
        int vector[] = {2,4,1,3};
        
        int [][]matriz2 = multiMatriz(matriz,vector);
        
        for(int i = 0; i < matriz2.length; i++){
            for(int j = 0; j < matriz2[i].length;j++){
                System.out.printf(" %d", matriz2[i][j]);
            }
            System.out.println();
        }
    }
    public static int[][]multiMatriz(int[][]matriz,int[]vector){
        int matriz2[][] = new int[matriz.length][matriz.length];
        
        for(int i = 0; i < matriz.length; i++){
            for(int j = 0; j < matriz[i].length;j++){
                matriz2[j][i] = matriz[j][i]*vector[i];
            }
        }
        return matriz2;
    }
}
