package exception;

public class Excepcion extends Exception {
    public Excepcion(){
        super("\u001B[31m" + "Erro xeral no Monopoly" + "\u001B[0m");
    }
    
    public Excepcion(String mensaje){
        super("\u001B[31m" + mensaje + "\u001B[0m");
    }
}