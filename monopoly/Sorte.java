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
    public void loxica(Jugador banca,Jugador actual, ArrayList<ArrayList<Casillavella>> pos){

        switch(this.index){
            case 0:
                //Moverse a la casilla solar 19
                Juego.consol.imprimir("Oh no excursión á solar 19");
                Casillavella casactual=actual.getAvatar().getLugar();
                if(casactual.getPosicion()==36){
                    actual.sumarFortuna(Valor.SUMA_VUELTA);
                    actual.getEstatisticas().sumarsalidas();
                    actual.getEstatisticas().sumarVoltas();
                }
                casactual.eliminarAvatar(actual.getAvatar());
                Casillavella dest = actual.getAvatar().posIndex(32,pos);
                dest.anhadirAvatar(actual.getAvatar());
                actual.getAvatar().setLugar(dest);
                this.sumar();
                dest.evaluarCasilla(actual,banca,0,pos);
                break;
            case 1:
                Juego.consol.imprimir("Vai o cárcere, sen pasar pola casilla de saída.");
                if(actual.getAvatar().getLugar().getPosicion()!=7){
                    actual.getEstatisticas().sumarVoltas();
                }
                actual.encarcelar(pos);
                this.sumar();
                break;
            case 2:
                Juego.consol.imprimir("Recibes 1.000.000! Lucky you...");
                //Recibir 1.000.000
                actual.sumarFortuna(1000000);
                actual.getEstatisticas().sumarbote(1000000);
                this.sumar();
                break;
            case 3:
                Juego.consol.imprimir("Paga a cada xogador 250.000€");;
                //Paga a cada xogador 250.000
                float bote = 250000;
                for(ArrayList<Casillavella> lado: pos){
                    for(Casillavella cas: lado){
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
                Juego.consol.imprimir("Oh no! Retrocedes tres casillas");
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
                Juego.consol.imprimir("Múltanche por usar o teléfono mentres conduces, paga 150000 euros");
                //Múltanche por usar o tlf mentres conduces, paga 150000
                actual.sumarFortuna(-150000);
                banca.sumarFortuna(150000);
                actual.getEstatisticas().acImpPagado(150000);
                this.sumar();
                break;
            case 6:
                Juego.consol.imprimir("Avanza cara a casilla máis cercana (de transporte)");
                //Avanza ata a casilla de transporte máis cercana, se non ten dono podes mercala. Se o ten, pagas o doble do habitual
                boolean iterado= false;
                Casillavella destino = actual.getAvatar().getLugar();
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
