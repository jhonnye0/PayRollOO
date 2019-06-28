package undoRedo;

import empresa.funcionarios.Employee;
import empresa.sindicato.Union;

import java.util.ArrayList;
import java.util.List;

public class ReCareTaker implements CareInter {
    private int index;
    private List<Object> obj = new ArrayList<>();

    public int getIndex() {
        return index;
    }

    public void save(undoRedo.Originator state){
        index++;
        this.obj.add(state.save());
    }

    public void undo(undoRedo.Originator state){

        if(index - 1 >= 0) {

            state.undoToLastSave(obj.get(index - 1));
            index -= 1;
        }else{
            ArrayList<Employee> list = new ArrayList<>();
            ArrayList<Union> union = new ArrayList<>();
            undoRedo.Originator o = new undoRedo.Originator(list, union);
            state.undoToLastSave(o.save());
        }
    }

    public void clear(){
        obj.clear();
    }
}
