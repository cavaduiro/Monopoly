package monopoly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
    private boolean solvente=true; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.
    private Jugador banca; //Jugador que representa a la banca.
    private boolean partidaIniciada=false; //Booleano para comprobar si la partida ha comenzado (mínimo 2 jugadores creados).
    private boolean partidaFinalizada = false;

    public Menu(){

        Scanner scanner = new Scanner(System.in);
        String comando;
        this.banca = new Jugador();
        this.tablero = new Tablero(this.banca);
        this.jugadores = new ArrayList<Jugador>();
        this.avatares = new ArrayList<Avatar>();
        this.dado1 = new Dado();
        this.dado2 = new Dado();
        while(!partidaFinalizada){
            System.out.println(tablero);
            System.out.println("\n$: ");
            comando = scanner.nextLine();
            analizarComando(comando);

        }
        System.out.println("\n/---------------/\n");
        System.out.println("Créditos");

    }

    public Tablero getTablero() {
        return tablero;
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
                if(cmdseparado.length<2){ //En todos estos comandos tes que comprobar que hay suficientes argumentos, senón intentas acceder a posicións de memoria erróneas
                    System.out.println("\nNúmero de argumentos erróneo.\n");
                    break;
                }
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
            case "ver":
                break;
            case "listar":
                listar(cmdseparado);
                break;
            case "empezar":
                iniciarPartida();
                break;
            case "lanzar":
                if(cmdseparado.length<2){
                    System.out.println("\nNúmero de argumentos erróneo.\n");
                    break;
                }
                if(cmdseparado[1].equalsIgnoreCase("dados")){
                    lanzarDados(cmdseparado);

                }else{
                    System.out.println("\nComando introducido erróneo.\n");
                }
                break;
            case "salir":
                if(cmdseparado.length<2){
                    System.out.println("\nNúmero de argumentos erróneo.\n");
                    break;
                }
                if(cmdseparado[1].equalsIgnoreCase("cárcel") || cmdseparado[1].equalsIgnoreCase("carcel")){
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
                if(cmdseparado.length<2){
                    System.out.println("\nNúmero de argumentos erróneo.\n");
                    break;
                }
                break;
            case "comandos":
                if(cmdseparado.length < 2){
                    System.out.println("\nNúmero de argumentos erróneo.\n");
                    break;
                }
                leerArquivo(cmdseparado[1]);
                break;
            case "estadisticas":
                if(!partidaIniciada){
                    System.out.println("\nA partida aínda non comezou, non hai estatísticas que mostrar.\n");
                    break;
                }
                if (cmdseparado.length < 2){
                    estadisticasPartida();
                    break;
                }else if(cmdseparado.length>3)
                {
                    System.out.println("\nNumero de comandos erróneo...");
                    break;
                }
                estadisticasXogador(cmdseparado);
                break;
            case "edificar":
                if(!partidaIniciada){
                    System.out.println("\nA partida aínda non comezou, non se poden edificar edificios.\n");
                    break;
                }
                if(cmdseparado.length<2){
                    System.out.println("\nNúmero de argumentos erróneo.\n");
                    break;
                }
                edificar(cmdseparado);
                break;
            case "vender":
                if(!partidaIniciada){
                    System.out.println("\nA partida aínda non comezou, non se poden vender edificios.\n");
                    break;
                }
                if(cmdseparado.length<4){
                    System.out.println("\nNúmero de argumentos erróneo.\n");
                    break;
                }
                vender(cmdseparado);
                break;
            default:
                System.out.println("\nComando introducido erróneo.\n");
                break;

        }
    }

    private void listar(String[] partes){
        if(partes.length<2){
            System.out.println("\nNon inseriu o número de comandos suficientes...\n");
        }
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
            System.out.println("A partida xa comezou. Non se poden engadir máis xogadores.\n");
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
            System.out.println("Tipo de avatar non válido.");
                return;
        }

        else{
            for(Jugador aux:jugadores){

                if(aux.getNombre().equals(partes[2])){
                    System.out.println("Xa existe un xogador con ese nome.");
                    return;
                }
                else if(aux.getAvatar().getTipo().equalsIgnoreCase(partes[3])){
                    System.out.println("Xa existe un xogador con ese avatar.");
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
            System.out.println("\nNon hai xogadores aínda.\n");
            return;
        }
        System.out.println("Nome do xogador a describir: "+partes[2] );
        boolean encontrado = false;
        for(Jugador aux:jugadores){
            if(aux.getNombre().equals(partes[2])){
                System.out.println(aux);
                encontrado = true;
                break;
            }
        }
        if(!encontrado){
            System.out.println("\nNon existe un xogador con ese nome " + partes[2] + "\n");
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
        System.out.println("\n{\nNome: "+ jugadorActual.getNombre()+"\nAvatar: "+jugadorActual.getAvatar().getId()+"\n}\n");
    }


    //Metodo que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    private void lanzarDados(String[] partes) {
        if(partidaIniciada==false && jugadores.size()>=2){
            partidaIniciada=true;
            System.out.println("A partida comezou\n");
        }
        else if(partidaIniciada==false && jugadores.size()<2){
            System.out.println("A partida aínda non comezou, necesítanse dous xogadores.");
            return;
        }

        boolean forzar=false;
        if(partes.length == 3) {
            forzar = true;
        }
        Jugador jugadorActual= jugadores.get(turno);
        System.out.println(jugadorActual);
        if(tirado){
            System.out.println("Xa tirou os dados este turno.");
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
                System.out.println("\nSacaches un "+valor1+" e un "+valor2+". Saíches do cárcere.");
                return;
            }else{
                jugadorActual.sumartiradascarcel();
                System.out.println("\nSegues na cárcere. Levas "+jugadorActual.getTiradasCarcel()+" turnos.\n");
                tirado =true;
                return;
            }
        }

        if(lanzamientos==2 && valor1==valor2){
            System.out.println("Sacaches tres dobles seguidos, vas ao cárcere.\n");
            jugadorActual.encarcelar(tablero.getPosiciones());
            lanzamientos=0;
            tirado=true;
            return;
        }
        lanzamientos++;
        System.out.println("Sacaches un "+valor1+" e un "+valor2+".\n");
        jugadorActual.getAvatar().moverAvatar(tablero.getPosiciones(),valor1+valor2);



        solvente = jugadorActual.getAvatar().getLugar().evaluarCasilla(jugadorActual,banca,valor1+valor2, getTablero().getPosiciones());

        tirado=true;
        if(valor1==valor2&&!jugadorActual.getEnCarcel()){
            System.out.println("Sacaches dobles");
            tirado=false;
            return;
        }


    }
    void salirCarcelPagando(){
        Jugador actual=jugadores.get(turno);
        if(!actual.getEnCarcel()){
            System.out.println("O xogador non está no cárcere");
            return;
        }
        if(actual.getFortuna()<500000){
            System.out.println("O xogador non ten cartos para saír do cárcere...");
            return;
        }
        if(tirado){
            System.out.println("Xa tiraches os dados");
            return;
        }
        else{
            actual.sumarFortuna(-500000);
            actual.getEstatisticas().acImpPagado(500000);
            actual.setEnCarcel(false);
            System.out.println(actual.getNombre()+" pagaches a cuota (500000) e saíches do cárcere, podes tirar os dados.");
            return;
        }
    }
    /*Metodo que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {
        if(!solvente){
            System.out.println("\nO xogador actual non é solvente, non pode facer compras...");
            return;
        }
        Casilla casillaComprar = tablero.encontrar_casilla(nombre);
        if(casillaComprar == null){
            System.out.println("Non existe ningunha casilla co nome."+ nombre);
            return;
        }
        if(!"Solar".equals(casillaComprar.getTipo()) && !"Transportes".equals(casillaComprar.getTipo()) && casillaComprar.getTipo()!="Servicios"){
            System.out.println("Esa casilla non é mercable.\n So podes comprar casillas de tipo Solar, Transporte o Servicio.");
            return;
        }
        if(casillaComprar.getDuenho() != banca){
            System.out.println("Esa casilla xa ten dono, é de "+casillaComprar.getDuenho().getNombre()+".");
            return;
        }
        
        Jugador jugadorActual= jugadores.get(turno);
        if(jugadorActual.getAvatar().getLugar() != casillaComprar){
            System.out.println("Non estás nesa casilla que queres comprar.");
            return;
        }
        if(jugadorActual.getFortuna() < casillaComprar.getValor()){
            System.out.println("Non tes suficiente fortuna para comprar a casilla.\nA casilla costa "+casillaComprar.getValor()+" e ti tes aforrado"+jugadorActual.getFortuna()+".");
            return;
        }
        casillaComprar.comprarCasilla(jugadorActual, banca);

    }

    private void edificar(String[] partes){
        Jugador jugadorActual= jugadores.get(turno);
        if(!partes[1].equals("casa")&&!partes[1].equals("hotel")&&!partes[1].equals("piscina")&&!partes[1].equals("deporte")){
            System.out.println("Tipo de edificio non válido. Podes construir casa, hotel, piscina ou deporte.");
            return;
        }

        Casilla casillaEdificar = jugadorActual.getAvatar().getLugar();

        if(casillaEdificar.getDuenho() != jugadorActual){
            System.out.println("Non estás na túa propiedade.");
            return;
        }
        
        if(!casillaEdificar.getGrupo().esDuenhoGrupo(jugadorActual)){
            System.out.println("Necesitas comprar todas as casillas do grupo para poder edificar nesta casilla");
            return;
        }

        casillaEdificar.edificar(partes[1], jugadorActual);
    }

    private void vender(String[] partes){
        Jugador jugadorActual= jugadores.get(turno);
        if(!partes[1].equals("casa")&&!partes[1].equals("hotel")&&!partes[1].equals("piscina")&&!partes[1].equals("deporte")){
            System.out.println("Tipo de edificio non válido. Podes vender casa, hotel, piscina ou deporte.");
            return;
        }
        Casilla casilla = tablero.encontrar_casilla(partes[2]);        
        if(casilla==null){
            System.out.println("Non existe ningunha casilla co nome."+ partes[2]);
            return;
        }
        if(casilla.getDuenho() != jugadorActual){
            System.out.println("A casilla "+casilla.getNombre()+"non é da túa propiedade.");
            return;
        }
        if(!casilla.getGrupo().esDuenhoGrupo(jugadorActual)){
            System.out.println("Non eres dueño de todas as casillas do grupo, por tanto non hai edificios que vender nelas");
            return;
        }
        //Como paso partes[3] a tipo int??
    
        casilla.vender(partes[1], Integer.parseInt(partes[3]), jugadorActual);
    }
    
    //Metodo que ejecuta todas las acciones relacionadas con el comando 'salir carcel'.
    private void salirCarcel() {
        if(jugadores.size()==0){
            System.out.println("Non hai xogadores creados todavía.");
            return;
        }
        Jugador jugadorActual= jugadores.get(turno);
        if(!jugadorActual.getEnCarcel()){
            System.out.println("Non estás no cárcere.");
            return;
        }
        else{
            jugadorActual.setEnCarcel(false);

            System.out.println("Saíches do cárcere.");
        }
    }

    // Metodo que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta() {
        ArrayList<ArrayList<Casilla>> pos = tablero.getPosiciones();
        for (ArrayList<Casilla> lado : pos) {
            for (Casilla casilla : lado) {
                if (casilla.getDuenho() == banca && ("Solar".equals(casilla.getTipo()) || "Transportes".equals(casilla.getTipo()) || "Servicios".equals(casilla.getTipo()))) {
                    System.out.println(casilla);
                }
            }
        }
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
            System.out.println("Aínda tes que tirar os dados.");
            return;
        }
        Jugador jugadorActual= jugadores.get(turno);
        if(jugadorActual.getEnCarcel() && jugadorActual.getTiradasCarcel()==3){
            if(jugadorActual.getFortuna()<500000){
                System.out.println("Non tes cartos para saír do cárcere (500000€) ...");
                solvente=false;
                //return; En futuras entregas onde podas recaudar cartos vendendo e hipotecando propiedades, non se pode facer return aquí, porque podes conseguir cartos e pagar a multa
            }else{
                solvente=true;
                jugadorActual.sumarFortuna(-500000);
                jugadorActual.setEnCarcel(false);
                jugadorActual.setTiradasCarcel(0);
                System.out.println("Saíches automaticamente do cárcere tras tres turnos.\nPagaches 500000€ como multa dude.");
            }

        }
        if(!solvente){
            Jugador Recaudador = jugadorActual.getAvatar().getLugar().getDuenho();
            System.out.println("\nNon pagaches as débedas.");
            bancarrota(jugadorActual, Recaudador);
            //solvente = jugadorActual.getAvatar().getLugar().evaluarCasilla(jugadorActual,banca,0); <-non ten sentido facer aquí solvente, xa se fai cando tiras os dados.
            //^^, senón tiras os dados, non cambia o teu estado do xogo. Con saber en que casilla estás xa sabes a quen lle debes os cartos
            //^^ non lle vas pagar a ningúen máis que ao xogador da casilla que che fixo perder os cartos
            System.out.println("\nTurno acabado. O xogador "+ jugadorActual.getNombre()+ " foi eliminado. \n");
            jugadores.remove(turno);
            if(jugadores.size()==1){
                turno =0;
                partidaFinalizada = finalizarPartida();
                return;
                //Impide inmediatamente que se poida realizar calquera outra acción.

            }
        //Esto entendo que pode ser exactamente igual, non hai por que facer return realmente (mentres queden xogadores)
        }
        tirado=false;
        lanzamientos=0;
        turno++;
        if(turno==jugadores.size()){
            turno=0;
        }

        if(!partidaFinalizada)
        {
            System.out.println("Turno acabado. Agora é o turno de "+jugadores.get(turno).getNombre()+".");
        }
    }

    private void bancarrota(Jugador endeudado, Jugador Recaudador){
    //Quitaránse todas as propidedades do xogar endeudado e daranse ao outro xogador/banca
        if(Recaudador == this.banca){
            System.out.println("\nTodas as propiedades do xogador "+endeudado.getNombre()+" serán traspasadas á Banca.\n");
        }else{
            System.out.println("\nAs propiedades de "+endeudado.getNombre()+" serán revocadas a "+Recaudador.getNombre()+".\n");
        }
        if(!endeudado.getPropiedades().isEmpty()){
            for(Casilla aux: endeudado.getPropiedades()) {
                aux.setDueño(Recaudador);
            }
        }else{
                System.out.println("\nO xogador endeudado non ten propiedades...");
            }

    }

    private void leerArquivo(String nomeArquivo){
        try {
            java.io.File archivo = new java.io.File(nomeArquivo);
            Scanner lector = new Scanner(archivo);
            ArrayList<String> comandos = new ArrayList<String>();
            
            while(lector.hasNextLine()) {
                comandos.add(lector.nextLine());
            }
            lector.close();
            
            for(String comando : comandos) {
                if(!comando.trim().isEmpty()) {
                    System.out.println("Executando: " + comando);
                    analizarComando(comando);
                }
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Non se atopou o arquivo: " + nomeArquivo);
        } catch (Exception e) {
            System.out.println("Error ao ler o arquivo: " + e.getMessage());
        }

    }
    private boolean finalizarPartida(){
        Jugador ganhador = jugadores.getFirst();
        System.out.println("\nO Gañador da partida foi "+ganhador.getNombre() + ", felicidades!\n");
        System.out.println("Rematou a partida con " + ganhador.getFortuna()+" $, e as súas propiedades son:\n ");
        System.out.println("{\n");
        for(Casilla aux : ganhador.getPropiedades()){
            System.out.println(aux.getNombre() + "\n");
        }
        System.out.println("}");
    return true;
    }
    private void estadisticasPartida() {
        boolean primeraComprada = false;
        boolean grupoComprado = false;
        int rentGrupoMax =-1*10^8;
        int rentGrupoActual = 0;
        Casilla casillaMax = this.tablero.getPosiciones().get(0).get(0); //Inicializamos casillaMax con la primera casilla del tablero tipo soloar
        Casilla freqmax = this.tablero.getPosiciones().get(0).get(0);
        for (ArrayList<Casilla> lado : this.tablero.getPosiciones()) {
            for (Casilla casilla : lado) {
                if ((casilla.getTipo().equalsIgnoreCase("Transportes") || casilla.getTipo().equalsIgnoreCase("Servicios") || casilla.getTipo().equalsIgnoreCase("Solar")) && casilla.getDuenho() != banca) {
                    if ((casilla.getRentabilidad() > casillaMax.getRentabilidad() || casillaMax.getDuenho() == banca)) {
                        casillaMax = casilla;
                    }
                }
            }
        }

        Set<String> set = this.tablero.getGrupos().keySet();
        Grupo grupomax = null;
        for (String key : set) {
            Grupo grupo=this.tablero.getGrupos().get(key);
            grupoComprado = false;
            for(Casilla aux: grupo.getMiembros()){
                if(aux.getDuenho()!=banca){
                    rentGrupoActual+=aux.getRentabilidad();
                    grupoComprado = true;
                }
            }
            if(rentGrupoActual>rentGrupoMax && grupoComprado){
                rentGrupoMax=rentGrupoActual;
                grupomax=grupo;
            }

        }
        if (casillaMax.getDuenho() == banca) {
            System.out.println("\n -*Ningunha propiedade foi comprada aínda.\n");
            primeraComprada = false;
        }else{
            System.out.println(" -*Casilla máis rentable: "+casillaMax.getNombre()+".\n");
            primeraComprada = true;
        }
        for (ArrayList<Casilla> lado : this.tablero.getPosiciones()) {
            for (Casilla aux : lado) {
                    if (aux.getCaidas() > freqmax.getCaidas()) {
                        freqmax = aux;
                }
            }
        }
        if(primeraComprada){
            System.out.println(" -*O grupo máis rentable é: "+grupomax.getColorGrupo());
        }else{
            System.out.println(" -*Ningún grupo foi comprado aínda.\n");
        }
        System.out.println(" -*Casilla máis frecuentada: "+freqmax.getNombre()+", cunha frecuencia de "+freqmax.getCaidas()+" caidas.\n");
        Jugador voltasmax = jugadores.get(0);
        Jugador fortunamax = jugadores.get(0);
        for(Jugador aux: jugadores){
            if(aux.getEstatisticas().getVoltasDadas()>voltasmax.getEstatisticas().getVoltasDadas()){
                voltasmax = aux;
            }
            if(aux.getFortuna()>fortunamax.getFortuna()){
                fortunamax = aux;
            }
        }
        System.out.println(" -*Xogador con máis voltas: "+voltasmax.getNombre()+".\n");
        System.out.println(" -*Xogador en cabeza: "+fortunamax.getNombre()+", con unha fortuna de "+fortunamax.getFortuna()+" €.\n");


    }

    private void estadisticasXogador(String[] comandos){
        for(Jugador aux: jugadores){
            if(aux.getNombre().equalsIgnoreCase(comandos[1])){
                System.out.println(aux.getEstatisticas());
                return;
            }
        }
        System.out.println("\nNon existe ningún xogador con ese nome.");
        return;
    }
}
