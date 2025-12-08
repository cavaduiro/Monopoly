package casillas;
import java.util.ArrayList;

import monopoly.Juego;
import partida.*;
public class Transporte extends Propiedad {
    public Transporte(String nombre, int posicion, float valor, Jugador duenho, float impuesto, float hipoteca) {
        super(nombre, posicion, valor, duenho, impuesto, hipoteca);
    }
    @Override
    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada,ArrayList<ArrayList<Casilla>> pos){
        if (perteneceAJugador(actual)){  
        }
        else if(perteneceAJugador(banca)){
        }
        else{
            int numTransportes=0;
            //ERROR
            for (Casilla propiedad : super.getDuenho().getPropiedades()) {
                if (propiedad instanceof Transporte) {
                    numTransportes++;
                }
            }
            float impuesto = this.getAlquiler()*numTransportes;
            if(actual.getFortuna()<impuesto){ //No puede pagar
                return false;
            }
            else{
            if(!this.getHipotecada()){
            actual.sumarFortuna(-impuesto);
            super.getDuenho().sumarFortuna(impuesto);
            Juego.consol.imprimir("O xogador " + actual.getNombre() + " pagou " + impuesto + " $ de aluguer a " + super.getDuenho().getNombre() + " por caer na casilla "+ this.getNombre() + ".");
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
   public boolean alquiler( ){
    return true;
   }
   @Override
   public float valor( ){
    return 0;
   }
   
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\tnombre: ").append(this.getNombre()).append("\n");
        sb.append(" \ttipo: ").append(" Transporte").append("\n");
        sb.append(" \tpropietario: ").append(this.getDuenho().getNombre()).append("\n");
        sb.append(" \tvalor: ").append(this.getValor()).append("\n");
        sb.append(" \talquiler: ").append(this.getAlquiler()).append("\n}");
          for (Avatar avatarEnCasilla : this.getAvatares()) {
                sb.append(avatarEnCasilla.getJugador().getNombre()).append(", ");

            }
        return sb.toString();
    }
    

    
}