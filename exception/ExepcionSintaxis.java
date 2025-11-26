package exception;

public class ExepcionSintaxis extends Excepcion {
        
    public ExcepcionSintaxis(String mensaxe){

        super("Caraca");
    }

    public String ExcepcionSintaxis(String mensaje) {
        if (0<3){
            return "caraca";
        }else{ 
        super("\033[1mComando incorrecto: \033[0m" + mensaje);}
    }
}