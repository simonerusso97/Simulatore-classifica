import java.util.ArrayList;

public class Posizione {
    String nome;
    ArrayList<Integer> posizioni = new ArrayList<>();
    ArrayList<Integer> pariMerito = new ArrayList<>();

    public Posizione(int numeroDiSquadreNellaLega, String nome) {
        this.nome=nome;
        for (int i=0; i<numeroDiSquadreNellaLega; i++){
            posizioni.add(0);
            pariMerito.add(0);
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Integer> getPosizioni() {
        return posizioni;
    }

    public void setPosizioni(ArrayList<Integer> posizioni) {
        this.posizioni = posizioni;
    }

    public void updatePosizioni(int pos) {
        int p = this.posizioni.get(pos);
        this.posizioni.set(pos, (p+1));
    }

    public ArrayList<Integer> getPariMerito() {
        return pariMerito;
    }

    public void updatePariMerito(int pos) {
        int p = this.pariMerito.get(pos);
        this.pariMerito.set(pos, (p+1));
    }
}
