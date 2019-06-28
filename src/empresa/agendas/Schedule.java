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

    void roolSheet(ArrayList<Employee> list, int day, int week);

}
