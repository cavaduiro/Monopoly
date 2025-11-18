package partida;

import monopoly.*;
public class Estats {

    //Atributos
    private float dineroInvertido;
    private float pagoTasasImp;
    private float pagoAlquileres;
    private float cobroAlquileres;
    private float cobroSalidas;
    private float premiosInversionesBote;
    private float vecesCarcel;
    private float voltasDadas;
    private Jugador xogador;


    public Estats(Jugador xog) {
        this.xogador = xog;
    } //Referencia ao xogador que posee as stats


    //Getters e Setters

    public float getVoltasDadas() {
        return voltasDadas;
    }
    public void setVoltasDadas(float voltasDadas) {
        this.voltasDadas = voltasDadas;
    }

    public float getDineroInvertido() {
        return dineroInvertido;
    }

    public void setDineroInvertido(float dineroInvertido) {
        this.dineroInvertido = dineroInvertido;
    }

    public float getPagoTasasImp() {
        return pagoTasasImp;
    }

    public void setPagoTasasImp(float pagoTasasImp) {
        this.pagoTasasImp = pagoTasasImp;
    }

    public float getPagoAlquileres() {
        return pagoAlquileres;
    }

    public void setPagoAlquileres(float pagoAlquileres) {
        this.pagoAlquileres = pagoAlquileres;
    }

    public float getCobroAlquileres() {
        return cobroAlquileres;
    }

    public void setCobroAlquileres(float cobroAlquileres) {
        this.cobroAlquileres = cobroAlquileres;
    }

    public float getCobroSalidas() {
        return cobroSalidas;
    }

    public void setCobroSalidas(float cobroSalidas) {
        this.cobroSalidas = cobroSalidas;
    }

    public float getPremiosInversionesBote() {
        return premiosInversionesBote;
    }

    public void setPremiosInversionesBote(float premiosInversionesBote) {
        this.premiosInversionesBote = premiosInversionesBote;
    }

    public float getVecesCarcel() {
        return vecesCarcel;
    }

    public void setVecesCarcel(float vecesCarcel) {
        this.vecesCarcel = vecesCarcel;
    }

    //Fin de getters e setters

    //Función que acumula as veces que un xogador vai ao cárcere
    public void sumarCarcel() {
        this.vecesCarcel++;
    }

    //Función que suma canto pagaches en impostos
    public void acImpPagado(float imp) {
        this.pagoTasasImp += imp;
    }

    //Función que suma os cartos que ganhas ao pasar pola saida
    public void sumarsalidas() {
        this.cobroSalidas += Valor.SUMA_VUELTA;
    }

    //Función que chama desde o inquilino cando se produce a transacción,
    public void transAlq(float pago) {
        Jugador rentista = this.xogador.getAvatar().getLugar().getDuenho();
        this.pagoAlquileres += pago;
        rentista.getEstatisticas().cobroAlquileres += pago;
    }
    public void pagoinversion(float pago){
        this.dineroInvertido += pago;
    }

    public void sumarVoltas() {
        this.voltasDadas++;
    }

    public void sumarbote(float premio) {
        this.premiosInversionesBote += premio;
    }

    @Override
    public String toString() {
        return "Estatísticas do xogador " + this.xogador.getNombre() + ":\n" +
                "Dinero invertido en propiedades: " + dineroInvertido + "€\n" + //v
                "Dinero pagado en taxas e impostos: " + pagoTasasImp + "€\n" +
                "Dinero pagado en alugueres: " + pagoAlquileres + "€\n" +
                "Dinero cobrado en alugueres: " + cobroAlquileres + "€\n" +
                "Dinero cobrado ao pasar pola saída: " + cobroSalidas + "€\n" +
                "Premios gañados en inversións e bote: " + premiosInversionesBote + "€\n" +
                "Número de veces na cárcere: " + vecesCarcel + "\n" +
                "Número de voltas dadas ao taboleiro: " + voltasDadas + "\n";

    }
}