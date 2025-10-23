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
    private int caidas;


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

    public ArrayList<Avatar> getAvatares() {
        return this.avatares;
    }
    
    public String getColorCasilla() {
        if (this.grupo != null) {
            return this.grupo.getColorGrupo();
        }
        return Valor.WHITE;
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

    public void setDueño(Jugador duenho) {
        this.duenho = duenho;
    }
    public void setValor(float valor) {
        this.valor = valor;
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

    public void sumarFreq(){
        this.caidas++;
    }
    /*Método para evaluar qué hacer en una casilla concreta. Parámetros:
    * - Jugador cuyo avatar está en esa casilla.
    * - La banca (para ciertas comprobaciones)-> temos que levar a conta de cantos cartos se recaudan
    * - El valor de la tirada: para determinar impuesto a pagar en casillas de servicios.
    * Valor devuelto: true en caso de ser solvente (es decir, de cumplir las deudas), y false
    * en caso de no cumplirlas.
    * En caso de non cumprirse, declárase como en bancarrota ao xogador, e dalle todas as súas propiedas ao outro xogador
    * */
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada,ArrayList<ArrayList<Casilla>> pos) {
        if (this.duenho == banca) {
            if (this.tipo.equals("Impuesto")) {
                if (actual.getFortuna() >= this.impuesto) {
                    actual.sumarFortuna(-this.impuesto);
                    actual.getEstatisticas().acImpPagado(this.impuesto);
                    System.out.println("O xogador " + actual.getNombre() + " pagou " + this.impuesto + " á banca por caer na casilla " + this.nombre + ".\n");
                    banca.sumarFortuna(this.impuesto);
                } else {
                    System.out.println("O xogador " + actual.getNombre() + " non ten cartos para pagar os impostos da casilla " + this.nombre + ".\n");
                    return false;
                }
            } else if (this.tipo != null && this.tipo.equals("Especial")) {
                if (this.nombre != null && this.nombre.equals("Suerte")) {
                    // Lógica de suerte-> seguintes entregas
                } else if (this.nombre != null && this.nombre.equals("Caja de Comunidad")) {
                    // Lógica de caja de comunidad ->seguintes entregas
                } else if (this.nombre != null && this.nombre.equals("Parking")) {
                    System.out.println("\nO xogador "+actual.getNombre()+" recibirá "+banca.getFortuna()+" en impostos. \n");
                    actual.sumarFortuna(banca.getFortuna());
                    banca.setFortuna(0);
                } else if (this.nombre != null && this.nombre.equals("IrCarcel")) {
                    System.out.println("\nCaíches en IrCarcel, irás ao cárcere...\n");
                    actual.encarcelar(pos);
                    return true; //O xogador segue solvente aínda se na cárcel.
                }
            }
        } else {
            if (this.duenho == actual) {
                //non fai nada, o xogador é dono da casilla xa lol
            } else {
                // Si el dueño tiene todas las casillas del grupo, el impuesto es el doble
                float impuestoSolar = this.impuesto;
                if (this.grupo != null && this.grupo.esDuenhoGrupo(this.duenho)) {
                    System.out.println("\nO dono da casilla " + this.nombre + " ten todas as casillas do grupo, polo que o imposto a pagar será o dobre.\n");
                    impuestoSolar *= 2;
                }
                if (this.tipo != null && (this.tipo.equals("Solar"))) {
                    if (actual.getFortuna() >= impuestoSolar    ) {
                        actual.sumarFortuna(-impuestoSolar);
                        if (this.duenho != null) {
                            this.duenho.sumarFortuna(impuestoSolar);
                        }
                        System.out.println("O xogador " + actual.getNombre() + " pagou " + impuestoSolar + " ao xogador " + (this.duenho != null ? this.duenho.getNombre() : "Desconocido") + " por caer na casilla " + this.nombre + ".\n");
                    } else {
                        System.out.println("O xogador " + actual.getNombre() + " non ten suficientes cartos para pagar o alquilar da casilla " + this.nombre + ".\n");
                        return false;
                    }
                } else if(this.tipo != null && this.tipo.equals("Transportes")) {
                    int numTransportes = 0;
                    Jugador propietario = this.duenho;
                    for (Casilla propiedad : propietario.getPropiedades()) {
                        if (propiedad.getTipo() != null && propiedad.getTipo().equals("Transportes")) {
                            numTransportes++;
                        }
                    }
                    System.out.println("\nO dono da casilla " + this.nombre + " ten " + numTransportes + " transportes, polo que o imposto multiplicarase por " + numTransportes + ".\n");
                    float impuestoTransporte = this.impuesto * numTransportes;
                    if (actual.getFortuna() >= impuestoTransporte) {
                        actual.sumarFortuna(-impuestoTransporte);
                        if (this.duenho != null) {
                            this.duenho.sumarFortuna(impuestoTransporte);
                        }
                        System.out.println("O xogador " + actual.getNombre() + " pagou " + impuestoTransporte + " ao xogador " + (this.duenho != null ? this.duenho.getNombre() : "Desconocido") + " por caer na casilla " + this.nombre + ".\n");
                    } else {
                        System.out.println("O xogador " + actual.getNombre() + " non tes suficientes cartos para pagar o transporte da casilla " + this.nombre + ".\n");
                        return false;
                    }
                } else if (this.tipo != null && this.tipo.equals("Servicios")) {
                    Jugador propietario = this.duenho;
                    int numServicios = 0;
                    for (Casilla propiedad : propietario.getPropiedades()) {
                        if (propiedad.getTipo() != null && propiedad.getTipo().equals("Servicios")) {
                            numServicios++;
                        }
                    }
                    int multiplicador=2;
                    if(numServicios==2){
                        multiplicador=4;
                    }
                    System.out.println("\nO dono da casilla " + this.nombre + " ten " + numServicios + " servizos, polo que o imposto multiplicarase por " + multiplicador + ".\n");
                    float impuestoServicios = this.impuesto * tirada * multiplicador;
                    if (actual.getFortuna() >= impuestoServicios) {
                        actual.sumarFortuna(-impuestoServicios);
                        if (this.duenho != null) {
                            this.duenho.sumarFortuna(impuestoServicios);
                        }
                        System.out.println("O xogador " + actual.getNombre() + " pagou " + impuestoServicios + " ao xogador " + (this.duenho != null ? this.duenho.getNombre() : "Desconocido") + " por caer en la casilla " + this.nombre + ".\n");
                    } else {
                        System.out.println("O xogador " + actual.getNombre() + " non ten suficientes cartos para pagar o servizo da casilla " + this.nombre + ".\n");
                        return false;
                    }
                }
            }
        }
        return true;
    }







    /*Método usado para comprar una casilla determinada. Parámetros:
    * - Jugador que solicita la compra de la casilla.
    * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
       if(this.getDuenho()==banca) {
           solicitante.anhadirPropiedad(this);
           this.duenho = solicitante;
           solicitante.sumarFortuna(-this.valor);
              System.out.println("\nO xogador " + solicitante.getNombre() + " comprou a casilla " + this.nombre + " por " + this.valor + " euros.\n");
       }
       else {
           System.out.println("\nA casilla xa ten dono.\n");
       }
    }

    /*Método para añadir valor a una casilla. Utilidad:
    * - Sumar valor a la casilla de parking.
    * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de todos los jugadores. Esto es porrazo no está en ningún sitio
    * Este método toma como argumento la cantidad a añadir del valor de la casilla.*/
    public void sumarValor(float suma) {
        this.setValor(this.valor + suma);
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
        if(this.duenho!=null && this.duenho.getNombre().equalsIgnoreCase("Banca")) {
        return "\n{\nTipo: " + this.tipo + "\ngrupo: " + this.grupo.getColorGrupo() + "\nValor: " + this.valor + "\nAlquiler: " + this.impuesto + "\nHipoteca: " + this.hipoteca + "\n}";
        }
        return "";
        //return "\nA casilla non está en venda.\n";
    }
    @Override
    public String toString() {
        if(this.grupo==null){
            return "\n{\n  nombre: " + this.nombre + "\n  tipo: " + this.tipo + "\n  valor: " + this.valor + "\n  propietario: " + this.duenho.getNombre()  + "\n  alquiler: " + this.impuesto + "\n}";
        }
        return "\n{\n  nombre: " + this.nombre + "\n  tipo: " + this.tipo + "\n  grupo: " + Valor.getNombreColor(this.grupo.getColorGrupo()) +"\n  valor: " + this.valor + "\n  propietario: " + this.duenho.getNombre()  + "\n  alquiler: " + this.impuesto + "\n}";
    }
}
   
