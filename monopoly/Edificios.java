package monopoly;

import java.util.HashMap;
import java.util.Map;

public class Edificios {
    private Map<String, Integer> counters = new HashMap<>();

    private String id;
    private String tipo;
    private int numCasas;
    private boolean tenEdificio;
    private int custo;
    private int alquiler;

    public Edificios(String tipo, int custo, int alquiler, int numCasas){
        this.tipo = tipo;
        this.custo = custo;
        this.alquiler = alquiler;
        this.numCasas = numCasas;
        this.id = generateId(tipo);
    }

    public Edificios(String tipo, int custo, int alquiler, boolean tenEdificio){
        this.tipo = tipo;
        this.custo = custo;
        this.alquiler = alquiler;
        this.tenEdificio = tenEdificio;
        this.id = generateId(tipo);
    }

    private String generateId(String tipo){
        String key = tipo.toLowerCase().trim();
        int count = counters.getOrDefault(key, 0) + 1;
        counters.put(key, count);
        return key + "-" + count;
    }

    public String getId(){
        return this.id;
    }

    public void anhadirCasa(){
        this.numCasas += 1;
    }

    public void eliminarCasa(){
        if(this.numCasas > 0){
            this.numCasas -= 1;
        }
    }

    public void setNumCasas(int numCasas){
        this.numCasas = numCasas;
    }
    
    public void setTenEdificio(boolean tenEdificio){
        this.tenEdificio = tenEdificio;
    }

    public boolean getTenEdificio(){
        return this.tenEdificio;
    }
    public int getNumCasas(){
        return this.numCasas;
    }
    public int getCusto(){
        return this.custo;
    }
    public int getAlquiler(){
        return this.alquiler;
    }
    public String getTipo(){
        return this.tipo;
    }
}
