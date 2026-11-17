
package segunda.Ejercicio02;

public class Perro extends Animal{
    String raza;
    String nombre;

    public Perro(String raza, String nombre, int id, String fechNac, String procedencia, double peso, boolean vacunado, char sexo) {
        super(id, fechNac, procedencia, peso, vacunado, sexo);
        this.raza = raza;
        this.nombre = nombre;
    }

    @Override
    void hablar(){
        System.out.printf("Soy un perro %s y me llamo %s\n",raza,nombre);
    }
}
