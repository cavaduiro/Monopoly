package monopoly;

import exception.*;
import exception.valorInvalido.ExcepcionOOR;
import exception.valorInvalido.ExcepcionSinCartos;
public interface  Comando {
    public void crearxogador(String[] partes)throws ExcepcionLoxicaPartida, ExcepcionSintaxis, ExcepcionOOR, ExcepcionValorInvalido;
    public void describir(String[] partes)throws ExcepcionSintaxis, ExcepcionLoxicaPartida, ExcepcionValorInvalido, ExcepcionNoExiste;
    public void xogadorturno();

    public void listar(String[] partes)  throws ExcepcionSintaxis, ExcepcionLoxicaPartida;
    public void iniciarPartida();
    public void lanzarDados(String[] partes) throws ExcepcionLoxicaPartida;
    public void salirCarcelPagando() throws ExcepcionLoxicaPartida, ExcepcionSinCartos;
    public void salirCarcel() throws ExcepcionLoxicaPartida;
    public void acabarTurno() throws ExcepcionLoxicaPartida, ExcepcionSinCartos;
    public void comprar(String partes) throws ExcepcionLoxicaPartida, ExcepcionNoExiste, ExcepcionValorInvalido;
    public void leerArquivo(String partes);
    public void estadisticasPartida();
    public void estadisticasXogador(String[] comandos) throws ExcepcionNoExiste;
    public void edificar(String[] partes) throws ExcepcionValorInvalido, ExcepcionLoxicaPartida, ExcepcionSinCartos;
    public void vender(String[] partes) throws ExcepcionValorInvalido, ExcepcionLoxicaPartida, ExcepcionNoExiste;
    public void hipotecar(String[] partes) throws ExcepcionNoExiste, ExcepcionLoxicaPartida;
    public void deshipotecar(String[] partes) throws ExcepcionNoExiste, ExcepcionLoxicaPartida, ExcepcionSinCartos;
    public void tratos(String[] partes) throws ExcepcionNoExiste, ExcepcionLoxicaPartida, ExcepcionValorInvalido;
    public void aceptarTrato(String idTrato) throws ExcepcionNoExiste, ExcepcionLoxicaPartida, ExcepcionValorInvalido;
    public void eliminarTrato(String idTrato) throws ExcepcionNoExiste;
    public void verTratos();

    public void mostrarAxuda();





}
