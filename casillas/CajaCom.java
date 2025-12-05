package casillas;

import monopoly.*;
import partida.*;
import java.util.ArrayList;

public class CajaCom extends Accion {
    private Jugador duenho;
    private Comunidade com;

    public CajaCom(String nombre, int posicion, Jugador dono) {
        super(nombre, posicion);
        this.duenho = dono;
        com = Comunidade.getInstance();
        
    }

    @Override
    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada, ArrayList<ArrayList<Casilla>> pos) {
        com.loxica(banca, actual, pos);
        return true;
    }

    @Override
    public Jugador getDuenho() {
       return duenho;
    }
}
