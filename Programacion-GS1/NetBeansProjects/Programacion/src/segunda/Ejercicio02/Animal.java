
package segunda.Ejercicio02;

public abstract class Animal {
    int ID;
    String fechaNac;
    private String procedencia;
    double peso;
    boolean vacunado;
    char sexo;
    
    public Animal(int id,String fechNac,String procedencia,double peso,boolean vacunado,char sexo){
        this.ID = id;
        this.fechaNac = fechNac;
        this.peso = peso;
        this.procedencia = procedencia;
        this.vacunado = vacunado;
        this.sexo = sexo;
    }
    //Al poner abstract a void hablar significa que las subclases de la clase Animal estan obligadas a 
    //implementar el metodo hablar();
    abstract void hablar();

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public boolean getVacunado() {
        return vacunado;
    }

    public void setVacunado(boolean vacunado) {
        this.vacunado = vacunado;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }
    
}
