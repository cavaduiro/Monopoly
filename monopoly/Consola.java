package monopoly;

import java.util.Scanner;

public class Consola implements ConsolaInt{
    
    private static final Scanner scanner = new Scanner(System.in);
    @Override
    public void imprimir(String mensaje){
        System.out.println(mensaje);
    }
    @Override
    public String leer(String desc){
        String comando;
        System.out.println(desc);
        comando = scanner.nextLine();
        return comando;
    }
    public void impTablero(Tablero tab){
        System.out.println(tab);
    }
    @Override
    public void cred(){
        System.out.println("\n/---------------/\n");
        System.out.println("Créditos");
    }
}
