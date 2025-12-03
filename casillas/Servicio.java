package casillas;
import java.util.ArrayList;
import monopoly.*;
import partida.*;
public class Servicio extends Propiedad {
    private static final float MULTIPLICADOR_ALQUILER = 2;
    private static final float MULTIPLICADOR_ALQUILER_DOS_SERVICIOS = 4;

    public Servicio(String nombre, int precioCompra) {
        super(nombre, precioCompra);
    }
    @Override
    public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada,ArrayList<ArrayList<Casilla>> pos){
        float impuesto=0;
        if (perteneceAJugador(actual)){  
        }
        else if(perteneceAJugador(banca)){

        }
        else{
            int numServicios=0;
            //ERROR
            for (Casilla propiedad : super.getDuenho().getPropiedades()) {
                if (propiedad instanceof Servicio) {
                    numServicios++;
                }
            }
            if (numServicios == 1) {
                impuesto = this.getAlquiler()* tirada * MULTIPLICADOR_ALQUILER;
            } else if (numServicios == 2) {
                impuesto = this.getAlquiler()*tirada * MULTIPLICADOR_ALQUILER_DOS_SERVICIOS;         
            }
            if(actual.getFortuna()<impuesto){ //No puede pagar
                return false;
            }
            else{
                actual.sumarFortuna(-impuesto);
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