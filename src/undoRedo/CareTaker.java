package undoRedo;

import empresa.funcionarios.Employee;
import empresa.sindicato.Union;

import java.util.ArrayList;
import java.util.List;

public class CareTaker implements CareInter {

    private int index;
    private List<Object> obj = new ArrayList<>();

    public int getIndex() {
        return index;
    }

    public void save(Originator state){
        index++;
        this.obj.add(state.save());
    }

    public void undo(Originator state){

        if(index - 2 >= 0) {

            state.undoToLastSave(obj.get(index - 2));
            index -= 2;
        }else{
            ArrayList<Employee> list = new ArrayList<>();
            ArrayList<Union> union = new ArrayList<>();
            Originator o = new Originator(list, union);
            state.undoToLastSave(o.save());
        }
    }

    public void clear(){
        index = 0;
        obj.clear();
    }
}
