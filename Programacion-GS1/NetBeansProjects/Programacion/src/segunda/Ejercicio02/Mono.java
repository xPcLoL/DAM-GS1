
package segunda.Ejercicio02;

public class Mono extends Animal{
    String especie;
    boolean raboTrepador;

    public Mono(String especie, boolean raboTrepador, int id, String fechNac, String procedencia, double peso, boolean vacunado, char sexo) {
        super(id, fechNac, procedencia, peso, vacunado, sexo);
        this.especie = especie;
        this.raboTrepador = raboTrepador;
    }
    
    @Override
    void hablar(){
        System.out.printf("Soy un mono %s , que nací en %s\n",especie,getProcedencia());
    }
    
}
