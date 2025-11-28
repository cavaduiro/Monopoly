
package casillas;
import partida.Jugador;

public abstract class Propiedad extends CasillaNueva {
    private Jugador duenho;
    private float valor;
    public Propiedad(String nombre, int posicion) {
        super(nombre, posicion);
    }
    // Implementación específica de Propiedad
    // 
boolean perteneceAJugador(Jugador jugador){
        return this.duenho.equals(jugador);
    }

abstract boolean alquiler( );

abstract float valor( );

void comprar(Jugador jugador){
    this.duenho = jugador;
    jugador.sumarFortuna(-this.valor);
 }
}