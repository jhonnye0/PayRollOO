import empresa.funcionarios.*;
import empresa.agendas.*;
import empresa.sindicato.*;
import empresa.funcionarios.comissoes.*;
import empresa.funcionarios.horista.*;
import façade.Acesso;
import undoRedo.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        Acesso acess = new Acesso();
        Time time = new Time();
        acess.acess(time);

        System.out.println("Obrigado por utilizar nossos servicos!!");
    }
}
