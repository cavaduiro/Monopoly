package monopoly;

import java.util.ArrayList;
import casillas.*;

import partida.Jugador;

public class Comunidade extends Carta {


    private static Comunidade instancia = null;
    private int index = 0;
    @Override
    public void sumar() {
        this.index++;
        this.index = this.index % 6;
    }
    

    
    @Override
    public void loxica(Jugador banca, Jugador actual, ArrayList<ArrayList<Casilla>> pos) {

        switch (this.index) {
            case 0:
                this.sumar();
                Juego.consol
                        .imprimir("Paga unha multa de 500000 euros por un fin de semana nun balneario de 5 estrelas");
                //CHEQUEAR FORTUNA, SE NON TEN, DEBERÍA MORRER
                actual.sumarFortuna(-500000);
                break;
            case 1:
                this.sumar();
                Juego.consol.imprimir("Vas á cárcel sin pasar por casilla de salida e sin cobrar");
                actual.encarcelar(pos);
                //actual.getEstatisticas().sumarCarcel();
                break;
            case 2:
                this.sumar();
                //Colócate en casilla de salida cobrando 200000
                Juego.consol.imprimir("Vas á casilla de saída, cobrando 2000000 euros.");
                ;
                Casilla casactual = actual.getAvatar().getLugar();
                casactual.eliminarAvatar(actual.getAvatar());
                Casilla dest = actual.getAvatar().posIndex(0, pos);
                dest.anhadirAvatar(actual.getAvatar());
                actual.getEstatisticas().sumarVoltas();
                actual.sumarFortuna(Valor.SUMA_VUELTA);
                actual.getEstatisticas().sumarsalidas();
                actual.getAvatar().setLugar(dest);
                break;
            case 3:
                this.sumar();
                ;
                Juego.consol.imprimir("Devolución de Facenda, recibes 500000");
                actual.sumarFortuna(500000);
                actual.getEstatisticas().sumarbote(500000);
                break;
            case 4:
                this.sumar();
                Juego.consol.imprimir("Retrocede a Solar1");
                casactual = actual.getAvatar().getLugar();
                casactual.eliminarAvatar(actual.getAvatar());
                dest = actual.getAvatar().posIndex(1, pos);
                dest.anhadirAvatar(actual.getAvatar());
                actual.getAvatar().setLugar(dest);
                dest.EvaluarCasilla(actual, banca, 0, pos);
                break;
            case 5:
                this.sumar();
                Juego.consol.imprimir("Vas ó Solar20");
                casactual = actual.getAvatar().getLugar();
                casactual.eliminarAvatar(actual.getAvatar());
                dest = actual.getAvatar().posIndex(34, pos);
                dest.anhadirAvatar(actual.getAvatar());
                actual.getAvatar().setLugar(dest);
                dest.EvaluarCasilla(actual, banca, 0, pos);
                break;
        }
    }
    
    public static synchronized Comunidade getInstance()
    {
        if (instancia == null)
            instancia = new Comunidade();

        return instancia;
    }
}
