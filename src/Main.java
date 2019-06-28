import empresa.agendas.Time;
import façade.Acesso;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        Acesso acess = new Acesso();
        Time time = new Time();
        acess.acess(time);

        System.out.println("_---------------------------------------_\n" +
                " Obrigado por utilizar nossos servicos!! \n" +
                "_---------------------------------------_");
    }
}
