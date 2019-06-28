package empresa.funcionarios;

import empresa.funcionarios.Employee;

public class Salaried extends Employee
{
    private double monthlySalary;

    public Salaried(int id, String name, String adress, double monthlySalary) {
        super(id, name, adress);
        this.monthlySalary = monthlySalary;
        super.setFundo(monthlySalary);
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    @Override
    public String toString() {
        String Pm;
        if(super.getpayMSchedule() == 1)
            Pm = "Cheque em maos";
        else if (super.getpayMSchedule() == 2)
            Pm = "Cheque pelos correios";
        else
            Pm = "Deposito bancário";
        return "\nEmployee - ["+ super.getId() +"]" +
                "\nName: " + super.getName() +
                "\nAdress: " + super.getAdress() +
                "\nPayment Method: " + Pm +
                "\nSalary: R$" + monthlySalary +
                "\nType: Salariado"+
                "\nTotal: R$" + super.getFundo() + "\n";
    }

    public Employee makeCopy(){
        return new Salaried(this.getId(), this.getName(), this.getAdress(), this.monthlySalary);
    }

    @Override
    public double calcSalary() {
        return monthlySalary;
    }
}
