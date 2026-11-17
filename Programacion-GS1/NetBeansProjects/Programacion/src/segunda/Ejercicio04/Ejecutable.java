
package segunda.Ejercicio04;

public class Ejecutable {
    //constantes en mayusculas
    //el modificador static implica que el atributo al que se le
    // aplica puede ser accedido mediante el nombre de la clase
    public static final int FILAS = 6;
    public static final int COLUMNAS = 8;
    
    public static void main(String arg[]) throws Exception{
       Bombo b1;
       b1 = new Bombo();
       int[][] matriz = new int [FILAS][COLUMNAS];
       
       
       // Aquí para que se use la funcion una unica vez
       b1.sacaBola(matriz);
       
        for(int i = 0; i < matriz.length;i++){
            for(int j = 0; j < matriz[i].length;j++){
                System.out.printf("%d ",matriz[i][j]);
            }
            System.out.println();
        }
       
       
       /*
       //Aquí para que se use la funcion en cada iteracion del bucle
       for(int i = 0; i < matriz.length;i++){
            for(int j = 0; j < matriz[i].length;j++){
                matriz[i][j] = b1.sacaBolaUnica();
            }
        }
       for(int i = 0; i < matriz.length;i++){
            for(int j = 0; j < matriz[i].length;j++){
                System.out.printf("%d ",matriz[i][j]);
            }
            System.out.println();
        }
        */
    }
}
