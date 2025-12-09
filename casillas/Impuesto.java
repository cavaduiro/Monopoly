package casillas;

import exception.valorInvalido.ExcepcionSinCartos;
import monopoly.Juego;
import partida.Jugador;

public class Impuesto extends Casilla {
    private float impuesto;
    private Jugador duenho;

    public Impuesto(String nombre, int posicion, Jugador dono, float impuesto) {
        super(nombre, posicion);
        this.impuesto = impuesto;
        this.duenho = dono;
    }
    public float getImpuesto(){
        return this.impuesto;
    }

    @Override
    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada, java.util.ArrayList<java.util.ArrayList<Casilla>> pos){
        try {
            if (actual.getFortuna() < this.impuesto) {
                throw new ExcepcionSinCartos(actual.getNombre(), (int)actual.getFortuna(), (int)this.impuesto);
            }
            actual.sumarFortuna(-this.impuesto);
            actual.getEstatisticas().acImpPagado(this.impuesto);
            Juego.consol.imprimir("O xogador " + actual.getNombre() + " pagou " + this.impuesto
                    + " á banca por caer na casilla " + this.getNombre() + ".");
            banca.sumarFortuna(this.impuesto);
            return true;
        } catch (ExcepcionSinCartos e) {
            Juego.consol.imprimir(e.getMessage());
            return false;
        }
    }
    @Override
    public Jugador getDuenho() {
       return duenho;
    }
 @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\tnombre: ").append(this.getNombre()).append("\n");
        sb.append(" \ttipo: ").append(" Servicio").append("\n");
        sb.append(" \talquiler: ").append(this.getImpuesto()).append("\n}");
        return sb.toString();
    }
}
