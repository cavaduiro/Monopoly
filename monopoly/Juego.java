package monopoly;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


import partida.*;
import casillas.*;
import casillas.Propiedad;
import exception.ExcepcionSintaxis;
import javax.net.ssl.TrustManager;

public class Juego implements Comando{
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
    private boolean comandos = false;
    final public static Consola consol = new Consola();

    @SuppressWarnings("Convert2Diamond")
    public Juego(){
        String comando;
        this.banca = new Jugador();
        this.tablero = new Tablero(this.banca);
        this.jugadores = new ArrayList<Jugador>();
        this.avatares = new ArrayList<Avatar>();
        this.dado1 = new Dado();
        this.dado2 = new Dado();
        while(!partidaFinalizada){
            try {
                consol.impTablero(tablero);
                if(partidaIniciada){ //Mostramos o nome do xogador que ten o turno
                    comando = consol.leer("Turno de "+"\033[1m" + jugadores.get(turno).getNombre() + ": \033[0m");
                }else{ //Se a partida non comezou, mostramos o prompt xenérico
                    comando = consol.leer("\033[1m$:\033[0m");
                }
                
                analizarComando(comando);
            } catch (ExcepcionSintaxis e) //aquí hai que facer multicatch
            {
                consol.imprimir(e.getMessage());
            }
            
            
        }
        consol.cred();
    }

    public Tablero getTablero() {
        return tablero;
    }
    @Override
    public void iniciarPartida() {
    }
    
