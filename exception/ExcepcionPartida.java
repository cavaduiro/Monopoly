package exception;

public class ExcepcionPartida extends Excepcion {
    public ExcepcionPartida(){
        super();
    }
    
    public ExcepcionPartida(String mensaje){
       super("\033[1mO estado da partida non acepta o comando");
    }
    
}
