package casillas;

import monopoly.Juego;
import partida.Jugador;

public class Especial extends Casilla {
    public Especial(String nombre, int posicion) {
        super(nombre, posicion);
    }

    @Override
    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada,java.util.ArrayList<java.util.ArrayList<Casilla>> pos) {
        if(this.getNombre().equals("Salida")){
        }
        else if(this.getNombre().equals("Cárcel")){
        Juego.consol.imprimir("Caíches en IrCarcel, irás ao cárcere...");
        actual.encarcelar(pos);
        }
        else if(this.getNombre().equals("Carcel")){
            Juego.consol.imprimir("Só pasaches pola Cárcel, non tes que facer nada.");
        }
        return true;
    }
}
