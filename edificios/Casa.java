package edificios;


import casillas.*;

public class Casa extends Edificio {
    private String idCasa; // ids das casas (se aplicable)
    public Casa(Solar casilla, float custo, float alquiler, String id, String tipo) {
        super(casilla, custo, alquiler, tipo);
        this.idCasa = id;
    }

    public String getIdCasas() {
        String copia = (String) (this.idCasa);
        return copia; // devolve copia para non exponer interna
    }

}
