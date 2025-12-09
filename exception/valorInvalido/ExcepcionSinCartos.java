package exception.valorInvalido;

public class ExcepcionSinCartos extends Exception {
    public ExcepcionSinCartos(String nombreXogador, int cartosTen, int cartosNecesita) {
        super("O xogador " + nombreXogador + " non ten cartos suficientes: ten " + cartosTen + "$ e necesita " + cartosNecesita + ".");
    }
} 