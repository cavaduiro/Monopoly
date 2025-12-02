package exception;

public class ExcepcionNoExiste extends Excepcion {
    public ExcepcionNoExiste(){
        super();
    }
    
    public ExcepcionNoExiste(String mensaje){
       super("\033[1mNo existe: \033[0m" + mensaje);
    }
    
}
