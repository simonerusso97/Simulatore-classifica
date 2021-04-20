import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class Simulatore {

    public static void main(String[] args) {

        ArrayList<Squadra> squadraArrayList = new ArrayList<>();
        Giornata giornata;
        Calendario calendario;
        ArrayList<Classifica> classificaArrayList = new ArrayList<>();
        Classifica classifica;
        ArrayList<Integer> esito;
        ArrayList<ArrayList<Integer>> chosenArray = new ArrayList<>();
        SessionManager.getInstance().getSESSION().put("chosenArray", chosenArray);
        int nEsitiPossibili;
        Classifica classificaIniziale;

        //AGGIUNGO LE SQUADRE
        squadraArrayList.add(new Squadra("Paris Saint-Chiangimuertu", 0, 57));
        squadraArrayList.add(new Squadra("Lokovid Mosca", 1, 46));
        squadraArrayList.add(new Squadra("IlGrandeTonino", 2, 42));
        squadraArrayList.add(new Squadra("AD10S", 3, 49));
        squadraArrayList.add(new Squadra("CoronaVirus Entella", 4, 37));
        squadraArrayList.add(new Squadra("HERTA MPONE", 5, 36));
        squadraArrayList.add(new Squadra("Benfiga", 6, 41));
        squadraArrayList.add(new Squadra("Antonio Russo", 7, 31));

        classificaIniziale = new Classifica();
        for(Squadra squadra: squadraArrayList){
            Squadra temp = new Squadra(squadra.getNome(), squadra.getId(), squadra.getPunti());
            classificaIniziale.getSquadra().add(temp);
        }

        classificaIniziale.setGiornata(0);
        classificaArrayList.add(classificaIniziale);


        //CREO IL CALENDARIO
        calendario = ListMatches(squadraArrayList);

        //STAMPO IL CALENDARIO
        stampaCalendario(calendario);
        calendario.getGiornataArrayList().remove(0);
        //CALCOLO TUTTE LE COMBINAZIONI POSSIBILI DEGLI ESITI DELLE PARTITE
        Combinatorio.CombinationRepetition(squadraArrayList.size()/2);
        nEsitiPossibili=chosenArray.size();

        ArrayList<Classifica> classificaDiGiornataArrayList;
        //PER OGNI GIORNATA
        for(int k=0; k<calendario.getGiornataArrayList().size(); k++){
            giornata = calendario.getGiornataArrayList().get(k);
            System.out.println("inizio giornata "+ (k+1));

            //CERCO LE CLASSIFICHE DI GIORNATA
            classificaDiGiornataArrayList = new ArrayList<>();
            for(Classifica c: classificaArrayList){
                if(c.getGiornata()==k)
                    classificaDiGiornataArrayList.add(c);
            }

            //PER OGNI CLASSIFICA DI GIORNATA
            for(Classifica c: classificaDiGiornataArrayList) {
                //SETTO LA CLASSIFICA DI GIORNATA
                for (Squadra squadra : c.getSquadra()) {
                    for (Partita par : giornata.getPartitaArrayList()) {
                        Squadra squadra1 = par.getSquadra1();
                        Squadra squadra2 = par.getSquadra2();
                        if (squadra.getNome().equalsIgnoreCase(squadra1.getNome()))
                            par.getSquadra1().setPunti(squadra.getPunti());
                        if (squadra.getNome().equalsIgnoreCase(squadra2.getNome()))
                            par.getSquadra2().setPunti(squadra.getPunti());
                    }
                }


                //PER OGNI ESITO
                for(int i=0; i<nEsitiPossibili; i++){
                esito=chosenArray.get(i);

                    classifica = new Classifica();
                    //ANALIZZO I RISULTATI DELLA GIORNATA
                    for (int t=0; t<giornata.getPartitaArrayList().size(); t++) {
                        Partita par=giornata.getPartitaArrayList().get(t);
                        if (esito.get(t) == 1)
                            par.getSquadra1().updatePunti(3);
                        else if (esito.get(t) == 2)
                            par.getSquadra2().updatePunti(3);
                        else {
                            par.getSquadra1().updatePunti(1);
                            par.getSquadra2().updatePunti(1);
                        }
                        //COSTRUISCO LA CLASSIFICA DI GIORNATA

                        classifica.getSquadra().add(new Squadra(par.getSquadra1().getNome(), par.getSquadra1().getId(), par.getSquadra1().getPunti()));
                        classifica.getSquadra().add(new Squadra(par.getSquadra2().getNome(), par.getSquadra1().getId(), par.getSquadra2().getPunti()));
                    }
                    classifica.setGiornata(k+1);
                    classificaArrayList.add(classifica);
                }

            }
            for(int l=0; classificaArrayList.get(l).getGiornata()<k+1; l++)
                classificaArrayList.remove(l);
            System.out.println();
        }
        int cont=0;
        for (Classifica c: classificaArrayList) {
            if(c.getGiornata()==calendario.giornataArrayList.size()) {
                cont++;
                System.out.println("CALSSIFICA ");
                c.getSquadra().sort(new SortByPunti());
                for (Squadra squadra : c.getSquadra()) {
                    System.out.println(squadra.getNome() + "   pt." + squadra.getPunti());
                }
                System.out.println();
            }
        }
        System.out.println("GLI SCENARI POSSIBILI SONO:" +cont);
    }





    static Calendario ListMatches(ArrayList<Squadra> squadraArrayList){


        int numDays = (squadraArrayList.size() - 1); // Days needed to complete tournament
        int halfSize = squadraArrayList.size() / 2;

        ArrayList<Squadra> teams = new ArrayList<>();
        Partita partita;
        Giornata giornata;
        Calendario calendario = new Calendario();

        teams.addAll(squadraArrayList); // Add teams to List and remove the first team
        teams.remove(0);

        int teamsSize = teams.size();

        for (int day = 0; day < numDays; day++)
        {
            giornata = new Giornata(day);
            int teamIdx = day % teamsSize;

            partita= new Partita(teams.get(teamIdx), squadraArrayList.get(0));
            giornata.getPartitaArrayList().add(partita);

            for (int idx = 1; idx < halfSize; idx++)
            {
                int firstTeam = (day + idx) % teamsSize;
                int secondTeam = (day  + teamsSize - idx) % teamsSize;
                partita = new Partita(teams.get(firstTeam), teams.get(secondTeam));
                giornata.getPartitaArrayList().add(partita);
            }
            calendario.getGiornataArrayList().add(giornata);
        }
        return calendario;
    }

    static void stampaCalendario(Calendario calendario){
        int k=0;
        for (Giornata gior: calendario.getGiornataArrayList()) {
            System.out.println("GIORNATA "+k);
            for (Partita par : gior.getPartitaArrayList()){
                System.out.println(par.getSquadra1().getNome() +" - "+ par.getSquadra2().getNome());
            }
            System.out.println("---------------");
            k++;
        }
    }
    static class SortByPunti implements Comparator<Squadra>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Squadra a, Squadra b)
        {
            return a.punti - b.punti;
        }
    }

}
