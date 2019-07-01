package façade;

import empresa.agendas.Schedule;
import empresa.funcionarios.Employee;
import empresa.sindicato.Union;

import java.util.ArrayList;

public class Empresa {

    private ArrayList<Employee> list = new ArrayList<>();
    private ArrayList<Union> union = new ArrayList<>();
    private ArrayList<Schedule> agendas = new ArrayList<>();

    public ArrayList<Employee> getList() {
        return list;
    }

    public void setList(ArrayList<Employee> list) {
        this.list = list;
    }

    public ArrayList<Union> getUnion() {
        return union;
    }

    public void setUnion(ArrayList<Union> union) {
        this.union = union;
    }

    public ArrayList<Schedule> getAgendas() {
        return agendas;
    }

    public void setAgendas(ArrayList<Schedule> agendas) {
        this.agendas = agendas;
    }
}
