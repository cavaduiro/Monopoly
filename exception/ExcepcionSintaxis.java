package exception;
public class ExcepcionSintaxis extends Excepcion {
    public ExcepcionSintaxis(){
        super();
    }
    
    public ExcepcionSintaxis(String mensaje){
       super("\033[1mComando incorrecto: \033[0m" + mensaje);
    }
}