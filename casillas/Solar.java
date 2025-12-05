package casillas;
import java.util.ArrayList;
import java.util.HashMap;

import monopoly.*;
import partida.*;
import edificios.*;
import javax.net.ssl.HostnameVerifier;

public class Solar extends Propiedad {
    private Grupo grupo;
    private ArrayList<Casa> casas;
    private int custoCasa;
    private Hotel hotel;
    private Piscina piscina;
    private Pista pista;
    private int numCasas;
    public Solar(String nombre, int posicion, float valor, Jugador duenho, float alquiler, float hipoteca) {
        //super(nombre, posicion, valor, alquiler, hipoteca, duenho);
        super(nombre, posicion, valor, duenho, alquiler, hipoteca);
        hotel= new Hotel(this, (int) Valor.getCosteCompraEdificio(posicion, "piscina"),
                (int) Valor.getCosteAlquilerEdificio(posicion, "piscina"), "piscina");
        piscina = new Piscina(this, (int) Valor.getCosteCompraEdificio(posicion, "piscina"),
                (int) Valor.getCosteAlquilerEdificio(posicion, "piscina"), "piscina");
        pista = new Pista(this, (int) Valor.getCosteCompraEdificio(posicion, "pista"),
                (int) Valor.getCosteAlquilerEdificio(posicion, "pista"), "pista");
        custoCasa = Valor.getCosteCompraEdificio(getPosicion(), "casa");
    }

    
    




    public void setGrupo(Grupo grup) {
        this.grupo = grup;
    }

    public Grupo getGrupo() {
        return this.grupo;
    }

     // Engadir unha casa: xera id e anade ao array (ata 4)
     public void anhadirCasa() {
         if (this.numCasas < 4) {
             String newId = Casa.generateID("Casa");
             Casa casa = new Casa(this, (int) Valor.getCosteCompraEdificio(getPosicion(), "casa"),
                     (int) Valor.getCosteAlquilerEdificio(getPosicion(), "casa"), newId, "casa");
             casas.add(casa);
             this.numCasas += 1;
         }
     }
    




     public void edificar(String tipo, Jugador jugadorActual) {
        



        boolean hotelConstruido = hotel.getTenEdificio();
        boolean piscinaConstruida = piscina.getTenEdificio();
        boolean pistaConstruida = pista.getTenEdificio();
        
        if (tipo.equalsIgnoreCase("casa")) {
            int precioConstrucion = 
            if(numCasas>=4){
                //ERROR CHEQUEAABLE (hai varios)
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
        Juego.consol.imprimir("Construiches un/a "+tipo+" na casilla "+this.getNombre()+" por "+precioConstrucion+"$");
        jugadorActual.sumarFortuna(-precioConstrucion);
        this.rentabilidad-=precioConstrucion;
        jugadorActual.getEstatisticas().pagoinversion(precioConstrucion);
    }
    //OVERRIDES
       @Override
        public boolean EvaluarCasilla(Jugador actual, Jugador banca, int tirada,ArrayList<ArrayList<Casilla>> posiciones)
        {

            return false;
        }
        @Override
        public float valor( )
        {
            return 0;
        }
        @Override
        public boolean alquiler() 
        {
            return true;
        }
    }