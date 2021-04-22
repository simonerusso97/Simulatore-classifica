import java.util.ArrayList;

public class Classifica {
    ArrayList<Squadra> squadra = new ArrayList<>();
    int giornata;
    public Classifica() {
    }

    public ArrayList<Squadra> getSquadra() {
        return squadra;
    }

    public void setSquadra(ArrayList<Squadra> squadra) {
        this.squadra = squadra;
    }

    public int getGiornata() {
        return giornata;
    }

    public void setGiornata(int giornata) {
        this.giornata = giornata;
    }
}
