package empresa.agendas;
import empresa.funcionarios.Employee;

import java.util.ArrayList;
import java.util.Scanner;

public class WeeklySchedule implements empresa.agendas.Schedule {

    static Scanner input = new Scanner(System.in);
    private int frequence;
    private int dayWeek;
    private int paymentMethod;

    public WeeklySchedule(int paymentMethod, int frequence, int dayWeek) {
        this.paymentMethod = paymentMethod;
        this.frequence = frequence;
        this.dayWeek = dayWeek;
    }

    public WeeklySchedule makeCopy(){
        return new WeeklySchedule(this.paymentMethod, this.frequence, this.dayWeek);
    }

    public int getFrequence() {
        return frequence;
    }

    public void setFrequence(int frequence) {
        this.frequence = frequence;
    }

    public int getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(int dayWeek) {
        this.dayWeek = dayWeek;
    }

    public int getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void createNewSchedule(ArrayList<empresa.agendas.Schedule> agendas){

        System.out.println("Qual a frequencia pagamento?(acima de 3 semanas é inviavel)");
        int frequence;
        while(true)
        {
            try {
                frequence = input.nextInt();
                if(frequence < 1 || frequence > 3)
                {
                    System.out.println("Intervalo inadequado, digite novamente..");
                }
                else break;
            }catch (Exception e){
                System.out.println("Formato especifcado incorreto");
                input.nextLine();
            }
        }
        System.out.print("Qual dia da semana sera efetuado o pagamento?\n\n" +
                "1. Domingo\n" +
                "2. Segunda\n" +
                "3. Terca\n" +
                "4. Quarta\n" +
                "5. Quinta\n" +
                "6. Sexta\n" +
                "7. Sabado\n");
        int dayweek;
        while(true)
        {
            try {
                dayweek = input.nextInt();
                if(dayweek < 0 || dayweek > 7)
                {
                    System.out.println("Intervalo inadequado, digite novamente..");
                }
                else break;
            }catch (Exception e){
                System.out.println("Formato especifcado incorreto");
                input.nextLine();
            }
        }
        WeeklySchedule aux = new WeeklySchedule(0, frequence, dayweek);
        agendas.add(aux);
    }

    public void changeSchedule(ArrayList<empresa.agendas.Schedule> agendas, Employee x, int num){
        int valid = 0;
        int cont = 0;
        for (empresa.agendas.Schedule e : agendas) {
            if (e instanceof WeeklySchedule) {
                String dW = null;
                valid = 1;
                switch (((WeeklySchedule) e).getDayWeek()) {
                    case 1:
                        dW = "Domingo";
                        break;
                    case 2:
                        dW = "Segunda";
                        break;
                    case 3:
                        dW = "Terça";
                        break;
                    case 4:
                        dW = "Quarta";
                        break;
                    case 5:
                        dW = "Quinta";
                        break;
                    case 6:
                        dW = "Sexta";
                        break;
                    case 7:
                        dW = "Sábado";
                        break;
                    default:
                        break;
                }
                System.out.println("[" + cont + "]- Semanal " + ((WeeklySchedule) e).getFrequence() + " " + dW);
            }
            cont ++;
        }
        if (valid == 0) {
            System.out.println("Somente agendas default\n");
        }else {
            System.out.println("\nDigite o número da agenda que deseja:");
            int a;
            while(true)
            {
                try {
                    a = input.nextInt();
                    if(a > agendas.size() || a < 0)
                        System.out.print("Intervalo incorreto digite novamente..\n");
                    else if(num == 2){
                        x.setSchedule(new WeeklySchedule(x.getpayMSchedule(), ((WeeklySchedule)agendas.get(a)).getFrequence(), ((WeeklySchedule)agendas.get(a)).getDayWeek()));
                        break;
                    }else{
                        System.out.print("Agenda do tipo incorreta digite novamente..\n");
                    }
                }catch (Exception e) {
                    System.out.print("Formato incorreto digite novamente..\n");
                    input.nextLine();
                }
            }
        }
    }

    @Override
    public int checkValid() {
        return 0;
    }

    @Override
    public String toString() {
        String dW = null;
        switch (this.dayWeek) {
            case 1:
                dW = "Domingo";
                break;
            case 2:
                dW = "Segunda";
                break;
            case 3:
                dW = "Terça";
                break;
            case 4:
                dW = "Quarta";
                break;
            case 5:
                dW = "Quinta";
                break;
            case 6:
                dW = "Sexta";
                break;
            case 7:
                dW = "Sábado";
                break;
            default:
                break;
        }
        return "Agenda:\n" + "Semanal " + this.frequence + " " + dW;
    }

    public void roolSheet(ArrayList<Employee> list, int day, int week){
        for (Employee e : list) {

            String Pm;
            if (e.getpayMSchedule() == 1)
                Pm = "Cheque em maos";
            else if (e.getpayMSchedule() == 2)
                Pm = "Cheque pelos correios";
            else
                Pm = "Deposito bancario";

            if(e.getSchedule() instanceof WeeklySchedule){
                if(((WeeklySchedule) e.getSchedule()).getFrequence()%week == 0){
                    if (day == ((WeeklySchedule) e.getSchedule()).getDayWeek()){
                        System.out.println("Employee de ID [" + e.getId() +"]\n" +
                                "Foi pago atraves de: " + Pm);
                        System.out.println("--------------------------------");
                    }
                }
            }
        }
    }
}

