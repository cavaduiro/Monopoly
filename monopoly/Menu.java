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
    private int lanzamientos=0; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; //Tablero en el que se juega.
    private Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.
    private Jugador banca;
    private boolean partidaIniciada=false; //Booleano para comprobar si la partida ha comenzado (mínimo 2 jugadores creados).

    public Menu(){

        Scanner scanner = new Scanner(System.in);
        String comando;
        this.tablero = new Tablero(this.banca);
        this.jugadores = new ArrayList<Jugador>();
        this.avatares = new ArrayList<Avatar>();
        this.dado1 = new Dado();
        this.dado2 = new Dado();
        System.out.println(tablero);
        while(true){
            System.out.println(tablero);
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
                describir(cmdseparado);
                break;
            case "jugador":
                xogadorTurno();
                break;
            case "listar":
                listar(cmdseparado);
                break;
            case "lanzar":
                if(cmdseparado[1].equalsIgnoreCase("dados")){
                    lanzarDados(cmdseparado);

                }else{
                    System.out.println("\nComando introducido erróneo.\n");
                }
                break;
            case "salir":
                if(cmdseparado[1].equalsIgnoreCase("cárcel")){
                    salirCarcelPagando();
                    break;
                }
                    salirCarcel();
                break;
            case "acabar":
                acabarTurno();
                break;
            case "comprar":
                comprar(cmdseparado[1]);
                break;
            default:
                System.out.println("\nComando introducido erróneo.\n");
                break;

        }
    }

    private void listar(String[] partes){
        if(partes[1].equalsIgnoreCase("jugadores")){
            listarJugadores();
        }else if(partes[1].equalsIgnoreCase("enventa")){
            listarVenta();
        }else{
            System.out.println("\nComando introducido erróneo.\n");
        }
    }

    private void describir(String[] partes){
        if(partes.length < 2 || partes.length > 3){
            System.out.println("\nNúmero de parámetros incorrecto.\n");
            System.out.println("Uso: describir jugador nombreJugador\nUso: describir nombreCasilla\n");
            return;
        }
        if(partes[1].equalsIgnoreCase("jugador")){
            descJugador(partes);
        }else{
            descCasilla(partes);
        }
    }

    private void crearxogador(String[] partes) {
        if(partidaIniciada){
            System.out.println("La partida ya ha comenzado. No se pueden añadir más jugadores.\n");
            return;
        }
        if(partes.length !=4){
            System.out.println("\nNúmero de parámetros incorrecto.\n");
            System.out.println("Uso: crear jugador nombre tipo_avatar\n");
            return;
        }
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
            Jugador nuevo= new Jugador(partes[2],partes[3],auxsal,avatares);

            jugadores.add(nuevo);
            auxsal.anhadirAvatar(nuevo.getAvatar());
        }

    }
    /*Metodo que realiza las acciones asociadas al comando 'describir jugador'.
    * Parámetro: comando introducido
     */
    private void descJugador(String[] partes) {

        if(jugadores.size()==0){
            System.out.println("\nNo hay jugadores creados todavía.\n");
            return;
        }
        System.out.println("Nombre del jugador a describir: "+partes[2] );
        boolean encontrado = false;
        for(Jugador aux:jugadores){
            if(aux.getNombre().equals(partes[2])){
                System.out.println(aux);
                encontrado = true;
                break;
            }
        }
        if(!encontrado){
            System.out.println("\nNo existe ningún jugador con el nombre " + partes[2] + "\n");
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
        Casilla aux =tablero.encontrar_casilla(nombre[1]);
        
        if(aux != null){
            System.out.println(aux);
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
    private void lanzarDados(String[] partes) {
        if(partidaIniciada==false && jugadores.size()>=2){
            partidaIniciada=true;
            System.out.println("La partida ha comenzado\n");
        }
        else if(partidaIniciada==false && jugadores.size()<2){
            System.out.println("La partida no ha comenzado. Deben crearse al menos dos jugadores.");
            return;
        }

        boolean forzar=false;
        if(partes.length == 3) {
            forzar = true;
        }
        Jugador jugadorActual= jugadores.get(turno);
        System.out.println(jugadorActual);
        if(tirado){
            System.out.println("Ya has tirado los dados en este turno.");
            return;
        }
        int valor1;
        int valor2;
        if (forzar) {
            String[] dadoforzado = partes[2].split("\\+");
            valor1 = Integer.parseInt(dadoforzado[0]);
            valor2 = Integer.parseInt(dadoforzado[1]);
        }else {
            valor1 = dado1.hacerTirada();
            valor2 = dado2.hacerTirada();
        }
        if(jugadorActual.getEnCarcel()){
            if (valor1==valor2){ //Para salir da cárcel por turnos, espérase a que acabe o turno
                jugadorActual.setEnCarcel(false);
                lanzamientos=0;
                tirado=false;
                System.out.println("\nHas sacado un "+valor1+" y un "+valor2+". Has salido de la cárcel.");
                return;
            }else{
                jugadorActual.sumartiradascarcel();
            }
        }

        if(lanzamientos==2 && valor1==valor2){
            System.out.println("Has sacado tres dobles seguidos. Vas a la cárcel.\n");
            jugadorActual.encarcelar(tablero.getPosiciones());
            lanzamientos=0;
            tirado=true;
            return;
        }
        lanzamientos++;
        System.out.println("Has sacado un "+valor1+" y un "+valor2+".\n");
        solvente = jugadorActual.getAvatar().getLugar().evaluarCasilla(jugadorActual,banca,valor1+valor2);

        tirado=true;
        if(valor1==valor2){
            System.out.println("Has sacado dados dobles.");
            tirado=false;
            return;
        }


    }
    void salirCarcelPagando(){
        Jugador actual=jugadores.get(turno);
        if(!actual.getEnCarcel()){
            System.out.println("El jugador no está en la cárcel");
            return;
        }
        if(actual.getFortuna()<500000){
            System.out.println("El jugador no tiene dinero para salir de la cárcel");
            return;
        }
        if(tirado){
            System.out.println("Ya has tirado los dados");
            return;
        }
        else{
            actual.sumarFortuna(-500000);
            actual.setEnCarcel(false);
            System.out.println(actual.getNombre()+"ha pagado 500000 y ha salido de la carcel,puede tirar los dados");
            return;
        }
    }
    /*Metodo que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {
        Casilla casillaComprar = tablero.encontrar_casilla(nombre);
        if(casillaComprar == null){
            System.out.println("No existe ninguna casilla con el nombre."+ nombre);
            return;
        }
        if(casillaComprar.getTipo()!="Solar" && casillaComprar.getTipo()!="Transporte" && casillaComprar.getTipo()!="Servicio"){
            System.out.println("Esa casilla no es comprable.\n Solo puedes comprar casillas de tipo Solar, Transporte o Servicio.");
            return;
        }
        if(casillaComprar.getDuenho() != banca){
            System.out.println("Esa casilla ya tiene dueño, es de "+casillaComprar.getDuenho().getNombre()+".");
            return;
        }
        
        Jugador jugadorActual= jugadores.get(turno);
        if(jugadorActual.getAvatar().getLugar() != casillaComprar){
            System.out.println("No estás en esa casilla que quieres comprar.");
            return;
        }
        if(jugadorActual.getFortuna() < casillaComprar.getValor()){
            System.out.println("No tienes suficiente fortuna para comprar esa casilla.\nLa casilla cuesta "+casillaComprar.getValor()+" y tú tienes ahorrado"+jugadorActual.getFortuna()+".");
            return;
        }
        casillaComprar.comprarCasilla(jugadorActual, banca);

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
        //Comprobar que a partida está empezada
        if(!tirado){
            System.out.println("Aún tienes que tirar los dados.");
            return;
        }
        Jugador jugadorActual= jugadores.get(turno);
        if(jugadorActual.getEnCarcel() && jugadorActual.getTiradasCarcel()==3){
            jugadorActual.setEnCarcel(false);
            System.out.println("Has salido de la cárcel automáticamente tras tres turnos.");
        }
        if(!solvente){
            System.out.println("No has pagado todas tus deudas.");
            //Debería haber algo tipo, pagar de novo ou algo? tipo, aquí outr avez chamar a solvente?
            solvente = jugadorActual.getAvatar().getLugar().evaluarCasilla(jugadorActual,banca,0);
            //Pero, oof, así non poderías pasar a tirada
            //Dádelle unha volta

            return;
        }
        tirado=false;
        lanzamientos=0;
        turno++;
        if(turno==jugadores.size()){
            turno=0;
        }
        System.out.println("Turno acabado. Ahora es el turno de "+jugadores.get(turno).getNombre()+".");
    }

}
