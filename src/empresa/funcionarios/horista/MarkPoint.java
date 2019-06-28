package empresa.funcionarios.horista;

public class MarkPoint {
    private int[] array = new int[2];

    public int getEntry(){
        return this.array[0];
    }

    public int getOut(){
        return this.array[1];
    }

    public void enterEntry(int hour) {
        this.array[0] = hour;
    }

    public void insertOut(int hour) {
        this.array[1] = hour;
    }

    public int getDiff() {
        int i;
        int cont = 0;
        for(i = this.array[0]; i != this.array[1]; i++)
        {
            i%=24;
            cont++;
        }
        return cont;
    }
}
