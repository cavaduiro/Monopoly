package casillas;

import java.util.ArrayList;
import monopoly.*;
import partida.*;

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
        return com.loxica(banca, actual, pos);
    }

    @Override
    public Jugador getDuenho() {
       return duenho;
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
