package monopoly;

public interface  Comando {
    public void crearxogador(String[] partes);
    public void describir(String[] partes);
    public void xogadorturno();
    public void listar(String[] partes);
    public void iniciarPartida();
    public void lanzarDados(String[] partes);
    public void salirCarcelPagando();
    public void salirCarcel();
    public void acabarTurno();
    public void comprar(String partes);
    public void leerArquivo(String partes);
    public void estadisticasPartida();
    public void estadisticasXogador(String[] partes);
    public void edificar(String[] partes);
    public void vender(String[] partes);
    public void hipotecar(String[] partes);
    public void deshipotecar(String[] partes);
    public void tratos(String[] partes);
    public void aceptarTrato(String idTrato);
    public void mostrarAxuda();





}
