package casillas;
import java.util.ArrayList;
import monopoly.*;
import partida.*;
public class Solar extends Propiedad {
    public Solar(String nombre, int posicion, float valor, float alquiler, float hipoteca, Jugador duenho) {
        super(nombre, posicion, valor, alquiler, hipoteca, duenho);
    }

    
    

    //OVERRIDES
       @Override
        public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada,ArrayList<ArrayList<Casilla>> posiciones)
        {

            return false;
        }
        @Override
        public float valor( )
        {
            return 0;
        }
        @Override
        public boolean alquiler() 
        {
            return true;
        }
    }