package exception;
public class ExcepcionValorInvalido extends Excepcion {
    public ExcepcionValorInvalido(){
        super();
    }
    
    public ExcepcionValorInvalido(String mensaje){
       super("\033[1mValor inválido: \033[0m" + mensaje);
    }
    
}