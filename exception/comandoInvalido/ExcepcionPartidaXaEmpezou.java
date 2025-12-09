package exception.comandoInvalido;
import exception.ExcepcionLoxicaPartida;

public class ExcepcionPartidaXaEmpezou extends ExcepcionLoxicaPartida {
    public ExcepcionPartidaXaEmpezou(){
        super();
    }
    
    public ExcepcionPartidaXaEmpezou(String mensaje){
        super("A partida xa comezou, "+mensaje);
    }
    
}