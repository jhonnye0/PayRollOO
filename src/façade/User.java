package façade;

import empresa.agendas.Time;
import empresa.funcionarios.*;
import empresa.funcionarios.comissoes.*;
import empresa.funcionarios.horista.*;
import empresa.agendas.*;
import undoRedo.*;

import java.util.ArrayList;
import java.util.Scanner;

public class User {

    static Scanner input = new Scanner(System.in);

    public void user(Employee usario, Time time, Manager manager,
                     ArrayList<Schedule> agendas, Originator originator,
                     Originator reOriginator, CareTaker unCareTaker, ReCareTaker reCareTaker, Empresa empresa){

        while(true)
        {
            System.out.println("\n----------------------------------\n");
            System.out.println("Digite a operacao que deseja:\n\n" +
                    "0. Finalizar\n" +
                    "1. Registro de vendas\n" +
                    "2. Marcar ponto\n" +
                    "3. Alterar agenda de pagamento\n");

            int operation = num0123Check();

            switch (operation){
                case 0:
                    return;
                case 1:
                    if(usario instanceof Comissioned){

                        System.out.println("Qual o valor total de vendas no dia?");
                        double sales = negativeNum(-1);
                        ((Comissioned)usario).registerSales(time.getDAY(), sales);
                        usario.setFundo(usario.getFundo()+ sales*(((Comissioned)usario).getComPercentual()/100));

                        System.out.println("Venda registrada com sucesso..");
                    }else {
                        System.out.println("Seu tipo nao esta habilitado a registrar venda");
                    }
                    break;
                case 2:
                    if (usario instanceof Hourly) {
                        System.out.println("1 - Entrada / 2 - Saída");
                        int num = num12Check();
                        if (num == 1) {
                            ((Hourly) usario).markEntracePoint(time.getHOUR());
                        } else if (num == 2) {
                            ((Hourly) usario).markOutPoint(time.getHOUR());
                        }
                        System.out.println("Ponto marcado com sucesso..");
                    } else {
                        System.out.println("Seu tipo nao esta habilitado a marcar ponto");
                    }
                    break;
                case 3:
                    System.out.println("Que tipo de agenda deseja?");
                    System.out.println("1 - Mensal / 2 - Semanal");
                    int num = num12Check();
                    input.nextLine();
                    if (num == 1) {
                        MonthlySchedule x = new MonthlySchedule(0, 0);
                        x.changeSchedule(agendas, usario, num);
                    } else if (num == 2) {
                        WeeklySchedule x = new WeeklySchedule(0, 0, 0);
                        x.changeSchedule(agendas, usario, num);
                    }
                    System.out.print("Agenda alterada com sucesso..\n");
                    break;
            }
        }
    }

    private static int num0123Check() {
        int num;
        while (true) {
            try {
                num = input.nextInt();
                if (num >= 0 || num <= 3) {
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

    private static int num12Check() {
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

    private static double negativeNum(double n){
        int valid = -1;
        while (n < 0) {
            if (valid == 0) {
                System.out.println("Intervalo inadequado, digite novamente..");
            }
            valid = 0;
            try {
                n = input.nextDouble();
            } catch (Exception e) {
                System.out.println("Tipo inadequado, digite novamente..");
                input.nextLine();
            }
        }
        return n;
    }
}
