import java.util.ArrayList;

public class Giornata {
    ArrayList<Partita> partitaArrayList = new ArrayList<>();
    int id;

    public Giornata(int id) {
        this.id = id;
    }

    public ArrayList<Partita> getPartitaArrayList() {
        return partitaArrayList;
    }

    public void setPartitaArrayList(ArrayList<Partita> partitaArrayList) {
        this.partitaArrayList = partitaArrayList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
