import java.util.ArrayList;

public class Calendario {
ArrayList<Giornata> giornataArrayList= new ArrayList<>();

    public Calendario() {

    }

    public ArrayList<Giornata> getGiornataArrayList() {
        return giornataArrayList;
    }

    public void setGiornataArrayList(ArrayList<Giornata> giornataArrayList) {
        this.giornataArrayList = giornataArrayList;
    }
}
