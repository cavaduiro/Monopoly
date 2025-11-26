package monopoly;

import java.io.Console;
import partida.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

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
    private float rentabilidad;
    private int caidas;
    boolean hipotecada=false;
    private Map<String, Edificios> edificios; //Edificios construidos en la casilla (si es solar).

    public Casilla() {
    }//Parámetros vacíos

    /*Constructor para casillas tipo Solar, Servicios o Transporte:
    * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte), posición en el tablero, valor y dueño.
     */
    @SuppressWarnings("Convert2Diamond")
    public Casilla(String nombre, String tipo, int posicion, float valor, Jugador duenho, float impuesto, float hipoteca) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.valor = valor;
        this.duenho = duenho;
        this.impuesto = impuesto;
        this.hipoteca = hipoteca;
        this.avatares = new ArrayList<Avatar>();
        this.rentabilidad=-impuesto;
        this.hipotecada=false;
        if(tipo.equals("Solar")){
            this.edificios = new HashMap<String, Edificios>();
            this.edificios.put("casa", new Edificios("casa", Valor.getCosteCompraEdificio(posicion, "casa"), Valor.getCosteAlquilerEdificio(posicion, "casa"), 0, this));
            this.edificios.put("hotel", new Edificios("hotel", Valor.getCosteCompraEdificio(posicion, "hotel"), Valor.getCosteAlquilerEdificio(posicion, "hotel"), false, this));
            this.edificios.put("piscina", new Edificios("piscina", Valor.getCosteCompraEdificio(posicion, "piscina"), Valor.getCosteAlquilerEdificio(posicion, "piscina"), false, this));
            this.edificios.put("deporte", new Edificios("deporte", Valor.getCosteCompraEdificio(posicion, "deporte"), Valor.getCosteAlquilerEdificio(posicion, "deporte"), false, this));
        }
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
    public float getRentabilidad(){return this.rentabilidad;}
    public Jugador getDuenho(){return this.duenho;}

    public float getValor() {return this.valor;}

    public float getImpuesto() {return this.impuesto;}

    public float getHipoteca() {return this.hipoteca;}

    public int getPosicion() {return this.posicion;}

    public int getCaidas() {return this.caidas;}

    public boolean getHipotecada() {return this.hipotecada;}

    public Map<String, Edificios> getEdificios() {return this.edificios;}

    /****************************/



    /***********SETTERS**********/
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
    public void setCaidas(int caidas) {this.caidas = caidas;}
    public void setDueño(Jugador duenho) {
        this.duenho = duenho;
    }
    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setHipotecada(boolean hipotecada) {
        this.hipotecada = hipotecada;
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
                    loxicaSorte(banca,actual,pos);
                } else if (this.nombre != null && this.nombre.equals("Caja")) {
                    loxicaComunidade(banca,actual,pos);
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
            if ((this.duenho == actual)||hipotecada) {
                if(hipotecada){
                    System.out.println("\nA casilla "+this.nombre+" está hipotecada, polo que non se paga aluguer.\n");
                }
                
            } else {
                // Si el dueño tiene todas las casillas del grupo, el impuesto es el doble
                float impuestoSolar = this.impuesto;
                int casas=this.edificios.get("casa").getNumCasas();
                boolean hotel=this.edificios.get("hotel").getTenEdificio(), piscina=this.edificios.get("piscina").getTenEdificio(), deporte=this.edificios.get("deporte").getTenEdificio();
                if (this.grupo != null && this.grupo.esDuenhoGrupo(this.duenho)&&casas==0&&!hotel&&!piscina&&!deporte) {
                    System.out.println("\nO dono da casilla " + this.nombre + " ten todas as casillas do grupo, polo que o imposto a pagar será o dobre.\n");
                    impuestoSolar *= 2;
                }else{
                    impuestoSolar=0; //Imposto base 0, imos sumar o das construccións SOLO
                    if(casas>0){
                        impuestoSolar=this.edificios.get("casa").getAlquiler()*casas;
                        System.out.println("\nO dono da casilla "+this.nombre+" ten "+casas+" casas, con alquiler de "+this.edificios.get("casa").getAlquiler()+" cada unha.\n");
                    }
                    if (hotel) {
                        impuestoSolar += this.edificios.get("hotel").getAlquiler();
                        System.out.println("\nO dono da casilla "+this.nombre+" ten un hotel, con alquiler de "+this.edificios.get("hotel").getAlquiler()+".\n");
                    }
                    if (piscina) {
                        impuestoSolar += this.edificios.get("piscina").getAlquiler();
                        System.out.println("\nO dono da casilla "+this.nombre+" ten unha piscina, con alquiler de "+this.edificios.get("piscina").getAlquiler()+".\n");
                    }
                    if (deporte) {
                        impuestoSolar += this.edificios.get("deporte").getAlquiler();
                        System.out.println("\nO dono da casilla "+this.nombre+" ten unha pista, con alquiler de "+this.edificios.get("deporte").getAlquiler()+".\n");   
                    }
                }

                if (this.tipo != null && (this.tipo.equals("Solar"))) {
                    impuestoSolar = this.impuesto;
                    if (actual.getFortuna() >= impuestoSolar) {
                        actual.getEstatisticas().transAlq(impuestoSolar);
                        this.sumarRentabilidad(impuestoSolar);
                        actual.sumarFortuna(-impuestoSolar);
                        if (this.duenho != null) {
                            this.duenho.sumarFortuna(impuestoSolar);
                        }
                        System.out.println("O xogador " + actual.getNombre() + " pagou " + impuestoSolar + " ao xogador " + (this.duenho != null ? this.duenho.getNombre() : "Desconocido") + " por caer na casilla " + this.nombre + ".\n");
                    } else {
                        System.out.println("O xogador " + actual.getNombre() + " non ten suficientes cartos para pagar o aluguer da casilla " + this.nombre + ".\n");
                        return false;
                    }
                } else if(this.tipo != null && this.tipo.equals("Transportes")) {
                    int numTransportes = 0;
                    Jugador propietario = this.duenho;
                    for (Casilla propiedad : propietario.getPropiedades()) {
                        if (propiedad.getTipo() != null && propiedad.getTipo().equals("Transportes") && !propiedad.hipotecada) {
                            numTransportes++;
                        }
                    }
                    System.out.println("\nO dono da casilla " + this.nombre + " ten " + numTransportes + " transportes, polo que o imposto multiplicarase por " + numTransportes + ".\n");
                    float impuestoTransporte = this.impuesto * numTransportes;
                    if(tirada == 0){
                        impuestoTransporte *= 2;
                    }
                    if (actual.getFortuna() >= impuestoTransporte) {
                        actual.getEstatisticas().transAlq(impuestoTransporte);
                        this.sumarRentabilidad(impuestoTransporte);
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
                        if (propiedad.getTipo() != null && propiedad.getTipo().equals("Servicios") && !propiedad.hipotecada) {
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
                        this.sumarRentabilidad(impuestoServicios);
                        actual.getEstatisticas().transAlq(impuestoServicios);
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

    /**
     * Lóxica de sorte
     */
    private void loxicaSorte(Jugador banca,Jugador actual, ArrayList<ArrayList<Casilla>> pos){

        switch(banca.getIndexsorte()){
            case 0:
                //Moverse a la casilla solar 19
                System.out.println("Oh no excursión á solar 19\n");
                Casilla casactual=actual.getAvatar().getLugar();
                if(casactual.getPosicion()==36){
                    actual.sumarFortuna(Valor.SUMA_VUELTA);
                    actual.getEstatisticas().sumarsalidas();
                    actual.getEstatisticas().sumarVoltas();
                }
                casactual.eliminarAvatar(actual.getAvatar());
                Casilla dest = actual.getAvatar().posIndex(32,pos);
                dest.anhadirAvatar(actual.getAvatar());
                actual.getAvatar().setLugar(dest);
                banca.sumarSorte();
                dest.evaluarCasilla(actual,banca,0,pos);
                break;
            case 1:
                System.out.println("\nVai o cárcere, sen pasar pola casilla de saída.\n");
                //actual.getEstatisticas().sumarCarcel();
                if(actual.getAvatar().getLugar().getPosicion()!=7){
                    actual.getEstatisticas().sumarVoltas();
                }
                actual.encarcelar(pos);
                banca.sumarSorte();
                break;
            case 2:
                System.out.println("\nRecibes 1.000.000! Lucky you...\n");
                //Recibir 1.000.000
                actual.sumarFortuna(1000000);
                actual.getEstatisticas().sumarbote(1000000);
                banca.sumarSorte();
                break;
            case 3:
                System.out.println("\nPaga a cada xogador 250.000€\n");
                //Paga a cada xogador 250.000
                float bote = 250000;
                for(ArrayList<Casilla> lado: pos){
                    for(Casilla cas: lado){
                        for(Avatar av: cas.getAvatares()){
                                if(!av.getJugador().equals(actual)){
                                    actual.getEstatisticas().acImpPagado(bote);
                                    actual.sumarFortuna(-bote);
                                    av.getJugador().sumarFortuna(bote);
                                    av.getJugador().getEstatisticas().sumarbote(bote);
                                }
                            }
                        }
                    }

                banca.sumarSorte();
                break;
            case 4:
                System.out.println("Oh no! Retrocedes tres casillas\n");
                //Retroceder tres casillas
                casactual=actual.getAvatar().getLugar();
                casactual.eliminarAvatar(actual.getAvatar());
                int posdest= casactual.getPosicion()-3;
                dest = actual.getAvatar().posIndex(posdest,pos);
                dest.anhadirAvatar(actual.getAvatar());
                actual.getAvatar().setLugar(dest);
                banca.sumarSorte();
                dest.evaluarCasilla(actual,banca,0,pos);
                break;
            case 5:
                System.out.println("Múltanche por usar o teléfono mentres conduces, paga 150000 euros\n");
                //Múltanche por usar o tlf mentres conduces, paga 150000
                actual.sumarFortuna(-150000);
                banca.sumarFortuna(150000);
                actual.getEstatisticas().acImpPagado(150000);
                banca.sumarSorte();
                break;
            case 6:
                System.out.println("Avanza cara a casilla máis cercana (de transporte)\n");
                //Avanza ata a casilla de transporte máis cercana, se non ten dono podes mercala. Se o ten, pagas o doble do habitual
                boolean iterado= false;
                Casilla destino = actual.getAvatar().getLugar();
                banca.sumarSorte();
                for(int novacas : Valor.transportes){
                    if(actual.getAvatar().getLugar().getPosicion()<novacas && !iterado){
                        casactual=actual.getAvatar().getLugar();
                        casactual.eliminarAvatar(actual.getAvatar());
                        destino = actual.getAvatar().posIndex(novacas,pos);  
                        destino.anhadirAvatar(actual.getAvatar());
                        actual.getAvatar().setLugar(destino);
                        iterado = true;
                        destino.evaluarCasilla(actual,banca,0,pos);
                        return;
                    }
                }

            default:
                break;
        }
    };
    /**
     * Lóxica comunidade
     */
    private void loxicaComunidade(Jugador banca,Jugador actual, ArrayList<ArrayList<Casilla>> pos){

        switch(banca.getIndexcom()){
            case 0:
                banca.sumarCom();
                Juego.consol.imprimir("Paga unha multa de 500000 euros por un fin de semana nun balneario de 5 estrelas");
                actual.sumarFortuna(-500000);
                break;
            case 1:
                banca.sumarCom();
                Juego.consol.imprimir("Vas á cárcel sin pasar por casilla de salida e sin cobrar");
                actual.encarcelar(pos);
                //actual.getEstatisticas().sumarCarcel();
                break;
            case 2:
                banca.sumarCom();
                //Colócate en casilla de salida cobrando 200000
                Juego.consol.imprimir("Vas á casilla de saída, cobrando 2000000 euros.");
                Casilla casactual=actual.getAvatar().getLugar();
                casactual.eliminarAvatar(actual.getAvatar());
                Casilla dest = actual.getAvatar().posIndex(0,pos);
                dest.anhadirAvatar(actual.getAvatar());
                actual.getEstatisticas().sumarVoltas();
                actual.sumarFortuna(Valor.SUMA_VUELTA);
                actual.getEstatisticas().sumarsalidas();
                actual.getAvatar().setLugar(dest);
                break;
            case 3:
                banca.sumarCom();
                System.out.println("Devolución de Facenda, recibes 500000)\n");
                actual.sumarFortuna(500000);
                actual.getEstatisticas().sumarbote(500000);
                break;
            case 4:
                banca.sumarCom();
                System.out.println("Retrocede a Solar1\n");
                casactual=actual.getAvatar().getLugar();
                casactual.eliminarAvatar(actual.getAvatar());
                dest= actual.getAvatar().posIndex(1,pos);
                dest.anhadirAvatar(actual.getAvatar());
                actual.getAvatar().setLugar(dest);
                dest.evaluarCasilla(actual,banca,0,pos);
                break;
            case 5:
                banca.sumarCom();
                System.out.println("Vas ó Solar20\n");
                casactual=actual.getAvatar().getLugar();
                casactual.eliminarAvatar(actual.getAvatar());
                dest= actual.getAvatar().posIndex(34,pos);
                dest.anhadirAvatar(actual.getAvatar());
                actual.getAvatar().setLugar(dest);
                dest.evaluarCasilla(actual, banca, 0, pos);
                break;
            default:
                break;
        }
    }




    /*Método usado para comprar una casilla determinada. Parámetros:
    * - Jugador que solicita la compra de la casilla.
    * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
       if(this.getDuenho()==banca) {
           solicitante.anhadirPropiedad(this);
           this.duenho = solicitante;
           solicitante.sumarFortuna(-this.valor);
           solicitante.getEstatisticas().pagoinversion(this.valor);
              System.out.println("\nO xogador " + solicitante.getNombre() + " comprou a casilla " + this.nombre + " por " + this.valor + " euros.\n");
       }
       else {
           System.out.println("\nA casilla xa ten dono.\n");
       }
    }

    public void hipotecarCasilla() {
        this.hipotecada = true;
        this.duenho.sumarFortuna(this.hipoteca);
        System.out.println("\nA casilla " + this.nombre + " foi hipotecada por " + this.hipoteca + " euros.\n Ata que a deshipoteques, non poderás cobrar aluguer por esta casilla.\n");
        if(this.grupo!=null){
            System.out.println("Ademais, tampouco poderás construir no grupo "+Valor.getNombreColor(this.grupo.getColorGrupo())+".\n");
        }

    }

    public void deshipotecarCasilla() {
        if(this.duenho.getFortuna() < this.hipoteca) {
            System.out.println("\nNon tes suficiente fortuna para deshipotecar esta casilla. Tes "+this.duenho.getFortuna()+" euros, pero necesitas "+this.hipoteca+" euros.\n");
            return;
        }
        this.hipotecada = false;
        this.duenho.sumarFortuna(-this.hipoteca);
        System.out.println("\nA casilla " + this.nombre + " foi deshipotecada por " + this.hipoteca + " euros.\n Xa podes cobrar aluguer por esta casilla.\n");
        if(this.grupo != null){
            System.out.println("Ademais, xa podes construir no grupo "+Valor.getNombreColor(this.grupo.getColorGrupo())+".\n");
        }
    }

    /*Método para añadir valor a una casilla. Utilidad:
    * - Sumar valor a la casilla de parking.
    * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de todos los jugadores. Esto es porrazo no está en ningún sitio
    * Este método toma como argumento la cantidad a añadir del valor de la casilla.*/
    public void sumarValor(float suma) {
        this.setValor(this.valor + suma);
    }

    public void edificar(String tipo, Jugador jugadorActual){
        int precioConstrucion = this.edificios.get(tipo).getCusto();
        int numCasas = this.edificios.get("casa").getNumCasas();
        boolean hotelConstruido = this.edificios.get("hotel").getTenEdificio();
        boolean piscinaConstruida = this.edificios.get("piscina").getTenEdificio();
        boolean pistaConstruida = this.edificios.get("deporte").getTenEdificio();
        if(tipo.equals("casa")){
            if(numCasas>=4){
                System.out.println("Xa tes o número máximo de casa permitidas na casilla");
                return;
            }
            if(jugadorActual.getFortuna()<precioConstrucion){
                System.out.println("Non tes suficiente fortuna para construír unha casa nesta casilla, custa "+precioConstrucion+"€");
                return;
            }
            if(hotelConstruido||piscinaConstruida||pistaConstruida){
                System.out.println("Non podes construír máis casas nesta casilla, xa tes un hotel ou unha instalación deportiva ou piscina construída");
                return;
            }
            this.edificios.get(tipo).anhadirCasa();
        }
        //ESTO PÖDESE MODULARIZAR MOITO MOITO MOITO; DE MOMENTO DAME PEREZA
        if(tipo.equals("hotel")){
            if(hotelConstruido){
                System.out.println("Xa tes un hotel construido");
                return;
            }
            if(numCasas!=4){
                System.out.println("Necesitas ter 4 casas nesta casilla para construír un hotel");
                return;
            }
            if(jugadorActual.getFortuna()<precioConstrucion){
                System.out.println("Non tes suficiente fortuna para construír un hotel nesta casilla, custa "+precioConstrucion+"€");
                return;
            }
            this.edificios.get("casa").setNumCasas(0);
            this.edificios.get(tipo).setTenEdificio(true);
        }
        if(tipo.equals("piscina")){
            if(piscinaConstruida){
                System.out.println("Xa tes unha piscina construída");
                return;
            }
            if(pistaConstruida){
                System.out.println("Non podes construír unha piscina nesta casilla, xa tes unha instalación deportiva construída");
                return;
            }
            if(!hotelConstruido){
                System.out.println("Necesitas ter un hotel nesta casilla para construír unha piscina");
                return;
            }
            if(jugadorActual.getFortuna()<precioConstrucion){
                System.out.println("Non tes suficiente fortuna para construír unha piscina nesta casilla, custa "+precioConstrucion+"€");
                return;
            }
            this.edificios.get(tipo).setTenEdificio(true);
        }
        if(tipo.equals("deporte")){
            if(pistaConstruida){
                System.out.println("Xa tes unha instalación deportiva construída");
                return;
            }
            if(!hotelConstruido || !piscinaConstruida){
                System.out.println("Necesitas ter un hotel e unha piscina nesta casilla para construír unha instalación deportiva");
                return;
            }
            if(jugadorActual.getFortuna()<precioConstrucion){
                System.out.println("Non tes suficiente fortuna para construír unha instalación deportiva nesta casilla, custa "+precioConstrucion+"€");
                return;
            }
            this.edificios.get(tipo).setTenEdificio(true);
        }

        System.out.println("Construiches un/a "+tipo+" na casilla "+this.getNombre()+" por "+precioConstrucion+"€");
        jugadorActual.sumarFortuna(-precioConstrucion);
        this.rentabilidad-=precioConstrucion;
        jugadorActual.getEstatisticas().pagoinversion(precioConstrucion);
    }


    public void vender(String tipo, int numEdificios, Jugador jugadorActual){
        int numCasas = this.edificios.get("casa").getNumCasas();
        boolean hotelConstruido = this.edificios.get("hotel").getTenEdificio();
        boolean piscinaConstruida = this.edificios.get("piscina").getTenEdificio();
        boolean pistaConstruida = this.edificios.get("deporte").getTenEdificio();

        if(tipo.equals("casa")){
            if(numCasas<numEdificios){
                System.out.println("Non tes tantas casas construídas nesta casilla, no solar "+this.getNombre()+" tes "+numCasas+" casas construídas");
                return;
            }else if(numEdificios>numCasas){
                System.out.println("Non tes tantas casas construídas nesta casilla, no solar "+this.getNombre()+" tes "+numCasas+" casas construídas");
                return;
            }
            this.edificios.get(tipo).setNumCasas(numCasas - numEdificios);
        }
        if(tipo.equals("hotel")){
            if(!hotelConstruido){
                System.out.println("Non tes un hotel construído nesta casilla");
                return;
            }
            if(pistaConstruida){
                System.out.println("Non podes vender o hotel se tes unha instalación deportiva construída");
                return;
            }
            if(piscinaConstruida){
                System.out.println("Non podes vender o hotel se tes unha piscina construída");
                return;
            }
            if(numEdificios>1){
                System.out.println("Non poder haber "+numEdificios+" construidos, só un");
                return;
            }
            this.edificios.get(tipo).setTenEdificio(false);
        }
        if(tipo.equals("piscina")){
            if(!piscinaConstruida){
                System.out.println("Non tes unha piscina construída nesta casilla");
                return;
            }
            if(pistaConstruida){
                System.out.println("Non podes vender a piscina se tes unha instalación deportiva construída");
                return;
            }
            if(numEdificios>1){
                System.out.println("Non poder haber "+numEdificios+" construidos, só unha");
                return;
            }
            this.edificios.get(tipo).setTenEdificio(false);
        }
        if(tipo.equals("deporte")){
            if(!pistaConstruida){
                System.out.println("Non tes unha instalación deportiva construída nesta casilla");
                return;
            }
            if(numEdificios>1){
                System.out.println("Non poder haber "+numEdificios+" construidos, só unha");
                return;
            }
            this.edificios.get(tipo).setTenEdificio(false);
        }
        jugadorActual.sumarFortuna((this.edificios.get(tipo).getCusto())*numEdificios);
        System.out.println("Vendeches "+numEdificios+" "+tipo+" na casilla "+this.getNombre()+" por "+(this.edificios.get(tipo).getCusto())*numEdificios+"€");
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

    private void sumarRentabilidad(float cantidad){
        this.rentabilidad+=cantidad;
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
        StringBuilder sb = new StringBuilder();
        if(this.tipo.equals("Solar")){
            sb.append("{\n\tnombre: ").append(this.nombre).append("\n");
            sb.append(" \ttgrupo: ").append(Valor.getNombreColor(this.grupo.getColorGrupo())).append("\n");
            sb.append(" \ttipo: ").append(this.tipo).append("\n");
            sb.append(" \tpropietario: ").append(this.duenho.getNombre()).append("\n");
            sb.append(" \tvalor: ").append(this.valor).append("\n");
            sb.append(" \talquiler: ").append(this.impuesto).append("\n");
            sb.append(" \tvalor casa: ").append(this.edificios.get("casa").getCusto()).append("\n");
            sb.append(" \tvalor hotel: ").append(this.edificios.get("hotel").getCusto()).append("\n");
            sb.append(" \tvalor piscina: ").append(this.edificios.get("piscina").getCusto()).append("\n");
            sb.append(" \tvalor deporte: ").append(this.edificios.get("deporte").getCusto()).append("\n");
            sb.append(" \talquiler casa: ").append(this.edificios.get("casa").getAlquiler()).append("\n");
            sb.append(" \talquiler hotel: ").append(this.edificios.get("hotel").getAlquiler()).append("\n");
            sb.append(" \talquiler piscina: ").append(this.edificios.get("piscina").getAlquiler()).append("\n");
            sb.append(" \talquiler deporte: ").append(this.edificios.get("deporte").getAlquiler()).append("\n}");
        }
        else if(this.tipo.equals("Transportes") || this.tipo.equals("Servicios")){
            sb.append("{\tnombre: ").append(this.nombre).append("\n");
            sb.append(" \ttipo: ").append(this.tipo).append("\n");
            sb.append(" \tpropietario: ").append(this.duenho.getNombre()).append("\n");
            sb.append(" \tvalor: ").append(this.valor).append("\n");
            sb.append(" \talquiler: ").append(this.impuesto).append("\n}");
        }
        else if(this.tipo.equals("Impuesto")){
            sb.append("{\n\ttipo: ").append(this.tipo).append("\n");
            sb.append(" \timpuesto: ").append(this.impuesto).append("\n}");
        }
        else if(this.nombre.equalsIgnoreCase("Parking")){
            sb.append("{\tbote: TENO A BANCA E EEEEE");
            sb.append(" \tXogadores na casilla: [");
            for(Avatar avatarEnCasilla: this.avatares){
                sb.append(avatarEnCasilla.getJugador().getNombre()).append(", ");
            }
            sb.append("]\n}");
        }
        else if(this.nombre.equalsIgnoreCase("Cárcel")){
            sb.append("{\tCusto salir: 5000000\n");
            sb.append(" \tXogadores na casilla: [");
            for(Avatar avatarEnCasilla: this.avatares){
                sb.append(avatarEnCasilla.getJugador().getNombre()).append(", ");
            }
            sb.append("]\n}");
        }else{
            sb.append("{\tnombre: ").append(this.nombre).append("\n");
            sb.append(" \ttipo: ").append(this.tipo).append("\n}");
        }
        return sb.toString();
    }
}
   
