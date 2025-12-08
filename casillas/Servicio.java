package casillas;

import java.util.ArrayList;
import monopoly.*;
import partida.*;

public class Servicio extends Propiedad {
    private static final float MULTIPLICADOR_ALQUILER = 2;
    private static final float MULTIPLICADOR_ALQUILER_DOS_SERVICIOS = 4;

    public Servicio(String nombre, int posicion, float valor, Jugador duenho, float alquiler, float hipoteca) {
        super(nombre, posicion, valor, duenho, alquiler, hipoteca);
    }

    /**
     * public Transporte(String nombre, int posicion, float valor, Jugador duenho,
     * float impuesto, float hipoteca) {
     * super(nombre, posicion, valor, duenho, impuesto, hipoteca);
     * }
     */
    @Override
    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada, ArrayList<ArrayList<Casilla>> pos) {
        float impuesto = 0;
        if (perteneceAJugador(actual)) {
        } else if (perteneceAJugador(banca)) {

        } else {
            int numServicios = 0;
            // ERROR

            for (Propiedad propiedad : this.getDuenho().getPropiedades()) {
                if (propiedad instanceof Servicio) {
                    numServicios++;
                }
            }
            if (numServicios == 1) {
                impuesto = this.getAlquiler() * tirada * MULTIPLICADOR_ALQUILER;
            } else if (numServicios == 2) {
                impuesto = this.getAlquiler() * tirada * MULTIPLICADOR_ALQUILER_DOS_SERVICIOS;
            }
            if (actual.getFortuna() < impuesto) { // No puede pagar
                Juego.consol.imprimir("O xogador "+actual.getNombre()+" non pode pagar, debe "+impuesto+" e ten "+actual.getFortuna());
                return false;
            } else {
                if(!this.getHipotecada()){
                actual.sumarFortuna(-impuesto);
                this.getDuenho().sumarFortuna(impuesto);
                Juego.consol.imprimir("O xogador " + actual.getNombre() + " pagou " + impuesto + " $ de aluguer a " + this.getDuenho().getNombre() + " por caer na casilla " + this.getNombre() + ".");
                actual.getEstatisticas().transAlq(impuesto);
                }
                else{
                    Juego.consol.imprimir("A casilla "+ this.getNombre() + " está hipotecada, polo que non se paga aluguer.");
                }
            }
        }
        return true;
    }

    @Override
    public boolean alquiler() {
        return true;
    }

    @Override
    public float valor() {
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\tnombre: ").append(this.getNombre()).append("\n");
        sb.append(" \ttipo: ").append(" Servicio").append("\n");
        sb.append(" \tpropietario: ").append(this.getDuenho().getNombre()).append("\n");
        sb.append(" \tvalor: ").append(this.getValor()).append("\n");
        sb.append(" \talquiler: ").append(this.getAlquiler()).append("\n}");
        for (Avatar avatarEnCasilla : this.getAvatares()) {
                sb.append(avatarEnCasilla.getJugador().getNombre()).append(", ");

            }
        return sb.toString();
    }

    
}