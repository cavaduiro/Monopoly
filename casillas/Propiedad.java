
package casillas;
import partida.Jugador;
import java.util.ArrayList;

import monopoly.Juego;
import partida.*;

public abstract class Propiedad extends Casilla {
    private Jugador duenho;
    private float valor;
    private float alquiler;
    private float hipoteca;
    private boolean hipotecada;
    private float rentabilidade;
    public Propiedad(String nombre, int posicion,float valor, Jugador duenho,float alquiler,float hipoteca) {
        super(nombre, posicion);
        this.valor = valor;
        this.alquiler = alquiler;
        this.hipoteca = hipoteca;
        this.duenho = duenho;
    }
    // Implementación específica de Propiedad
    // 
    public boolean perteneceAJugador(Jugador jugador){
        return this.duenho.equals(jugador);
    }

    public abstract boolean alquiler( );

    public abstract float valor( );

      public void comprarCasilla(Jugador solicitante, Jugador banca) {
       if(this.getDuenho()==banca) {
           solicitante.anhadirPropiedad(this);
           this.duenho = solicitante;
           solicitante.sumarFortuna(-this.valor);
           solicitante.getEstatisticas().pagoinversion(this.valor);
            Juego.consol.imprimir("nO xogador " + solicitante.getNombre() + " comprou a casilla " + this.getNombre() + " por " + this.valor + " $.");

       }
       else {
            //ERROR CHEQUEABLE
           Juego.consol.imprimir("\nA casilla xa ten dono.\n");
       }
    }
    public Jugador getDuenho(){
        return this.duenho;
    }
    public  float getValor(){
        return this.valor;
    }
    public  float getAlquiler(){
        return this.alquiler;
    }

    public boolean getHipotecada() {
        return hipotecada;
    }

    public float getRentabilidade() {
        return rentabilidade;
    }

    public void setRentabilidade(float rent) {
        this.rentabilidade = rent;
    }
    @Override
        public abstract boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada,ArrayList<ArrayList<Casilla>> posiciones);
    }