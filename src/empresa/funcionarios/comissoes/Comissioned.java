package empresa.funcionarios.comissoes;

import empresa.funcionarios.Employee;
import empresa.funcionarios.Salaried;

public class Comissioned extends Salaried {

    private double comPercentual;
    private Sells sells = new Sells();

    public Comissioned(int id, String name, String adress, double monthlySalary, double comPercentual) {
        super(id, name, adress, monthlySalary);
        this.comPercentual = comPercentual;
        super.setFundo(monthlySalary);
    }

    public double getComPercentual() {
        return comPercentual;
    }

    public void setComPercentual(double comPercentual) {
        this.comPercentual = comPercentual;
    }

    public void registerSales(int day, double sale){
        this.sells.registerSale(day, sale);
    }

    public Employee makeCopy(){
        Employee x = new Comissioned(this.getId(), this.getName(), this.getAdress(), this.getMonthlySalary(), this.comPercentual);
        int i;
        for (i = 0; i < 32; i++){
            ((Comissioned)x).sells.registerSale(i, this.sells.getDaySale(i));
        }
        return x;
    }

    @Override
    public String toString() {
        String Pm;
        if(super.getpayMSchedule() == 1)
            Pm = "Cheque em mãos";
        else if (super.getpayMSchedule() == 2)
            Pm = "Cheque pelos correios";
        else
            Pm = "Depósito bancário";
        return "\nEmployee - ["+ super.getId() +"]" +
                "\nName: " + super.getName() +
                "\nAdress: " + super.getAdress() +
                "\nPayment Method: " + Pm +
                "\nSalary: R$" + super.getMonthlySalary() +
                "\nComission: " + comPercentual + "%" +
                "\nType: Comissionado" +
                "\nTotal: " + super.getFundo() + "\n";
    }

    @Override
    public double calcSalary() {
        return super.getMonthlySalary() + super.getMonthlySalary()*comPercentual; // algo assim
    }
}