package partida;
import java.lang.Math;

public class Dado {
    //El dado solo tiene un atributo en nuestro caso: su valor.
    private int valor;

    //Metodo para simular lanzamiento de un dado: devolverá un valor aleatorio entre 1 y 6.
    //Cada dado ten valores distintos, xa que senón non poderíamos mirar cando salen os dados dobles.
    public int hacerTirada() {
        return (int)((Math.random()*6+1));
    }

}
