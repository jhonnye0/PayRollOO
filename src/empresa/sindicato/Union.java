package empresa.sindicato;
import empresa.funcionarios.Employee;
import java.util.ArrayList;

public class Union {

    private boolean union;
    private double unionTax;

    public Union(boolean union, double unionTax) {
        this.union = union;
        this.unionTax = unionTax;
    }

    public boolean isUnion() {
        return union;
    }

    public void setUnion(boolean union) {
        this.union = union;
    }

    public double getUnionTax() {
        return unionTax;
    }

    public void setUnionTax(double unionTax) {
        this.unionTax = unionTax;
    }

    public Union makeCopy(){
        return new Union(this.union, this.unionTax);
    }

    public static void lauchFee(ArrayList<Employee> list, int id, double fee) {
        Employee x = list.get(id);
        x.setFundo(x.getFundo() - fee);
        System.out.println("Taxa lançada com sucesso..\n");
    }
}
