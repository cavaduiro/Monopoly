package casillas;

import monopoly.Juego;
import partida.Jugador;

public class Impuesto extends Casilla {
    private float impuesto;

    public Impuesto(String nombre, int posicion, float impuesto) {
        super(nombre, posicion);
        this.impuesto = impuesto;
    }

    @Override
    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada,java.util.ArrayList<java.util.ArrayList<Casilla>> pos) {
        if (actual.getFortuna() >= this.impuesto) {
            actual.sumarFortuna(-this.impuesto);
            actual.getEstatisticas().acImpPagado(this.impuesto);
            Juego.consol.imprimir("O xogador " + actual.getNombre() + " pagou " + this.impuesto
                    + " á banca por caer na casilla " + this.getNombre() + ".");
            banca.sumarFortuna(this.impuesto);
        }
        return true;
    }

}
