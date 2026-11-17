
package Tercera.Ejercicio09;

public class MazoPalo extends Mazo{
    public static final int POSY = 20;
    private int palo;
    public MazoPalo(int posx) {
        super(posx, POSY,Carta.ANCHURA, Carta.ALTURA);
    }

    @Override
    public boolean añadir(Carta carta) {
        if((getCartas().isEmpty())&& (carta.getValor()== 1)){
            getCartas().add(carta);
            this.setPalo(carta.getPalo());
            return true;
        } else{
                if((palo == carta.getPalo()) && (carta.getValor()-1) == this.extraer().getValor()){
                    getCartas().add(carta);
                    return true;
                }
        }
        return false;
    }

    @Override
    public void recolocar() {
        this.extraer().actualizar(this.x, POSY);
        
    }

    public int getPalo() {
        return palo;
    }

    public void setPalo(int palo) {
        this.palo = palo;
    }
    
    
}
