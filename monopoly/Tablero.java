package monopoly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import partida.*;


public class Tablero {
    //Atributos.
    private ArrayList<ArrayList<Casilla>> posiciones; //Posiciones del tablero: se define como un arraylist de arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; //Grupos del tablero, almacenados como un HashMap con clave String (será el color del grupo).
    private Jugador banca; //Un jugador que será la banca.


    //Constructor: únicamente le pasamos el jugador banca (que se creará desde el menú).
    public Tablero(Jugador banca) {
        this.banca = banca;
        this.posiciones = new ArrayList<>();
        this.grupos = new HashMap<>();
        this.generarCasillas(); //Con este disparador empeza a crear as casillas do tableiro
        //(Este this non sería necesario non, pq non se está referindo a ningún atributo porpio nin nada, non? Ponmo copilot idk)
        //Hugo: supoño que se usará cando fas tablero monopoly = new tablero() ???
    }
    
    //Metodo para crear todas las casillas del tablero. Formado a su vez por cuatro métodos (1/lado).
    private void generarCasillas() {
        this.insertarLadoSur();
        this.insertarLadoOeste();
        this.insertarLadoNorte();
        this.insertarLadoEste();
    }
    
    //Metodo para insertar las casillas del lado sur.
    private void insertarLadoSur() {
        Casilla salida = new Casilla("Salida", "Especial", 0, this.banca); 
        Casilla solar1 = new Casilla("Solar1", "Solar", 1, 600000, this.banca, 20000, 300000);
        Casilla caja1 = new Casilla("Caja", "Especial", 2, this.banca);
        Casilla solar2 = new Casilla("Solar2", "Solar", 3, 600000, this.banca, 40000, 300000);
        Casilla impuesto1 = new Casilla("Imp1", "Impuesto", 4, this.banca, 2000000.f);
        Casilla trans1 = new Casilla("Trans1", "Transportes", 5, 500000, this.banca, 250000, 0);
        Casilla solar3 = new Casilla("Solar3", "Solar", 6, 1000000, this.banca, 60000, 500000);
        Casilla suerte1 = new Casilla ("Suerte", "Especial", 7, this.banca);
        Casilla solar4 = new Casilla ("Solar4", "Solar", 8, 1000000, this.banca, 60000, 500000);
        Casilla solar5 = new Casilla ("Solar5", "Solar", 9, 1200000, this.banca, 80000, 600000);
        Casilla carcel = new Casilla ("Cárcel", "Especial", 10, this.banca);
        
        //Metemos as casillas no lado sur na posición 0 do array de arrays de posicións
        ArrayList<Casilla> ladoSur = new ArrayList<>();
        ladoSur.add(salida);
        ladoSur.add(solar1);
        ladoSur.add(caja1);
        ladoSur.add(solar2);
        ladoSur.add(impuesto1);
        ladoSur.add(trans1);
        ladoSur.add(solar3);
        ladoSur.add(suerte1);
        ladoSur.add(solar4);
        ladoSur.add(solar5);
        ladoSur.add(carcel);
        this.posiciones.add(ladoSur);

        //Ahora facemos os grupos
        Grupo grupoNegro = new Grupo(solar1, solar2, Valor.BLACK);
        Grupo grupoCeleste = new Grupo(solar3, solar4, solar5, Valor.CYAN);
        this.grupos.put("Negro", grupoNegro);
        this.grupos.put("Celeste", grupoCeleste);
    }

