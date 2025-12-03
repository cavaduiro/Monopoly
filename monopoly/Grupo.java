package monopoly;

import partida.*;
import java.util.ArrayList;


class Grupo {

    //Atributos
    private ArrayList<Casillavella> miembros; //Casillas miembros del grupo.
    private String colorGrupo; //Color del grupofewfe
    private int numCasillas; //Número de casillas del grupo.

    //Constructor vacío.
    public Grupo() {
    }

    /*Constructor para cuando el grupo está formado por DOS CASILLAS:
    * Requiere como parámetros las dos casillas miembro y el color del grupo.
     */
    public Grupo(Casillavella cas1, Casillavella cas2, String colorGrupo) {
        this.miembros = new ArrayList<Casillavella>();
        this.miembros.add(cas1);
        cas1.setGrupo(this);
        this.miembros.add(cas2);
        cas2.setGrupo(this);
        this.colorGrupo = colorGrupo;
        this.numCasillas = 2;
    }

    /*Constructor para cuando el grupo está formado por TRES CASILLAS:
    * Requiere como parámetros las tres casillas miembro y el color del grupo.
     */
    public Grupo(Casillavella cas1, Casillavella cas2, Casillavella cas3, String colorGrupo) {
        this.miembros = new ArrayList<Casillavella>();
        this.miembros.add(cas1);
        cas1.setGrupo(this);
        this.miembros.add(cas2);
        cas2.setGrupo(this);
        this.miembros.add(cas3);
        cas3.setGrupo(this);
        this.colorGrupo = colorGrupo;
        this.numCasillas = 3;
    }

    public String getColorGrupo() {
        return colorGrupo;
    }
    /* Método que añade una casilla al array de casillas miembro de un grupo.
    * Parámetro: casilla que se quiere añadir.
     */
    public void anhadirCasilla(Casillavella miembro) {
        this.miembros.add(miembro);
        this.numCasillas++;
    }

    /*Metodo que comprueba si el jugador pasado tiene en su haber todas las casillas del grupo:
    * Parámetro: jugador que se quiere evaluar.
    * Valor devuelto: true si es dueño de todas las casillas del grupo, false en otro caso.
     */
    public boolean esDuenhoGrupo(Jugador jugador) {
        for (Casillavella casilla : miembros) { //Por cada casilla del grupo
            if (casilla.getDuenho() != jugador) {

                return false;
            }
        }

        return true;
        
    }

    public ArrayList<Casillavella> getMiembros() {
        return miembros;
    }
}
