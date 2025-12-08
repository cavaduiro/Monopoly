package monopoly; // The package for the current file

import casillas.*;
import java.util.ArrayList;
import partida.*; 

public abstract class Carta {
    public abstract boolean loxica(Jugador banca, Jugador actual, ArrayList<ArrayList<Casilla>> pos);
    public abstract void sumar();
}