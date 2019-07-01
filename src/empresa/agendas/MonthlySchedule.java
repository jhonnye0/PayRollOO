package empresa.agendas;
import empresa.funcionarios.Employee;
import java.util.ArrayList;
import java.util.Scanner;

public class MonthlySchedule implements Schedule
{
    static Scanner input = new Scanner(System.in);
    private int paymentMethod;
    private int payday;

    public MonthlySchedule(int paymentMethod, int payday) {
        this.paymentMethod = paymentMethod;
        this.payday = payday;
    }

    public int getPayday() {
        return payday;
    }

    public void setPayday(int payday) {
        this.payday = payday;
    }

    public int getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString()
    {
        return "Agenda:\n" + "Mensal " + payday;
    }

    public void createNewSchedule(ArrayList<Schedule> agendas) {

        System.out.println("Qual o dia do pagamento?");
        int valid = checkValid();
        MonthlySchedule aux = new MonthlySchedule(0, valid);
        agendas.add(aux);
    }

    public int checkValid() {
        String valid;

        while (true) {
            try{
                valid = input.nextLine();

                if(valid.equals("$")) return 31;
                else if ((Integer.parseInt(valid) >= 0 && Integer.parseInt(valid) <= 31)) {
                    return Integer.parseInt(valid);
                }
                else System.out.println("Intervalo inadequado, digite novamente..");
            }catch (Exception e){
                System.out.println("Tipo inadequado digite novamente!");
            }
        }
    }

    public void changeSchedule(ArrayList<Schedule> agendas, Employee x, int num){
        int cont = 0;
        int valid = 0;
        for (Schedule e : agendas) {
            if (e instanceof MonthlySchedule) {
                System.out.println("[" + cont + "]- Mensal " + ((MonthlySchedule) e).getPayday());
                valid = 1;
            }
            cont ++;
        }
        if (valid == 0) {
            System.out.println("Somente agendas default\n");
        }else {
            System.out.println("\nDigite o numero da agenda que deseja:");
            int a;
            while (true) {
                try {
                    a = input.nextInt();
                    if (a > agendas.size() || a < 0)
                        System.out.print("Intervalo incorreto digite novamente..\n");
                    else if (num == 1) {
                        x.setSchedule(new MonthlySchedule(x.getpayMSchedule(), ((MonthlySchedule) agendas.get(a)).getPayday()));
                        break;
                    }else{
                        System.out.print("Agenda do tipo incorreta digite novamente..\n");
                    }
                } catch (Exception e) {
                    System.out.print("Formato incorreto digite novamente..\n");
                    input.nextLine();
                }
            }
        }
    }

    public MonthlySchedule makeCopy(){
        return new MonthlySchedule(this.paymentMethod, this.payday);
    }

    public void roolSheet(ArrayList<Employee> list, int day, int week) {

        for (Employee e : list) {

            String Pm;
            if (e.getpayMSchedule() == 1)
                Pm = "Cheque em maos";
            else if (e.getpayMSchedule() == 2)
                Pm = "Cheque pelos correios";
            else
                Pm = "Deposito bancario";

            if (e.getSchedule() instanceof MonthlySchedule) {
                if (day == ((empresa.agendas.MonthlySchedule) e.getSchedule()).getPayday()) {
                    System.out.println("Employee de ID [" + e.getId() + "]\n" +
                            "Foi pago atraves de: " + Pm);
                    System.out.println("--------------------------------");
                }
            }
        }
    }
}
