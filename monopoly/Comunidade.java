package monopoly;

import java.util.ArrayList;

import partida.Jugador;

public class Comunidade extends Carta {



    private int index = 0;
    @Override
    public void sumar(){    
        this.index++;
        this.index = this.index%6;
    }
    @Override
    public void loxica(Jugador banca,Jugador actual, ArrayList<ArrayList<Casilla>> pos){

        switch(banca.getIndexcom()){
            case 0:
                this.sumar();
                System.out.println("\nPaga unha multa de 500000 euros por un fin de semana nun balneario de 5 estrelas\n");
                actual.sumarFortuna(-500000);
                break;
            case 1:
                this.sumar();
                System.out.println("\nVas á cárcel sin pasar por casilla de salida e sin cobrar\n");
                actual.encarcelar(pos);
                //actual.getEstatisticas().sumarCarcel();
                break;
            case 2:
                this.sumar();
                //Colócate en casilla de salida cobrando 200000
                System.out.println("\nVas á casilla de saída, cobrando 2000000 euros.\n");
                Casilla casactual=actual.getAvatar().getLugar();
                casactual.eliminarAvatar(actual.getAvatar());
                Casilla dest = actual.getAvatar().posIndex(0,pos);
                dest.anhadirAvatar(actual.getAvatar());
                actual.getEstatisticas().sumarVoltas();
                actual.sumarFortuna(Valor.SUMA_VUELTA);
                actual.getEstatisticas().sumarsalidas();
                actual.getAvatar().setLugar(dest);
                break;
            case 3:
                this.sumar();;
                System.out.println("Devolución de Facenda, recibes 500000)\n");
                actual.sumarFortuna(500000);
                actual.getEstatisticas().sumarbote(500000);
                break;
            case 4:
                this.sumar();
                System.out.println("Retrocede a Solar1\n");
                casactual=actual.getAvatar().getLugar();
                casactual.eliminarAvatar(actual.getAvatar());
                dest= actual.getAvatar().posIndex(1,pos);
                dest.anhadirAvatar(actual.getAvatar());
                actual.getAvatar().setLugar(dest);
                dest.evaluarCasilla(actual,banca,0,pos);
                break;
            case 5:
                this.sumar();;
                System.out.println("Vas ó Solar20\n");
                casactual=actual.getAvatar().getLugar();
                casactual.eliminarAvatar(actual.getAvatar());
                dest= actual.getAvatar().posIndex(34,pos);
                dest.anhadirAvatar(actual.getAvatar());
                actual.getAvatar().setLugar(dest);
                dest.evaluarCasilla(actual, banca, caidas, pos);
                break;
        }
    }
}
