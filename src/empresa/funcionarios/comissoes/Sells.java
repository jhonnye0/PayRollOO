package empresa.funcionarios.comissoes;

public class Sells {
    private double[] arr = new double[32];

    public void registerSale(int day, double sale){
        this.arr[day] = sale;
    }

    public double getDaySale(int i){
        return this.arr[i];
    }
}
