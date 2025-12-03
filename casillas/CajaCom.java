package casillas;

import monopoly.*;
import partida.*;
import java.util.ArrayList;

public class CajaCom extends Accion {
    private Comunidade comunidade;
    public CajaCom(String nombre, int posicion, Comunidade comunidade) {
        super(nombre, posicion);
        this.comunidade = comunidade;
    }
    @Override
    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada,ArrayList<ArrayList<Casilla>> pos){
        comunidade.loxica(banca, actual, pos);
        return true;
}
