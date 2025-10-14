package monopoly;

import partida.*;
import java.util.ArrayList;

//Probando probando probando probandofijoiejfoewijwfoiwj
public class Casilla {

    //Atributos:
    private String nombre; //Nombre de la casilla
    private String tipo; //Tipo de casilla (Solar, Especial, Transporte, Servicios, Comunidad, Suerte y Impuesto).
    private float valor; //Valor de esa casilla (en la mayoría será valor de compra, en la casilla parking se usará como el bote).
    private int posicion; //Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private Jugador duenho; //Dueño de la casilla (por defecto sería la banca).
    private Grupo grupo; //Grupo al que pertenece la casilla (si es solar).
    private float impuesto; //Cantidad a pagar por caer en la casilla: el alquiler en solares/servicios/transportes o impuestos.
    private float hipoteca; //Valor otorgado por hipotecar una casilla
    private ArrayList<Avatar> avatares; //Avatares que están situados en la casilla.


    public Casilla() {
    }//Parámetros vacíos

    /*Constructor para casillas tipo Solar, Servicios o Transporte:
    * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte), posición en el tablero, valor y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, float valor, Jugador duenho, float impuesto, float hipoteca) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.valor = valor;
        this.duenho = duenho;
        this.impuesto = impuesto;
        this.hipoteca = hipoteca;
        this.avatares = new ArrayList<Avatar>();
    }

    /*Constructor utilizado para inicializar las casillas de tipo IMPUESTOS.
    * Parámetros: nombre, posición en el tablero, impuesto establecido y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, Jugador duenho, float impuesto) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.duenho = duenho;
        this.impuesto = impuesto;
        this.avatares = new ArrayList<Avatar>();
    }

    /*Constructor utilizado para crear las otras casillas (Suerte, Caja de comunidad y Especiales):
    * Parámetros: nombre, tipo de la casilla (será uno de los que queda), posición en el tablero y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.duenho = duenho;
        this.avatares = new ArrayList<Avatar>();
    }

    /***********GETTERS**********/
    public String getNombre() {
        return this.nombre;
    }   
    
   public Grupo getGrupo() {
        return this.grupo;
    }

    public String getTipo() {
        return this.tipo;
    }

    public Jugador getDuenho(){return this.duenho;}

    public float getValor() {return this.valor;}

    public float getImpuesto() {return this.impuesto;}

    public float getHipoteca() {return this.hipoteca;}

    public int getPosicion() {return this.posicion;}

    /****************************/



    /***********SETTERS**********/
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
    /****************************/

    //Método utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        this.avatares.add(av);
    }

    //Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        this.avatares.remove(av);
    }

    /*Método para evaluar qué hacer en una casilla concreta. Parámetros:
    * - Jugador cuyo avatar está en esa casilla.
    * - La banca (para ciertas comprobaciones).
    * - El valor de la tirada: para determinar impuesto a pagar en casillas de servicios.
    * Valor devuelto: true en caso de ser solvente (es decir, de cumplir las deudas), y false
    * en caso de no cumplirlas.*/
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        return true;
    }

    /*Método usado para comprar una casilla determinada. Parámetros:
    * - Jugador que solicita la compra de la casilla.
    * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
       if(this.getDuenho().getNombre().equals("banca")) {
           solicitante.anhadirPropiedad(this);
           this.duenho = solicitante;
           solicitante.sumarFortuna(-this.valor);
       }
       else {
           System.out.println("\nA casilla xa ten dono.\n");
       }
    }

    /*Método para añadir valor a una casilla. Utilidad:
    * - Sumar valor a la casilla de parking.
    * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de todos los jugadores.
    * Este método toma como argumento la cantidad a añadir del valor de la casilla.*/
    public void sumarValor(float suma) {
    }

    /*Método para mostrar información sobre una casilla.
    * Devuelve una cadena con información específica de cada tipo de casilla.*/
    public String infoCasilla() {
        String info = new String();
        info+= "\n{\nTipo: " + this.tipo;
        info+= "\nGrupo: " + this.grupo.getColorGrupo();
        info+= "\nPropietario: " + this.duenho.getNombre();
        info+= "\nValor: " + this.valor;
        info+= "\nAlquiler: " + this.impuesto;
        info+= "\nHipoteca: " + this.hipoteca;
        info+= "\n}";
        return info;
    }

    /* Método para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
    public String casEnVenta() {
        return "returnfalsoparaquerunee";

    }

    @Override
    public String toString() {
        return "\n{\nTipo=" + this.tipo + "\ngrupo=" + this.grupo +"\nvalor=" + this.valor + "\npropietario=" + this.duenho  + "\nalquiler="+ this.impuesto + "\n}";
    }
}
