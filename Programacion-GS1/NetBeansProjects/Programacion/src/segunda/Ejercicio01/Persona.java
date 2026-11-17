
package segunda.Ejercicio01;

public class Persona {
    private String nombre;
    private String nif;
    private double altura;
    private String direccion;
    private String codPostal;
    //Lo de aqui abajo es el metodo constructor
    public Persona(String nif,String nom,double alt,String dir,String cp){
        nombre = nom;
        this.nif = nif;
        altura = alt;
        direccion = dir;
        codPostal = cp;
    }
    
    public Persona(String nom, String dir){
        this(dir,nom,0,"nolose","nose");
        //this() lo que es es una llamada a la clase public Persona, le paso los parametros
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nom){
        nombre = nom;
    }
    public String getDireccion(){
        return direccion;
    }
    public void setDireccion(String dir){
        direccion = dir;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }
    public void mostrar(){
        System.out.printf("%s\n%s\n%f\n%s\n%s\n",nif,nombre,altura,direccion,codPostal);
    }
}
