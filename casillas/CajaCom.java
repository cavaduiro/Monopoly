package casillas;

import monopoly.*;
import partida.*;
import java.util.ArrayList;

public class CajaCom extends Accion {
    private Jugador duenho;
    //private Comunidade comunidade;
    //isto debería ser un singleton

    public CajaCom(String nombre, int posicion, Jugador dono) {
        super(nombre, posicion);
        //this.comunidade = comunidade;
        this.duenho = dono;
    }

    @Override
    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada, ArrayList<ArrayList<Casilla>> pos) {
        comunidade.loxica(banca, actual, pos);
        return true;
    }
}
