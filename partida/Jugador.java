package partida;

import java.util.ArrayList;

import monopoly.*;


public class Jugador {

    //Atributos:
    private String nombre; //Nombre del jugador
    private Avatar avatar; //Avatar que tiene en la partida.
    private float fortuna; //Dinero que posee.
    private float gastos; //Gastos realizados a lo largo del juego.
    private boolean enCarcel; //Será true si el jugador está en la carcel
    private int tiradasCarcel; //Cuando está en la carcel, contará las tiradas sin éxito que ha hecho allí para intentar salir (se usa para limitar el numero de intentos).
    private int vueltas; //Cuenta las vueltas dadas al tablero.
    private ArrayList<Casilla> propiedades; //Propiedades que posee el jugador.

    //Constructor vacío. Se usará para crear la banca.
    public Jugador() {
        this.fortuna = 0;
    }

    /*Constructor principal. Requiere parámetros:
    * Nombre del jugador, tipo del avatar que tendrá, casilla en la que empezará y ArrayList de
    * avatares creados (usado para dos propósitos: evitar que dos jugadores tengan el mismo nombre y
    * que dos avatares tengan mismo ID). Desde este constructor también se crea el avatar.
     */
    public Jugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados) {
        {
            this.nombre = nombre;
            this.avatar = new Avatar(tipoAvatar,this, inicio, avCreados);
            this.fortuna = 15000000;
            this.propiedades = new ArrayList<Casilla>();
        }
    }


    //Getters:

    public int getTiradasCarcel() {
        return tiradasCarcel;
    }

    public Avatar getAvatar() {
        return avatar;
    }
    public String getNombre() {
        return nombre;
    }
    public boolean getEnCarcel() {
        return enCarcel;
    }
    public float getFortuna() {
        return fortuna;
    }

    public ArrayList<Casilla> getPropiedades() {return propiedades;}

    //Setters:
    public void setEnCarcel(boolean enCarcel) {
        this.enCarcel = enCarcel;
    }
    public void setFortuna(int novaFortuna){this.fortuna = novaFortuna;}
    //Otros métodos:
    //Metodo para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.
    public void anhadirPropiedad(Casilla casilla) {
        if (this.propiedades.contains(casilla)) {
            System.out.println("\nO xogador xa ten esa propiedade.\n");
            return;
        }
        if(this.fortuna < casilla.getValor()) {
            System.out.println("\nO xogador non ten suficiente fortuna para comprar esa propiedade.\n");
            return;
        }
        else {
            this.propiedades.add(casilla);
        }
    }

    //Metodo para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
        if(this.propiedades.contains(casilla)){
            this.propiedades.remove(casilla);
        }else{
            System.out.println("\nO xogador non ten esa propiedade.\n");
        }

    }

    //Metodo para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        this.fortuna += valor;
    }

    //Metodo para sumar gastos a un jugador.
    //Parámetro: valor a añadir a los gastos del jugador (será el precio de un solar, impuestos pagados...).
    public void sumarGastos(float valor) {
        this.gastos += valor;
    }

    /*Metodo para establecer al jugador en la cárcel.
    * Se requiere disponer de las casillas del tablero para ello (por eso se pasan como parámetro).*/
    public void encarcelar(ArrayList<ArrayList<Casilla>> pos) {
        this.enCarcel = true;
        this.tiradasCarcel = 0;
        //Buscamos la casilla de la cárcel y movemos el avatar allí.
        for (ArrayList<Casilla> lado : pos) {
            for (Casilla casilla : lado) {
                if (casilla.getNombre().equals("Cárcel")) {
                    this.avatar.moverAvatar(pos, casilla.getPosicion() - this.avatar.getLugar().getPosicion());
                }
            }
        }
    }
    public void sumartiradascarcel(){
        this.tiradasCarcel++;
    }

    @Override
    public String toString()
    {   //Falta facer un tostring de propiedades
        StringBuilder sb = new StringBuilder();
        sb.append("\n{\nNombre: ").append(this.nombre)
          .append("\nAvatar: ").append(this.avatar != null ? this.avatar.getId() : "null")
          .append("\nFortuna: ").append(this.fortuna)
          .append("\nPropiedades: ");
        if (this.propiedades != null && !this.propiedades.isEmpty()) {
            for (int i = 0; i < propiedades.size(); i++) {
                Casilla p = propiedades.get(i);
                sb.append(p != null ? p.getNombre() : "null");
                if (i < propiedades.size() - 1) {
                    sb.append(", ");
                }
            }
        } else {
            sb.append("None");
        }
        sb.append("\n}");
        return sb.toString();
    }

}
