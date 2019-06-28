import empresa.funcionarios.*;
import empresa.agendas.*;
import empresa.sindicato.*;
import empresa.funcionarios.comissoes.*;
import empresa.funcionarios.horista.*;
import undoRedo.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        ArrayList<Employee> list = new ArrayList<>();
        ArrayList<Union> union = new ArrayList<>();
        ArrayList<Schedule> agendas = new ArrayList<>();

        Originator originator = new Originator(list, union);
        Originator reOriginator = new Originator(list, union);
        CareTaker unCareTaker = new CareTaker();
        ReCareTaker reCareTaker = new ReCareTaker();

        int qt = 0;

        while (true) {
            int i;

            System.out.println("\n----------------------------------\n");
            System.out.println("\nModo de acesso: (Digite o numero)\n" +
                    "0. Encerrar acesso\n" +
                    "1. Admin\n" +
                    "2. Empregado\n");

            int o = -1;
            int valid = -1;
            while (o < 0 || o > 2) {
                if (valid == 0)
                    System.out.print("Numero fora do intervalo, digite novamente..\n");
                valid = 0;
                try {
                    o = input.nextInt();
                } catch (Exception e) {
                    valid = 1;
                    System.out.print("Formato incorreto digite novamente..\n");
                    input.nextLine();
                }
            }

            int id;
            int operation;

            if (o == 1) {
                while (true) {
                    System.out.println(unCareTaker.getIndex());
                    System.out.print(reCareTaker.getIndex());
                    System.out.println("\n----------------------------------\n");
                    System.out.println("Digite o numero da operacao que deseja:\n\n" +
                            "0. Finalizar\n" +
                            "1. Adcionar empregado\n" +
                            "2. Remover empregado\n" +
                            "3. Lancar taxa de servico\n" +
                            "4. Reajustar informacoes de um empregado\n" +
                            "5. Rodar a folha de pagamento para hoje\n" +
                            "6. Criar nova agenda de pagamento\n" +
                            "7. Passar as horas/ Checar horas\n" +
                            "8. Passar os dias/ Checar dia\n" +
                            "9. Passar as semanas / Checar semana\n" +
                            "10. Checar os empregados registrados na empresa\n" +
                            "11. Desfazer operacao /  Refazer operacao\n");

                    if(Time.getDAY() == 0) Time.setDAY(1);

                    try {
                        operation = input.nextInt();
                    } catch (Exception e) {
                        operation = -1;
                    }
                    input.nextLine();
                    if (operation == 0) break;


                    switch (operation) {
                        case 1:
                            Manager.addEmployee(list, union, qt);
                            qt++;

                            originator.setState(list, union);
                            unCareTaker.save(originator);
                            break;
                        case 2:
                            System.out.print("Digite o ID do empregado que deseja remover:\n");
                            id = negativeNum(input.nextInt());
                            input.nextLine();

                            if (Manager.haveEmp(list, id)) {
                                Manager.removeEmployee(list, union, id, qt);
                                qt--;
                                originator.setState(list, union);
                                unCareTaker.save(originator);
                            }

                            System.out.println("\nDigite ENTER:");
                            input.nextLine();
                            break;
                        case 3:
                            System.out.println("Qual o preco da taxa estipulada pelo sindicato?");
                            double tax = negativeNum(input.nextDouble());

                            System.out.print("Qual o ID do empregado?\n");
                            id = negativeNum(input.nextInt());
                            input.nextLine();

                            if (Manager.haveEmp(list, id)) {
                                Union.lauchFee(list, id, tax);
                                originator.setState(list, union);
                                unCareTaker.save(originator);
                            }

                            System.out.println("Digite ENTER:");
                            input.nextLine();
                            break;
                        case 4:
                            System.out.print("Qual o ID do empregado?\n");
                            id = negativeNum(input.nextInt());
                            input.nextLine();

                            if (Manager.haveEmp(list, id)) {
                                Manager.updateEmployee(list, union, id, qt);
                                originator.setState(list, union);
                                unCareTaker.save(originator);
                            }
                            break;
                        case 5:
                            Schedule.roolSheet(list, Time.getDAY(), Time.getWEEK());
                            originator.setState(list, union);
                            unCareTaker.save(originator);

                            input.nextLine();
                            System.out.print("\nDIGITE ENTER PARA CONTINUAR\n");
                            input.nextLine();
                            break;
                        case 6:
                            System.out.println("Que tipo de agenda deseja criar?");
                            System.out.println("1 - Mensal / 2 - Semanal");
                            int num = num12Check();
                            if (num == 1) {
                                MonthlySchedule x = new MonthlySchedule(0, 0);
                                x.createNewSchedule(agendas);
                            } else if (num == 2) {
                                WeeklySchedule x = new WeeklySchedule(0, 0, 0);
                                x.createNewSchedule(agendas);
                            }
                            input.nextLine();
                            System.out.print("Agenda criada com sucesso..\n");

                            System.out.println("Digite ENTER:");
                            input.nextLine();
                            break;
                        case 7:
                            System.out.println("1 - Passar horas / 2 - Checar horas");
                            num = num12Check();
                            if(num == 1){
                                System.out.println("Quantas horas deseja passar?");
                                int x = negativeNum(-1);
                                Time.setHOUR(x);
                            }else{
                                if(Time.getHOUR() > 9)
                                    System.out.println("HORA: " + Time.getHOUR() + ":00");
                                else System.out.println("HORA: " + "0"+ Time.getHOUR() + ":00");
                            }
                            input.nextLine();
                            System.out.println("Digite ENTER:");
                            input.nextLine();
                            break;
                        case 8:
                            System.out.println("1 - Passar dias / 2 - Checar dias");
                            num = num12Check();
                            if(num == 1){
                                System.out.println("Quantos dias deseja passar?");
                                int x = negativeNum(-1);
                                Time.setDAY(x);
                            }else{
                                System.out.println("DIA: " + Time.getDAY());
                            }
                            if(Time.getDAY() == 31)
                            {
                                for(Employee e : list){
                                    if(union.get(e.getId()).isUnion()){
                                        e.setFundo(e.getFundo()-union.get(e.getId()).getUnionTax());
                                    }
                                }
                            }
                            input.nextLine();
                            System.out.println("Digite ENTER:");
                            input.nextLine();
                            break;
                        case 9:
                            System.out.println("1 - Passar semanas / 2 - Checar semanas");
                            num = num12Check();
                            if(num == 1){
                                System.out.println("Quantas semanas deseja passar?");
                                int x = negativeNum(-1);
                                Time.setWEEK(x);
                            }else{
                                System.out.println("SEMANA: " + Time.getDAY());
                            }
                            input.nextLine();
                            System.out.println("Digite ENTER:");
                            input.nextLine();
                            break;
                        case 10:
                            valid = -1;
                            for (i = 0; i < list.size(); i++) {
                                valid++;
                                Manager.printEmployee(list, union, i);
                            }
                            if (valid == -1)
                                System.out.println("Nao ha empregados registrados..");

                            System.out.print("\n-----------------------------------\n");
                            System.out.print("\nDIGITE ENTER PARA CONTINUAR\n");
                            input.nextLine();
                            break;
                        case 11:
                            System.out.println("1 - Defazer operacao / 2 - Refazer operacao");
                            num = num12Check();
                            if(num == 1){
                                if(unCareTaker.getIndex() != 0){
                                    reOriginator.setState(list, union);
                                    reCareTaker.save(reOriginator);
                                    unCareTaker.undo(originator);
                                    list = originator.getEmp();
                                    union = originator.getUn();
                                    qt = list.size();
                                }else{
                                    System.out.println("Nao ha operacoes disponiveis para fazer");
                                }

                            }else if (num == 2){

                                if(reCareTaker.getIndex() != 0){
                                    unCareTaker.clear();
                                    reCareTaker.undo(reOriginator);
                                    list = reOriginator.getEmp();
                                    union = reOriginator.getUn();
                                    qt = list.size();
                                }else{
                                    System.out.println("Nao ha operacoes disponiveis para fazer");
                                }
                            }
                            input.nextLine();
                            System.out.println("Digite ENTER:");
                            input.nextLine();
                        default:
                            System.out.print("Digite novamente o numero..\n");
                            break;
                    }
                }
            }
            else if (o == 2) {
                while (true) {
                    System.out.println("\n----------------------------------\n");
                    System.out.println("Digite a operacao que deseja:\n\n" +
                            "0. Finalizar\n" +
                            "1. Registro de vendas\n" +
                            "2. Marcar ponto\n" +
                            "3. Alterar agenda de pagamento\n");
                    operation = input.nextInt();
                    input.nextLine();
                    if (operation == 0) break;
                    switch (operation) {
                        case 1:
                            System.out.println("Digite seu ID:");
                            id = negativeNum(input.nextInt());
                            if (Manager.haveEmp(list, id)) {
                                if (list.get(id) instanceof Comissioned) {
                                    System.out.println("Qual o valor total de vendas no dia?");
                                    double sales = negativeNum(-1);
                                    ((Comissioned)list.get(id)).registerSales(Time.getDAY(), sales);
                                    list.get(id).setFundo(list.get(id).getFundo()+ sales*(((Comissioned)list.get(id)).getComPercentual()/100));

                                    originator.setState(list, union);
                                    unCareTaker.save(originator);
                                    System.out.println("Venda registrada com sucesso..");
                                }else {
                                    System.out.println("Seu tipo nao esta habilitado a registrar venda");
                                }
                            }
                            break;
                        case 2:
                            System.out.println("Digite seu ID:");
                            id = negativeNum(input.nextInt());
                            if (Manager.haveEmp(list, id)) {
                                if (list.get(id) instanceof Hourly) {

                                    System.out.println("1 - Entrada / 2 - Saída");
                                    int num = num12Check();
                                    if (num == 1) {
                                        ((Hourly) list.get(id)).markEntracePoint(Time.getHOUR());
                                    } else if (num == 2) {
                                        ((Hourly) list.get(id)).markOutPoint(Time.getHOUR());
                                    }
                                    originator.setState(list, union);
                                    unCareTaker.save(originator);
                                    System.out.println("Ponto marcado com sucesso..");
                                } else {
                                    System.out.println("Seu tipo nao esta habilitado a marcar ponto");
                                }
                            }
                            break;
                        case 3:
                            System.out.println("Digite seu ID:");
                            id = negativeNum(input.nextInt());
                            if (Manager.haveEmp(list, id)) {

                                System.out.println("Que tipo de agenda deseja?");
                                System.out.println("1 - Mensal / 2 - Semanal");
                                int num = num12Check();
                                input.nextLine();
                                if (num == 1) {
                                    MonthlySchedule x = new MonthlySchedule(0, 0);
                                    x.changeSchedule(agendas, list.get(id), num);

                                    originator.setState(list, union);
                                    unCareTaker.save(originator);
                                } else if (num == 2) {
                                    WeeklySchedule x = new WeeklySchedule(0, 0, 0);
                                    x.changeSchedule(agendas, list.get(id), num);

                                    originator.setState(list, union);
                                    unCareTaker.save(originator);
                                }
                                System.out.print("Agenda alterada com sucesso..\n");
                            }
                            break;
                    }
                }
            } else if (o == 0) break;
        }
    }

    private static int num12Check() {
        int num;
        while (true) {
            try {
                num = input.nextInt();
                if (num == 1 || num == 2) {
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
