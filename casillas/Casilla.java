package casillas;

import java.util.ArrayList;
import monopoly.*;
import partida.*;

public abstract class Casilla {
    // Atributos
    private final ArrayList<Avatar> avatares;
    private final String nombre;
    private final int posicion;
    private int frecuencia;
    private Jugador duenho;
    // Constructor
    public Casilla(String nombre, int posicion) {
        this.avatares = new ArrayList<>();
        this.nombre = nombre;
        this.posicion = posicion;
        this.frecuencia = 0;
    }


    public boolean estaAvatar(Avatar avatar) {
        return avatares.contains(avatar);
    }

    public void sumarFreq() {
        this.frecuencia += 1;
    }
    public int getFrecuencia() {
        return this.frecuencia;
    }


    public ArrayList<Avatar> getAvatares() {
        return avatares;
    }
    
    //Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        this.avatares.remove(av);
    }

    public void anhadirAvatar(Avatar av) {
        this.avatares.add(av);
    }

    public String getColorCasilla() {
        if (this instanceof Solar aux) {
            return aux.getGrupo().getColorGrupo();
        }
        return Valor.WHITE;
    }
    //GETTERS 
    public String getNombre(){
        return this.nombre;
    }

    public int getPosicion() {
        return this.posicion;
    }


    //SETTERS
    public void setDuenho(Jugador novo) {
        duenho = novo;
    }

    //métodos a implementar
    public abstract boolean  EvaluarCasilla(Jugador actual, Jugador banca, int tirada,ArrayList<ArrayList<Casilla>> pos);
    
    public Jugador getDuenho() {
        return this.duenho;
    }

    public int getCaidas() 
    {
        return frecuencia;
    }


}