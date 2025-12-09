package exception.comandoInvalido;
import exception.ExcepcionLoxicaPartida;

public class ExcepcionXaTirachesDados extends ExcepcionLoxicaPartida {
    public ExcepcionXaTirachesDados(){
        super();
    }
    
    public ExcepcionXaTirachesDados(String mensaje){
        super("Xa tiraches os dados");
    }
    
}
