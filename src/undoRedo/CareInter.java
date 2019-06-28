package undoRedo;

public interface CareInter {
    void save(Originator state);

    void undo(Originator state);

    void clear();
}
