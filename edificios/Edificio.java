package edificios;
import java.util.HashMap;
import java.util.Map;

import casillas.Casilla;

public class Edificio {
    private static Map<String, Integer> counters = new HashMap<>();
    private Casilla casilla;
    private boolean tenEdificio;
    private float custo;
    private float alquiler;
    public Edificio(Casilla casilla, float custo, float alquiler) {
        this.casilla = casilla;
        this.custo = custo;
        this.alquiler = alquiler;
    }
 

}
