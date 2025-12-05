package casillas;

import monopoly.*;
import partida.*;
import java.util.ArrayList;

public class CasillaSorte extends Accion {
    private Jugador duenho;
    private final Sorte sorte;

    public CasillaSorte(String nombre, int posicion, Jugador banca) {
        super(nombre, posicion);
        this.duenho = banca;
        sorte = Sorte.getInstance();
    }

    @Override
    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada, ArrayList<ArrayList<Casilla>> pos) {
        sorte.loxica(banca, actual, pos);
        return true;
    }
    @Override
    public Jugador getDuenho() {
       return duenho;
    }
}
