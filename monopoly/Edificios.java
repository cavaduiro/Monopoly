package monopoly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Edificios {
    // contador global por clave (tipo, tipo-casa, etc.)
    // Mantémse global e só incrementa: nunca decrementa. Conta para todos os edificios.
    private static Map<String, Integer> counters = new HashMap<>();

    private String id;                 // id do propio edificio (pode ser null ata crealo)
    private ArrayList<String> idCasas; // ids das casas (se aplicable)
    private String tipo;
    private int numCasas;
    private boolean tenEdificio;
    private int custo;
    private int alquiler;
    private Casilla casilla;
    // Constructor para propiedades con casas (o máis simple posible)
    // NON xenera ids aquí; so declara a lista e o número de casas local
    public Edificios(String tipo, int custo, int alquiler, int numCasas, Casilla casilla){
        this.tipo = tipo;
        this.custo = custo;
        this.alquiler = alquiler;
        this.numCasas = 0; // garantir rango 0..4
        this.idCasas = new ArrayList<>(); // non se crean ids de casas aquí
        this.casilla = casilla;
    }

    // Constructor para edificios únicos (p.ex. piscina, deporte, hotel)
    // NON xenera id aquí nin lista de casas
    public Edificios(String tipo, int custo, int alquiler, boolean tenEdificio, Casilla casilla){
        this.tipo = tipo;
        this.custo = custo;
        this.alquiler = alquiler;
        this.tenEdificio = tenEdificio; // non xenera id automaticamente
        this.id = null;
        this.casilla = casilla;
    }

    // Xera o seguinte id para a clave dada (contador global que só avanza)
    private static String generateID(String rawKey){
        String key = rawKey.toLowerCase().trim();
        int count = counters.getOrDefault(key, 0) + 1;
        counters.put(key, count);
        return key + "-" + count;
    }

    public String getId(){
        return this.id;
    }

    public ArrayList<String> getIdCasas(){
        return new ArrayList<>(this.idCasas); // devolve copia para non exponer interna
    }

    // Engadir unha casa: xera id e anade ao array (ata 4)
    public void anhadirCasa(){
        if(this.numCasas < 4){
            String newId = generateID(this.tipo);
            this.idCasas.add(newId);
            this.numCasas += 1;
        }
    }

    // Eliminar última casa: quita id do array (non se devolve contador)
    public void eliminarCasa(){
        if(this.numCasas > 0 && !this.idCasas.isEmpty()){
            this.idCasas.remove(this.idCasas.size() - 1);
            this.numCasas -= 1;
        }
    }

    // Configurar número de casas usando as operacións anteriores
    public void setNumCasas(int numCasas){
        if(numCasas < 0) numCasas = 0;
        if(numCasas > 4) numCasas = 4;
        while(this.numCasas < numCasas){
            anhadirCasa();
        }
        while(this.numCasas > numCasas){
            eliminarCasa();
        }
    }
    
    // Ao activar o edificio único, xera id; ao desactivar, elimina a referencia (non se devolve contador)
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

    @Override
    public String toString()
    {   //Falta facer un tostring de propiedades
        StringBuilder sb = new StringBuilder();
        if(this.tipo=="casa"){
            for(String casaId : this.idCasas){
                sb.append("\nid: ").append(casaId);
                sb.append("\npropietario: ").append(this.casilla.getDuenho().getNombre());
                sb.append("\ncasilla: ").append(this.casilla.getNombre());
                sb.append("\ngrupo: ").append(Valor.getNombreColor(this.casilla.getGrupo().getColorGrupo()));
                sb.append("\ncoste: ").append(this.custo);
            }
        }else{
            sb.append("\nid: ").append(this.id);
            sb.append("\npropietario: ").append(this.casilla.getDuenho().getNombre());
            sb.append("\ncasilla: ").append(this.casilla.getNombre());
            sb.append("\ngrupo: ").append(Valor.getNombreColor(this.casilla.getGrupo().getColorGrupo()));
            sb.append("\ncoste: ").append(this.custo);
        }
        sb.append("\n");
        return sb.toString();
    }
}