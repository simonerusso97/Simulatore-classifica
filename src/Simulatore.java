import java.util.ArrayList;

public class Simulatore {

    public static void main(String[] args) {
        ArrayList<Squadra> squadraArrayList = new ArrayList<>();
        Calendario calendario;
        ArrayList<Classifica> classificaArrayList = new ArrayList<>();
        ArrayList<ArrayList<Integer>> combinazioneEsiti = new ArrayList<>();
        SessionManager.getInstance().getSESSION().put("combinazioneEsiti", combinazioneEsiti);
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
        calendario = Business.getInstance().ListMatches(squadraArrayList);

        //STAMPO IL CALENDARIO
        Business.getInstance().stampaCalendario(calendario);

        //CALCOLO TUTTE LE COMBINAZIONI POSSIBILI DEGLI ESITI DELLE PARTITE
        Combinatorio.CombinationRepetition(squadraArrayList.size()/2);


        //PER OGNI GIORNATA
        int k=0;
        for(Giornata giornata: calendario.getGiornataArrayList()){
            System.out.println("INIZIO GIORNATA: "+ (giornata.getId()+1));

            //PER OGNI CLASSIFICA UPTODATE RICAVO LA CLASSIFICA SUCCESSIVA ED ELIMINO QUELLE VECCHIE
            while (classificaArrayList.get(0).getGiornata()==k) {
                Classifica classificaUpToDate=classificaArrayList.get(0);

                //SETTO LE SQUADRE IN BASE ALLA CLASSIFICA UPTODATE
                squadraArrayList.clear();
                for(Squadra squadra: classificaUpToDate.getSquadra()){
                    squadraArrayList.add(new Squadra(squadra));
                }
                //SIMIULO LA GIORNATA CON OGNI POSSIBILE ESITO
                for(ArrayList<Integer> esito: combinazioneEsiti){
                    int i=0;
                    for (Partita partita: giornata.getPartitaArrayList()){
                        //COLLEGO LA LISTA DELLE SQUADRE NELLA CLASSIFICA UPTODATE ALLE SQUADRE DELLE PARTITE IN MODO DA AGGIORNARE I PUNTI
                        for(Squadra squadra: classificaUpToDate.getSquadra()){
                            if(partita.getSquadra1().getId()==squadra.getId())
                                partita.setSquadra1(squadra);
                            else if(partita.getSquadra2().getId()==squadra.getId())
                                partita.setSquadra2(squadra);
                        }
                        //SIMULO LE PARTITE
                        if(esito.get(i)==1)
                            partita.getSquadra1().updatePunti(3);
                        else if(esito.get(i)==2)
                            partita.getSquadra2().updatePunti(3);
                        else if (esito.get(i) == 0) {
                            partita.getSquadra2().updatePunti(1);
                            partita.getSquadra1().updatePunti(1);
                        }
                        i++;
                    }
                    //SALVO LA NUOVA CLASSIFICA
                    Classifica classifica = new Classifica();
                    classifica.setGiornata(k+1);
                    for(Squadra squadra: classificaUpToDate.getSquadra())
                        classifica.getSquadra().add(new Squadra(squadra));
                    classificaArrayList.add(classifica);
                }
                classificaArrayList.remove(0);
            }
            k++;
        }
        /*int cont=0;
        for (Classifica c: classificaArrayList) {
            if(c.getGiornata()==calendario.giornataArrayList.size()-1) {
                cont++;
                System.out.println("CALSSIFICA ");
                c.getSquadra().sort(new SortByPunti());
                for (Squadra squadra : c.getSquadra()) {
                    System.out.println(squadra.getNome() + "   pt." + squadra.getPunti());
                }
                System.out.println();
            }
        }
        System.out.println("GLI SCENARI POSSIBILI SONO:" +cont);*/
    }








}