    //Metodo que inserta casillas del lado oeste.
    private void insertarLadoOeste() {
        Casilla solar6 = new Casilla("Solar6", "Solar", 11, 1400000, this.banca, 100000, 700000);
        //NON SEI CANTO COSTA A CASILLA DE SERVICIOS, O VALOR É ARBITRARIO
        Casilla serv1 = new Casilla("Serv1", "Servicios", 12, 1500000, this.banca, 50000, 0);
        Casilla solar7 = new Casilla("Solar7", "Solar", 13, 1400000, this.banca, 100000, 700000);
        Casilla solar8 = new Casilla("Solar8", "Solar", 14, 1600000, this.banca, 120000, 800000);
        Casilla trans2 = new Casilla("Trans2", "Transportes", 15, 500000, this.banca, 250000, 0);
        Casilla solar9 = new Casilla("Solar9", "Solar", 16, 1800000, this.banca, 140000, 900000);
        Casilla caja2 = new Casilla("Caja", "Especial", 17, this.banca);
        Casilla solar10 = new Casilla("Solar10", "Solar", 18, 1800000, this.banca, 140000, 900000);
        Casilla solar11 = new Casilla("Solar11", "Solar", 19, 2200000, this.banca, 160000, 1000000);

        //Ahora metemos as casillas no lado oeste na posición 1 do array de arrays de posicións
        ArrayList<Casilla> ladoOeste = new ArrayList<>();
        ladoOeste.add(solar6);
        ladoOeste.add(serv1);
        ladoOeste.add(solar7);
        ladoOeste.add(solar8);
        ladoOeste.add(trans2);
        ladoOeste.add(solar9);
        ladoOeste.add(caja2);
        ladoOeste.add(solar10);
        ladoOeste.add(solar11);
        this.posiciones.add(ladoOeste);

        //Agora facemos os grupos
        Grupo grupoRosa = new Grupo(solar6, solar7, solar8, Valor.PINK);
        Grupo grupoNaranja = new Grupo(solar9, solar10, solar11, Valor.ORANGE);
        this.grupos.put("Rosa", grupoRosa);
        this.grupos.put("Naranja", grupoNaranja);
    }

    //Método para insertar las casillas del lado norte.
    private void insertarLadoNorte() {
        Casilla parking = new Casilla("Parking", "Especial", 20, this.banca); //O impuesto de Parking vai ser o bote
        Casilla solar12 = new Casilla("Solar12", "Solar", 21, 2200000, this.banca, 180000, 1100000);
        Casilla suerte2 = new Casilla("Suerte", "Especial", 22, this.banca);
        Casilla solar13 = new Casilla("Solar13", "Solar", 23, 2200000, this.banca, 180000, 1100000);
        Casilla solar14 = new Casilla("Solar14", "Solar", 24, 2400000, this.banca, 200000, 120000);
        Casilla trans3 = new Casilla("Trans3", "Transportes", 25, 500000, this.banca, 250000, 0);
        Casilla solar15 = new Casilla("Solar15", "Solar", 26, 2600000, this.banca, 220000, 1300000);
        Casilla solar16 = new Casilla("Solar16", "Solar", 27, 2600000, this.banca, 220000, 1300000);  
        Casilla serv2 = new Casilla("Serv2", "Servicios", 28, 1500000, this.banca, 50000, 0);
        Casilla solar17 = new Casilla("Solar17", "Solar", 29, 2800000, this.banca, 240000, 1400000);
        Casilla irCarcel = new Casilla("IrCarcel", "Especial", 30, this.banca);

        //Ahora metemos as casillas no lado norte na posición 2 do array de arrays de posicións
        ArrayList<Casilla> ladoNorte = new ArrayList<>();
        ladoNorte.add(parking);
        ladoNorte.add(solar12);
        ladoNorte.add(suerte2);
        ladoNorte.add(solar13);
        ladoNorte.add(solar14);
        ladoNorte.add(trans3);
        ladoNorte.add(solar15);
        ladoNorte.add(solar16);
        ladoNorte.add(serv2);
        ladoNorte.add(solar17);
        ladoNorte.add(irCarcel);
        this.posiciones.add(ladoNorte);

        //Agora facemos os grupos
        Grupo grupoRojo = new Grupo(solar12, solar13, solar14, Valor.RED);
        Grupo grupoMorado = new Grupo(solar15, solar16, solar17, Valor.PURPLE);
        this.grupos.put("Rojo", grupoRojo);
        this.grupos.put("Morado", grupoMorado);
    }

