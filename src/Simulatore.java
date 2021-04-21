import java.util.ArrayList;
import java.util.Scanner;

public class Simulatore {

    public static void main(String[] args) {
        Calendario calendario = new Calendario();
        ArrayList<Classifica> classificaArrayList = new ArrayList<>();
        ArrayList<ArrayList<Integer>> combinazioneEsiti = new ArrayList<>();
        SessionManager.getInstance().getSESSION().put("combinazioneEsiti", combinazioneEsiti);
        Classifica classificaIniziale;
        double nPossibiliClassificheFinali = 0;
        ArrayList<Posizione> posizioneArrayList = new ArrayList<>();


        System.out.println("USARE MASSIMO A 5 GIORNATE DAL TERMINE!!!");
        System.out.println("Quante squadre ci sono nella tua lega?");
        Scanner sc=new Scanner(System.in);
        sc.useDelimiter(System.lineSeparator());
        int nSquadre = sc.nextInt();
        ArrayList<Squadra> squadraArrayList = new ArrayList<>();
        for(int l=0; l<nSquadre; l++){
            System.out.println("Nome squadra");
            String nomeSquadra = sc.next();
            System.out.println("Quanti punti ha in quessto momento?");
            int punti = sc.nextInt();

            squadraArrayList.add(new Squadra(l, nomeSquadra, punti));
            posizioneArrayList.add(new Posizione(nSquadre, nomeSquadra));
        }

        System.out.println("Quante giornate ci sono ancora da giocare?");
        int gi=sc.nextInt();
        if(gi % nSquadre-1 > 1 ) {
            System.out.println("Inserisci manualmente il calendario");
            //TODO:DA COMPLETARE
        }
        else if(gi % nSquadre-1 > 0) {
            System.out.println("Inserisci le partite della giornata appena conclusa");
            //TODO:DA COMPLETARE
        }

        //COSTRUISCO LA CLASSIFICA INIZIALE
        classificaIniziale = new Classifica();
        for(Squadra squadra: squadraArrayList){
            classificaIniziale.getSquadra().add(new Squadra(squadra.getId(), squadra.getNome(), squadra.getPunti()));
        }
        classificaIniziale.getSquadra().sort(new SortByPunti());
        classificaIniziale.setGiornata(0);
        classificaArrayList.add(classificaIniziale);

        //CREO IL CALENDARIO
        if(calendario.getGiornataArrayList().size()==0)
            calendario = Business.getInstance().ListMatches(squadraArrayList);

        //STAMPO IL CALENDARIO
        Business.getInstance().stampaCalendario(calendario);

        //CALCOLO TUTTE LE COMBINAZIONI POSSIBILI DEGLI ESITI DELLE PARTITE
        Combinatorio.CombinationRepetition(squadraArrayList.size()/2);



        for(int j=1; j<calendario.getGiornataArrayList().size()+1; j++){
            nPossibiliClassificheFinali += Math.pow(combinazioneEsiti.size(), j);
        }
        double tot = 0;
        double status;

        //PER OGNI GIORNATA
        int k=0;
        for(Giornata giornata: calendario.getGiornataArrayList()){
            System.out.println("INIZIO GIORNATA: "+ (giornata.getId()+1));

            //PER OGNI CLASSIFICA UPTODATE RICAVO LA CLASSIFICA SUCCESSIVA ED ELIMINO QUELLE VECCHIE

            while (classificaArrayList.size()> 0 && classificaArrayList.get(0).getGiornata()==k) {
                Classifica classificaUpToDate=classificaArrayList.get(0);

                //SETTO LE SQUADRE IN BASE ALLA CLASSIFICA UPTODATE
                squadraArrayList.clear();
                for(Squadra squadra: classificaUpToDate.getSquadra()){
                    squadraArrayList.add(new Squadra(squadra));
                }
                //SIMIULO LA GIORNATA CON OGNI POSSIBILE ESITO
                for(ArrayList<Integer> esito: combinazioneEsiti){
                    int i=0;
                    squadraArrayList.clear();
                    for (Partita partita: giornata.getPartitaArrayList()){
                        //COLLEGO LA LISTA DELLE SQUADRE NELLA CLASSIFICA UPTODATE ALLE SQUADRE DELLE PARTITE IN MODO DA AGGIORNARE I PUNTI
                        for(Squadra squadra: classificaUpToDate.getSquadra()){
                            if(partita.getSquadra1().getId()==squadra.getId())
                                partita.getSquadra1().setPunti(squadra.getPunti());
                            else if(partita.getSquadra2().getId()==squadra.getId())
                                partita.getSquadra2().setPunti(squadra.getPunti());
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
                        squadraArrayList.add(partita.getSquadra1());
                        squadraArrayList.add(partita.getSquadra2());
                        i++;
                    }
                    tot++;
                    //SALVO LA NUOVA CLASSIFICA
                    Classifica classifica = new Classifica();
                    classifica.setGiornata(k+1);
                    squadraArrayList.sort(new SortByPunti());
                    for(Squadra squadra: squadraArrayList)
                        classifica.getSquadra().add(new Squadra(squadra));
                    if(k<calendario.getGiornataArrayList().size()-1)
                        classificaArrayList.add(classifica);
                    else{

                        int pos;
                        System.out.println("CALCOLO LE POSSIBILITA'");
                        //TODO IMPLEMENTARE I PARI MERITO
                        for (Posizione posizione: posizioneArrayList) {
                            pos=0;
                            for(Squadra squadra : classifica.getSquadra()){
                                if(posizione.getNome().equalsIgnoreCase(squadra.getNome())){
                                    posizione.updatePosizioni(pos);
                                    if(pos>0 && pos<nSquadre-1){
                                        if(classifica.getSquadra().get(pos-1).getPunti()==squadra.getPunti() || classifica.getSquadra().get(pos+1).getPunti()==squadra.getPunti())
                                            posizione.updatePariMerito(pos);
                                    }
                                    else if(pos==0){
                                        if(classifica.getSquadra().get(pos+1).getPunti()==squadra.getPunti())
                                            posizione.updatePariMerito(pos);
                                    }
                                    else if(pos==nSquadre-1){
                                        if(classifica.getSquadra().get(pos-1).getPunti()==squadra.getPunti())
                                            posizione.updatePariMerito(pos);
                                    }
                                }
                                pos++;
                            }
                        }
                    }
                }
                classificaArrayList.remove(0);

                status = (tot/nPossibiliClassificheFinali)*100;
                status=Math.round(status);
                if(status<0.05)
                    System.out.print("[#                   ] "+ (status) +"%\r");
                else if(status<0.1)
                    System.out.print("[##                  ] "+ (status) +"%\r");
                else if(status<0.15)
                    System.out.print("[###                 ] "+ (status) +"%\r");
                else if(status<0.2)
                    System.out.print("[####                ] "+ (status) +"%\r");
                else if(status<0.25)
                    System.out.print("[#####               ] "+ (status) +"%\r");
                else if(status<0.3)
                    System.out.print("[######              ] "+ (status) +"%\r");
                else if(status<0.35)
                    System.out.print("[#######             ] "+ (status) +"%\r");
                else if(status<0.4)
                    System.out.print("[########            ] "+ (status) +"%\r");
                else if(status<0.45)
                    System.out.print("[#########           ] "+ (status) +"%\r");
                else if(status<0.5)
                    System.out.print("[##########          ] "+ (status) +"%\r");
                else if(status<0.55)
                    System.out.print("[###########         ] "+ (status) +"%\r");
                else if(status<0.6)
                    System.out.print("[############        ] "+ (status) +"%\r");
                else if(status<0.65)
                    System.out.print("[#############       ] "+ (status) +"%\r");
                else if(status<0.7)
                    System.out.print("[##############      ] "+ (status) +"%\r");
                else if(status<0.75)
                    System.out.print("[###############     ] "+ (status) +"%\r");
                else if(status<0.8)
                    System.out.print("[################    ] "+ (status) +"%\r");
                else if(status<0.85)
                    System.out.print("[#################   ] "+ (status) +"%\r");
                else if(status<0.9)
                    System.out.print("[##################  ] "+ (status) +"%\r");
                else if(status<0.95)
                    System.out.print("[################### ] "+ (status) +"%\r");
            }
            k++;
        }
        for (Posizione posizione: posizioneArrayList) {
            System.out.println("La squadra "+ posizione.getNome() + " ottiene questi piazzamenti ");
            for(int l=0; l<nSquadre; l++) {
                System.out.println((l + 1) + "°:" + posizione.getPosizioni().get(l) + " (" + posizione.getPariMerito().get(l) + " pari merito)");
            }
        }
    }
}
