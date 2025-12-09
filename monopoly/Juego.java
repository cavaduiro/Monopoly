package monopoly;



import casillas.*;
import exception.Excepcion;
import exception.valorInvalido.ExcepcionOOR;
import exception.ExcepcionNoExiste;
import exception.ExcepcionLoxicaPartida;
import exception.ExcepcionSintaxis;
import exception.ExcepcionValorInvalido;
import exception.comandoInvalido.ExcepcionPartidaNonEmpezou;
import exception.comandoInvalido.ExcepcionPartidaXaEmpezou;
import exception.comandoInvalido.ExcepcionXaTirachesDados;
import exception.valorInvalido.ExcepcionOOR;
import exception.valorInvalido.ExcepcionSinCartos;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import partida.*;

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
    private HashMap<Jugador, ArrayList<Tratos>> tratos;
    private boolean primeiraBancarrota = true;

    @SuppressWarnings("Convert2Diamond")
    public Juego(){
        String comando;
        this.banca = new Jugador();
        this.tablero = new Tablero(this.banca);
        this.jugadores = new ArrayList<Jugador>();
        this.avatares = new ArrayList<Avatar>();
        this.dado1 = new Dado();
        this.dado2 = new Dado();
        this.tratos = new HashMap<Jugador, ArrayList<Tratos>>();
        while(!partidaFinalizada){
            try {
                consol.impTablero(tablero);
                if(partidaIniciada&&lanzamientos==0){ //Ao cambiar o turno, dinse os tratos pendentes
                    if(tratos.get(jugadores.get(turno)) != null && !tratos.get(jugadores.get(turno)).isEmpty()){
                        consol.imprimir("\n\033[1mTes tratos pendentes por aceptar.\033[0m");
                        for(Tratos trato : tratos.get(jugadores.get(turno))){
                            consol.imprimir("*ID - "+trato.getId());
                            consol.imprimir("    "+trato.getOfertante().getNombre()+" ofrece: "+(trato.getDineroOfrecido()>0 ? trato.getDineroOfrecido()+" euros " : "")+(trato.getPropiedadOfrecida()!=null ? trato.getPropiedadOfrecida().getNombre() : ""));
                            consol.imprimir("    Pide a "+trato.getReceptor().getNombre()+": "+(trato.getDineroSolicitado()>0 ? trato.getDineroSolicitado()+" euros " : "")+(trato.getPropiedadSolicitada()!=null ? trato.getPropiedadSolicitada().getNombre() : "")+"\n\n");
                        }
                    }
                }
                if(partidaIniciada){ //Mostramos o nome do xogador que ten o turno
                    comando = consol.leer("Turno de " + "\033[1m" + jugadores.get(turno).getNombre() + ": \033[0m");
                } else { //Se a partida non comezou, mostramos o prompt xenérico
                    comando = consol.leer("\033[1m$:\033[0m");
                }
                analizarComando(comando);
            } catch (Excepcion e) //aquí hai que facer multicatch
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
    private void analizarComando(String comando) throws Excepcion{
        String[] cmdseparado = comando.split(" "); //Separamos el comando por espacios para analizarlo mejor, .split(" ") separa en cada espacio
        switch (cmdseparado[0]){
            case "crear":
                if(cmdseparado.length!=4){
                    throw new ExcepcionSintaxis("Uso: crear jugador <nombreJugador> <tipoAvatar>");
                }
                crearxogador(cmdseparado);
                break;
            case "describir":
                if(cmdseparado.length!=3&&cmdseparado.length!=2){
                 throw new ExcepcionSintaxis("Uso: describir jugador <nombreJugador>  -  describir <nombreCasilla>");
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
                    // ("Número de argumentos erróneo.");
                    throw new ExcepcionSintaxis("Uso: lanzar dados  -  lanzar dados dado1+dado2");
                }
                if(cmdseparado[1].equalsIgnoreCase("dados")){
                    lanzarDados(cmdseparado);
                }
                break;
            case "salir":
                if(cmdseparado.length<2){
                    throw new ExcepcionSintaxis("Uso: salir carcel");
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
                    throw new ExcepcionSintaxis("Uso: comprar <propiedad>");
                }
                comprar(cmdseparado[1]);
                break;
            case "comandos":
                if(cmdseparado.length < 2){
                    throw new ExcepcionSintaxis("Uso: comandos <archivo>");
                }
                leerArquivo(cmdseparado[1]);
                break;
            case "estadisticas":
                if(!partidaIniciada){
                    throw new ExcepcionPartidaNonEmpezou("non hai estatísticas que mostrar.");
                }
                if (cmdseparado.length < 2){
                    estadisticasPartida();
                    break;
                }else if(cmdseparado.length>3)
                {
                    throw new ExcepcionSintaxis("Uso: estadisticas <opcional: jugador>");
                }
                estadisticasXogador(cmdseparado);
                break;
            case "edificar":
                if(!partidaIniciada){
                    throw new ExcepcionPartidaNonEmpezou("non se poden edificar edificios.");
                }
                if(cmdseparado.length<2){
                    throw new ExcepcionSintaxis("Uso: edificar <propiedad>");
                
                }
                edificar(cmdseparado);
                break;
            case "vender":
                if(!partidaIniciada){
                    throw new ExcepcionPartidaNonEmpezou("non se poden vender edificios.");
                }
                if(cmdseparado.length<4){
                    throw new ExcepcionSintaxis("Uso: vender <tipo> <solar> <cantidade>");
                }
                
                vender(cmdseparado);
                break;
            case "hipotecar":
                if (!partidaIniciada){
                    throw new ExcepcionPartidaNonEmpezou("non se poden hipotecar propiedades.");
                }
                if (cmdseparado.length < 2){
                    throw new ExcepcionSintaxis("Uso: hipotecar <propiedad>");
                    
                }
                hipotecar(cmdseparado);
                break;
            case "deshipotecar":
                if (!partidaIniciada){
                       throw new ExcepcionPartidaNonEmpezou("non se poden hipotecar propiedades.");
                        
                    }
                if (cmdseparado.length < 2){
                    
                    throw new ExcepcionSintaxis("Uso: deshipotecar <propiedad>");
                }
                deshipotecar(cmdseparado);
                break;
            case "trato":
                if(!partidaIniciada){
                    throw new ExcepcionPartidaNonEmpezou("non se poden facer tratos.");
                    
                }
                if(cmdseparado.length!=5 && cmdseparado.length!=7){
                    
                    throw new ExcepcionSintaxis("Uso: trato <nombreReceptor> <propiedadOfrecida/dineroOfrecido> <propiedadSolicitada/dineroSolicitado>");
                   
                }
                tratos(cmdseparado);
                break;
            case "aceptar":
                if(!partidaIniciada){
                    throw new ExcepcionPartidaNonEmpezou("non se poden aceptar tratos.");
                    
                }
                if(cmdseparado.length!=2){
                    throw new ExcepcionSintaxis("Uso: aceptar <idTrato>");
                    
                }
                aceptarTrato(cmdseparado[1]);
                break;
            case "eliminar":
                if(!partidaIniciada){
                    throw new ExcepcionPartidaNonEmpezou("non se poden eliminar tratos.");
                
                }
                if(cmdseparado.length!=2){
                   
                    throw new ExcepcionSintaxis("Uso: eliminar <idTrato>");
                    
                }
                eliminarTrato(cmdseparado[1]);
                break;
            case "tratos":
                if(!partidaIniciada){
                    throw new ExcepcionPartidaNonEmpezou("non se poden ver tratos.");
           
                }
                if(cmdseparado.length!=1){ 
                    throw new ExcepcionSintaxis("Uso: tratos");
                 
                }
                verTratos();
                break;
            case "axuda":
                mostrarAxuda();
                break;
            default:
                throw new ExcepcionSintaxis("Comando non recoñecido. Escribe 'axuda' para ver os comandos dispoñibles.");
        }
    }

    /*
     *Método que realiza as accións asociadas ao comando 'listar'.
     * Parámetro: array de cadenas de caracteres con las partes del comando. 
    */
    @Override
    public void listar(String[] partes) throws ExcepcionSintaxis, ExcepcionLoxicaPartida {
        if(partes.length<2){
            throw new ExcepcionSintaxis("Uso: listar jugadores\nUso: listar enventa\nUso: listar edificios <opcional: colorGrupo>");
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
            throw new ExcepcionSintaxis("Uso: listar jugadores\nUso: listar enventa\nUso: listar edificios <opcional: colorGrupo>");
        }
    }

    /*
     *Método que realiza as accións asociadas ao comando 'describir'.
     * Parámetro: array de cadenas de caracteres con las partes del comando. 
    */
    @Override
    public void describir(String[] partes) throws ExcepcionSintaxis, ExcepcionNoExiste, ExcepcionLoxicaPartida {
        if(partes.length < 2 || partes.length > 3){
            throw new ExcepcionSintaxis("Uso: describir jugador nombreJugador\nUso: describir nombreCasilla");
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
    public void crearxogador(String[] partes) throws ExcepcionLoxicaPartida,ExcepcionValorInvalido, ExcepcionSintaxis{
        if(partidaIniciada){
            throw new ExcepcionPartidaXaEmpezou("non se poden engadir máis xogadores.");
        }
        if(partes.length !=4){
            throw new ExcepcionSintaxis("Uso: crear jugador nome tipoAvatar");
        }
        if(jugadores.size()==4){
            throw new ExcepcionOOR("Número máximo de xogadores alcanzado (4).");
    
        }
        if(!partes[3].equalsIgnoreCase("coche")&&!partes[3].equalsIgnoreCase("sombrero")&&!partes[3].equalsIgnoreCase("esfinge")&&!partes[3].equalsIgnoreCase("pelota"))    {
            throw new ExcepcionValorInvalido("Tipo de avatar non válido. Os tipos dispoñibles son: coche, sombrero, esfinge e pelota.");
        }
        for(Jugador aux:jugadores){
            if (aux.getNombre().equals(partes[2])) {
                throw new ExcepcionValorInvalido("X;a existe un xogador co nome " + partes[2] + ".");
            }
            else if(aux.getAvatar().getTipo().equalsIgnoreCase(partes[3])){
                throw new ExcepcionValorInvalido("Xa existe un xogador co avatar "+partes[3]+".");
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
    private void descJugador(String[] partes) throws ExcepcionLoxicaPartida,ExcepcionNoExiste {
        if(jugadores.isEmpty()){
            throw new ExcepcionLoxicaPartida("Non hai xogadores aínda.");
            
        }
        //consol.imprimir("Nome do xogador a describir: "+partes[2] );
        boolean encontrado = false;
        for(Jugador aux:jugadores){
            if(aux.getNombre().equals(partes[2])){
                consol.imprimir(aux.toString());
                encontrado = true;
                break;
            }
        }
        if(!encontrado){
            throw new ExcepcionNoExiste("Non existe un xogador co nome " + partes[2]);
        }
    }
    

    /*
     *Describe unha casilla dado polo nome.
     * Parámetro: nome da casilla a describir.
    */
    public void descCasilla(String[] nombre) throws ExcepcionNoExiste {
        Casilla aux =tablero.encontrar_casilla(nombre[1]);
        if(aux != null){
            consol.imprimir(aux.toString());
        }else{
            throw new ExcepcionNoExiste("Non existe ningunha casilla chamada " + nombre[1]);
        }
    }

    /*
     *Mostra a información do xogador que ten o turno.
     * Parámetro: -
    */
    @Override
    public void xogadorturno(){
        Jugador jugadorActual = jugadores.get(turno);
        consol.imprimir(jugadorActual.toString());
    }

    /*
     *Lánzanse os dados e móvese o avatar do xogador que ten o turno.
     * Parámetro: os números dos dados (opcional).
    */ 
   @Override
    public void lanzarDados(String[] partes) throws ExcepcionLoxicaPartida {
        if(partidaIniciada==false && jugadores.size()>=2){
            partidaIniciada=true;
            consol.imprimir("\033[1m\nA partida comezou\033[0m\n");
        }
        else if(partidaIniciada==false && jugadores.size()<2){
            throw new ExcepcionPartidaNonEmpezou("non se poden lanzar os dados, necesítanse dous xogadores.");
        }

        boolean forzar=false;
        if(partes.length == 3) {
            forzar = true;
        }
        Jugador jugadorActual= jugadores.get(turno);
        //System.out.println(jugadorActual);
        if(tirado){
            throw new ExcepcionXaTirachesDados();
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
            consol.imprimir("\nSacaches tres dobles seguidos, \033[1mvas ao cárcere.\033[0m\n");
            jugadorActual.encarcelar(tablero.getPosiciones());
            lanzamientos=0;
            tirado=true;
            return;
        }
        lanzamientos++;
        consol.imprimir("Sacaches un "+"\033[1m"+valor1+" e un "+valor2+"\033[0m.\n");
        jugadorActual.getAvatar().moverAvatar(tablero.getPosiciones(),valor1+valor2);

        this.solvente = jugadorActual.getAvatar().getLugar().EvaluarCasilla(jugadorActual,banca,valor1+valor2, getTablero().getPosiciones());
        
        tirado=true;
        if(valor1==valor2&&!jugadorActual.getEnCarcel()){
            consol.imprimir("Sacaches dobles");
            tirado=false;

        }
    }

    /*
     *Sale da cárcere pagando a fianza.
     * Parámetro: - 
    */
   @Override
    public void salirCarcelPagando() throws ExcepcionLoxicaPartida, ExcepcionSinCartos {
        Jugador actual=jugadores.get(turno);
        if(!actual.getEnCarcel()){
            throw new ExcepcionLoxicaPartida("Non estás no cárcere");
        }
        if(actual.getFortuna()<500000){
            throw new ExcepcionSinCartos(actual.getNombre(), (int)actual.getFortuna(), 500000);
        }
        if(tirado){
            throw new ExcepcionXaTirachesDados();

        }
        else{
            actual.sumarFortuna(-500000);
            actual.getEstatisticas().acImpPagado(500000);
            actual.setEnCarcel(false);
            consol.imprimir(actual.getNombre()+" pagaches a cuota de 500000€ e saíches do cárcere, podes tirar os dados.");

        }
    }

    /*
     *Compras a propiedade na que está o avatar do xogador que ten o turno.
     * Parámetro: nome da casilal a comprar. 
    */
   @Override
    public void comprar(String nombre) throws ExcepcionLoxicaPartida, ExcepcionNoExiste, ExcepcionValorInvalido {
        if(!solvente){
            throw new ExcepcionLoxicaPartida("Non es solvente, non podes comprar propiedades ata pagar as túas débedas.");
        }
        Casilla casillaComprar = tablero.encontrar_casilla(nombre);
        if (casillaComprar == null) {
            throw new ExcepcionNoExiste("Non existe ningunha casilla chamada " + nombre);
        }
        
        if (!(casillaComprar instanceof Propiedad)){
            throw new ExcepcionValorInvalido("Non podes comprar esa casilla, non é unha propiedade.");
        }
        Propiedad casaux = (Propiedad)casillaComprar;
        if(casaux.getDuenho() != banca){
            throw new ExcepcionValorInvalido("Non podes comprar esa casilla, xa ten dono.");
        }
        
        Jugador jugadorActual= jugadores.get(turno);
        if(jugadorActual.getAvatar().getLugar() != casaux){
            throw new ExcepcionLoxicaPartida("Non estás na casilla que queres comprar.");
        }
        if(casaux.getDuenho() == jugadorActual){
            throw new ExcepcionLoxicaPartida("Xa es dono desa propiedade.");
        }
        if(jugadorActual.getFortuna() < casaux.getValor()){
            throw new ExcepcionSinCartos(jugadorActual.getNombre(), (int)jugadorActual.getFortuna(), (int)casaux.getValor());
        }
        casaux.comprarCasilla(jugadorActual, banca);

    }
    @Override
    public  void edificar(String[] partes) throws ExcepcionValorInvalido, ExcepcionLoxicaPartida, ExcepcionSinCartos {
        Jugador jugadorActual= jugadores.get(turno);
        if(!partes[1].equals("casa")&&!partes[1].equals("hotel")&&!partes[1].equals("piscina")&&!partes[1].equals("deporte")){
            throw new ExcepcionValorInvalido("Tipo de edificio non válido. Podes edificar casa, hotel, piscina ou deporte.");
        }

        //ERROR 
        Casilla casillaEdificar = jugadorActual.getAvatar().getLugar();

        if(casillaEdificar.getDuenho() != jugadorActual){
            throw new ExcepcionLoxicaPartida("Non estás na túa propiedade.");
        }

        if (!(casillaEdificar instanceof Solar)) {
            throw new ExcepcionLoxicaPartida("Só podes edificar en solares.");
        }
        Solar solaredificar = (Solar) casillaEdificar;
        ArrayList<Solar> casillasGrupo = solaredificar.getGrupo().getMiembros();

        for(Propiedad aux: casillasGrupo){
            if(aux.getHipotecada()){
                throw new ExcepcionLoxicaPartida("Non podes edificar cunha propiedade do grupo hipotecada.");
            }
        }
        
        if(!solaredificar.getGrupo().esDuenhoGrupo(jugadorActual)){
            throw new ExcepcionLoxicaPartida("Necesitas comprar todas as casillas do grupo para poder edificar nesta casilla");
        }

        solaredificar.edificar(partes[1], jugadorActual);
    }

    @Override
    public void vender(String[] partes) throws ExcepcionValorInvalido, ExcepcionLoxicaPartida, ExcepcionNoExiste {
        Jugador jugadorActual= jugadores.get(turno);

        if(Integer.parseInt(partes[3]) <=0){
            throw new ExcepcionValorInvalido("A cantidade de edificios a vender debe ser maior que 0.");
        }

        if(!partes[1].equals("casa")&&!partes[1].equals("hotel")&&!partes[1].equals("piscina")&&!partes[1].equals("deporte")){
            throw new ExcepcionValorInvalido("Tipo de edificio non válido. Podes vender casa, hotel, piscina ou deporte.");
        }
        Casilla casillaex = tablero.encontrar_casilla(partes[2]);        
        
        if (casillaex == null) {
            throw new ExcepcionNoExiste("Non existe ningunha casilla co nome." + partes[2]);
        }
        //AUÍ DEBERÍAMOS CHEQUER SE É INSTANCIA DE 

        
        if (!(casillaex instanceof Solar)) {
            //error chequeable
            throw new ExcepcionLoxicaPartida("Só podes vender edificios en solares.");
        }
        Solar solarex = (Solar) casillaex;
        if (solarex.getDuenho() != jugadorActual) {
            throw new ExcepcionLoxicaPartida("Non podes vender se non eres dueño do solar.");
        }
        if(!solarex.getGrupo().esDuenhoGrupo(jugadorActual)){
            throw new ExcepcionLoxicaPartida("Non eres dueño de todas as casillas do grupo, por tanto non hai edificios que vender nelas");
        }
    
        solarex.vender(partes[1], Integer.parseInt(partes[3]), jugadorActual);
    }
    
    //Metodo que ejecuta todas las acciones relacionadas con el comando 'salir carcel'.
    @Override
    public void salirCarcel() throws ExcepcionLoxicaPartida {
        if(jugadores.isEmpty()){
            throw new ExcepcionPartidaNonEmpezou("non hai xogadores aínda");
        }
        Jugador jugadorActual= jugadores.get(turno);
        if(!jugadorActual.getEnCarcel()){
            throw new ExcepcionLoxicaPartida("Non estás no cárcere.");
        }
        else{
            jugadorActual.setEnCarcel(false);

            consol.imprimir("Saíches do cárcere.");
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
    private void listarEdificios() throws ExcepcionLoxicaPartida {
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
            throw new ExcepcionLoxicaPartida("Non hai edificios construídos no taboleiro.");
        }
    }

    private void listarEdificiosGrupo(String colorGrupo) throws ExcepcionLoxicaPartida {
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
                    //non hai pq, xa temos métodos de tenhotel, tenpiscina, tenpista, por tanto sería igual...
                    atopou=true;
                }
            }
        }
        if (!atopou) {
            throw new ExcepcionLoxicaPartida("Non hai edificios construídos neste grupo de propiedades.");
        }else{
            if(deporte){
                consol.imprimir("Xa non podes construír máis edificios neste grupo.");
                return;
            }
            if(piscina){
                consol.imprimir("Xa non podes construír máis casas, hoteis nin piscinas, pero aínda podes construír un deporte.");
                return;
            }
            if(hotel){
                consol.imprimir("Xa non podes construír máis casas nin hoteis, pero aínda podes construír unha piscina e un deporte.");
                return;
            }
            if(casas == 4){
                consol.imprimir("Xa non podes construír máis casas neste grupo, pero aínda podes construír un hotel, unha piscina e un deporte.");
            }
            if(casas < 4){
                consol.imprimir("Aínda podes construír "+(4-casas)+" casas neste grupo, un hotel, unha piscina e un deporte.");
            }
        }
    }


    //Hai que ter todos os edificios dun grupo para poder hipotecar unha propiedade???? no
    @Override
    public void hipotecar(String[] partes) throws ExcepcionNoExiste, ExcepcionLoxicaPartida {
        Jugador jugadorActual= jugadores.get(turno);
        Casilla casillaHipotecar = tablero.encontrar_casilla(partes[1]);    
        if(casillaHipotecar==null){
            throw new ExcepcionNoExiste("Non existe ningunha casilla co nome."+ partes[1]);
        }
        if (casillaHipotecar.getDuenho() != jugadorActual) {
            throw new ExcepcionLoxicaPartida("A casilla "+casillaHipotecar.getNombre()+" non é da túa propiedade, non a podes hipotecar.");
        }
        Propiedad casillaPropiedade = (Propiedad) casillaHipotecar;
        if(casillaPropiedade.getHipotecada()){
            throw new ExcepcionLoxicaPartida("A casilla "+casillaPropiedade.getNombre()+" xa está hipotecada.");
        }
        if (casillaPropiedade instanceof Solar casaux) {
            if(casaux.getNumCasas()>0){
                throw new ExcepcionLoxicaPartida("Esta propiedade ten casas construídas, debes vendelas antes de hipotecar.");
            }
            if(casaux.tenEdificio()){
                throw new ExcepcionLoxicaPartida("Esta propiedade ten edificios construídos, debes vendelos antes de hipotecar.");
            }
            if(casaux.getGrupo().haiEdificiosNoGrupo()){
                throw new ExcepcionLoxicaPartida("Non podes hipotecar esta propiedade porque hai edificios construídos noutro solar do mesmo grupo "+Valor.getNombreColor(casaux.getGrupo().getColorGrupo())+".");
            }
        }
        casillaPropiedade.hipotecarCasilla();
    }   

    @Override
    public void deshipotecar(String[] partes) throws ExcepcionNoExiste, ExcepcionLoxicaPartida, ExcepcionSinCartos {
        Jugador jugadorActual= jugadores.get(turno);
        Casilla casillaDeshipotecar = tablero.encontrar_casilla(partes[1]);    
        if(casillaDeshipotecar==null){
            throw new ExcepcionNoExiste("Non existe ningunha casilla co nome."+ partes[1]);
        }

        if(casillaDeshipotecar.getDuenho() != jugadorActual){
            throw new ExcepcionLoxicaPartida("A casilla "+casillaDeshipotecar.getNombre()+" non é da túa propiedade, non a podes deshipotecar.");
        }
        if (!(casillaDeshipotecar instanceof Propiedad)) {
            throw new ExcepcionLoxicaPartida("Só podes deshipotecar propiedades.");
        }
        Propiedad casdeship = (Propiedad) casillaDeshipotecar;
        
        if(casdeship.getHipotecada()==false){
            throw new ExcepcionLoxicaPartida("A casilla "+casdeship.getNombre()+" non está hipotecada.");
        }
        casdeship.deshipotecarCasilla();
    }


    private boolean esFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }   

    @Override
    public void tratos(String[] partes) throws ExcepcionNoExiste, ExcepcionLoxicaPartida, ExcepcionValorInvalido {
        Jugador solicitante = jugadores.get(turno);
        Jugador receptor = null;
        Propiedad propiedadOfrecida= null;
        float dineroOfrecido=0;
        Propiedad propiedadSolicitada= null;
        float dineroSolicitado=0;
        int caso = 0;
        String nombreReceptor = partes[1].split(":")[0];
        for(Jugador aux : jugadores){
            if(aux.getNombre().equalsIgnoreCase(nombreReceptor)){
            receptor = aux;
            break;
            }
        }
        if(receptor == null){
            throw new ExcepcionNoExiste("Non existe ningún xogador co nome " + nombreReceptor + ".");
        }
        if(receptor == solicitante){
            throw new ExcepcionLoxicaPartida("Non te podes facer un trato a ti mesmo.");
        }
        if(partes.length==7){
            if(partes[4].equalsIgnoreCase("y")){ //Trato do estilo (Casilla e diñeiro, Casilla)
                caso = 5;
                String argumento1 = partes[3].replace("(", "").trim(); //Aquí eliminamos el paréntesis de apertura
                propiedadOfrecida = (Propiedad) tablero.encontrar_casilla(argumento1);          

                String argumento2 = partes[5].replace(",", "").trim(); //Aquí eliminamos a coma de despois da cantidade de diñeiro ofrecida
                if(!esFloat(argumento2)){
                    throw new ExcepcionValorInvalido("O diñeiro ofrecido non é válido.");
                }
                dineroOfrecido = Float.parseFloat(argumento2);

                String argumento3 = partes[6].replace(")", "").trim(); //Aquí eliminamos o paréntese de peche
                propiedadSolicitada = (Propiedad) tablero.encontrar_casilla(argumento3);

            }
            else if(partes[5].equalsIgnoreCase("y")){ //Trato do estilo (Casilla, Casilla e diñeiro)
                caso = 4;
                String argumento1 = partes[3].replace("(", "").replace(",", "").trim(); //Aquí eliminamos el paréntesis de apertura e a coma despois da casilla ofrecida
                propiedadOfrecida = (Propiedad) tablero.encontrar_casilla(argumento1);          

                propiedadSolicitada = (Propiedad) tablero.encontrar_casilla(partes[4]);

                String argumento3 = partes[6].replace(")", "").trim(); //Aquí eliminamos o paréntese de peche
                if(!esFloat(argumento3)){
                    throw new ExcepcionValorInvalido("O diñeiro solicitado non é válido.");
                }
                dineroSolicitado = Float.parseFloat(argumento3);
            }
        }
        else{
            String argumento1 = partes[3].replace("(", "").replace(",", "").trim(); //Aquí eliminamos el paréntesis de apertura e a coma despois
            String argumento2 = partes[4].replace(")", "").trim(); //Aquí eliminamos o paréntese de peche
            if(!esFloat(argumento1)&&!esFloat(argumento2)){
                caso = 1; //Trato do estilo (Casilla, Casilla)
                propiedadOfrecida = (Propiedad) tablero.encontrar_casilla(argumento1);
                propiedadSolicitada = (Propiedad) tablero.encontrar_casilla(argumento2);
            }
            else if(esFloat(argumento1)){
                caso = 3; //Trato do estilo (Diñeiro, Casilla)
                dineroOfrecido = Float.parseFloat(argumento1);
                propiedadSolicitada = (Propiedad) tablero.encontrar_casilla(argumento2);
            }
            else if(esFloat(argumento2)){
                caso = 2; //Trato do estilo (Casilla, Diñeiro)
                propiedadOfrecida = (Propiedad) tablero.encontrar_casilla(argumento1);
                dineroSolicitado = Float.parseFloat(argumento2);
            }
            else{
                throw new ExcepcionValorInvalido("Os argumentos do trato non son válidos.");
            }
        }


        if(caso==1||caso==2||caso==4||caso==5){
            if(propiedadOfrecida == null){
                throw new ExcepcionNoExiste("A propiedade ofrecida non existe.");
            }
        }

        if(caso==1||caso==3||caso==4||caso==5){
            if(propiedadSolicitada == null){
                throw new ExcepcionNoExiste("A propiedade solicitada non existe.");
            }
        }

        Tratos nuevoTrato = new Tratos(solicitante, receptor, propiedadOfrecida, dineroOfrecido, propiedadSolicitada, dineroSolicitado, caso);
        //tratos é un hashmap onde a clave é o id do trato e o valor é un arraylst de tratos
        tratos.computeIfAbsent(receptor, k -> new ArrayList<>()).add(nuevoTrato);
    }

    @Override
    public void aceptarTrato(String idTrato) throws ExcepcionNoExiste, ExcepcionLoxicaPartida, ExcepcionValorInvalido {
        Jugador receptor = jugadores.get(turno);
        boolean existeTrato = false;
        Tratos trato=null;
        if(!tratos.containsKey(receptor) || tratos.get(receptor).isEmpty()){
            throw new ExcepcionNoExiste("Non tes ningún trato pendente.");
        }
        for(Tratos t : tratos.get(receptor)){
            if(t.getId().equalsIgnoreCase(idTrato)){
                existeTrato = true;
                trato = t;
                break;
            }
        }
        if(!existeTrato){
            throw new ExcepcionNoExiste("Non existe ningún trato co ID " + idTrato + " para ti.");
        }


        if(trato.getTipoTrato()==1||trato.getTipoTrato()==2||trato.getTipoTrato()==4||trato.getTipoTrato()==5){
            if(trato.getPropiedadOfrecida().getDuenho() != trato.getOfertante()){
                throw new ExcepcionLoxicaPartida("Non podes aceptar este trato porque a propiedade " + trato.getPropiedadOfrecida().getNombre() + " xa non pertence ao ofertante.");
            }   
            if(trato.getPropiedadOfrecida().getHipotecada()){
                throw new ExcepcionLoxicaPartida("Non podes aceptar este trato porque a propiedade " + trato.getPropiedadOfrecida().getNombre() + " está hipotecada.");
            }
            if(trato.getPropiedadOfrecida() instanceof Solar solarOfrecida){
                if(solarOfrecida.tenEdificio()){
                    throw new ExcepcionLoxicaPartida("Non podes aceptar este trato porque a propiedade " + trato.getPropiedadOfrecida().getNombre() + " ten edificios construídos.");
                }
                if(solarOfrecida.getGrupo().haiEdificiosNoGrupo()){
                    throw new ExcepcionLoxicaPartida("Non podes aceptar este trato porque hai edificios construídos noutro solar do mesmo grupo que " + trato.getPropiedadOfrecida().getNombre() + ", o grupo "+Valor.getNombreColor(solarOfrecida.getGrupo().getColorGrupo())+".");
                }
            }
        }


        if(trato.getTipoTrato()==1||trato.getTipoTrato()==3||trato.getTipoTrato()==4||trato.getTipoTrato()==5){
            if(trato.getPropiedadSolicitada().getDuenho() != receptor){
                throw new ExcepcionLoxicaPartida("Non podes aceptar este trato porque a propiedade " + trato.getPropiedadSolicitada().getNombre() + " xa non che pertence.");
            }
            if(trato.getPropiedadSolicitada().getHipotecada()){
                throw new ExcepcionLoxicaPartida("Non podes aceptar este trato porque a propiedade " + trato.getPropiedadSolicitada().getNombre() + " está hipotecada.");
            }
            if(trato.getPropiedadSolicitada() instanceof Solar solarSolicitada){
                if(solarSolicitada.tenEdificio()){
                    throw new ExcepcionLoxicaPartida("Non podes aceptar este trato porque a propiedade " + trato.getPropiedadSolicitada().getNombre() + " ten edificios construídos.");
                }
                if(solarSolicitada.getGrupo().haiEdificiosNoGrupo()){
                    throw new ExcepcionLoxicaPartida("Non podes aceptar este trato porque hai edificios construídos noutro solar do mesmo grupo que " + trato.getPropiedadSolicitada().getNombre() + ", o grupo "+Valor.getNombreColor(solarSolicitada.getGrupo().getColorGrupo())+".");
                }
            }
            

        }

        if(trato.getTipoTrato()==2||trato.getTipoTrato()==4){
            if(trato.getDineroSolicitado() > receptor.getFortuna()){
                throw new ExcepcionSinCartos(receptor.getNombre(), (int)receptor.getFortuna(), (int)trato.getDineroSolicitado());
            }
        }

        if(trato.getTipoTrato()==3||trato.getTipoTrato()==5){
            if(trato.getDineroOfrecido() > trato.getOfertante().getFortuna()){
                throw new ExcepcionSinCartos(trato.getOfertante().getNombre(), (int)trato.getOfertante().getFortuna(), (int)trato.getDineroOfrecido()); 
            }
        }

        Jugador solicitante = trato.getOfertante();
        Propiedad propiedadOfrecida = trato.getPropiedadOfrecida();
        Propiedad propiedadSolicitada = trato.getPropiedadSolicitada();
        float dineroOfrecido = trato.getDineroOfrecido();
        float dineroSolicitado = trato.getDineroSolicitado();
        int tipoTrato = trato.getTipoTrato();
        switch(tipoTrato){
            case 1: //Casilla por casilla
                propiedadOfrecida.intercambiarDuenho(receptor, solicitante);
                propiedadSolicitada.intercambiarDuenho(solicitante, receptor);
                consol.imprimir("Trato realizado!!: " + solicitante.getNombre() + " intercambia " + propiedadOfrecida.getNombre() + " por " + propiedadSolicitada.getNombre() + " con " + receptor.getNombre() + ".");
                break;
            case 2: //Casilla por diñeiro
                propiedadOfrecida.intercambiarDuenho(receptor, solicitante);
                solicitante.sumarFortuna(dineroSolicitado);
                receptor.sumarFortuna(-dineroSolicitado);
                consol.imprimir("Trato realizado!!: " + solicitante.getNombre() + " vende " + propiedadOfrecida.getNombre() + " por " + dineroSolicitado + "€ a " + receptor.getNombre() + ".");
                break;
            case 3: //Diñeiro por casilla
                propiedadSolicitada.intercambiarDuenho(solicitante, receptor);
                receptor.sumarFortuna(dineroOfrecido);
                solicitante.sumarFortuna(-dineroOfrecido);
                consol.imprimir("Trato realizado!!: " + receptor.getNombre() + " vende " + propiedadSolicitada.getNombre() + " por " + dineroOfrecido + "€ a " + solicitante.getNombre() + ".");
                break;
            case 4: //Casilla por casilla e diñeiro
                propiedadOfrecida.intercambiarDuenho(receptor, solicitante);
                propiedadSolicitada.intercambiarDuenho(solicitante, receptor);
                receptor.sumarFortuna(-dineroSolicitado);
                solicitante.sumarFortuna(dineroSolicitado);
                consol.imprimir("Trato realizado!!: " + solicitante.getNombre() + " intercambia " + propiedadOfrecida.getNombre() + " por "+dineroSolicitado+"$ e " + propiedadSolicitada.getNombre() + " con " + receptor.getNombre() + ".");
                break;
            case 5: //Casilla e diñeiro por casilla
                propiedadOfrecida.intercambiarDuenho(receptor, solicitante);
                propiedadSolicitada.intercambiarDuenho(solicitante, receptor);
                solicitante.sumarFortuna(-dineroOfrecido);
                receptor.sumarFortuna(dineroOfrecido);
                consol.imprimir("Trato realizado!!: " + solicitante.getNombre() + " intercambia " + propiedadOfrecida.getNombre() + " e "+dineroOfrecido+"$ por " + propiedadSolicitada.getNombre() + " con " + receptor.getNombre() + ".");
                break;
        }
        tratos.get(receptor).remove(trato);
    }

    @Override
    public void eliminarTrato(String idTrato) throws ExcepcionNoExiste {
        Jugador jugadorActual = jugadores.get(turno);
        boolean existeTrato = false;
        Tratos trato=null;
        //Quero facer que itere por todos os tratos que EFECTUOU o xogador actual, non por todos os tratos que ten o xogador actual
        //Pa eso, facemos:
        if(tratos.get(jugadorActual)==null || tratos.get(jugadorActual).isEmpty()){
            throw new ExcepcionNoExiste("Non tes ningún trato efectuado pendente.");
        }
        for(Jugador j: jugadores){
            if(j == jugadorActual){
                continue;
            }
            for(Tratos t : tratos.get(j)){
                if(t.getId().equalsIgnoreCase(idTrato)){
                    existeTrato = true;
                    trato = t;
                    break;
                }
            }
        }

        if(!existeTrato){
            throw new ExcepcionNoExiste("Non existe ningún trato co ID " + idTrato + " efectuado por ti.");
        }


        tratos.get(trato.getReceptor()).remove(trato);
        consol.imprimir("Trato eliminado correctamente.");
    }

    @Override
    public void verTratos(){
        Jugador jugadorActual = jugadores.get(turno);
        if(tratos.get(jugadorActual)==null || tratos.get(jugadorActual).isEmpty()){
            consol.imprimir("Non tes ningún trato pendente.");
            return;
        }
        for(Tratos t : tratos.get(jugadorActual)){
            consol.imprimir(t.toString());
        }
    }

    
    // Metodo que realiza las acciones asociadas al comando 'acabar turno'.
    @Override
    public void acabarTurno() throws ExcepcionLoxicaPartida, ExcepcionSinCartos {
        //Comprobar que a partida está empezada
        if(!tirado){
            throw new ExcepcionLoxicaPartida("Non podes acabar o teu turno sen tirar os dados.");
        }
        Jugador jugadorActual= jugadores.get(turno);
        if(jugadorActual.getEnCarcel() && jugadorActual.getTiradasCarcel()==3){
            if(jugadorActual.getFortuna()<500000){
                solvente=false;
                throw new ExcepcionSinCartos(jugadorActual.getNombre(), (int)jugadorActual.getFortuna(), 500000);
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
            if(!primeiraBancarrota){
                solvente = jugadorActual.getAvatar().getLugar().EvaluarCasilla(jugadorActual,banca,0, getTablero().getPosiciones());
            }
            if(solvente){
                consol.imprimir("\nXa pagaches as túas débedas, podes seguir xogando normalmente.\n");
                primeiraBancarrota=true;
                tirado=false;
                lanzamientos=0;
                turno++;
                if(turno==jugadores.size()){
                    turno=0;
                }
                return;
            }
            Jugador Recaudador = jugadorActual.getAvatar().getLugar().getDuenho();
            consol.imprimir("\nTes débedas pendentes sen pagar");
            String resposta = consol.leer("Queres rendirte? (Si, Non)\n");
            if(resposta.equalsIgnoreCase("Non")){
                if(primeiraBancarrota){
                    primeiraBancarrota=false;
                    consol.imprimir("\nLembrámosche que podes obter cartos vendendo edificios ou hipotecando propiedades.\n");
                    return;
                }else{
                    consol.imprimir("\nXa te avisamos unha vez, non podes seguir xogando sen pagar as túas débedas.\n");
                    return;
                }
                
            }if(resposta.equalsIgnoreCase("Si")) {
                bancarrota(jugadorActual, Recaudador);
            }
            //solvente = jugadorActual.getAvatar().getLugar().EvaluarCasilla(jugadorActual,banca,0, getTablero().getPosiciones()); <-non ten sentido facer aquí solvente, xa se fai cando tiras os dados.
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
                consol.imprimir("\nO xogador endeudado non ten propiedades...");
            }

    }@Override
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

                consol.imprimir(Valor.BLUE+"-Executando desde arquivo: "+Valor.RESET + comando);

                try {
                    analizarComando(comando);  // ← pode fallar
                } catch (Exception eComando) {
                    consol.imprimir(eComando.getMessage());
                    // SEGUIMOS co seguinte comando
                }

            }
        }

    } catch (java.io.FileNotFoundException e) {
        consol.imprimir("Non se atopou o arquivo: " + nomeArquivo);
    } catch (Exception e) {
        consol.imprimir("Error ao ler o arquivo: " + e.getMessage());
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
        consol.imprimir("}");
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
            consol.imprimir("\n -*Ningunha propiedade foi comprada aínda.\n");
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
            consol.imprimir(" -*O grupo máis rentable é: "+ Valor.getNombreColor(grupomax.getColorGrupo()));
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
            consol.imprimir(" -*Xogador con máis voltas: "+voltasmax.getNombre()+".\n");
        }else{
            consol.imprimir(" -*Ningún xogador ten voltas todavía.\n");
        }

        consol.imprimir(" -*Xogador en cabeza: "+fortunamax.getNombre()+", con unha fortuna de "+fortunamax.getFortuna()+" €.\n");

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
    public void estadisticasXogador(String[] comandos) throws ExcepcionNoExiste {
        for(Jugador aux: jugadores){
            if(aux.getNombre().equalsIgnoreCase(comandos[1])){
                consol.imprimir(aux.getEstatisticas().toString());
                return;
            }
        }
        throw new ExcepcionNoExiste("Non existe ningún xogador co nome "+comandos[1]+".");
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
