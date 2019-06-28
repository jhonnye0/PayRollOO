package empresa.funcionarios.horista;

import empresa.funcionarios.Employee;

public class Hourly extends Employee
{
    private double hourlyWage;
    private MarkPoint point = new MarkPoint();

    public Hourly(int id, String name, String adress, double hourlyWage) {
        super(id, name, adress);
        this.hourlyWage = hourlyWage;
    }

    public double getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    @Override
    public String toString() {
        String Pm = null;
        if(super.getpayMSchedule() == 1)
            Pm = "Cheque em mãos";
        else if (super.getpayMSchedule() == 2)
            Pm = "Cheque pelos correios";
        else if(super.getpayMSchedule() == 3)
            Pm = "Depósito bancário";
        return "\nEmployee - ["+ super.getId() +"]" +
                "\nName: " + super.getName() +
                "\nAdress: " + super.getAdress() +
                "\nPayment Method: " + Pm +
                "\nSalary/Hour: R$" + hourlyWage +
                "\nType: Horista" +
                "\nTotal: " + super.getFundo() + "\n";
    }

    public void markEntracePoint(int hour){
        this.point.enterEntry(hour);
    }

    public void markOutPoint(int hour){
        this.point.insertOut(hour);

        if(point.getDiff() > 8){
            super.setFundo(super.getFundo() + 1.5*(point.getDiff()-8)*hourlyWage);
        }else{
            super.setFundo(((point.getDiff())*this.hourlyWage));
        }
    }

    public Employee makeCopy(){
        Employee x = new Hourly(this.getId(), this.getName(), this.getAdress(), this.hourlyWage);
        ((Hourly)x).point.enterEntry(this.point.getEntry());
        ((Hourly)x).point.insertOut(this.point.getOut());
        return x;
    }

    public int getDiff(){
        System.out.println("Horas trabalhadas:" + point.getDiff() + "hrs");
        return point.getDiff();
    }

    @Override
    public double calcSalary() {
        return 0;
    }
}