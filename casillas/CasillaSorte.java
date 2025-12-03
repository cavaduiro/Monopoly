package casillas;

import monopoly.*;
import partida.*;
import java.util.ArrayList;

public class CasillaSorte extends Accion {
    private Sorte sorte;
    public CasillaSorte(String nombre, int posicion, Sorte sorte) {
        super(nombre, posicion);
        this.sorte = sorte;
    }
    @Override
    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada,ArrayList<ArrayList<Casilla>> pos){
        sorte.loxica(banca, actual, pos);
        return true;
}
