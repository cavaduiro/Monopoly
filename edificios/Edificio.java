package edificios;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import casillas.*;

public abstract class Edificio {
    private static Map<String, Integer> counters = new HashMap<>();
    private Solar casilla;
    private boolean tenEdificio;
    private float custo;
    private float alquiler;
    private String id;
    private String tipo;                 // id do propio edificio (pode ser null ata crealo)
    public Edificio(Solar casilla, float custo, float alquiler, String tipo) {
        this.casilla = casilla;
        this.custo = custo;
        this.alquiler = alquiler;
        this.tipo = tipo;
        
    }

    public static String generateID(String rawKey) 
    {
        String key = rawKey.toLowerCase().trim();
        int count = counters.getOrDefault(key, 0) + 1;
        counters.put(key, count);
        return key + "-" + count;
    }

    public String getId() {
        return this.id;
    }

    public boolean getTenEdificio() {
        return this.tenEdificio;
    }

    public int getCusto() {
        return (int)custo;
    }

    public void setTenEdificio(boolean tenEdificio){
        if(tenEdificio && !this.tenEdificio){
            // activando: xerar id se non existe
            if(this.id == null){
                this.id = generateID(this.tipo);
            }
            this.tenEdificio = true;
        } else if(!tenEdificio && this.tenEdificio){
            // desactivando: borrar referencia ao id
            this.id = null;
            this.tenEdificio = false;
        } else {
            this.tenEdificio = tenEdificio; // sen cambio de id
        }
    }
    

}
