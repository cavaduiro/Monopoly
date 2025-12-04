package casillas;
import java.util.ArrayList;
import monopoly.*;
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
            actual.sumarFortuna(-impuesto);
            super.getDuenho().sumarFortuna(impuesto);
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
   
    

    
}