    //Metodo que inserta las casillas del lado este.
    private void insertarLadoEste() {
        Casilla solar18 = new Casilla("Solar18", "Solar", 31, 3000000, this.banca, 260000, 1500000);
        Casilla solar19 = new Casilla("Solar19", "Solar", 32, 3000000, this.banca, 260000, 1500000);
        Casilla caja3 = new Casilla("Caja", "Especial", 33, this.banca);
        Casilla solar20 = new Casilla("Solar20", "Solar", 34, 3200000, this.banca, 280000, 160000);
        Casilla trans4 = new Casilla("Trans4", "Transportes", 35, 500000, this.banca, 250000, 0);
        Casilla suerte3 = new Casilla("Suerte", "Especial", 36, this.banca);
        Casilla solar21 = new Casilla("Solar21", "Solar", 37, 3500000, this.banca, 350000, 1750000);
        Casilla impuesto2 = new Casilla("Imp2", "Impuesto", 38, this.banca, 2000000);
        Casilla solar22 = new Casilla("Solar22", "Solar", 39, 4000000, this.banca, 500000, 2000000);

        //Ahora metemos as casillas no lado este na posición 3 do array de arrays de posicións
        ArrayList<Casilla> ladoEste = new ArrayList<>();
        ladoEste.add(solar18);
        ladoEste.add(solar19);
        ladoEste.add(caja3);
        ladoEste.add(solar20);
        ladoEste.add(trans4);
        ladoEste.add(suerte3);
        ladoEste.add(solar21);
        ladoEste.add(impuesto2);
        ladoEste.add(solar22);
        this.posiciones.add(ladoEste);

        //Agora facemos os grupos
        Grupo grupoVerde = new Grupo(solar18, solar19, solar20, Valor.GREEN);
        Grupo grupoAzul = new Grupo(solar21, solar22, Valor.BLUE);
        this.grupos.put("Verde", grupoVerde);
        this.grupos.put("Azul", grupoAzul);

    }
    private StringBuilder imprimirAvatares(StringBuilder sb, String color, ArrayList<Casilla> lado, int indice) {
    sb.append(color).append("|");
    int espacioLibre = 10;
    for (int j = 0; j < lado.get(indice).getAvatares().size(); j++) {
        espacioLibre -= 2;
    }
    for (int j = 0; j < espacioLibre; j++) {
        sb.append("_");
    }
    for (Avatar avatar : lado.get(indice).getAvatares()) {
        sb.append("\u001B[1m").append("&").append(avatar.getId()).append("\u001B[0m");
    }
    sb.append(color).append("|");
    return sb;
}






private void imprimirLineaCasilla(List<Casilla> lado, StringBuilder sb, int indice, int inicio, int fin) {
    String color = lado.get(indice).getColorCasilla();
    Casilla casilla = lado.get(indice);
    String nombre = casilla.getNombre();

    if (inicio == 0) { // primera línea: edificios / espacios
        if ("Solar".equalsIgnoreCase(casilla.getTipo())&& !casilla.getHipotecada()) {
            boolean haiHotel = casilla.getEdificios().get("hotel").getTenEdificio();
            boolean haiPiscina = casilla.getEdificios().get("piscina").getTenEdificio();
            boolean haiDeporte = casilla.getEdificios().get("deporte").getTenEdificio();
            int numCasas = casilla.getEdificios().get("casa").getNumCasas();

            int usados = (haiHotel ? 2 : 0) + (haiPiscina ? 2 : 0) + (haiDeporte ? 2 : 0) + (numCasas * 2);
            int espaciosBlanco = Math.max(0, 10 - usados);

            sb.append(color).append("|");
            if (haiDeporte) sb.append("● ");
            if (haiHotel) sb.append("▲ ");
            if (haiPiscina) sb.append("◆ ");
            for (int k = 0; k < numCasas; k++) sb.append("■ ");
            sb.append(" ".repeat(espaciosBlanco)).append(color).append("|");
        } else if ("Solar".equalsIgnoreCase(casilla.getTipo()) && casilla.getHipotecada()) {
            sb.append(color).append("|").append("-".repeat(10)).append("|");
        } else{
            sb.append(color).append("|").append(" ".repeat(10)).append(color).append("|");
        }
        

    } else if (inicio == 10) { // segunda línea: nombre centrado
        String textoCentrado = centrarTexto(nombre, 10);
        sb.append(color).append("|").append(textoCentrado).append(color).append("|");
    }
}

private String centrarTexto(String texto, int ancho) {
    if (texto.length() >= ancho) {
        return texto.substring(0, ancho);
    }
    
    int espacios = ancho - texto.length();
    int espaciosIzquierda = espacios / 2;
    int espaciosDerecha = espacios - espaciosIzquierda;
    
    return " ".repeat(espaciosIzquierda) + texto + " ".repeat(espaciosDerecha);
}

private void imprimirEspaciosCentrales(StringBuilder sb, int fila) {
    //Quero imprimir aquí un Ascii con un dibujo
    //Entonces, primeiro: cantos espacios hai que imprimri par asaber o ancho central máximo=
    //resposta: 11 casillas * 10 caracteres por casilla - 2 (os dous bordes das casillas)= 108
    //Vaise chamando fila a fila, entonces podemos facer un ascii que según o valor que se lle dea vai imprimido unha liña, como un array ,sbes?

    String[] ascii = new String[50];
    int espaciosBlanco = 108;
    int espaciosAntes = 25;
    int espaciosDespois= espaciosBlanco - espaciosAntes - 62; //64 é o ancho do debuxo
    ascii[0] = " ".repeat(espaciosBlanco);
    ascii[1] = " ".repeat(espaciosBlanco);
    ascii[2] = "⠀".repeat(espaciosAntes) + "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀ ⠀⠀⠀⠀⠀" + " ".repeat(espaciosDespois);
    ascii[3] = "⠀".repeat(espaciosAntes) + "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⣿⣿⡟⠛⠛⠛⠻⢶⣦⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀"+ " ".repeat(espaciosDespois-18)+"______________"+" ".repeat(4);
    ascii[4] = "⠀".repeat(espaciosAntes) + "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⣿⣿⠃⠀⠀⠀⢠⣿⣿⣿⣿⣷⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀"+ " ".repeat(espaciosDespois-19)+"|___𝐋𝐞𝐲𝐞𝐧𝐝𝐚____|"+" ".repeat(3);
    ascii[5] = "⠀".repeat(espaciosAntes) + "⠀⠀⠀⠀⠀⠀ ⠀⠀⠀⢀⣤⠶⡶⢶⡶⣒⣲⠆⢰⣿⣿⣏⣀⣀⡀⢠⣿⣿⣿⣿⣿⡿⢹⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀"+ " ".repeat(espaciosDespois-19)+"| ■ Casas      |"+" ".repeat(3);
    ascii[6] = "⠀".repeat(espaciosAntes) + "⠀⠀⠀⠀⠀⠀⠀⠀⠀⣴⠟⢴⣄⠹⣦⠙⣯⠀⣠⣿⣿⠟⠛⠻⠿⢛⣿⣿⣿⣿⣿⡟⣰⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀"+ " ".repeat(espaciosDespois-19)+"| ▲ Hotel      |"+" ".repeat(3);
    ascii[7] = "⠀".repeat(espaciosAntes) + "⠀⠀⠀⠀⠀⠀⠀⠀⣸⠏⠀⢸⡏⢛⡿⠿⣿⣾⣿⣿⣯⣤⣤⣤⣤⣼⣿⣿⣿⣿⣿⣧⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀"+ " ".repeat(espaciosDespois-19)+"| ◆ Piscina    |"+" ".repeat(3);
    ascii[8] = "⠀".repeat(espaciosAntes) + "⠀⠀⠀⠀⠀⢀⣰⣦⡿⠀⠀⠋⢀⣠⡴⢞⡿⢻⠆⠀⠀⠩⠿⠻⣯⠉⠙⠛⢻⣿⣭⡛⢷⣦⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀"+ " ".repeat(espaciosDespois-19)+"| ● P.Deporte  |"+" ".repeat(3);
    ascii[9] = "⠀".repeat(espaciosAntes) + "⠀⠀⠀⠀⠀⣼⣿⣟⠙⠷⠦⣯⡉⠀⠀⣾⠙⠗⢀⠀⠀⠀⣶⠀⠘⠀⠀⠀⠻⡿⣚⣿⠀⢹⡇⠀⠀⠀⠀⠀⣠⡴⠞⢿⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀ "+ " ".repeat(espaciosDespois-19)+"| - Hipotecada |"+" ".repeat(3);
    ascii[10] = "⠀".repeat(espaciosAntes) + "⠀⠀⠀⠀⠀⣿⣿⣿⣿⣶⣶⣾⡅⠀⢰⡏⠀⣰⣏⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠟⠋⢻⡶⠟⣀⣠⡤⠴⣷⣞⣭⣤⣤⣤⣿⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀ ⠀⠀"+ " ".repeat(espaciosDespois-19)+"|______________|"+" ".repeat(3);
    ascii[11] = "⠀".repeat(espaciosAntes) + "⠀⠀⠀⠀⠐⣿⢹⣿⣿⣿⣿⣿⡇⣶⠾⠷⠟⠉⠉⠉⠙⢷⣄⠀⠀⣠⠀⠀⢀⣀⣤⣾⠷⠛⠉⠀⣾⠟⣽⣿⣤⣄⡀⠀⠈⢹⣇⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀ ⠀ "+ " ".repeat(espaciosDespois);
    ascii[12] = "⠀".repeat(espaciosAntes) + "⠀⠀⠀⠀⠀⣿⡘⣿⣿⣿⣿⣿⣿⣾⢷⣤⣀⣤⣾⣤⣀⡀⣙⣻⣿⠏⠀⢀⣼⣿⠟⢁⠀⠀⠀⠀⠀⢸⡏⣿⣿⣦⣉⣳⣤⣾⣯⡻⣦⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀ ⠀ "+ " ".repeat(espaciosDespois);
    ascii[13] = "⠀".repeat(espaciosAntes) + "⠀⠀⠀⠀⠀⠘⣷⡻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣍⠀⠀⠉⠙⠋⠉⠀⠀⣠⡿⣿⣻⣶⣿⣷⡄⠀⠀⠀⠀⠀⠻⣿⣿⣿⣿⣿⣿⣿⣿⣟⣷⡀⠀⠀⠀⠀⠀⠀⠀ ⠀⠀ "+ " ".repeat(espaciosDespois);
    ascii[14] = "⠀".repeat(espaciosAntes) + "⠀⠀⠀⠀⠀⠀⠘⢷⣝⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣶⣶⣶⣶⣾⣁⣼⠏⢸⡯⢿⣿⣿⣌⡃⠀⠀⠀⠀⣀⢰⡌⣿⣿⣿⣿⣿⣿⣿⣿⣸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀  "+ " ".repeat(espaciosDespois);
    ascii[15] = "⠀".repeat(espaciosAntes) + "⠀⠀⠀⠀⠀⠀⠀⠀⠙⠷⣮⣟⡻⠿⣿⣿⣿⣿⣷⡿⣿⡛⢿⣬⣽⠃⠀⣾⡿⣦⣼⣿⣿⡇⢠⣄⣀⣰⣯⣼⣥⠿⢿⡍⢻⣿⣿⣿⣿⢻⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀  "+ " ".repeat(espaciosDespois);
    ascii[16] = "⠀".repeat(espaciosAntes) + "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠙⠛⠓⡒⠒⣲⣿⣿⣿⣷⡀⠈⢱⠦⠀⠸⣷⡙⠙⠿⠟⠁⢈⡿⣭⣭⣉⠀⠀⠀⠀⢻⡄⢿⣿⣿⢟⡾⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀  "+ " ".repeat(espaciosDespois);
    ascii[17] = "⠀".repeat(espaciosAntes) + "⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣴⠶⠾⠛⠛⠛⠉⠙⠛⠛⠛⠷⠶⣤⣘⢣⣄⣹⣷⣄⡲⠤⡗⣛⣁⣾⣣⡿⠀⠀⠀⠀⢸⡇⢻⣿⣿⠟⠁⣠⣶⣶⣄⡀⠀⠀⠀⠀ ⠀⠀"+ " ".repeat(espaciosDespois);
    ascii[18] = "⠀".repeat(espaciosAntes) + "⠀⠀⠀⠀⠀⠀⠀⢀⣴⠟⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠙⠋⠉⠛⠿⡿⢷⣶⣾⡿⠿⣋⣴⠃⠀⠀⣠⣾⣿⣿⣿⣥⠶⠛⠿⣿⣿⠿⠋⣀⡀⠀⠀⠀  "+ " ".repeat(espaciosDespois);
    ascii[19] = "⠀".repeat(espaciosAntes) + "⠀⠀⠀⠀⠀⠀⣠⡟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣄⠀⠀⠀⠀⠀⠉⠛⠿⠭⠤⢒⡤⠾⠿⠿⠟⠛⠉⠀⠀⠀⠀⠀⠀⠀⢰⣿⣛⡶⣤⣀⠀⠀"+ " ".repeat(espaciosDespois);
    ascii[20] = "⠀".repeat(espaciosAntes) + "⣞⢿⣶⣤⣤⣐⣟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠀⠀⠀⠀⠸⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣾⣝⢧⡀"+ " ".repeat(espaciosDespois);
    ascii[21] = "⠀".repeat(espaciosAntes) + "⠈⠻⣟⢿⣿⣿⣿⡀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⡶⠳⠤⠞⠳⣤⣀⠀⠀⠀⠀⠘⢷⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣴⠟⠉⠉⠙⠛⠿⡿⠇"+ " ".repeat(espaciosDespois);
    ascii[22] = "⠀".repeat(espaciosAntes) + "⠀⠀⠈⠛⠾⢿⣿⠀⠀⠀⠀⠀⠀⢀⣠⡾⠟⠁⠀⠀⠀⠀⠀⠈⠙⠻⠶⠶⣦⠤⠄⠙⠻⣶⣤⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣠⣴⠾⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀  "+ " ".repeat(espaciosDespois);
    ascii[23] = "⠀".repeat(espaciosAntes) + "⠀⠀⠀⠀⠀⠀⠀⣾⣿⣿⣿⣶⡴⠟⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠛⠿⠿⣷⣶⣶⣶⣶⣶⣶⠶⠿⠟⠛⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀  "+ " ".repeat(espaciosDespois);
    ascii[24] = "⠀".repeat(espaciosAntes) + "⠀⠀⠀⠀⠀⠀⠀⠈⠙⠛⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀"+ " ".repeat(espaciosDespois);
    ascii[25] = " ".repeat(espaciosBlanco);
    sb.append(Valor.WHITE).append(ascii[fila]);

}

@Override
public String toString() {
    ArrayList<Casilla> ladoEste = posiciones.get(1);
    ArrayList<Casilla> ladoNorte = posiciones.get(2);
    ArrayList<Casilla> ladoSur = posiciones.get(0);
    ArrayList<Casilla> ladoOeste = posiciones.get(3);

    StringBuilder sb = new StringBuilder();

    // Lado Norte
    for (Casilla casilla : ladoNorte) {
        sb.append(casilla.getColorCasilla()).append(" __________ ");
    }
    sb.append("\n");
    
    // Primera línea: espacios en blanco
    for (int i = 0; i < ladoNorte.size(); i++) {
        imprimirLineaCasilla(ladoNorte, sb, i, 0, 10);
    }
    sb.append("\n");
    
    // Segunda línea: nombres centrados
    for (int i = 0; i < ladoNorte.size(); i++) {
        imprimirLineaCasilla(ladoNorte, sb, i, 10, 20);
    }
    sb.append("\n");
    
    // Tercera línea: avatares
    for (int i = 0; i < ladoNorte.size(); i++) {
        imprimirAvatares(sb, ladoNorte.get(i).getColorCasilla(), ladoNorte, i);
    }
    sb.append("\n");
    int imprimirAscii=0;
    // Lados Oeste y Este
    for (int fila = 0; fila < ladoOeste.size(); fila++) {
        int indiceEste = ladoEste.size() - 1 - fila;
        // Línea 1: espacios en blanco este
        imprimirLineaCasilla(ladoEste, sb, indiceEste, 0, 10);
        imprimirEspaciosCentrales(sb, imprimirAscii);
        imprimirAscii++;
        // Línea 1: espacios en blanco oeste
        imprimirLineaCasilla(ladoOeste, sb, fila, 0, 10);
        sb.append("\n");

        // Línea 2: nombres centrados este
        imprimirLineaCasilla(ladoEste, sb, indiceEste, 10, 20);
        imprimirEspaciosCentrales(sb, imprimirAscii);
        imprimirAscii++;
        // Línea 2: nombres centrados oeste
        imprimirLineaCasilla(ladoOeste, sb, fila, 10, 20);
        sb.append("\n");

        // Línea 3: avatares este
        imprimirAvatares(sb, ladoEste.get(indiceEste).getColorCasilla(), ladoEste, indiceEste);

        if (fila == ladoOeste.size() - 1) {
            sb.append(" ");
            for (int j = ladoSur.size() - 2; j > 0; j--) {
                sb.append(ladoSur.get(j).getColorCasilla()).append("__________ ");
                if(j != 1) sb.append(" ");
            }
        } else {
            imprimirEspaciosCentrales(sb, imprimirAscii);
            imprimirAscii++;
        }

        // Línea 3: avatares oeste
        imprimirAvatares(sb, ladoOeste.get(fila).getColorCasilla(), ladoOeste, fila);
        sb.append("\n");
    }

    // Lado Sur
    // Primera línea: espacios en blanco
    for (int i = ladoSur.size() - 1; i >= 0; i--) {
        imprimirLineaCasilla(ladoSur, sb, i, 0, 10);
    }
    sb.append("\n");
    
    // Segunda línea: nombres centrados
    for (int i = ladoSur.size() - 1; i >= 0; i--) {
        imprimirLineaCasilla(ladoSur, sb, i, 10, 20);
    }
    sb.append("\n");
    
    // Tercera línea: avatares
    for (int i = ladoSur.size() - 1; i >= 0; i--) {
        imprimirAvatares(sb, ladoSur.get(i).getColorCasilla(), ladoSur, i);
    }
    sb.append("\n");

    return sb.toString();
}
    //Metodo usado para buscar la casilla con el nombre pasado como argumento:
    public Casilla encontrar_casilla(String nombre){
        for (ArrayList<Casilla> lado : this.posiciones) {
            for (Casilla casilla : lado) {
                if (casilla.getNombre().equalsIgnoreCase(nombre)) {
                    return casilla;
                }
            }
        }
        return null;
    }
    //Getters y setters:
    public ArrayList<ArrayList<Casilla>> getPosiciones() {
        return posiciones;
    }
    public HashMap<String, Grupo> getGrupos() {
        return grupos;
    }
    public Jugador getBanca() {
        return banca;
    }

}