    /*
     * Método que analiza el comando introducido por el usuario y llama a los métodos correspondientes.
     * Parámetro: cadena de caracteres con el comando introducido.
    */
    private void analizarComando(String comando) throws ExcepcionSintaxis{
        String[] cmdseparado = comando.split(" "); //Separamos el comando por espacios para analizarlo mejor, .split(" ") separa en cada espacio
        switch (cmdseparado[0]){
            case "crear":
                if(cmdseparado.length!=4){
                    throw new ExcepcionSintaxis("Uso: crear jugador <nombreJugador> <tipoAvatar>");
                }
                crearxogador(cmdseparado);
                break;
            case "describir":
                if(cmdseparado.length!=2){
                 System.out.println("Uso: describir jugador <nombreJugador>  -  describir <nombreCasilla>");
                 break;
                }
                describir(cmdseparado);
                break;
            case "jugador":
                xogadorturno();
                break;
            case "ver":
            if(comandos)
                {consol.impTablero(tablero);}
                break;
            case "listar":
                listar(cmdseparado);
                break;
            case "empezar":
                iniciarPartida();
                break;
            case "lanzar":
                if(cmdseparado.length<2){
                    //ERROR CHEQUEABLE
                    Valor.error("Número de argumentos erróneo.");
                    consol.imprimir("Uso: lanzar dados  -  lanzar dados dado1+dado2");
                }
                if(cmdseparado[1].equalsIgnoreCase("dados")){
                    lanzarDados(cmdseparado);
                }
                break;
            case "salir":
                if(cmdseparado.length<2){
                    Valor.error("Número de argumentos erróneo.");
                    consol.imprimir("Uso: salir carcel");
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
                if(cmdseparado.length<2){
                    Valor.error("Número de argumentos erróneo.");
                    consol.imprimir("Uso: comprar <propiedad>");
                    break;
                }
                comprar(cmdseparado[1]);
                break;
            case "comandos":
                if(cmdseparado.length < 2){
                    Valor.error("Número de argumentos erróneo.");
                    consol.imprimir("Uso: comandos <archivo>");
                    break;
                }
                leerArquivo(cmdseparado[1]);
                break;
            case "estadisticas":
                if(!partidaIniciada){
                    Valor.error("A partida aínda non comezou, non hai estatísticas que mostrar.");
                    break;
                }
                if (cmdseparado.length < 2){
                    estadisticasPartida();
                    break;
                }else if(cmdseparado.length>3)
                {
                    Valor.error("Numero de comandos erróneo");
                    consol.imprimir("Uso: estadisticas <opcional: jugador>");
                    break;
                }
                estadisticasXogador(cmdseparado);
                break;
            case "edificar":
                if(!partidaIniciada){
                    Valor.error("A partida aínda non comezou, non se poden edificar edificios.");
                    break;
                }
                if(cmdseparado.length<2){
                    Valor.error("Número de argumentos erróneo.");
                    consol.imprimir("Uso: edificar <propiedad>");
                    break;
                }
                edificar(cmdseparado);
                break;
            case "vender":
                if(!partidaIniciada){
                    Valor.error("A partida aínda non comezou, non se poden vender edificios.");
                    break;
                }
                if(cmdseparado.length<4){
                    Valor.error("Número de argumentos erróneo.");
                    consol.imprimir("Uso: vender <tipo> <solar> <cantidade>");
                    break;
                }
                
                vender(cmdseparado);
                break;
            case "hipotecar":
                if (!partidaIniciada){
                    Valor.error("A partida aínda non comezou, non se poden hipotecar propiedades.");
                    break;
                }
                if (cmdseparado.length < 2){
                    Valor.error("Número de argumentos erróneo.");
                    consol.imprimir("Uso: hipotecar <propiedad>");
                    break;
                }
                hipotecar(cmdseparado);
                break;
            case "deshipotecar":
                if (!partidaIniciada){
                        Valor.error("A partida aínda non comezou, non se poden hipotecar propiedades.");
                        break;
                    }
                if (cmdseparado.length < 2){
                    Valor.error("Número de argumentos erróneo.");
                    System.out.println("Uso: deshipotecar <propiedad>");
                    break;
                }
                deshipotecar(cmdseparado);
                break;
            case "axuda":
                mostrarAxuda();
                break;
            default:
                Valor.error("\nComando introducido erróneo.\n");

                break;

        }
    }

    /*
     *Método que realiza as accións asociadas ao comando 'listar'.
     * Parámetro: array de cadenas de caracteres con las partes del comando. 
    */
    @Override
    public void listar(String[] partes){
        if(partes.length<2){
            Valor.error("Non inseriu o número de comandos suficientes");
            System.out.println("Uso: listar jugadores\nUso: listar enventa\nUso: listar edificios <opcional: colorGrupo>");
        }
        if(partes[1].equalsIgnoreCase("jugadores")){
            listarJugadores();
        }else if(partes[1].equalsIgnoreCase("enventa")){
            listarVenta();
        }else if(partes[1].equalsIgnoreCase("edificios") && partes.length==2){
            listarEdificios();
        }else if(partes[1].equalsIgnoreCase("edificios") && partes.length==3){
            listarEdificiosGrupo(partes[2]);
        }else{
            Valor.error("Comando introducido erróneo.");
        }
    }

    /*
     *Método que realiza as accións asociadas ao comando 'describir'.
     * Parámetro: array de cadenas de caracteres con las partes del comando. 
    */
    @Override
    public void describir(String[] partes){
        if(partes.length < 2 || partes.length > 3){
            Valor.error("Número de parámetros incorrecto");
            System.out.println("Uso: describir jugador nombreJugador\nUso: describir nombreCasilla");
            return;
        }
        if(partes[1].equalsIgnoreCase("jugador")&& partes.length==3){
            descJugador(partes);
        }else{
            descCasilla(partes);
        }
    }

    /*
     *Método que crea un xogador coa información dada no comando.
     * Parámetro: nome do xogador a crear. 
    */
   @Override
    public void crearxogador(String[] partes) {
        if(partidaIniciada){
            Valor.error("A partida xa comezou. Non se poden engadir máis xogadores.");
            return;
        }
        if(partes.length !=4){
            Valor.error("Número de parámetros incorrecto.");
            System.out.println("Uso: crear jugador nome tipoAvatar");
            return;
        }
        if(jugadores.size()==4){
            Valor.error("Número máximo de xogadores alcanzado (4).");
            return;
        }
        if(!partes[3].equalsIgnoreCase("coche")&&!partes[3].equalsIgnoreCase("sombrero")&&!partes[3].equalsIgnoreCase("esfinge")&&!partes[3].equalsIgnoreCase("pelota"))    {
            Valor.error("Tipo de avatar non válido. Os tipos dispoñibles son: coche, sombrero, esfinge e pelota.");
                return;
        }
        for(Jugador aux:jugadores){
            if(aux.getNombre().equals(partes[2])){
                Valor.error("Xa existe un xogador co nome "+partes[2]+".");
                return;
            }
            else if(aux.getAvatar().getTipo().equalsIgnoreCase(partes[3])){
                Valor.error("Xa existe un xogador co avatar "+partes[3]+".");
                return;
            }
        }
        Casilla auxsal = tablero.encontrar_casilla("Salida"); //Colocamos o xogador creado na casilla de saída
        Jugador nuevo= new Jugador(partes[2],partes[3],auxsal,avatares);

        jugadores.add(nuevo);
        auxsal.anhadirAvatar(nuevo.getAvatar());
    }

    /*
     *Describe un xogador dado polo nome.
     * Parámetro: nome do xogador a describir. 
    */
    private void descJugador(String[] partes) {
        if(jugadores.isEmpty()){
            Valor.error("Non hai xogadores aínda.");
            return;
        }
        //System.out.println("Nome do xogador a describir: "+partes[2] );
        boolean encontrado = false;
        for(Jugador aux:jugadores){
            if(aux.getNombre().equals(partes[2])){
                System.out.println(aux);
                encontrado = true;
                break;
            }
        }
        if(!encontrado){
            Valor.error("Non existe un xogador co nome" + partes[2]);
        }
    }
    

    /*
     *Describe unha casilla dado polo nome.
     * Parámetro: nome da casilla a describir.
    */
    public void descCasilla(String[] nombre) {
        Casilla aux =tablero.encontrar_casilla(nombre[1]);
        if(aux != null){
            System.out.println(aux);
        }else{
            Valor.error("Non existe ningunha casilla chamada " + nombre[1]);
        }
    }

    /*
     *Mostra a información do xogador que ten o turno.
     * Parámetro: -
    */
    @Override
    public void xogadorturno(){
        Jugador jugadorActual = jugadores.get(turno);
        System.out.println(jugadorActual);
    }

    /*
     *Lánzanse os dados e móvese o avatar do xogador que ten o turno.
     * Parámetro: os números dos dados (opcional).
    */ 
   @Override
    public void lanzarDados(String[] partes) {
        if(partidaIniciada==false && jugadores.size()>=2){
            partidaIniciada=true;
            System.out.println("\033[1m\nA partida comezou\033[0m\n");
        }
        else if(partidaIniciada==false && jugadores.size()<2){
            Valor.error("A partida aínda non comezou, necesítanse dous xogadores.");
            return;
        }

        boolean forzar=false;
        if(partes.length == 3) {
            forzar = true;
        }
        Jugador jugadorActual= jugadores.get(turno);
        //System.out.println(jugadorActual);
        if(tirado){
            Valor.error("Xa tirou os dados este turno.");
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
        if(jugadorActual.getEnCarcel()){ //Se o xogador está na cárcere
            if (valor1==valor2){ //Se saca dobles, sae da cárcere
                jugadorActual.setEnCarcel(false);
                lanzamientos=0;
                tirado=false;
                consol.imprimir("Sacaches un "+"\033[1m"+valor1+" e un "+valor2+"\033[0m. Saíches do cárcere.");
                return;
            }else{
                jugadorActual.sumartiradascarcel();
                consol.imprimir("Segues na cárcere. Levas "+"\033[1m"+jugadorActual.getTiradasCarcel()+" turnos.\033[0m");
                tirado =true;
                return;
            }
        }

        if(lanzamientos==2 && valor1==valor2){
            System.out.println("\nSacaches tres dobles seguidos, \033[1mvas ao cárcere.\033[0m\n");
            jugadorActual.encarcelar(tablero.getPosiciones());
            lanzamientos=0;
            tirado=true;
            return;
        }
        lanzamientos++;
        consol.imprimir("Sacaches un "+"\033[1m"+valor1+" e un "+valor2+"\033[0m.\n");
        jugadorActual.getAvatar().moverAvatar(tablero.getPosiciones(),valor1+valor2);

        solvente = jugadorActual.getAvatar().getLugar().EvaluarCasilla(jugadorActual,banca,valor1+valor2, getTablero().getPosiciones());

        tirado=true;
        if(valor1==valor2&&!jugadorActual.getEnCarcel()){
            System.out.println("Sacaches dobles");
            tirado=false;

        }
    }

    /*
     *Sale da cárcere pagando a fianza.
     * Parámetro: - 
    */
   @Override
    public void salirCarcelPagando(){
        Jugador actual=jugadores.get(turno);
        if(!actual.getEnCarcel()){
            Valor.error("Non estás no cárcere");
            return;
        }
        if(actual.getFortuna()<500000){
            Valor.error("Non tes cartos de abondo para saír do cárcere." + "\033[1m Precisas 500000€, e tes "+String.format("%.0f", actual.getFortuna())+"€\033[0m");
            return;
        }
        if(tirado){
            Valor.error("Xa tiraches os dados");

        }
        else{
            actual.sumarFortuna(-500000);
            actual.getEstatisticas().acImpPagado(500000);
            actual.setEnCarcel(false);
            System.out.println(actual.getNombre()+" pagaches a cuota de 500000€ e saíches do cárcere, podes tirar os dados.");

        }
    }

    /*
     *Compras a propiedade na que está o avatar do xogador que ten o turno.
     * Parámetro: nome da casilal a comprar. 
    */
   @Override
    public void comprar(String nombre) {
        if(!solvente){
            Valor.error("O xogador actual non é solvente, non pode facer compras ata pagar as súas débedas.");
            return;
        }
        Casilla casillaComprar = tablero.encontrar_casilla(nombre);
        if (casillaComprar == null) {
            Valor.error("Non existe ningunha casilla co nome " + nombre);
            return;
        }
        
        if (!(casillaComprar instanceof Propiedad)){
            Valor.error("Esa casilla non é mercable, so podes comprar casillas de tipo Solar, Transporte o Servicio.");
            return;
        }
        Propiedad casaux = (Propiedad)casillaComprar;
        if(casaux.getDuenho() != banca){
            Valor.error("Esa casilla xa ten dono, é de "+casaux.getDuenho().getNombre()+".");
            return;
        }
        
        Jugador jugadorActual= jugadores.get(turno);
        if(jugadorActual.getAvatar().getLugar() != casaux){
            Valor.error("Non estás nesa casilla que queres comprar.");
            return;
        }
        if(jugadorActual.getFortuna() < casaux.getValor()){
            Valor.error("Non tes suficiente fortuna para comprar a casilla, a casilla costa "+casaux.getValor()+" e ti tes aforrado "+jugadorActual.getFortuna()+"€");
            return;
        }
        casaux.comprarCasilla(jugadorActual, banca);

    }
    @Override
    public  void edificar(String[] partes){
        Jugador jugadorActual= jugadores.get(turno);
        if(!partes[1].equals("casa")&&!partes[1].equals("hotel")&&!partes[1].equals("piscina")&&!partes[1].equals("deporte")){
            Valor.error("Tipo de edificio non válido. Podes construir casa, hotel, piscina ou deporte.");
            return;
        }

        //ERROR 
        Casilla casillaEdificar = jugadorActual.getAvatar().getLugar();

        if(casillaEdificar.getDuenho() != jugadorActual){
            Valor.error("Non estás na túa propiedade.");
            return;
        }

        if (!(casillaEdificar instanceof Solar)) {
            Valor.error("Só podes edificar en solares.");
            return;
        }
        Solar solaredificar = (Solar) casillaEdificar;
        ArrayList<Solar> casillasGrupo = solaredificar.getGrupo().getMiembros();

        for(Propiedad aux: casillasGrupo){
            if(aux.getHipotecada()){
                Valor.error("Non podes edificar neste grupo porque algunha casilla do grupo está hipotecada.");
                return;
            }
        }
        
        if(!solaredificar.getGrupo().esDuenhoGrupo(jugadorActual)){
            Valor.error("Necesitas comprar todas as casillas do grupo para poder edificar nesta casilla");
            return;
        }

        solaredificar.edificar(partes[1], jugadorActual);
    }

    @Override
    public void vender(String[] partes){
        Jugador jugadorActual= jugadores.get(turno);

        if(Integer.parseInt(partes[3]) <=0){
            Valor.error("A cantidade ten que ser maior que 0.");
            return;
        }

        if(!partes[1].equals("casa")&&!partes[1].equals("hotel")&&!partes[1].equals("piscina")&&!partes[1].equals("deporte")){
            Valor.error("Tipo de edificio non válido. Podes vender casa, hotel, piscina ou deporte.");
            return;
        }
        Casilla casillaex = tablero.encontrar_casilla(partes[2]);        
        
        if (casillaex == null) {
            Valor.error("Non existe ningunha casilla co nome." + partes[2]);
            return;
        }
        //AUÍ DEBERÍAMOS CHEQUER SE É INSTANCIA DE 

        
        if (!(casillaex instanceof Solar)) {
            //error chequeable
            Valor.error("Só podes vender edificios en solares.");
            return;
        }
        Solar solarex = (Solar) casillaex;
        if (solarex.getDuenho() != jugadorActual) {
            Valor.error("Non podes vender se non eres dueño do solar.");
        }
        if(!solarex.getGrupo().esDuenhoGrupo(jugadorActual)){
            Valor.error("Non eres dueño de todas as casillas do grupo, por tanto non hai edificios que vender nelas");
            return;
        }
    
        solarex.vender(partes[1], Integer.parseInt(partes[3]), jugadorActual);
    }
    
    //Metodo que ejecuta todas las acciones relacionadas con el comando 'salir carcel'.
    @Override
    public void salirCarcel() {
        if(jugadores.isEmpty()){
            Valor.error("Non hai xogadores creados todavía.");
            return;
        }
        Jugador jugadorActual= jugadores.get(turno);
        if(!jugadorActual.getEnCarcel()){
            Valor.error("Non estás no cárcere.");
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
                if (casilla.getDuenho() == banca && (casilla instanceof Propiedad)) {
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
    private void listarEdificios(){
        boolean atopou=false;
        ArrayList<ArrayList<Casilla>> pos = tablero.getPosiciones();
        for (ArrayList<Casilla> lado : pos) {
            for (Casilla casilla : lado) {
                if(!(casilla instanceof Solar)){
                    continue;
                } else if (casilla.getDuenho() == banca) {
                    continue;
                }
                Solar casaux = (Solar)casilla;
                //ERRORget
                // Edificios().get("casa");
                if(casaux.getNumCasas() > 0){
                    atopou=true;
                    consol.imprimir(casaux.getCasas().toString());
                }
                if (casaux.tenhotel()) {
                    atopou = true;
                    consol.imprimir(casaux.getHotel().toString());
                }
                if (casaux.tenpiscina()) {
                    atopou = true;
                    consol.imprimir(casaux.getPiscina().toString());
                }
                if (casaux.tenpista()) {
                    atopou = true;
                    consol.imprimir(casaux.getPista().toString());
                }

            }
        }
        if(!atopou){
            Valor.error("\nNon hai edificios construídos no tablero.\n");
        }
    }

    private void listarEdificiosGrupo(String colorGrupo){
        boolean atopou=false;
        ArrayList<ArrayList<Casilla>> pos = tablero.getPosiciones();
        int casas=0;
        boolean hotel=false, piscina=false, deporte=false;
        for (ArrayList<Casilla> lado : pos) {
            for (Casilla casilla : lado) {
                if(!(casilla instanceof Solar)){
                    continue;
                } else if (casilla.getDuenho() == banca) {
                    continue;
                }
                Solar solaredif = (Solar) casilla;
                if(Valor.getNombreColor(solaredif.getGrupo().getColorGrupo()).equalsIgnoreCase(colorGrupo)){        
                    
                    if(solaredif.getNumCasas() > 0){
                        casas=solaredif.getNumCasas();
                        consol.imprimir(solaredif.getCasas().toString());
                    }
                    if (solaredif.tenhotel()) {
                        consol.imprimir(solaredif.getHotel().toString());
                    }
                    if(solaredif.tenpiscina()){
                        consol.imprimir(solaredif.getPiscina().toString());
                    }
                    if (solaredif.tenpista()) {
                        consol.imprimir(solaredif.getPista().toString());
                    }
                    //ERROR todo isto hai que cambialo por get instance of
                    atopou=true;
                }
            }
        }
        if (!atopou) {
            //ERROR CHEQUEABLE
            System.out.println("Non hai edificios construídos neste grupo de cor ou a cor non é válida.");
        }else{
            if(deporte){
                System.out.println("Xa non podes construír máis edificios neste grupo.");
                return;
            }
            if(piscina){
                System.out.println("Xa non podes construír máis casas, hoteis nin piscinas, pero aínda podes construír un deporte.");
                return;
            }
            if(hotel){
                System.out.println("Xa non podes construír máis casas nin hoteis, pero aínda podes construír unha piscina e un deporte.");
                return;
            }
            if(casas == 4){
                System.out.println("Xa non podes construír máis casas neste grupo, pero aínda podes construír un hotel, unha piscina e un deporte.");
            }
            if(casas < 4){
                System.out.println("Aínda podes construír "+(4-casas)+" casas neste grupo, un hotel, unha piscina e un deporte.");
            }
        }
    }


    //Hai que ter todos os edificios dun grupo para poder hipotecar unha propiedade???? no
    @Override
    public void hipotecar(String[] partes){
        Jugador jugadorActual= jugadores.get(turno);
        Casilla casillaHipotecar = tablero.encontrar_casilla(partes[1]);    
        if(casillaHipotecar==null){
            Valor.error("Non existe ningunha casilla co nome."+ partes[1]);
            return;
        }
        if (casillaHipotecar.getDuenho() != jugadorActual) {
            Valor.error(
                    "A casilla " + casillaHipotecar.getNombre() + " non é da túa propiedade, non a podes hipotecar.");
            return;
        }
        Propiedad casillaPropiedade = (Propiedad) casillaHipotecar;
        if(casillaPropiedade.getHipotecada()){
            Valor.error("A casilla "+casillaHipotecar.getNombre()+" xa está hipotecada.");
            return;
        }
        if (casillaPropiedade instanceof Solar casaux) {
            if(casaux.getNumCasas()>0){
                Valor.error("Esta propiedade ten "+casaux.getNumCasas()+" casas construídas, debes vender as casas antes de hipotecar.");
                return;
            }
            if(casaux.tenEdificio()){
                Valor.error("Esta propiedade ten un algún edificio construído, debes vendelo antes de hipotecar.");
                return;
            }

    }
        casillaPropiedade.hipotecarCasilla();
}
    @Override
    public void deshipotecar(String[] partes){
        Jugador jugadorActual= jugadores.get(turno);
        Casilla casillaDeshipotecar = tablero.encontrar_casilla(partes[1]);    
        if(casillaDeshipotecar==null){
            Valor.error("Non existe ningunha casilla co nome."+ partes[1]);
            return;
        }

        if(casillaDeshipotecar.getDuenho() != jugadorActual){
            Valor.error("A casilla "+casillaDeshipotecar.getNombre()+" non é da túa propiedade, non a podes deshipotecar.");
            return;
        }
        if (!(casillaDeshipotecar instanceof Propiedad)) {
            //ERROR
            return;
        }
        Propiedad casdeship = (Propiedad) casillaDeshipotecar;
        
        if(casdeship.getHipotecada()==false){
            Valor.error("A casilla "+casdeship.getNombre()+" non está hipotecada.");
            return;
        }
        casdeship.deshipotecarCasilla();
    }

    // Metodo que realiza las acciones asociadas al comando 'acabar turno'.
    @Override
    public void acabarTurno() {
        //Comprobar que a partida está empezada
        if(!tirado){
            consol.imprimir("Aínda tes que tirar os dados.");
            return;
        }
        Jugador jugadorActual= jugadores.get(turno);
        if(jugadorActual.getEnCarcel() && jugadorActual.getTiradasCarcel()==3){
            if(jugadorActual.getFortuna()<500000){
                Valor.error("Non tes cartos para saír do cárcere (500000€) ...");
                solvente=false;
                //return; En futuras entregas onde podas recaudar cartos vendendo e hipotecando propiedades, non se pode facer return aquí, porque podes conseguir cartos e pagar a multa
            }else{
                solvente=true;
                jugadorActual.sumarFortuna(-500000);
                jugadorActual.setEnCarcel(false);
                jugadorActual.setTiradasCarcel(0);
                consol.imprimir("Saíches automaticamente do cárcere tras tres turnos.\nPagaches 500000€ como multa dude.");
            }

        }
        if(!solvente){
            Jugador Recaudador = jugadorActual.getAvatar().getLugar().getDuenho();
            consol.imprimir("\nNon pagaches as débedas.");
            bancarrota(jugadorActual, Recaudador);
            //solvente = jugadorActual.getAvatar().getLugar().evaluarCasilla(jugadorActual,banca,0); <-non ten sentido facer aquí solvente, xa se fai cando tiras os dados.
            //^^, senón tiras os dados, non cambia o teu estado do xogo. Con saber en que casilla estás xa sabes a quen lle debes os cartos
            //^^ non lle vas pagar a ningúen máis que ao xogador da casilla que che fixo perder os cartos
            consol.imprimir("\nTurno acabado. O xogador "+ jugadorActual.getNombre()+ " foi eliminado. \n");
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
            consol.imprimir("Turno acabado. Agora é o turno de "+jugadores.get(turno).getNombre()+".");
        }
    }

    private void bancarrota(Jugador endeudado, Jugador Recaudador){
    //Quitaránse todas as propidedades do xogar endeudado e daranse ao outro xogador/banca
        if(Recaudador == this.banca){
            consol.imprimir("\nTodas as propiedades do xogador "+endeudado.getNombre()+" serán traspasadas á Banca.\n");
        }else{
            consol.imprimir("\nAs propiedades de "+endeudado.getNombre()+" serán revocadas a "+Recaudador.getNombre()+".\n");
        }
        if(!endeudado.getPropiedades().isEmpty()){
            //ERROR
            for(Propiedad aux: endeudado.getPropiedades()) {
                aux.setDuenho(Recaudador);
            }
        }else{
            //ERROR CHEQUEABLE
                System.out.println("\nO xogador endeudado non ten propiedades...");
            }

    }
    @Override
    @SuppressWarnings("ConvertToTryWithResources")
    public void leerArquivo(String nomeArquivo){
        this.comandos = true;
        try {
            java.io.File archivo = new java.io.File(nomeArquivo);
            Scanner lector = new Scanner(archivo);
            ArrayList<String> comandos = new ArrayList<>();
            
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
            //ERROR CHEQUEABLE
            System.out.println("Non se atopou o arquivo: " + nomeArquivo);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error ao ler o arquivo: " + e.getMessage());
        }
        this.comandos = false;
    }
    private boolean finalizarPartida(){
        Jugador ganhador = jugadores.get(0);
        consol.imprimir("\nO Gañador da partida foi "+ganhador.getNombre() + ", felicidades!\n");
        consol.imprimir("Rematou a partida con " + ganhador.getFortuna()+" $, e as súas propiedades son:\n ");
        consol.imprimir("{\n");
        //ERROR
        for(Propiedad aux : ganhador.getPropiedades()){
            consol.imprimir(aux.getNombre());
        }
        System.out.println("}");
    return true;
    }
    @Override
    public void estadisticasPartida() {
        boolean primeraComprada = false;
        boolean grupoComprado = false;
        double rentGrupoMax =-1*Math.pow(10,8);
        double rentGrupoActual = 0;
        Casilla casillaMax = this.tablero.getPosiciones().get(0).get(0); //Inicializamos casillaMax con la primera casilla del tablero tipo soloar
        Casilla freqmax = this.tablero.getPosiciones().get(0).get(0);
        for (ArrayList<Casilla> lado : this.tablero.getPosiciones()) {
            for (Casilla casilla : lado) {
                if (casilla instanceof Propiedad && casilla.getDuenho() != banca) {
                    Propiedad propaux = (Propiedad) casilla;
                    if ((propaux.getRentabilidad() > propaux.getRentabilidad() || casillaMax.getDuenho() == banca)) {
                        casillaMax = casilla;
                    }
                }
            }
        }

        Set<String> set = this.tablero.getGrupos().keySet();
        Grupo grupomax = null;
        grupoComprado = false;
        for (String key : set) {
            Grupo grupo=this.tablero.getGrupos().get(key);
            //ERROR
            for(Casilla aux: grupo.getMiembros()){
                if(aux.getDuenho()!=banca && aux instanceof Solar){
                    rentGrupoActual+= (((Solar) aux).getRentabilidad());
                    grupoComprado = true;
                }
            }
            if(rentGrupoActual>rentGrupoMax && grupoComprado){
                rentGrupoMax=rentGrupoActual;
                grupomax=grupo;
            }

        }
        if (casillaMax.getDuenho() == banca) {
            //error chequeable
            System.out.println("\n -*Ningunha propiedade foi comprada aínda.\n");
            primeraComprada = false;
        }else{
            consol.imprimir(" -*Casilla máis rentable: "+casillaMax.getNombre()+".\n");
            primeraComprada = true;
        }
        for (ArrayList<Casilla> lado : this.tablero.getPosiciones()) {
            for (Casilla aux : lado) {
                    if (aux.getCaidas() > freqmax.getCaidas()) {
                        freqmax = aux;
                }
            }
        }
        if(grupoComprado){ 
            //ERROR
            consol.imprimir(" -*O grupo máis rentable é: "+grupomax.getColorGrupo());
        }else{
            consol.imprimir(" -*Ningún grupo foi comprado aínda.\n");
        }
        consol.imprimir(" -*Casilla máis frecuentada: "+freqmax.getNombre()+", cunha frecuencia de "+freqmax.getCaidas()+" caidas.\n");
        //Jugador voltasmax = jugadores.get(0);
        ArrayList<Jugador> topvoltas = new ArrayList<>(); //vou probar cunha lista para os empates
        ArrayList<Jugador> topfortunas = new ArrayList<>();
        topfortunas.add(jugadores.get(0));
        topvoltas.add(jugadores.get(0));
        //Jugador fortunamax = jugadores.get(0);
        for(Jugador aux: jugadores){
            float voltasaux = aux.getEstatisticas().getVoltasDadas();
            float cartosaux = aux.getFortuna()+aux.getEstatisticas().getDineroInvertido();
            if(voltasaux>topvoltas.get(0).getEstatisticas().getVoltasDadas()){
                //facemos unha lista co top de players con máis voltas
                for(int i = 0; i<topvoltas.toArray().length; i++){
                    topvoltas.remove(i);
                }
                topvoltas.add(aux);
            }else if(voltasaux == topvoltas.get(0).getEstatisticas().getVoltasDadas() &&!topvoltas.contains(aux)){
                topvoltas.add(aux);
            }
                //repetimos o proceso pero cos cartos
            if(cartosaux>topfortunas.get(0).getFortuna()+topfortunas.get(0).getEstatisticas().getDineroInvertido()){
                //facemos unha lista co top de players con máis voltas
                for(int i = 0; i<topfortunas.toArray().length; i++){
                    topfortunas.remove(i);
                }
                topfortunas.add(aux);
            }else if(cartosaux == (topfortunas.get(0).getFortuna()+topfortunas.get(0).getEstatisticas().getDineroInvertido()) && !topfortunas.contains(aux)){
                topfortunas.add(aux);
            }

        }
        /*Déixoo comentado por se a solución anterior era mellor
        *  for(Jugador aux: jugadores){
            if(aux.getEstatisticas().getVoltasDadas()>voltasmax.getEstatisticas().getVoltasDadas()){
                voltasmax = aux;
            }
            if(aux.getFortuna()>fortunamax.getFortuna()){
                fortunamax = aux;
            }
        }
        if(voltasmax.getEstatisticas().getVoltasDadas()!=0){
            System.out.println(" -*Xogador con máis voltas: "+voltasmax.getNombre()+".\n");
        }else{
            System.out.println(" -*Ningún xogador ten voltas todavía.\n");
        }

        System.out.println(" -*Xogador en cabeza: "+fortunamax.getNombre()+", con unha fortuna de "+fortunamax.getFortuna()+" €.\n");

        *
        *
        * */
        if(topvoltas.get(0).getEstatisticas().getVoltasDadas() == 0){
            consol.imprimir(" -*Ningún xogador ten voltas todavía.\n");
        }else if(topvoltas.size()==1){
            consol.imprimir(" -*Xogador con máis voltas ("+ topvoltas.get(0).getEstatisticas().getVoltasDadas()+"): "+topvoltas.get(0).getNombre()+".");
        }else{
            consol.imprimir(" -*Xogadores con máis voltas ("+ topvoltas.get(0).getEstatisticas().getVoltasDadas()+"):");
            for(Jugador aux: topvoltas){
                consol.imprimir("\t- "+ aux.getNombre()+"\n");
            }
        }
        if(topfortunas.size()==1){
            consol.imprimir(" -*Xogador con máis fortuna("+(topfortunas.get(0).getFortuna()+topfortunas.get(0).getEstatisticas().getDineroInvertido())+"): "+topfortunas.get(0).getNombre()+".");
        }else {
            consol.imprimir(" -*Xogadores con máis fortuna("+(topfortunas.get(0).getFortuna()+topfortunas.get(0).getEstatisticas().getDineroInvertido())+"):");
            for (Jugador aux : topfortunas) {
                consol.imprimir("\t- " + aux.getNombre());
            }
        }
    }
    @Override
    public void estadisticasXogador(String[] comandos){
        for(Jugador aux: jugadores){
            if(aux.getNombre().equalsIgnoreCase(comandos[1])){
                consol.imprimir(aux.getEstatisticas().toString());
                return;
            }
        }
        Valor.error("\nNon existe ningún xogador con ese nome.");
    }
    @Override
    public  void mostrarAxuda(){
        consol.imprimir(

                "\033[1m Lista de comandos dispoñibles: \033[0m\n" +
                "   -crear jugador <nombre> <tipo_avatar>\n" +
                "   -salir carcel\n" +
                "   -acabar turno\n" +
                "   -lanzar dados <dado1>+<dado2>\n" +
                "   -comprar <nombre_casilla>\n" +
                "   -edificar <tipo_edificio>\n" +
                "   -vender <tipo_edificio> <nombre_casilla> <num_edificios>\n" +
                "   -hipotecar <nombre_casilla>\n" +
                "   -deshipotecar <nombre_casilla>\n" +
                "   -listar jugadores\n" +
                "   -listar enventa\n" +
                "   -listar edificios <color_grupo>\n" +
                "   -describir jugador <nombre_jugador>\n" +
                "   -describir <nombre_casilla>\n" +
                "   -estadisticas [<nombre_jugador>]\n" +
                "   -comandos <nombre_archivo>\n"
        );


    }

}
