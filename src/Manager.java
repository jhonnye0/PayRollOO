import empresa.agendas.*;
import empresa.funcionarios.*;
import empresa.funcionarios.comissoes.Comissioned;
import empresa.funcionarios.horista.Hourly;
import empresa.sindicato.Union;

import java.util.ArrayList;
import java.util.Scanner;
public class Manager
{
    static Scanner input = new Scanner(System.in);

    static void addEmployee(ArrayList<Employee> list, ArrayList<Union> union, int total) {
        String name, adress;
        Employee x;
        System.out.print("Digite o nome:\n");
        name = input.nextLine();
        System.out.print("Endereco:\n");
        adress = input.nextLine();
        int type = typeCatch(0);

        double salary;
        if(type == 1)
        {
            System.out.print("Qual o salario/hora do empregado?\n");
            salary = negativeNum(-1);

            x = new Hourly(list.size(), name, adress, salary);
            System.out.print("Status de Pagamento default:\n" +
                    "\nSalario semanal: " + ((Hourly) x).getHourlyWage() +
                    "\nFrequencia: 1 vez na semana" +
                    "\nDia da semana: Sexta\n\n");

            int method = paymMeth();
            x.createSchedule(method, 1, 6);

        } else {
            System.out.print("Qual o salario do empregado?\n");
            salary = negativeNum(-1);
            int method = paymMeth();

            if(false);
            else if(type == 2)
            {
                double comPerc = comCatch(-1);
                x = new Comissioned(list.size(), name, adress, salary, comPerc);
                System.out.print("Status de Pagamento default:\n" +
                        "\nSalario mensal: " + ((Comissioned) x).getMonthlySalary() +
                        "\nPercentual de Comissao: " + ((Comissioned) x).getComPercentual() +
                        "\nDia de pagamento: 31\n\n");
            }
            else
            {
                x = new empresa.funcionarios.Salaried(list.size(), name, adress, salary);
                System.out.print("Status de Pagamento default:\n" +
                        "\nSalario mensal: " + ((empresa.funcionarios.Salaried) x).getMonthlySalary() +
                        "\nDia de pagamento: 31\n\n");
            }
            x.createSchedule(method, 31);
        }
        input.nextLine();

        x.setId(total);
        x.setUnionID(-1);
        syndCheck(x, union, total);
        list.add(x);

        System.out.println("Empregado registrado com sucesso!\n" +
                "ID: " + total);
        if(x.getUnionID() != -1)
            System.out.print("Union ID: " + x.getUnionID());

        System.out.print("\nDIGITE ENTER PARA CONTINUAR\n");
        input.nextLine();
    }

    static void removeEmployee(ArrayList<Employee> list, ArrayList<Union> union, int id, int total) {

        if(union.get(id).isUnion()) {
            union.get(id).setUnion(false); // unionID
            union.get(id).setUnionTax(0);
        }
        list.remove(id);
        System.out.println("Empregado removido com sucesso..");
    }

    static void updateEmployee(ArrayList<Employee> list, ArrayList<Union> union, int id, int total){
        while(true) {
            System.out.println("Digite o numero da informacao a qual deseja atualizar:\n\n" +
                    "0. Retornar\n" +
                    "1. Nome\n" +
                    "2. Endereço\n" +
                    "3. Tipo\n" +
                    "4. Metodo de pagamento\n" +
                    "5. Participacao no sindicato\n");
            int operation;

            try{
                operation = input.nextInt();
            } catch (Exception e) {
                operation = -1;
            }
            input.nextLine();

            if(operation == 0)
            {
                System.out.println("Finalizado.\n");
                break;
            }
            switch(operation) {
                case 1:
                    System.out.printf("Nome atual: %s\n", list.get(id).getName());
                    System.out.println("Digite o novo nome:");
                    list.get(id).setName(input.nextLine());
                    System.out.println("Atualizado.\n");
                    break;
                case 2:
                    System.out.printf("Endereco atual: %s\n", list.get(id).getAdress());
                    System.out.println("Digite o novo endereco:");
                    list.get(id).setAdress(input.nextLine());
                    System.out.println("Atualizado.\n");
                    break;
                case 3:
                    System.out.print("Tipo atual: ");
                    Employee x = list.get(id);
                    list.remove(id);
                    if(x instanceof  Hourly) System.out.print("Horista\n");
                    else if(x instanceof Comissioned) System.out.print("Comissionado\n");
                    else System.out.print("Assalariado\n");

                    int type = typeCatch(0);
                    Employee aux;
                    if(type == 1){

                        System.out.println("Qual o salario/hora do empregado?..");
                        double hourlyWage = negativeNum(-1);

                        aux = new Hourly(x.getId(), x.getName(), x.getAdress(), hourlyWage);
                        list.add(id, aux);

                    } else if(type == 2){

                        System.out.println("Qual o percentual de comissao do empregado?");
                        double comPerc = negativeNum(-1);
                        System.out.println("Qual o novo salario do empregado?");
                        double salary = negativeNum(-1);

                        aux = new Comissioned(x.getId(), x.getName(), x.getAdress(), salary, comPerc);
                        list.add(id, aux);

                    }else{

                        System.out.println("Qual o novo salario do empregado?");
                        double salary = negativeNum(-1);

                        aux = new empresa.funcionarios.Salaried(x.getId(), x.getName(), x.getAdress(), salary);
                        list.add(id, aux);

                    }
                    aux.schedule = x.schedule;
                    aux.setFundo(x.getFundo());
                    aux.setUnionID(x.getUnionID());

                    System.out.println("Atualizado.\n");
                    break;
                case 4:
                    int method = paymMeth();
                    Schedule aux1 = list.get(id).schedule;
                    if(aux1 instanceof MonthlySchedule){
                        list.get(id).createSchedule(method, ((MonthlySchedule) aux1).getPayday());
                    }else {
                        list.get(id).createSchedule(method, ((WeeklySchedule)aux1).getFrequence(), ((WeeklySchedule)aux1).getDayWeek());
                    }

                    System.out.println("Atualizado.\n");
                    break;
                case 5:
                    syndCheck(list.get(id), union, id);
                    if(union.get(id).isUnion())
                    {
                        System.out.println("Empregado deseja alterar o ID do sindicato?\n" +
                                "1. yes\n" +
                                "0. no");
                        while (true)
                        {
                            int valid;
                            try {
                                valid = input.nextInt();

                                if(valid == 1)
                                {
                                    list.get(id).setUnionID(total + (111171 % 10000) - 7);
                                    break;
                                }
                                else if(valid == 0)
                                    break;
                                else
                                    System.out.println("Numero incorreto digite novamente.\n");
                            }catch (Exception e){
                                System.out.println("Formato incorreto digite novamente.\n");
                                input.nextLine();
                            }
                        }
                    }
                    System.out.println("Atualizado.\n");
                    break;
            }
        }
    }

