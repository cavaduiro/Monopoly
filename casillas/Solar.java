package casillas;
import java.util.ArrayList;
import monopoly.*;
import partida.*;

public class Solar extends Propiedad {
    private Grupo grupo;
    public Solar(String nombre, int posicion, float valor, Jugador duenho, float alquiler, float hipoteca) {
        //super(nombre, posicion, valor, alquiler, hipoteca, duenho);
        super(nombre, posicion, valor, duenho, alquiler, hipoteca);
    }

    
    




    public void setGrupo(Grupo grup) {
        this.grupo = grup;
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