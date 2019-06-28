package undoRedo;

import empresa.agendas.MonthlySchedule;
import empresa.agendas.WeeklySchedule;
import empresa.funcionarios.Employee;
import empresa.sindicato.Union;

import java.util.ArrayList;

public class Originator {

    private ArrayList<Employee> emp = new ArrayList<>();
    private ArrayList<Union> un = new ArrayList<>();

    public Originator(ArrayList<Employee> list, ArrayList<Union> union) {
        for(Employee e : list){
            Employee aux = e.makeCopy();
            if(e.schedule instanceof MonthlySchedule){
                aux.schedule = ((MonthlySchedule)e.schedule).makeCopy();
            }else{
                aux.schedule = ((WeeklySchedule)e.schedule).makeCopy();
            }
            emp.add(aux);
        }
        for (Union u : union){
            un.add(u.makeCopy());
        }
    }

    public void setState(ArrayList<Employee> list, ArrayList<Union> union) {
        this.emp = new ArrayList<>();
        for(Employee e : list){
            Employee aux = e.makeCopy();
            if(e.schedule instanceof MonthlySchedule){
                aux.schedule = ((MonthlySchedule)e.schedule).makeCopy();
            }else if (e.schedule instanceof WeeklySchedule){
                aux.schedule = ((WeeklySchedule)e.schedule).makeCopy();
            }
            emp.add(aux);
        }
        this.un = new ArrayList<>();
        for (Union u : union){
            un.add(u.makeCopy());
        }
    }

    public ArrayList<Employee> getEmp() {
        return emp;
    }

    public ArrayList<Union> getUn() {
        return un;
    }

    public StateEmp save(){ return new StateEmp(this.emp, this.un); }

    public void undoToLastSave(Object obj){
        StateEmp memento = (StateEmp) obj;
        this.emp = memento.emp;
        this.un = memento.un;
    }

    private class StateEmp {

        private ArrayList<Employee> emp;
        private ArrayList<Union> un;

        StateEmp(ArrayList<Employee> list, ArrayList<Union> union) {

            this.emp = new ArrayList<>();
            for(Employee e : list){
                Employee aux = e.makeCopy();
                if(e.schedule instanceof MonthlySchedule){
                    aux.schedule = ((MonthlySchedule)e.schedule).makeCopy();
                }else{
                    aux.schedule = ((WeeklySchedule)e.schedule).makeCopy();
                }
                emp.add(aux);
            }
            this.un = new ArrayList<>();
            for (Union u : union){
                un.add(u.makeCopy());
            }
        }
    }
}
