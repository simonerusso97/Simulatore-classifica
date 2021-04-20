import java.util.ArrayList;

public class Business {

    private static Business instance;

    public static Business getInstance() {
        if(instance == null)
            instance = new Business();
        return instance;
    }

    Calendario ListMatches(ArrayList<Squadra> squadraArrayList){


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

    void stampaCalendario(Calendario calendario){
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
}
