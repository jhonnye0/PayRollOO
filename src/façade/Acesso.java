package façade;

import empresa.agendas.*;
import empresa.funcionarios.Employee;
import empresa.sindicato.Union;
import undoRedo.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Acesso {

    private int total = 0;
    static Scanner input = new Scanner(System.in);
    private Admin adm = new Admin();
    private User user = new User();

    public void acess(Time time) {
        ArrayList<Employee> list = new ArrayList<>();
        ArrayList<Union> union = new ArrayList<>();
        ArrayList<Schedule> agendas = new ArrayList<>();
        Originator originator = new Originator(list, union);
        Originator reOriginator = new Originator(list, union);
        CareTaker unCareTaker = new CareTaker();
        ReCareTaker reCareTaker = new ReCareTaker();

        while(true)
        {
            System.out.println("\n----------------------------------\n");
            System.out.println("\nModo de acesso: (Digite o numero)\n" +
                    "0. Encerrar acesso\n" +
                    "1. Admin\n" +
                    "2. Empregado\n");

            int o = num012Check();

            switch (o)
            {
                case 0:
                    return;
                case 1:
                    setTotal(adm.admin(list, union, agendas, new Manager(),
                            time, total, originator, reOriginator,
                    unCareTaker, reCareTaker));
                    break;
                case 2:
                    System.out.println("Digite seu ID:");
                    int id = negativeNum(input.nextInt());
                    if(list.get(id) == null) {
                        System.out.println("Nao ha empregados registrados ainda!\n");
                        return;
                    }else {
                        user.user(list.get(id), time, new Manager(), agendas,originator,
                                reOriginator, unCareTaker, reCareTaker);
                    }
                    break;
            }
        }
    }

    private void setTotal(int total){
        this.total = total;
    }

    private static int num012Check() {
        int num;
        while (true) {
            try {
                num = input.nextInt();
                if (num == 0|| num == 1 || num == 2) {
                    return num;
                }
                else {
                    System.out.println("Fora do intervalo.. digite novamente");
                }
            } catch (Exception e) {
                System.out.print("Formato incorreto digite novamente..\n");
                input.nextLine();
            }
        }
    }

    private static int negativeNum(int n){
        int valid = -1;
        while (n < 0) {
            if (valid == 0) {
                System.out.println("Intervalo inadequado, digite novamente..");
            }
            valid = 0;
            try {
                n = input.nextInt();
            } catch (Exception e) {
                System.out.println("Tipo inadequado, digite novamente..");
                input.nextLine();
            }
        }
        return n;
    }
}
