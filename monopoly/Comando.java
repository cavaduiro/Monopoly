package monopoly;

import exception.*;
import exception.valorInvalido.ExcepcionOOR;

public interface  Comando {
    public void crearxogador(String[] partes)throws ExcepcionPartida, ExcepcionSintaxis, ExcepcionOOR, ExcepcionValorInvalido;
    public void describir(String[] partes)throws ExcepcionSintaxis, ExcepcionPartida, ExcepcionValorInvalido, ExcepcionNoExiste;
    public void xogadorturno();

    public void listar(String[] partes)  throws ExcepcionSintaxis;
    public void iniciarPartida();
    public void lanzarDados(String[] partes) throws ExcepcionPartida;
    public void salirCarcelPagando();
    public void salirCarcel();
    public void acabarTurno();
    public void comprar(String partes);
    public void leerArquivo(String partes);
    public void estadisticasPartida();
    public void estadisticasXogador(String[] partes)throws ExcepcionPartida;
    public void edificar(String[] partes);
    public void vender(String[] partes);
    public void hipotecar(String[] partes);
    public void deshipotecar(String[] partes);
    public void tratos(String[] partes);
    public void aceptarTrato(String idTrato);
    public void eliminarTrato(String idTrato);
    public void verTratos();

    public void mostrarAxuda();





}
