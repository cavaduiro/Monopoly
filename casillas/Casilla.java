package casillas;

import java.io.Console;
import partida.*;
import java.util.ArrayList;
import java.util.Map;

import monopoly.Tablero;

import java.util.HashMap;

public abstract class Casilla {
    // Atributos
    private ArrayList<Avatar> avatares;
    private String nombre;
    private int posicion;
    private int frecuencia;
    // Constructor
    public Casilla(String nombre, int posicion) {
        this.avatares = new ArrayList<Avatar>();
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
    public void anhadirAvatar(Avatar av){
        this.avatares.add(av);
    }
    //GETTERS 
    public String getNombre(){
        return this.nombre;
    }
    public int getPosicion(){
        return this.posicion;
    }
    //SETTERS


    //métodos a implementar
    public abstract boolean  EvaluarCasilla(Jugador actual, Jugador banca, int tirada,ArrayList<ArrayList<Casilla>> pos) ;



}