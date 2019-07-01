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
    private Time time = new Time();

    public void acess() {
        Empresa empresa = new Empresa();
        Originator originator = new Originator(empresa.getList(), empresa.getUnion());
        Originator reOriginator = new Originator(empresa.getList(), empresa.getUnion());
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
                    setTotal(adm.admin(empresa.getList(), empresa.getUnion(), empresa.getAgendas(), new Manager(),
                            time, total, originator, reOriginator,
                    unCareTaker, reCareTaker, empresa));
                    break;
                case 2:
                    System.out.println("Digite seu ID:");
                    int id = negativeNum(input.nextInt());
                    try {
                        if(empresa.getList().get(id) == null){}
                        else {
                            user.user(empresa.getList().get(id), time, new Manager(), empresa.getAgendas(),originator,
                                    reOriginator, unCareTaker, reCareTaker, empresa);
                        }
                    }catch (Exception e){
                        System.out.println("Empregado nao registrado ainda!");
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
