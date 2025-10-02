package monopoly;

import partida.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


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
    }

    
    //Método para crear todas las casillas del tablero. Formado a su vez por cuatro métodos (1/lado).
    private void generarCasillas() {
        this.insertarLadoSur();
        this.insertarLadoOeste();
        this.insertarLadoNorte();
        this.insertarLadoEste();
    }
    
    //Método para insertar las casillas del lado sur.
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

    //Método que inserta casillas del lado oeste.
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
        Grupo grupoRosa = new Grupo(solar6, solar7, solar8, Valor.ORANGE);
        Grupo grupoNaranja = new Grupo(solar9, solar10, solar11, Valor.ORANGE);
        this.grupos.put("Rosa", grupoRosa);
        this.grupos.put("Naranja", grupoNaranja);
    }

    //Método para insertar las casillas del lado norte.
    private void insertarLadoNorte() {
        Casilla parking = new Casilla("Parking", "Especial", 20, this.banca, 0); //O impuesto de Parking vai ser o bote
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

    //Método que inserta las casillas del lado este.
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

    private String formatoCasilla(Casilla c, int linea) {
        String nombre = c.getNombre();
        String color;
        if(!c.getTipo().equals("Solar")) {
            color = Valor.WHITE; //Color por defecto para casillas que non son solares
        } else {
            color = c.getGrupo().getColorGrupo();
        }
        String reset = "\u001B[0m";

        switch (linea) {
            case 0: return color + "________" + reset;
            case 1: return color + "|      |" + reset;
            case 2: 
                String nombreAjustado = String.format("|%-6.6s|", nombre); // corta ou axusta
                return color + nombreAjustado + reset;
            case 3: return color + "|      |" + reset;
            default: return "";
        }
    }

    //Para imprimir el tablero, modificamos el método toString().
    @Override

public String toString() {
    StringBuilder builder = new StringBuilder();

    ArrayList<Casilla> sur = posiciones.get(0);
    ArrayList<Casilla> oeste = posiciones.get(1);
    ArrayList<Casilla> norte = posiciones.get(2);
    ArrayList<Casilla> este = posiciones.get(3);

    // Invertimos norte e este para manter orientación visual correcta
    ArrayList<Casilla> norteRev = new ArrayList<>(norte);
    ArrayList<Casilla> esteRev = new ArrayList<>(este);
    Collections.reverse(norteRev);
    Collections.reverse(esteRev);

    // Parte superior (Norte)
    for (int linea = 0; linea < 4; linea++) {
        for (Casilla c : norteRev) {
            builder.append(formatoCasilla(c, linea));
        }
        builder.append("\n");
    }

    // Laterais (Oeste e Este)
    int alto = oeste.size(); // ou esteRev.size(), deben coincidir
    for (int i = 0; i < alto; i++) {
        Casilla casillaOeste = oeste.get(i);
        Casilla casillaEste = esteRev.get(i);

        for (int linea = 0; linea < 4; linea++) {
            builder.append(formatoCasilla(casillaOeste, linea));
            for (int j = 0; j < norte.size() - 2; j++) {
                builder.append("        "); // espazo intermedio
            }
            builder.append(formatoCasilla(casillaEste, linea));
            builder.append("\n");
        }
    }

    // Parte inferior (Sur)
    for (int linea = 0; linea < 4; linea++) {
        for (Casilla c : sur) {
            builder.append(formatoCasilla(c, linea));
        }
        builder.append("\n");
    }

    return builder.toString();
}

    
    //Método usado para buscar la casilla con el nombre pasado como argumento:  
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
}
