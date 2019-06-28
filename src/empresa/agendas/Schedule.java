package empresa.agendas;
import empresa.funcionarios.Employee;

import java.util.ArrayList;

public interface Schedule
{
    int getPaymentMethod();

    void setPaymentMethod(int paymentMethod);

    int checkValid();

    void createNewSchedule(ArrayList<Schedule> agendas);

    void changeSchedule(ArrayList<Schedule> agendas, Employee x, int num);

    static void roolSheet(ArrayList<Employee> list, int day, int week){
        for(Employee e : list){

            String Pm;
            if(e.schedule.getPaymentMethod() == 1)
                Pm = "Cheque em mãos";
            else if (e.schedule.getPaymentMethod() == 2)
                Pm = "Cheque pelos correios";
            else
                Pm = "Depósito bancário";

            if (e.schedule instanceof empresa.agendas.MonthlySchedule){
                if(day == ((empresa.agendas.MonthlySchedule) e.schedule).getPayday()){
                    System.out.println("Employee de ID [" + e.getId() +"]\n" +
                            "Foi pago através de: " + Pm);
                    System.out.println("--------------------------------");
                }
            }else if(e.schedule instanceof WeeklySchedule){
                if(((WeeklySchedule) e.schedule).getFrequence()%week == 0){
                    if (day == ((WeeklySchedule) e.schedule).getDayWeek()){
                        System.out.println("Employee de ID [" + e.getId() +"]\n" +
                                "Foi pago através de: " + Pm);
                        System.out.println("--------------------------------");
                    }
                }
            }
        }
    }

}
