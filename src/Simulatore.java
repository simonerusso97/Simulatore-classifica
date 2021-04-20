import java.util.ArrayList;
import java.util.Scanner;

public class Simulatore {

    public static void main(String[] args) {
        System.out.println("Quale squadra ti interessa");
        Scanner sc=new Scanner(System.in);
        String squadraInteresse = sc.nextLine();
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


        double totRipetizioni = Math.pow(calendario.getGiornataArrayList().size(), combinazioneEsiti.size());
        double tot = 0;
        double status;

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
                    tot++;
                    status = (tot/totRipetizioni)*100;
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
                classificaArrayList.remove(0);
            }
            k++;
        }
        int cont=0;
        int pos;
        int pt=0;
        int pariMerito=0;
        for (Classifica c: classificaArrayList) {
                System.out.println("CALSSIFICA ");
                c.getSquadra().sort(new SortByPunti());
                pos=0;
                for (Squadra squadra : c.getSquadra()) {
                    if(pos < 3 && squadra.getNome().equals(squadraInteresse)){
                        cont++;
                    }
                    if(pos==2 && !squadra.getNome().equals(squadraInteresse)){
                        pt=squadra.getPunti();
                    }
                    if(pos>2 && squadra.getNome().equals(squadraInteresse) && squadra.getPunti()==pt){
                        cont++;
                        pariMerito++;
                    }

                    System.out.println(squadra.getNome() + "   pt." + squadra.getPunti());
                    pos++;
                }
                System.out.println("----------");
        }

        System.out.println(squadraInteresse + " ha " + cont + "scenari ha favore su "
                +classificaArrayList.size() +", di cui " + pariMerito +" a parimerito con il 3°");
    }
}
