package exception;
import monopoly.Valor;

public class ExcepcionLoxicaPartida extends Excepcion {
    public ExcepcionLoxicaPartida(){
        super();
    }
    
    public ExcepcionLoxicaPartida(String mensaje){
       super(Valor.RED + "Lóxica inválida: "+Valor.RESET+Valor.NEGRITA+mensaje + Valor.RESET);
    }
    
}
