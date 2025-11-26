package monopoly;

import java.util.Scanner;

public class Consola {
    public void imprimir(String mensaje){
        System.out.println("\n"+mensaje+"\n");
    }
    public String leer(String desc){
        Scanner scanner = new Scanner(System.in);
        String comando;
        System.out.println("\n"+desc);
        comando = scanner.nextLine();
        return comando;
    }
    public void impTablero(Tablero tab){
        System.out.println(tab);
    }
    public void cred(){
        System.out.println("\n/---------------/\n");
        System.out.println("Créditos");
    }
}
