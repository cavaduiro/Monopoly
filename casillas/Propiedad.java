
package casillas;
import java.util.ArrayList;
import monopoly.Juego;
import monopoly.Valor;
import partida.Jugador;

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
        if (this.getDuenho() == banca) {
            solicitante.anhadirPropiedad(this);
            this.duenho = solicitante;
            solicitante.sumarFortuna(-this.valor);
            solicitante.getEstatisticas().pagoinversion(this.valor);
            Juego.consol.imprimir("O xogador " + solicitante.getNombre() + " comprou a casilla " + getNombre() + " por "
                    + this.valor + " $.");

        } else {
            //ERROR CHEQUEABLE
            Juego.consol.imprimir("\nA casilla xa ten dono.\n");
        }
    }

    public  float getValor(){
        return this.valor;
    }
    public  float getAlquiler(){
        return this.alquiler;
    }

    public float getRentabilidad() {
        return rentabilidade;
    }

    public void setRentabilidade(float rent) {
        this.rentabilidade = rent;
    }

    public boolean getHipotecada() {
        return this.hipotecada;
    }
    public float getHipoteca() {
        return this.hipoteca;
    }

    @Override
    public Jugador getDuenho() {
        return this.duenho;
    }   

    @Override
    public void setDuenho(Jugador novo) {
        this.duenho = novo;
    }

    @Override
    public abstract boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada,
            ArrayList<ArrayList<Casilla>> posiciones);
        
    public void hipotecarCasilla() {
        this.hipotecada = true;
        this.duenho.sumarFortuna(this.hipoteca);
        Juego.consol.imprimir("A casilla " + this.getNombre() + " foi hipotecada por " + this.hipoteca + " euros.\n Ata que a deshipoteques, non poderás cobrar aluguer por esta casilla.");
        if (this instanceof Solar casaux) {
            Juego.consol.imprimir("Ademais, tampouco poderás construir no grupo "+Valor.getNombreColor((casaux.getGrupo().getColorGrupo())));
        }
    }
    //HAY QUE IMPLEMENTALO ABAIXO LOL     
    public void deshipotecarCasilla() {
        if(this.duenho.getFortuna() < this.hipoteca) {
            //ERROR CHEQUEABLE
            System.out.println("\nNon tes suficiente fortuna para deshipotecar esta casilla. Tes "+this.duenho.getFortuna()+" euros, pero necesitas "+this.hipoteca+" euros.\n");
            return;
        }
        this.hipotecada = false;
        this.duenho.sumarFortuna(-this.hipoteca);
        Juego.consol.imprimir("A casilla " + getNombre() + " foi deshipotecada por " + this.hipoteca + " euros.\n Xa podes cobrar aluguer por esta casilla.");
        if(this instanceof Solar casaux){
            Juego.consol.imprimir("Ademais, xa podes construir no grupo "+Valor.getNombreColor(casaux.getGrupo().getColorGrupo())+".");
        }
    }

    public void setDueño(Jugador Recaudador) {
        duenho = Recaudador;
    }

    
    }

    