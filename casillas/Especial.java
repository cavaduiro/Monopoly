package casillas;

import monopoly.Juego;
import partida.*;

public class Especial extends Casilla {

    private Jugador duenho;

    public Especial(String nombre, int posicion, Jugador banca) {
        super(nombre, posicion);
        this.duenho = banca;
    }

    @Override
    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada,
            java.util.ArrayList<java.util.ArrayList<Casilla>> pos) {
        if (this.getNombre().equals("Salida")) {
        } else if (this.getNombre().equals("Cárcel")) {
            Juego.consol.imprimir("Caíches en IrCarcel, irás ao cárcere...");
            actual.encarcelar(pos);
        } else if (this.getNombre().equals("Carcel")) {
            Juego.consol.imprimir("Só pasaches pola Cárcel, non tes que facer nada.");
        }
        return true;
    }

    @Override
    public Jugador getDuenho() {
        return duenho;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.getNombre().equalsIgnoreCase("Cárcel")) {
            sb.append("\tNome: Cárcel\n");
            sb.append("\tTipo: Especial\n");
            sb.append("{\tCusto salir: 5000000\n");
            sb.append(" \tXogadores na casilla: [");
            for (Avatar avatarEnCasilla : this.getAvatares()) {
                sb.append(avatarEnCasilla.getJugador().getNombre()).append(", ");

            }
        }
        else if (this.getNombre().equalsIgnoreCase("Salida")) {
            sb.append("\tNome: Salida\n");
            sb.append("\tTipo: Especial\n");
            sb.append(" \tXogadores na casilla: [");
            for (Avatar avatarEnCasilla : this.getAvatares()) {
                sb.append(avatarEnCasilla.getJugador().getNombre()).append(", ");

            }
        }
        else {
            sb.append("\tNome: IrCarcel\n");
            sb.append("\tTipo: Especial\n");
            sb.append(" \tXogadores na casilla: [");
            for (Avatar avatarEnCasilla : this.getAvatares()) {
                sb.append(avatarEnCasilla.getJugador().getNombre()).append(", ");

            }
        }
        return sb.toString(); 
    }
}
