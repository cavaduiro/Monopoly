
package casillas;
import partida.Jugador;
import java.util.ArrayList;
import partida.*;

public abstract class Propiedad extends Casilla {
    private Jugador duenho;
    private float valor;
    private float alquiler;
    public Propiedad(String nombre, int posicion) {
        super(nombre, posicion);
    }
    // Implementación específica de Propiedad
    // 
    public boolean perteneceAJugador(Jugador jugador){
        return this.duenho.equals(jugador);
    }

    public abstract boolean alquiler( );

    public abstract float valor( );

    public void comprar(Jugador jugador){
        this.duenho = jugador;
        jugador.sumarFortuna(-this.valor);
    }
    public Jugador getDuenho(){
        return this.duenho;
    }
    public  float getValor(){
        return this.valor;
    }
    public  float getAlquiler(){
        return this.alquiler;
    }
    @Override
        public abstract boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada,ArrayList<ArrayList<Casilla>> posiciones);
    }