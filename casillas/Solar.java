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
    //
     
    /*
    public void anhadirCasa() {
         if (this.numCasas < 4) {
             String newId = Casa.generateID("Casa");
             Casa casa = new Casa(this, (int) Valor.getCosteCompraEdificio(getPosicion(), "casa"),
                     (int) Valor.getCosteAlquilerEdificio(getPosicion(), "casa"), newId, "casa");
             casas.add(casa);
             this.numCasas += 1;
         }
     }
    
    
    */
     
    

     public int getNumCasas() {
         return numCasas;
    }

    public ArrayList<Casa> getCasas() {
        return casas;
    }

    public boolean tenhotel() {
        return hotel.getTenEdificio();
    }

    public boolean tenpiscina() {
        return piscina.getTenEdificio();
    }

    public boolean tenpista() {
        return pista.getTenEdificio();
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Piscina getPiscina() {
        return piscina;
    }

    public Pista getPista() {
        return pista;
    }



     public void edificar(String tipo, Jugador jugadorActual) {
         boolean hotelConstruido = hotel.getTenEdificio();
         boolean piscinaConstruida = piscina.getTenEdificio();
         boolean pistaConstruida = pista.getTenEdificio();
         int precioConstrucion = 0;
         if (tipo.equalsIgnoreCase("casa")) {
             precioConstrucion = custoCasa;
             if (numCasas >= 4) {
                 //ERROR CHEQUEAABLE (hai varios)
                 System.out.println("Xa tes o número máximo de casa permitidas na casilla");
                 return;
             }
             if (jugadorActual.getFortuna() < precioConstrucion) {
                 System.out.println("Non tes suficiente fortuna para construír unha casa nesta casilla, custa "
                         + precioConstrucion + "€");
                 return;
             }
             if (hotelConstruido || piscinaConstruida || pistaConstruida) {
                 System.out.println(
                         "Non podes construír máis casas nesta casilla, xa tes un hotel ou unha instalación deportiva ou piscina construída");
                 return;
             }
             anhadirCasa();
         }
         //ESTO PÖDESE MODULARIZAR MOITO MOITO MOITO; DE MOMENTO DAME PEREZA
         if (tipo.equalsIgnoreCase("hotel")) {
             precioConstrucion = hotel.getCusto();
             if (hotelConstruido) {
                 System.out.println("Xa tes un hotel construido");
                 return;
             }
             if (numCasas != 4) {
                 System.out.println("Necesitas ter 4 casas nesta casilla para construír un hotel");
                 return;
             }
             if (jugadorActual.getFortuna() < precioConstrucion) {
                 System.out.println("Non tes suficiente fortuna para construír un hotel nesta casilla, custa "
                         + precioConstrucion + "€");
                 return;
             }
             setNcasas(0);
             hotel.setTenEdificio(true);
         }
         if (tipo.equals("piscina")) {
             precioConstrucion = piscina.getCusto();
             if (piscinaConstruida) {
                 //error chequeable
                 System.out.println("Xa tes unha piscina construída");
                 return;
             }
             if (pistaConstruida) {
                 System.out.println(
                         "Non podes construír unha piscina nesta casilla, xa tes unha instalación deportiva construída");
                 return;
             }
             if (!hotelConstruido) {
                 System.out.println("Necesitas ter un hotel nesta casilla para construír unha piscina");
                 return;
             }
             if (jugadorActual.getFortuna() < precioConstrucion) {
                 System.out.println("Non tes suficiente fortuna para construír unha piscina nesta casilla, custa "
                         + precioConstrucion + "€");
                 return;
             }
             piscina.setTenEdificio(true);
         }
         if (tipo.equals("deporte")) {
             precioConstrucion = pista.getCusto();
             if (pistaConstruida) {
                 System.out.println("Xa tes unha instalación deportiva construída");
                 return;
             }
             if (!hotelConstruido || !piscinaConstruida) {
                 System.out.println(
                         "Necesitas ter un hotel e unha piscina nesta casilla para construír unha instalación deportiva");
                 return;
             }
             if (jugadorActual.getFortuna() < precioConstrucion) {
                 System.out.println(
                         "Non tes suficiente fortuna para construír unha instalación deportiva nesta casilla, custa "
                                 + precioConstrucion + "€");
                 return;
             }
             pista.setTenEdificio(true);
         }
         Juego.consol.imprimir(
                 "Construiches un/a " + tipo + " na casilla " + this.getNombre() + " por " + precioConstrucion + "$");
         jugadorActual.sumarFortuna(-precioConstrucion);
         setRentabilidade(getRentabilidad() - precioConstrucion);
         jugadorActual.getEstatisticas().pagoinversion(precioConstrucion);
     }
    
     public boolean tenEdificio()
     {
         return tenhotel() || tenpiscina() || tenpista();
     }


     public void vender(String tipo, int numEdificios, Jugador jugadorActual){
        boolean hotelConstruido = hotel.getTenEdificio();
        boolean piscinaConstruida = piscina.getTenEdificio();
        boolean pistaConstruida = pista.getTenEdificio();
        int precioventa = 0;
        if (tipo.equals("casa")) {
            precioventa = custoCasa;
            if(numCasas<numEdificios){
                System.out.println("Non tes tantas casas construídas nesta casilla, no solar "+this.getNombre()+" tes "+numCasas+" casas construídas");
                return;
            }else if(numEdificios>numCasas){
                System.out.println("Non tes tantas casas construídas nesta casilla, no solar "+this.getNombre()+" tes "+numCasas+" casas construídas");
                return;
            }
            setNcasas(numCasas - numEdificios);
        }
        if (tipo.equals("hotel")) {
            precioventa = hotel.getCusto();
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
            hotel.setTenEdificio(false);
        }
        if (tipo.equals("piscina")) {
            precioventa = piscina.getCusto();
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
            piscina.setTenEdificio(false);
        }
        if (tipo.equals("deporte")) {
            precioventa = pista.getCusto();
            if(!pistaConstruida){
                System.out.println("Non tes unha instalación deportiva construída nesta casilla");
                return;
            }
            if(numEdificios>1){
                System.out.println("Non poder haber "+numEdificios+" construidos, só unha");
                return;
            }
            pista.setTenEdificio(false);
        }
        jugadorActual.sumarFortuna(precioventa*numEdificios);
        Juego.consol.imprimir("Vendeches "+numEdificios+" "+tipo+" na casilla "+this.getNombre()+" por "+precioventa*numEdificios+"$");
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


        public void eliminarCasa(){
            if (this.numCasas > 0 && !this.casas.isEmpty()) {
                this.casas.remove(this.casas.size() - 1);
                this.numCasas -= 1;
            }
        }
        public void setNcasas(int numCasas){
        if(numCasas < 0) numCasas = 0;
        if(numCasas > 4) numCasas = 4;
        while(this.numCasas < numCasas){
            anhadirCasa();
        }
        while(this.numCasas > numCasas){
            eliminarCasa();
        }
    }    
    }
    