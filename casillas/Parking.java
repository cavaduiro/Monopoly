package casillas;

import java.util.ArrayList;

import monopoly.Juego;
import partida.*;

public class Parking extends Accion {
    private Jugador banca;

    public Parking(String nombre, int posicion, Jugador banca) {
        super(nombre, posicion);
        this.banca = banca;
    }

    @Override
    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada, ArrayList<ArrayList<Casilla>> pos) {
        Juego.consol.imprimir("O xogador " + actual.getNombre() + " recibirá " + banca.getFortuna() + " en impostos.");
        actual.sumarFortuna(banca.getFortuna());
        banca.setFortuna(0);
        return true;
    }

}
