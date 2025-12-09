package exception;
import monopoly.Valor;

public class ExcepcionNoExiste extends Excepcion {
    public ExcepcionNoExiste(){
        super();
    }
    
    public ExcepcionNoExiste(String mensaje){
        super(Valor.RED+"Non existe: "+Valor.RESET+Valor.NEGRITA+mensaje+Valor.RESET);
    }
    
}
