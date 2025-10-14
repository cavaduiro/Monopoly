package monopoly;

import java.util.ArrayList;
import java.util.Scanner;

import org.w3c.dom.ls.LSOutput;
import partida.*;

public class Menu {

    //Atributos
    private ArrayList<Jugador> jugadores; //Jugadores de la partida
    private ArrayList<Avatar> avatares; //Avatares en la partida.
    private int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; //Tablero en el que se juega.
    private Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private Jugador banca; //El jugador banca.
    private boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.

    public Menu(){
        Scanner scanner = new Scanner(System.in);
        String comando;
        this.tablero = new Tablero(this.banca);
        this.jugadores = new ArrayList<Jugador>();
        this.avatares = new ArrayList<Avatar>();
        System.out.println(tablero);
        while(true){
            System.out.println("\n$: ");
            comando = scanner.nextLine();
            analizarComando(comando);

        }

        //Faltan mill cousas, so estou poñend o imprescindible para imprimir o tablero

    }

    // Metodo para inciar una partida: crea los jugadores y avatares.
    private void iniciarPartida() {
    }
    
    /*Metodo que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetro: cadena de caracteres (el comando).
    */
    private void analizarComando(String comando) {
        String[] cmdseparado = comando.split(" "); //separa el comando por espacios y lo mete en array
        switch (cmdseparado[0]){
            case "crear":
                if(cmdseparado[1].equalsIgnoreCase("jugador")){
                    crearxogador(cmdseparado);
                }else{
                    System.out.println("\nComando introducido erróneo.\n");
                }break;
            case "describir":
                if(cmdseparado[1].equalsIgnoreCase("jugador")){
                    descJugador(cmdseparado);
                }else if(cmdseparado[1].equalsIgnoreCase("casilla")){
                    descCasilla(cmdseparado);
                }else{
                    System.out.println("\nComando introducido erróneo.\n");}
                break;
            case "jugador":
                xogadorTurno();
                break;
            case "lanzar":
                if(cmdseparado[1].equalsIgnoreCase("dados")){

                }
            default:
                System.out.println("\nComando introducido erróneo.\n");
                break;

        }
    }
    private void crearxogador(String[] partes) {
        if(jugadores.size()==4){
            System.out.println("Número máximo de jugadores alcanzado.");
            return;
        }
        if(!partes[3].equalsIgnoreCase("coche")&&!partes[3].equalsIgnoreCase("sombrero")&&!partes[3].equalsIgnoreCase("esfinge")&&!partes[3].equalsIgnoreCase("pelota"))    {
            System.out.println("Tipo de avatar no válido.");
                return;
        }

        else{
            for(Jugador aux:jugadores){

                if(aux.getNombre().equals(partes[2])){
                    System.out.println("Ya existe un jugador con ese nombre.");
                    return;
                }
                else if(aux.getAvatar().getTipo().equalsIgnoreCase(partes[3])){
                    System.out.println("Ya existe un jugador con ese avatar.");
                    return;
                }
            }
            Casilla auxsal = tablero.encontrar_casilla("Salida");
            jugadores.add(new Jugador(partes[2], partes[3],auxsal,avatares));
        }

    }
    /*Metodo que realiza las acciones asociadas al comando 'describir jugador'.
    * Parámetro: comando introducido
     */
    private void descJugador(String[] partes) {
        for(Jugador aux:jugadores){
            if(aux.getNombre().equals(partes[3])){
                System.out.println(aux);
            }
        }
    }

    /*Metodo que realiza las acciones asociadas al comando 'describir avatar'.
    * Parámetro: id del avatar a describir.
    */
    private void descAvatar(String ID) {
        for(Avatar aux:avatares){
            if(aux.getId().equals(ID)){
                System.out.println(aux.getId());
            }
        }
    }

    /* Metodo que realiza las acciones asociadas al comando 'describir nombre_casilla'.
    * Parámetros: nombre de la casilla a describir.
    */
    private void descCasilla(String[] nombre) {
        Casilla aux =tablero.encontrar_casilla(nombre[2]);
        if(aux != null){
            System.out.println(aux.infoCasilla());
        }else{
            System.out.println("\nNon existe casilla con ese nome.\n");
        }

    }
    //Metodo que indica o xogador que ten o turno actual

    private void xogadorTurno(){
        Jugador jugadorActual = jugadores.get(turno);
        System.out.println("\n{\nNombre: "+ jugadorActual.getNombre()+"\nAvatar: "+jugadorActual.getAvatar()+"\n}\n");
    }


    //Metodo que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    private void lanzarDados() {
        Jugador jugadorActual= jugadores.get(turno);
        if(tirado){
            System.out.println("Ya has tirado los dados en este turno.");
            return;
        }
         int valor1=dado1.hacerTirada();
         int valor2=dado2.hacerTirada();
        if(jugadorActual.getEnCarcel()){
            //Caso carcel
        }
        lanzamientos++;
        if(lanzamientos==3){
            System.out.println("Has sacado tres dobles seguidos. Vas a la cárcel.");
            jugadorActual.encarcelar(tablero.getPosiciones());
            lanzamientos=0;
            tirado=true;
            return;
        }
        jugadorActual.getAvatar().moverAvatar(tablero.getPosiciones(),valor1+valor2);
        System.out.println("Has sacado un "+valor1+" y un "+valor2+".");
        tirado=true;
        if(valor1==valor2){
            tirado=false;
            return;
        }


    }

    /*Metodo que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {

    }

    //Metodo que ejecuta todas las acciones relacionadas con el comando 'salir carcel'.
    private void salirCarcel() {
        Jugador jugadorActual= jugadores.get(turno);
        if(!jugadorActual.getEnCarcel()){
            System.out.println("No estás en la cárcel.");
            return;
        }
        else{
            jugadorActual.setEnCarcel(false);
            System.out.println("Has salido de la cárcel.");
        }
    }

    // Metodo que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta() {
    }

    // Metodo que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
        for(Jugador aux:jugadores){
            System.out.println(aux);
        }
    }

    // Metodo que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {

    }

    // Metodo que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
    }

}