    public static void printEmployee(ArrayList<Employee> list, ArrayList<Union> union, int id) {
        System.out.print("\n-----------------------------------\n");
        Employee x = list.get(id);
        if(x.schedule != null) {
            System.out.print(x.toString());
            System.out.println(x.schedule.toString());
        }
        if (union.get(id).isUnion()) {
            System.out.println("Union: true");
            System.out.println("Union ID: " + x.getUnionID());
            System.out.println("Union fee: " + union.get(id).getUnionTax());
        }
    }

    static boolean haveEmp(ArrayList<Employee> list, int id) {
        int valid;
        try {
            valid = 1;
            list.get(id);
        }catch (IndexOutOfBoundsException e){
            System.out.println("Empregado não registrado na empresa.");
            valid = 0;
        }
        return valid == 1;
    }

    private static void syndCheck(Employee x, ArrayList<Union> union, int total) {

        System.out.println("Empregado deseja filiar-se ao sindicato?" +
                "\n[y] - yes" +
                "\n[n] - no");
        label:
        while (true) {
            String in = input.nextLine();
            switch (in.intern()) {
                case "y": {

                    System.out.print("Qual a taxa sindical?\n");
                    double synTax = negativeNum(-1);
                    Union aux = new Union(true, synTax);
                    union.add(total, aux);

                    x.setUnionID(total + 10000);

                    input.nextLine();
                    break label;
                }
                case "n": {
                    Union aux = new Union(false, 0);
                    union.add(total, aux);
                    break label;
                }
                default:
                    System.out.println("Entrada inadequada, digite novamente..");
                    break;
            }
        }
    }

    private static int typeCatch(int type) {
        int valid = -1;
        System.out.print("Tipo de empregado: (Digite o número)\n" +
                "1. Horista\n" +
                "2. Comissionado\n" +
                "3. Assalariado\n");

        while(type < 1 || type > 3)
        {
            if(valid == 0)
                System.out.print("Número fora do intervalo, digite novamente..\n");
            valid = 0;
            try{
                type = input.nextInt();
            }catch (Exception e) {
                valid = 1;
                System.out.print("Tipo especificado incorreto, digite novamente..\n");
                input.nextLine();
            }
        }
        return type;
    }

    private static double comCatch(double comPerc) {
        System.out.print("Qual o percentual de comissão do empregado?\n");
        int valid = -1;
        while (comPerc < 0 || comPerc > 100)
        {
            if(valid == 0){
                System.out.print("Número fora do intervalo, digite novamente..\n");
            }
            valid = 0;
            try {
                comPerc = input.nextDouble();
            }catch (Exception e) {
                System.out.print("Percentual especificado imprópio, digite novamente..\n");
                input.nextLine();
            }
        }
        return comPerc;
    }

    private static int paymMeth() {
        int method = 0;
        int valid = -1;
        System.out.print("Qual método de pagamento que deseja?\n" +
                "1.Cheque em mãos\n" +
                "2.Cheque pelos correios\n" +
                "3.Déposito bancário\n");
        while(method < 1 || method > 3)
        {
            if(valid == 0)
                System.out.print("Número fora do intervalo, digite novamente..\n");
            valid = 0;
            try {
                method = input.nextInt();
            }catch (Exception e) {
                valid = 1;
                System.out.print("Tipo especificado incorreto, digite novamente..\n");
                input.nextLine();
            }
        }
        return method;
    }

    private static int negativeNum(int n) {
        int valid = -1;
        while (n < 0)
        {
            if(valid == 0)
                System.out.println("Intervalo inadequado, digite novamente..");
            valid = 0;
            try {
                n = input.nextInt();
            }catch (Exception e){
                System.out.println("Tipo inadequado, digite novamente..");
                input.nextLine();
            }
        }
        return n;
    }

    static double negativeNum(double n) {
        int valid = -1;
        while (n < 0)
        {
            if(valid == 0)
                System.out.println("Intervalo inadequado, digite novamente..");
            valid = 0;
            try {
                n = input.nextDouble();
            }catch (Exception e){
                System.out.println("Tipo inadequado, digite novamente..");
                input.nextLine();
            }
        }
        return n;
    }
}
