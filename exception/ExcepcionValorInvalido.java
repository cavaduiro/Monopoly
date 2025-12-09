package exception;

import monopoly.Valor;

public class ExcepcionValorInvalido extends Excepcion {
    public ExcepcionValorInvalido(){
        super();
    }
    
    public ExcepcionValorInvalido(String mensaje){
        super(Valor.RED+"Valor inválido: "+Valor.RESET+Valor.NEGRITA+mensaje+Valor.RESET);
    }
    
}