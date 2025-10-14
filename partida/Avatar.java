package partida;

import java.util.ArrayList;
import monopoly.*;


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

    public Casilla getLugar() {
        return lugar;
    }
    //Setters:

    //A continuación, tenemos otros métodos útiles para el desarrollo del juego.
    /*Metodo que permite mover a un avatar a una casilla concreta. Parámetros:
     * - Un array con las casillas del tablero. Se trata de un arrayList de arrayList de casillas (uno por lado).
     * - Un entero que indica el numero de casillas a moverse (será el valor sacado en la tirada de los dados).
     * EN ESTA VERSIÓN SUPONEMOS QUE valorTirada siemrpe es positivo.
     */
    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        int posicionActual = this.lugar.getPosicion();
        int nuevaPosicion = posicionActual + valorTirada;
        if (nuevaPosicion > 40) { //Si se pasa de la última casilla, se da una vuelta al tablero.
            nuevaPosicion = nuevaPosicion - 40;
            this.jugador.sumarFortuna(200000); //El jugador recibe 2000000 por pasar por la salida.
        }
        //Ahora buscamos la casilla correspondiente a la nueva posición y movemos el avatar allí.
        for (Casilla casilla : casillas.get(nuevaPosicion)) {
            if (casilla.getPosicion() == posicionActual) {
                casilla.eliminarAvatar(this); //Eliminamos el avatar de la casilla en la que estaba.
            }
            if (casilla.getPosicion() == nuevaPosicion) {
                casilla.anhadirAvatar(this); //Añadimos el avatar a la casilla a la que se mueve.
                this.lugar = casilla; //Actualizamos el lugar en el que está el avatar.
            }
        }

    }

    /*Metodo que permite generar un ID para un avatar. Sólo lo usamos en esta clase (por ello es privado).
     * El ID generado será una letra mayúscula. Parámetros:
     * - Un arraylist de los avatares ya creados, con el objetivo de evitar que se generen dos ID iguales.
     */
    private void generarId(ArrayList<Avatar> avCreados) {
        int x = (int) (Math.random() * 26 + 65); //valor aleatorio ascii
        this.id = String.valueOf(x);
        if (!avCreados.isEmpty()) {
            for (int i = 0; i < avCreados.size(); i++) {
                if (avCreados.get(i).id.equals(this.id)) {
                    i = 0;
                    x = (int) (Math.random() * 26 + 65);
                }
            }
            this.id = String.valueOf(x);
        }
        //Xa creo os avatares individuales arriba, non entendo como implementalo con esta función
    }
}