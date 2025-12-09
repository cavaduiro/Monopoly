package exception.valorInvalido;
import exception.ExcepcionValorInvalido;

public class ExcepcionSinCartos extends ExcepcionValorInvalido {
    public ExcepcionSinCartos(String nombreXogador, int cartosTen, int cartosNecesita) {
        super("O xogador " + nombreXogador + " non ten cartos suficientes: ten " + cartosTen + "$ e necesita " + cartosNecesita + ".");
    }
} 