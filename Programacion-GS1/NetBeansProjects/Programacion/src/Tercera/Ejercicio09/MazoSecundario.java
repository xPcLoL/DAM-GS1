
package Tercera.Ejercicio09;

public class MazoSecundario extends Mazo{
    public static final int POSICIONX = 110;
    public static final int POSICIONY = 20;
    public MazoSecundario(){
        super(POSICIONX,POSICIONY,Carta.ANCHURA,Carta.ALTURA);
    }

    @Override
    public boolean añadir(Carta carta) {
            getCartas().add(carta);
       return true;
    }

    @Override
    public void recolocar() {
        this.extraer().actualizar(POSICIONX, POSICIONY);
    }
    
}
