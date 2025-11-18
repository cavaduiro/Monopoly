package monopoly;


import java.util.ArrayList;
import java.util.Arrays;
//Hola
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

        public static int getCosteCompraEdificio(int posicion, String tipoEdificio) {
            //Os valores teñen estas coorelacións, miradeo no arquivo se qeurees 
            int valor =0;
            switch (posicion){
                case 1: case 3: case 6: case 8: case 9:
                    valor = 500000;
                    break;
                case 11: case 13: case 14: case 16: case 18: case 19:
                    valor = 1000000;
                    break;
                case 21: case 23: case 24: case 26: case 27: case 29:
                    valor = 1500000;
                    break;
                case 31: case 32: case 34: case 37: case 39:
                    valor = 2000000;
                    break;
                default:
                    return 0;
            }
            if(tipoEdificio.equals("hotel") || tipoEdificio.equals("casa")){
                return(valor);
            } else if(tipoEdificio.equals("piscina")){
                return (valor/5);
            } else if(tipoEdificio.equals("deporte")){
                return ((valor/5)*2);
            }
            return 0;
        
        }

        public static int getCosteAlquilerEdificio(int posicion, String tipoEdificio) {
            //Os valores teñen estas coorelacións, miradeo no arquivo se qeurees 
            int valor =0;
            switch (tipoEdificio){
                case "casa":
                    switch(posicion){
                        case 1:
                            valor = 400000;
                            break;
                        case 3:
                            valor = 800000;
                            break;
                        case 6: case 8:
                            valor = 1000000;
                            break;
                        case 9:
                            valor = 1250000;
                            break;
                        case 11: case 13: 
                            valor = 1500000;
                            break;
                        case 14:
                            valor = 1750000;
                            break;
                        case 16: case 18: 
                            valor = 1850000;
                            break;
                        case 19:
                            valor = 2000000;
                            break;
                        case 21: case 23: 
                            valor = 2200000;
                            break;
                        case 24:
                            valor = 2325000;
                            break;
                        case 26: case 27: 
                            valor = 2450000;
                            break;
                        case 29:
                            valor = 2600000;
                            break;
                        case 31: case 32:
                            valor = 2750000;
                            break;
                        case 34: 
                            valor = 3000000;
                            break;
                        case 37: 
                            valor = 3250000;
                            break;  
                        case 39:
                            valor = 4250000;
                            break;
                    }
                    return(valor);
                case "hotel": case "piscina": case "deporte": 
                    switch(posicion){
                        case 1:
                            valor = 2500000;
                            break;
                        case 3:
                            valor = 4500000;
                            break;
                        case 6: case 8:
                            valor = 5500000;
                            break;
                        case 9:
                            valor = 6000000;
                            break;
                        case 11: case 13: 
                            valor = 7500000;
                            break;
                        case 14:
                            valor = 9000000;
                            break;
                        case 16: case 18: 
                            valor = 9500000;
                            break;
                        case 19:
                            valor = 10000000;
                            break;
                        case 21: case 23: 
                            valor = 10500000;
                            break;
                        case 24:
                            valor = 11000000;
                            break;
                        case 26: case 27: 
                            valor = 11500000;
                            break;
                        case 29:
                            valor = 12000000;
                            break;
                        case 31: case 32:
                            valor = 12750000;
                            break;
                        case 34: 
                            valor = 14000000;
                            break;
                        case 37: 
                            valor = 17000000;
                            break;  
                        case 39:
                            valor = 20000000;
                            break;
                    }
                    break;

        
            }
            if(tipoEdificio.equals("hotel")){
                return(valor);
            } else if(tipoEdificio.equals("piscina") || tipoEdificio.equals("deporte")){
                return (valor/5);
            }
            return 0;
        }

        public static void error(String mensaje) {
            System.out.println(RED + "\033[1mError: \033[0m" + mensaje + RESET);
        }
}