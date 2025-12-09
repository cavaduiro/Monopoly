package exception.comandoInvalido;
import exception.ExcepcionLoxicaPartida;

public class ExcepcionPartidaNonEmpezou extends ExcepcionLoxicaPartida {
    public ExcepcionPartidaNonEmpezou(){
        super();
    }
    
    public ExcepcionPartidaNonEmpezou(String mensaje) {
        super("A partida non comezou, "+mensaje);
    }
    
}