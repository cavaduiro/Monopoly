package monopoly;


import java.util.ArrayList;
import java.util.Arrays;

public class Valor {
    //Se incluyen una serie de constantes útiles para no repetir valores.
    public static final float FORTUNA_BANCA = 15000000; // Cantidad que tiene inicialmente la Banca
    public static final float FORTUNA_INICIAL = 15000000; // Cantidad que recibe cada jugador al comenzar la partida
    public static final float SUMA_VUELTA = 2000000; // Cantidad que recibe un jugador al pasar pos la Salida
    public static ArrayList<Integer> transportes = new ArrayList<>(Arrays.asList(5,15,25,35));

    //Colores del texto:
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    //Añado o colores que faltaban
    public static final String ORANGE = "\u001B[38;5;208m";
    public static final String PINK = "\u001B[38;5;213m";

    public static String getNombreColor(String codigo) {
        if (codigo.equals(GREEN)) return "Verde";
        if (codigo.equals(RED)) return "Vermello";
        if (codigo.equals(BLUE)) return "Azul";
        if (codigo.equals(CYAN)) return "Cian";
        if (codigo.equals(YELLOW)) return "Amarelo";
        if (codigo.equals(PURPLE)) return "Púrpura";
        if (codigo.equals(WHITE)) return "Branco";
        if (codigo.equals(BLACK)) return "Negro";
        if (codigo.equals(ORANGE)) return "Laranxa";
        if (codigo.equals(PINK)) return "Rosa";
        if (codigo.equals(RESET)) return "RESET";
        return "DESCONOCIDO";
    }

}
