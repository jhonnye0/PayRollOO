package façade;

import empresa.agendas.*;
import empresa.funcionarios.Employee;
import empresa.sindicato.Union;
import undoRedo.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Admin {

    static Scanner input = new Scanner(System.in);
    private int save = 0;
    public int admin(ArrayList<Employee> list, ArrayList<Union> union,
                     ArrayList<Schedule> agendas, Manager manager, Time time,
                     int total, Originator originator, Originator reOriginator,
                     CareTaker unCareTaker, ReCareTaker reCareTaker) {

        while (true){
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

            int operation = num0_11Check();

            if (save == 1){
                originator.setState(list, union);
                unCareTaker.save(originator);
            }

            save = 0;

            switch (operation){
                case 0:
                    return total;
                case 1:
                    manager.addEmployee(list, union, total);
                    save = 1;
                    break;
                case 2:
                    System.out.println("Digite seu ID:");
                    int id = negativeNum(input.nextInt());

                    if(manager.haveEmp(list, id)){
                        manager.removeEmployee(list, union, id, total);
                        save = 1;
                    }
                    break;
                case 3:
                    System.out.println("Qual o preco da taxa estipulada pelo sindicato?");
                    double tax = negativeNum(input.nextDouble());
                    System.out.print("Qual o ID do empregado?\n");
                    id = negativeNum(input.nextInt());

                    if (manager.haveEmp(list, id)) {
                        Union.lauchFee(list, id, tax);
                        save = 1;
                    }
                    break;
                case 4:
                    System.out.print("Qual o ID do empregado?\n");
                    id = negativeNum(input.nextInt());
                    if (manager.haveEmp(list, id)) {
                        manager.updateEmployee(list, union, id, total);
                        save = 1;
                    }
                    break;
                case 5:
                    WeeklySchedule x = new WeeklySchedule(0,0,0);
                    MonthlySchedule y = new MonthlySchedule(0,0);
                    System.out.println("Semanais:");
                    x.roolSheet(list, time.getDAY(), time.getWEEK());
                    System.out.println("------------------------------\n" +
                            "Mensais:");
                    y.roolSheet(list, time.getDAY(), time.getWEEK());
                    save = 1;
                    break;
                case 6:
                    System.out.println("Que tipo de agenda deseja criar?");
                    System.out.println("1 - Mensal / 2 - Semanal");
                    int num = num12Check();
                    if (num == 1) {
                        MonthlySchedule a = new MonthlySchedule(0, 0);
                        a.createNewSchedule(agendas);
                    } else if (num == 2) {
                        WeeklySchedule a = new WeeklySchedule(0, 0, 0);
                        a.createNewSchedule(agendas);
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
                        int n = negativeNum(-1);
                        time.setHOUR(n);
                    }else{
                        if(time.getHOUR() > 9)
                            System.out.println("HORA: " + time.getHOUR() + ":00");
                        else System.out.println("HORA: " + "0"+ time.getHOUR() + ":00");
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
                        int n = negativeNum(-1);
                        time.setDAY(n);
                    }else{
                        System.out.println("DIA: " + time.getDAY());
                    }
                    if(time.getDAY() == 31)
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
                        int n = negativeNum(-1);
                        time.setWEEK(n);
                    }else{
                        System.out.println("SEMANA: " + time.getDAY());
                    }
                    input.nextLine();
                    System.out.println("Digite ENTER:");
                    input.nextLine();
                    break;
                case 10:
                    int valid = -1;
                    int i;
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
                            total = list.size();
                        }else{
                            System.out.println("Nao ha operacoes disponiveis para fazer");
                        }
                    }else if (num == 2){
                        if(reCareTaker.getIndex() != 0){
                            unCareTaker.clear();
                            reCareTaker.undo(reOriginator);
                            list = reOriginator.getEmp();
                            union = reOriginator.getUn();
                            total = list.size();
                        }else{
                            System.out.println("Nao ha operacoes disponiveis para fazer");
                        }
                    }
                    input.nextLine();
                    System.out.println("Digite ENTER:");
                    input.nextLine();
                    break;
                default:
                    System.out.print("Digite novamente o numero..\n");
                    break;
            }
        }
    }

    private static int num0_11Check() {
        int num;
        while (true) {
            try {
                num = input.nextInt();
                if (num >=0 || num <= 11) {
                    input.nextLine();
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
        input.nextLine();
        return n;
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
}
