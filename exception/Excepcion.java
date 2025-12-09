package exception;
import monopoly.Valor;

public class Excepcion extends Exception {
    public Excepcion(){
        super("\u001B[31m" + "Erro xeral no Monopoly" + "\u001B[0m");
    }
    
    public Excepcion(String mensaje){
        super(Valor.NEGRITA + mensaje + Valor.RESET);
    }
}