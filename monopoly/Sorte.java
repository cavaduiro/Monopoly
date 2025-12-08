package monopoly;

import casillas.*;
import java.util.ArrayList;
import partida.Avatar;
import partida.Jugador;

public class Sorte extends Carta {

    private static Sorte instancia = null;
    private int index = 0;
    @Override
    public void sumar(){    
        this.index++;
        this.index = this.index%7;
    }
    @Override
    public boolean loxica(Jugador banca, Jugador actual, ArrayList<ArrayList<Casilla>> pos) {

        switch (this.index) {
            case 0:
                //Moverse a la casilla solar 19
                Juego.consol.imprimir("Oh no excursión á solar 19");
                Casilla casactual = actual.getAvatar().getLugar();
                if (casactual.getPosicion() == 36) {
                    actual.sumarFortuna(Valor.SUMA_VUELTA);
                    actual.getEstatisticas().sumarsalidas();
                    actual.getEstatisticas().sumarVoltas();
                }
                casactual.eliminarAvatar(actual.getAvatar());
                Casilla dest = actual.getAvatar().posIndex(32, pos);
                dest.anhadirAvatar(actual.getAvatar());
                actual.getAvatar().setLugar(dest);
                dest.EvaluarCasilla(actual, banca, 0, pos);
                break;
            case 1:
                Juego.consol.imprimir("Vai o cárcere, sen pasar pola casilla de saída.");
                if (actual.getAvatar().getLugar().getPosicion() != 7) {
                    actual.getEstatisticas().sumarVoltas();
                }
                actual.encarcelar(pos);
                break;
            case 2:
                Juego.consol.imprimir("Recibes 1.000.000! Lucky you...");
                //Recibir 1.000.000
                actual.sumarFortuna(1000000);
                actual.getEstatisticas().sumarbote(1000000);
                break;
            case 3:
                Juego.consol.imprimir("Paga a cada xogador 250.000€");
                ;
                //Paga a cada xogador 250.000
                float bote = 250000;
                ArrayList<Jugador> auxJugadores = new ArrayList<>();
                for (ArrayList<Casilla> lado : pos) {
                    for (Casilla cas : lado) {
                        for (Avatar av : cas.getAvatares()) {
                            if (!av.getJugador().equals(actual)) {
                                auxJugadores.add(av.getJugador());
                            }
                        }
                    }
                }
                int numJugadores = auxJugadores.size();
                float totalPagar = bote * numJugadores;
                if (actual.getFortuna() < totalPagar) {
                    Juego.consol.imprimir("O xogador " + actual.getNombre() + " non pode pagar o bote total de "
                            + totalPagar + ", xa que só ten " + actual.getFortuna() + ".");
                    return false;
                } else {
                    for (Jugador aux : auxJugadores) {
                        actual.getEstatisticas().acImpPagado(bote);
                        actual.sumarFortuna(-bote);
                        aux.sumarFortuna(bote);
                        aux.getEstatisticas().sumarbote(bote);
                    }
                }
                break;
            case 4:
                Juego.consol.imprimir("Oh no! Retrocedes tres casillas");
                //Retroceder tres casillas
                casactual = actual.getAvatar().getLugar();
                casactual.eliminarAvatar(actual.getAvatar());
                int posdest = casactual.getPosicion() - 3;
                dest = actual.getAvatar().posIndex(posdest, pos);
                dest.anhadirAvatar(actual.getAvatar());
                actual.getAvatar().setLugar(dest);
                dest.EvaluarCasilla(actual, banca, 0, pos);
                break;
            case 5:
                Juego.consol.imprimir("Múltanche por usar o teléfono mentres conduces, paga 150000 euros");
                //Múltanche por usar o tlf mentres conduces, paga 150000
                if (actual.getFortuna() < 150000) {
                    Juego.consol.imprimir("O xogador " + actual.getNombre() + " non pode pagar a multa de 150000, xa que só ten "
                            + actual.getFortuna() + ".");
                    return false;
                } else {
                    actual.sumarFortuna(-150000);
                    actual.getEstatisticas().acImpPagado(150000);
                    Juego.consol.imprimir("O xogador " + actual.getNombre() + " pagou a multa de 150000 euros.");
                    break;
                }

            case 6:
                Juego.consol.imprimir("Avanza cara a casilla máis cercana (de transporte)");
                //Avanza ata a casilla de transporte máis cercana, se non ten dono podes mercala. Se o ten, pagas o doble do habitual
                boolean iterado = false;
                //Casilla destino = actual.getAvatar().getLugar();
                for (int novacas : Valor.transportes) {
                    if (actual.getAvatar().getLugar().getPosicion() < novacas && !iterado) {
                        casactual = actual.getAvatar().getLugar();
                        casactual.eliminarAvatar(actual.getAvatar());
                        Casilla destino = actual.getAvatar().posIndex(novacas, pos);
                        destino.anhadirAvatar(actual.getAvatar());
                        actual.getAvatar().setLugar(destino);
                        iterado = true;
                        destino.EvaluarCasilla(actual, banca, 0, pos);
                        return true;
                    }
                }
                break;
            }
        this.sumar();
        return true;
    }
        public static synchronized Sorte getInstance()
    {
        if (instancia == null)
            instancia = new Sorte();

        return instancia;
    }
}
