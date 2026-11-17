//Tratamiento de excepciones
package segunda.Ejercicio03;



public class Ejecutable {
    public static void main(String arg[]) throws Exception{
        
        Nif n1,n2;
        
        n1 = new Nif(3023677);
        //n2 = new Nif(3023677,'L');
        
        n1.mostrar();
        //n2.mostrar();
        //Esto de aqui abajo, el try y catch es para tratar excepciones, basicamente try intenta hacer algo,
        //y si sale el error que se pone ene el catch, se hace lo del catch, y luego sigu el programa
        try{
        System.out.println(("Hola "+(20/0)));
        }catch(ArithmeticException e){
            System.out.println("Se ha producido un error" + e.getMessage());
        }
        
        System.out.println("Adios");
    }
}
