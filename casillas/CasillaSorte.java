package casillas;

import java.util.ArrayList;
import monopoly.*;
import partida.*;

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
        return sorte.loxica(banca, actual, pos);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\tnombre: ").append(this.getNombre()).append("\n");
        sb.append(" \ttipo: ").append(" Accion").append("\n");
          for (Avatar avatarEnCasilla : this.getAvatares()) {
                sb.append(avatarEnCasilla.getJugador().getNombre()).append(", ");

            }
        return sb.toString();
    }
    
}
