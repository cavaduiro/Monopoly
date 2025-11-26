package exception;

public class ExepcionSintaxis extends Excepcion {
        
   
    public ExcepcionSintaxis(String mensaje) {
        super("\033[1mComando incorrecto: \033[0m" + mensaje);
    }
}