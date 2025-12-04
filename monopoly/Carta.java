package monopoly; // The package for the current file

import casillas.*;
import partida.*; 

import java.util.ArrayList; 

public abstract class Carta {
    public abstract void loxica(Jugador banca, Jugador actual, ArrayList<ArrayList<Casilla>> pos);
    public abstract void sumar();
}