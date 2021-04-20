import java.util.ArrayList;

public class Squadra {
    String nome;
    int id;
    int punti;

    public Squadra(String nome, int id, int punti) {
        this.nome = nome;
        this.id=id;
        this.punti=punti;
    }

    public Squadra(Squadra squadra) {
        this.nome = squadra.getNome();
        this.id=squadra.getId();
        this.punti=squadra.getPunti();
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public int getPunti() {
        return punti;
    }

    public void setPunti(int punti) {
        this.punti = punti;
    }

    public void updatePunti(int punti) {
        this.punti += punti;
    }


}
