package exception;

import monopoly.Valor;

public class ExcepcionSintaxis extends Excepcion {
    public ExcepcionSintaxis(){
        super();
    }
    
    public ExcepcionSintaxis(String mensaje){
        super(Valor.RED+"Sintaxis incorrecta: "+Valor.RESET+Valor.NEGRITA+mensaje+Valor.RESET);
    }
}