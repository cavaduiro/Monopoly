package casillas;

import monopoly.*;
import partida.*;
import java.util.ArrayList;

public class CasillaSorte extends Accion {
    private Jugador duenho;

    public CasillaSorte(String nombre, int posicion, Jugador banca) {
        super(nombre, posicion);
        this.duenho = banca;
    }

    @Override
    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada, ArrayList<ArrayList<Casilla>> pos) {
        sorte.loxica(banca, actual, pos);
        return true;
    }
}
