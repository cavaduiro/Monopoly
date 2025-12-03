package partida;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import monopoly.*;
import casillas.*;


public class Avatar {

    //Atributos
    private String id; //Identificador: una letra generada aleatoriamente.
    private String tipo; //Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; //Un jugador al que pertenece ese avatar.
    private Casilla lugar; //Los avatares se sitúan en casillas del tablero.

    //Constructor vacío
    public Avatar() {
    }

    /*Constructor principal. Requiere éstos parámetros:
     * Tipo del avatar, jugador al que pertenece, lugar en el que estará ubicado, y un arraylist con los
     * avatares creados (usado para crear un ID distinto del de los demás avatares).
     */
    public Avatar(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        this.tipo = tipo;
        this.jugador = jugador;
        this.lugar = lugar;
        generarId(avCreados);
    }

    //Getters:
    public String getTipo() {
        return tipo;
    }

    public String getId() {
        return id;
    }

    public Casilla getLugar() {return lugar;}

    public Jugador getJugador() {
        return jugador;
    }
    //Setters:
    public void setLugar(Casilla lugar) {
        this.lugar = lugar;
    }
    //A continuación, tenemos otros métodos útiles para el desarrollo del juego.
    /*Metodo que permite mover a un avatar a una casilla concreta. Parámetros:
     * - Un array con las casillas del tablero. Se trata de un arrayList de arrayList de casillas (uno por lado).
     * - Un entero que indica el numero de casillas a moverse (será el valor sacado en la tirada de los dados).
     * EN ESTA VERSIÓN SUPONEMOS QUE valorTirada siemrpe es positivo.
     */
    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        Casilla casillaActual = this.lugar;
        int posicionActual = this.lugar.getPosicion();
        int nuevaPosicion = posicionActual + valorTirada;
        if (nuevaPosicion > 40 && !jugador.getEnCarcel()) { //Si se pasa de la última casilla, se da una vuelta al tablero.
            nuevaPosicion = nuevaPosicion %40;
            this.jugador.getEstatisticas().sumarVoltas();
            this.jugador.sumarFortuna(2000000);
            this.jugador.getEstatisticas().sumarsalidas();//El jugador recibe 2000000 por pasar por la salida.
            Juego.consol.imprimir("O xogador " + this.jugador.getNombre() + " pasou pola saída e recibiu 2000000€.");
        }
        casillaActual.eliminarAvatar(this);
        Casilla nueva = posIndex(nuevaPosicion, casillas);
        nueva.sumarFreq();
        nueva.anhadirAvatar(this); //Añadimos el avatar a la casilla a la que se mueve
        this.lugar = nueva; //Actualizamos el lugar en el que está el avatar.
        if(casillaActual.getPosicion() > posicionActual && !jugador.getEnCarcel()){
            this.jugador.sumarFortuna(Valor.SUMA_VUELTA); //El jugador recibe 2000000 por pasar por la salida.
        }
        Juego.consol.imprimir("O xogador " + this.jugador.getNombre() + " moveuse da casilla " + casillaActual.getNombre() + " á casilla " + this.lugar.getNombre() + ".");
    }



    //Devolve a casilla co índice de 0 a 40
    public Casilla posIndex(int index,ArrayList<ArrayList<Casilla>> casillas){
        index= index%40;
        if(index<11){
            return casillas.get(0).get(index);
        }else if(index<20){
            return casillas.get(1).get(index-11);
        }else if(index<31){
            return casillas.get(2).get(index-20);
        }else{
            return casillas.get(3).get(index-31);
        }
    }
    
    /*Metodo que permite generar un ID para un avatar. Sólo lo usamos en esta clase (por ello es privado).
     * El ID generado será una letra mayúscula. Parámetros:
     * - Un arraylist de los avatares ya creados, con el objetivo de evitar que se generen dos ID iguales.
     */
    private void generarId(ArrayList<Avatar> avCreados)
    {
        String[] Ids = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L","M", "N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        List<String> disp = new ArrayList<>(Arrays.asList(Ids));

        // Quitamos os creados xa
        for (Avatar av : avCreados) {
            disp.remove(av.id);
        }

        if (disp.isEmpty()) {
            Valor.error("Non quedan IDs."); //caso imposible
        }
        int randomIndex = (int) (Math.random() * disp.size());
        this.id = disp.get(randomIndex);
    }
    private boolean esDupe(ArrayList<Avatar> avCreados){
        for(Avatar aux: avCreados){
            if(aux.id.equalsIgnoreCase(this.id)){
                return true;
            }
        }
        return false;
    }
}