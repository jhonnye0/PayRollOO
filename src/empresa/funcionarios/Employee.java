package empresa.funcionarios;
import empresa.agendas.*;
import empresa.sindicato.*;

public abstract class Employee
{
    private int id;
    private int unionID;
    private String name;
    private String adress;
    private double fundo;
    public Schedule schedule;

    public Employee(int id, String name, String adress) {
        this.id = id;
        this.name = name;
        this.adress = adress;
    }

    public abstract Employee makeCopy();

    public abstract double calcSalary();

    public int getId() {
        return id;
    }

    public int getUnionID() {
        return unionID;
    }

    public void setUnionID(int unionID) {
        this.unionID = unionID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void createSchedule(int paymentMethod, int payday) {
        this.schedule = new MonthlySchedule(paymentMethod, payday);
    }

    public void createSchedule(int paymentMethod, int frequence, int dayWeek) {
        this.schedule = new WeeklySchedule(paymentMethod, frequence, dayWeek);
    }

    public void setFundo(double fundo) {
        this.fundo = fundo;
    }

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }

    public double getFundo() {
        return fundo;
    }

    public void setpayMSchedule(int method){
        this.schedule.setPaymentMethod(method);
    }

    public int getpayMSchedule() {
        return this.schedule.getPaymentMethod();
    }
}
