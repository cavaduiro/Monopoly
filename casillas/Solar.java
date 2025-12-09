package casillas;
import edificios.*;
import exception.*;
import exception.valorInvalido.ExcepcionSinCartos;
import java.util.ArrayList;
import monopoly.*;
import partida.*;

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
        hotel= new Hotel(this, (int) Valor.getCosteCompraEdificio(posicion, "hotel"),
                (int) Valor.getCosteAlquilerEdificio(posicion, "hotel"), "hotel");
        piscina = new Piscina(this, (int) Valor.getCosteCompraEdificio(posicion, "piscina"),
                (int) Valor.getCosteAlquilerEdificio(posicion, "piscina"), "piscina");
        pista = new Pista(this, (int) Valor.getCosteCompraEdificio(posicion, "deporte"),
                (int) Valor.getCosteAlquilerEdificio(posicion, "deporte"), "deporte");
        casas = new ArrayList<>();
        this.numCasas = 0;
        this.custoCasa = Valor.getCosteCompraEdificio(getPosicion(), "casa");
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



     public void edificar(String tipo, Jugador jugadorActual) throws ExcepcionValorInvalido, ExcepcionLoxicaPartida , ExcepcionSinCartos{
         boolean hotelConstruido = hotel.getTenEdificio();
         boolean piscinaConstruida = piscina.getTenEdificio();
         boolean pistaConstruida = pista.getTenEdificio();
         int precioConstrucion = 0;
         if (tipo.equalsIgnoreCase("casa")) {
             precioConstrucion = custoCasa;
             if (numCasas >= 4) {
                 //ERROR CHEQUEAABLE (hai varios)
                throw new ExcepcionValorInvalido("Non podes construír máis casas nesta casilla, xa tes 4 casas construídas");
             }
             if (jugadorActual.getFortuna() < precioConstrucion) {
                throw new ExcepcionSinCartos(jugadorActual.getNombre(),(int)jugadorActual.getFortuna(),precioConstrucion);
            }
             if (hotelConstruido || piscinaConstruida || pistaConstruida) {
                throw new ExcepcionLoxicaPartida(
                         "Non podes construír máis casas nesta casilla, xa tes un hotel ou unha instalación deportiva ou piscina construída");
             }
             anhadirCasa();
         }
         //ESTO PÖDESE MODULARIZAR MOITO MOITO MOITO; DE MOMENTO DAME PEREZA
         if (tipo.equalsIgnoreCase("hotel")) {
             precioConstrucion = hotel.getCusto();
             if (hotelConstruido) {
                throw new ExcepcionLoxicaPartida("Non podes construír un hotel nesta casilla, xa tes un hotel construido"); 
             }
             if (numCasas != 4) {
                throw new ExcepcionLoxicaPartida("Necesitas ter 4 casas construídas nesta casilla para construír un hotel");
             }
             if (jugadorActual.getFortuna() < precioConstrucion) {
                 throw new ExcepcionSinCartos(jugadorActual.getNombre(),(int)jugadorActual.getFortuna(),precioConstrucion);
             }
             setNcasas(0);
             hotel.setTenEdificio(true);
         }
         if (tipo.equals("piscina")) {
             precioConstrucion = piscina.getCusto();
             if (piscinaConstruida) {
                throw new ExcepcionLoxicaPartida("Non podes construír unha piscina nesta casilla, xa tes unha piscina construída");
             }
             if (pistaConstruida) {
                 throw new ExcepcionLoxicaPartida("Non podes construír unha piscina nesta casilla, xa tes unha instalación deportiva construída");
             }
             
             if (!hotelConstruido) {
                 throw new ExcepcionLoxicaPartida("Necesitas ter un hotel nesta casilla para construír unha piscina");
             }
             if (jugadorActual.getFortuna() < precioConstrucion) {
                 throw new ExcepcionSinCartos(jugadorActual.getNombre(),(int) jugadorActual.getFortuna(),precioConstrucion);
             }
             
             piscina.setTenEdificio(true);
         }
         if (tipo.equals("deporte")) {
             precioConstrucion = pista.getCusto();
             if (pistaConstruida) {
                throw new ExcepcionLoxicaPartida("Non podes construír unha instalación deportiva nesta casilla, xa tes unha instalación deportiva construída");
             }
             if (!hotelConstruido || !piscinaConstruida) {
                throw new ExcepcionLoxicaPartida("Necesitas ter un hotel e unha piscina nesta casilla para construír unha instalación deportiva");
             }
             if (jugadorActual.getFortuna() < precioConstrucion) {
                throw new ExcepcionSinCartos(jugadorActual.getNombre(),(int) jugadorActual.getFortuna(),precioConstrucion);    
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
         return tenhotel() || tenpiscina() || tenpista() || getNumCasas() > 0;
     }


     public void vender(String tipo, int numEdificios, Jugador jugadorActual) throws ExcepcionLoxicaPartida, ExcepcionValorInvalido{
        boolean hotelConstruido = hotel.getTenEdificio();
        boolean piscinaConstruida = piscina.getTenEdificio();
        boolean pistaConstruida = pista.getTenEdificio();
        int precioventa = 0;
        if (tipo.equals("casa")) {
            precioventa = custoCasa;
            if(numCasas<numEdificios){
                throw new ExcepcionValorInvalido("Non tes tantas casas construídas nesta casilla, no solar "+this.getNombre()+" tes "+numCasas+" casas construídas");
            }else if(numEdificios>numCasas){
                throw new ExcepcionValorInvalido("Non tes tantas casas construídas nesta casilla, no solar "+this.getNombre()+" tes "+numCasas+" casas construídas");
            }
            setNcasas(numCasas - numEdificios);
        }
        if (tipo.equals("hotel")) {
            precioventa = hotel.getCusto();
            if(!hotelConstruido){
                throw new ExcepcionLoxicaPartida("Non tes un hotel construída nesta casilla");
            }
            if(pistaConstruida){
                throw new ExcepcionLoxicaPartida("Non podes vender o hotel se tes unha instalación deportiva construída");
            }
            if(piscinaConstruida){
                throw new ExcepcionLoxicaPartida("Non podes vender o hotel se tes unha piscina construída");
            }
            if(numEdificios>1){
                throw new ExcepcionValorInvalido("Non poder haber "+numEdificios+" construidos, só un");
            }
            hotel.setTenEdificio(false);
        }
        if (tipo.equals("piscina")) {
            precioventa = piscina.getCusto();
            if(!piscinaConstruida){
                throw new ExcepcionLoxicaPartida("Non tes unha piscina construída nesta casilla");
            }
            if(pistaConstruida){
                throw new ExcepcionLoxicaPartida("Non podes vender a piscina se tes unha instalación deportiva construída");
            }
            if(numEdificios>1){
                throw new ExcepcionValorInvalido("Non poder haber "+numEdificios+" construidos, só unha");
            }
            piscina.setTenEdificio(false);
        }
        if (tipo.equals("deporte")) {
            precioventa = pista.getCusto();
            if(!pistaConstruida){
                throw new ExcepcionLoxicaPartida("Non tes unha instalación deportiva construída nesta casilla");
            }
            if(numEdificios>1){
                throw new ExcepcionValorInvalido("Non poder haber "+numEdificios+" construidos, só unha");
            }
            pista.setTenEdificio(false);
        }
        jugadorActual.sumarFortuna(precioventa*numEdificios);
        Juego.consol.imprimir("Vendeches "+numEdificios+" "+tipo+" na casilla "+this.getNombre()+" por "+precioventa*numEdificios+"$");
    }

     // Engadir unha casa: xera id e anade ao array (ata 4)
     public void anhadirCasa() {
         if (this.numCasas < 4) {
             String newId = Casa.generateID("casa");
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
            try{
            if(this.getDuenho() != banca && this.getDuenho() != actual && !this.getHipotecada())
            {
                float alquilerTotal = this.getAlquiler();
                // engadir casas
                for(Casa casa : this.casas)
                {
                    alquilerTotal += casa.getAlquiler();
                }
                // engadir hotel
                if(this.tenhotel())
                {
                    alquilerTotal += this.getHotel().getAlquiler();
                }
                // engadir piscina
                if(this.tenpiscina())
                {
                    alquilerTotal += this.getPiscina().getAlquiler();
                }
                // engadir pista
                if(this.tenpista())
                {
                    alquilerTotal += this.getPista().getAlquiler();
                }
                Juego.consol.imprimir(actual.getNombre()+" paga "+alquilerTotal+"€ de aluguer por caer en "+this.getNombre()+" de "+this.getDuenho().getNombre());
                if(actual.getFortuna() < alquilerTotal)
                {
                    throw new ExcepcionSinCartos(actual.getNombre(), (int)actual.getFortuna(), (int)alquilerTotal);
                }
                actual.sumarFortuna(-alquilerTotal);
                this.getDuenho().sumarFortuna(alquilerTotal);
                actual.getEstatisticas().transAlq(alquilerTotal);
                return true;
            }
            if(this.getHipotecada())
            {
                Juego.consol.imprimir("A propiedade "+this.getNombre()+" está hipotecada, non se paga aluguer");
            }
            Juego.consol.imprimir(actual.getNombre()+" non paga aluguer por caer en "+this.getNombre()  );
            return true;
            }catch(Exception e){
                Juego.consol.imprimir(e.getMessage());
                return false;
            }
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
      public String infoCasilla() {
        String info = new String();
        info+= "\n{\nTipo: Solar";
        info+= "\nGrupo: " + this.grupo.getColorGrupo();
        info+= "\nPropietario: " + this.getDuenho().getNombre();
        info+= "\nValor: " + this.getValor();
        info+= "\nAlquiler: " + this.getAlquiler();
        info+= "\nHipoteca: " + this.getHipoteca();
        info+= "\n}";
        return info;
    }
    @Override
    public String toString(){
               StringBuilder sb = new StringBuilder();
            sb.append("{\n\tnombre: ").append(this.getNombre()).append("\n");
            sb.append(" \ttgrupo: ").append(Valor.getNombreColor(this.grupo.getColorGrupo())).append("\n");
            sb.append(" \ttipo: ").append("\tSolar").append("\n");
            sb.append(" \tpropietario: ").append(this.getDuenho().getNombre()).append("\n");
            sb.append(" \tvalor: ").append(this.getValor()).append("\n");
            sb.append(" \talquiler: ").append(this.getAlquiler()).append("\n");
            sb.append(" \tvalor casa: ").append(this.custoCasa).append("\n");
            sb.append(" \tvalor hotel: ").append(this.getHotel().getCusto()).append("\n");
            sb.append(" \tvalor piscina: ").append(this.getPiscina().getCusto()).append("\n");
            sb.append(" \tvalor deporte: ").append(this.getPista().getCusto()).append("\n");
            sb.append(" \talquiler casa: ").append((float) Valor.getCosteAlquilerEdificio(this.getPosicion(), "casa")).append("\n");
            sb.append(" \talquiler hotel: ").append(this.getHotel().getAlquiler()).append("\n");
            sb.append(" \talquiler piscina: ").append(this.getPiscina().getAlquiler()).append("\n");
            sb.append(" \talquiler deporte: ").append(this.getPista().getAlquiler()).append("\n}");
            return sb.toString();
    }

}
    