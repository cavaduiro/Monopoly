package monopoly;

import java.util.ArrayList;

import partida.Avatar;
import partida.Jugador;

public class Sorte extends Carta {

    private int index = 0;
    @Override
    public void sumar(){    
        this.index++;
        this.index = this.index%7;
    }
    @Override
    public void loxica(Jugador banca,Jugador actual, ArrayList<ArrayList<Casilla>> pos){

        switch(banca.getIndexsorte()){
            case 0:
                //Moverse a la casilla solar 19
                System.out.println("Oh no excursión á solar 19\n");
                Casilla casactual=actual.getAvatar().getLugar();
                if(casactual.getPosicion()==36){
                    actual.sumarFortuna(Valor.SUMA_VUELTA);
                    actual.getEstatisticas().sumarsalidas();
                    actual.getEstatisticas().sumarVoltas();
                }
                casactual.eliminarAvatar(actual.getAvatar());
                Casilla dest = actual.getAvatar().posIndex(32,pos);
                dest.anhadirAvatar(actual.getAvatar());
                actual.getAvatar().setLugar(dest);
                this.sumar();
                dest.evaluarCasilla(actual,banca,0,pos);
                break;
            case 1:
                System.out.println("\nVai o cárcere, sen pasar pola casilla de saída.\n");
                //actual.getEstatisticas().sumarCarcel();
                if(actual.getAvatar().getLugar().getPosicion()!=7){
                    actual.getEstatisticas().sumarVoltas();
                }
                actual.encarcelar(pos);
                this.sumar();
                break;
            case 2:
                System.out.println("\nRecibes 1.000.000! Lucky you...\n");
                //Recibir 1.000.000
                actual.sumarFortuna(1000000);
                actual.getEstatisticas().sumarbote(1000000);
                this.sumar();
                break;
            case 3:
                System.out.println("\nPaga a cada xogador 250.000€\n");
                //Paga a cada xogador 250.000
                float bote = 250000;
                for(ArrayList<Casilla> lado: pos){
                    for(Casilla cas: lado){
                        for(Avatar av: cas.getAvatares()){
                                if(!av.getJugador().equals(actual)){
                                    actual.getEstatisticas().acImpPagado(bote);
                                    actual.sumarFortuna(-bote);
                                    av.getJugador().sumarFortuna(bote);
                                    av.getJugador().getEstatisticas().sumarbote(bote);
                                }
                            }
                        }
                    }

                this.sumar();
                break;
            case 4:
                System.out.println("Oh no! Retrocedes tres casillas\n");
                //Retroceder tres casillas
                casactual=actual.getAvatar().getLugar();
                casactual.eliminarAvatar(actual.getAvatar());
                int posdest= casactual.getPosicion()-3;
                dest = actual.getAvatar().posIndex(posdest,pos);
                dest.anhadirAvatar(actual.getAvatar());
                actual.getAvatar().setLugar(dest);
                this.sumar();
                dest.evaluarCasilla(actual,banca,0,pos);
                break;
            case 5:
                System.out.println("Múltanche por usar o teléfono mentres conduces, paga 150000 euros\n");
                //Múltanche por usar o tlf mentres conduces, paga 150000
                actual.sumarFortuna(-150000);
                banca.sumarFortuna(150000);
                actual.getEstatisticas().acImpPagado(150000);
                this.sumar();
                break;
            case 6:
                System.out.println("Avanza cara a casilla máis cercana (de transporte)\n");
                //Avanza ata a casilla de transporte máis cercana, se non ten dono podes mercala. Se o ten, pagas o doble do habitual
                boolean iterado= false;
                Casilla destino = actual.getAvatar().getLugar();
                this.sumar();
                for(int novacas : Valor.transportes){
                    if(actual.getAvatar().getLugar().getPosicion()<novacas && !iterado){
                        casactual=actual.getAvatar().getLugar();
                        casactual.eliminarAvatar(actual.getAvatar());
                        destino = actual.getAvatar().posIndex(novacas,pos);  
                        destino.anhadirAvatar(actual.getAvatar());
                        actual.getAvatar().setLugar(destino);
                        iterado = true;
                        destino.evaluarCasilla(actual,banca,0,pos);
                        return;
                    }
                }
                break;
        }
    };
}
