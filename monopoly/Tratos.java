package monopoly;
import casillas.*;
import partida.*;

public class Tratos {
    private String id;
    private Jugador ofertante;
    private Jugador receptor;
    private Propiedad propiedadOfrecida;
    private float dineroOfrecido;
    private Propiedad propiedadSolicitada;
    private float dineroSolicitado;
    private int tipoTrato;
    /*
        1: Propiedad por Propiedad
        2: Propiedad por Diñeiro
        3: Diñeiro por Propiedad
        4: Propiedad por Propiedad y Diñeiro
        5: Propiedad e Diñeiro por Propiedad
    */
    static int contadorID = 0;

    int generarID() {
        return contadorID++;
    }

    public Tratos(){

    }

    public Tratos(Jugador ofertante, Jugador receptor, Propiedad propiedadOfrecida, float dineroOfrecido, Propiedad propiedadSolicitada, float dineroSolicitado, int tipoTrato) {
        this.id = "Trato"+generarID();
        this.ofertante = ofertante;
        this.receptor = receptor;
        this.propiedadOfrecida = propiedadOfrecida;
        this.dineroOfrecido = dineroOfrecido;
        this.propiedadSolicitada = propiedadSolicitada;
        this.dineroSolicitado = dineroSolicitado;
        this.tipoTrato = tipoTrato;
    }


    public Jugador getOfertante() {
        return ofertante;
    }
    public Jugador getReceptor() {
        return receptor;
    }
    public Propiedad getPropiedadOfrecida() {
        return propiedadOfrecida;
    }
    public float getDineroOfrecido() {
        return dineroOfrecido;
    }
    public Propiedad getPropiedadSolicitada() {
        return propiedadSolicitada;
    }
    public float getDineroSolicitado() {
        return dineroSolicitado;
    }
    public String getId() {
        return id;
    }
    public int getTipoTrato() {
        return tipoTrato;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{\nid: ").append(this.id).append("\n");
        sb.append("  Xogador ofertante: ").append(this.ofertante.getNombre()).append("\n");
        sb.append("  Xogador receptor: ").append(this.receptor.getNombre()).append("\n");
        sb.append("  trato: cambiar(");
        sb.append(this.dineroOfrecido>0 ? this.dineroOfrecido+" euros " : "").append(this.propiedadOfrecida!=null ? this.propiedadOfrecida.getNombre() : "").append(", ").append(this.dineroSolicitado>0 ? this.dineroSolicitado+" euros " : "").append(this.propiedadSolicitada!=null ? this.propiedadSolicitada.getNombre() : "").append(")\n}");
        return sb.toString();
    }
}