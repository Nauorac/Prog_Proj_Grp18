import java.util.HashMap;
import java.util.Map;

public class TrainSystems {
    public enum Gare {
        Namur, Bruxelles, Liege, Huy, Ottignies, Leuven
    }
    public enum Quai {
        Quai1, Quai2
    }

    private Map<Gare, Integer> gares;
    private Map<Gare, Integer> garesUrgence;
    private Map<Gare, Integer> garePrincipale;
    private Map<Gare, Integer> gareSecondaire;
    private Map<Gare, Map<Quai, Boolean>> quai;
    private Map<Gare, Integer> nbTrain;
    private Map<Gare, Integer> nbTrainUrgence;
    private int nbTrainTotal;
    private Map<String, Gare> Line_Gare ;
    private Map<String, Integer> Line ;
    private Map<String, Gare> Line_Urgence ;
    private Map<String, Integer> Second_Line ;
    private Map<String, Boolean> In_First_Line ;
    private Map<String, Boolean> In_Second_Line ;
    private Map<String, Boolean> Aiguillages ;
    private Map<Gare, Boolean> freinageLine;
    private boolean stateSystem;
    private boolean stateBrake;
    private Map<Gare, Boolean> Appli_Voyageur ;
    private Integer temp ;
    private Integer temp2 ;
    private Boolean temp3 ;
    private Boolean temp4 ;


    //nb_train_ur?,  4variables feux,
    public TrainSystems() {
        initialisation();
    }

    private void initialisation() {
        //@ inv 1 (\forall Gare g; g == Gare.Bruxelles || g == Gare.Namur || g == Gare.Liege; Gares.containsKey(g));
        gares = new HashMap<>();
        gares.put(Gare.Namur, 1);
        gares.put(Gare.Bruxelles, 2);
        gares.put(Gare.Liege, 3);

        garePrincipale = new HashMap<>();
        garePrincipale.put(Gare.Bruxelles, 0);
        garePrincipale.put(Gare.Namur, 0);
        garePrincipale.put(Gare.Liege, 0);

        gareSecondaire = new HashMap<>();
        gareSecondaire.put(Gare.Ottignies, 0);
        gareSecondaire.put(Gare.Huy, 0);
        gareSecondaire.put(Gare.Leuven, 0);

        nbTrain = new HashMap<>();
        for (Gare g : Gare.values()) {
            nbTrain.put(g, 0);
        }
        nbTrainUrgence = new HashMap<>();
        nbTrainUrgence.put(Gare.Huy,0) ;
        nbTrainUrgence.put(Gare.Leuven,0) ;
        nbTrainUrgence.put(Gare.Ottignies,0) ;
        nbTrainTotal = 0;
        freinageLine = new HashMap<>();
        for (Gare g : new Gare[]{Gare.Bruxelles, Gare.Namur, Gare.Liege}) {
            freinageLine.put(g, false);
        }

        stateSystem = false;
        stateBrake = false;
        temp = 0;
        temp2 = 0;
        temp3 = false ;
        temp4 = false ;

        quai = new HashMap<>();
        for (Gare g : Gare.values()) {
            Map<Quai, Boolean> quaiGare = new HashMap<>();
            quaiGare.put(Quai.Quai1, false);
            if (g == Gare.Namur || g == Gare.Bruxelles || g == Gare.Liege) {
                quaiGare.put(Quai.Quai2, false);
            }
            quai.put(g, quaiGare);
        }
        Line_Gare = new HashMap<>();
        Line_Gare.put("Namur↦Bruxelles", Gare.Bruxelles);
        Line_Gare.put("Bruxelles↦Namur", Gare.Namur);
        Line_Gare.put("Namur↦Liege", Gare.Liege);
        Line_Gare.put("Liege↦Namur", Gare.Namur);
        Line_Gare.put("Bruxelles↦Liege", Gare.Liege);
        Line_Gare.put("Liege↦Bruxelles", Gare.Bruxelles);

        Line = new HashMap<>();
        Line.put("Namur↦Bruxelles",0);
        Line.put("Bruxelles↦Namur",0);
        Line.put("Namur↦Liege",0) ;
        Line.put("Liege↦Namur",0) ;
        Line.put("Bruxelles↦Liege",0) ;
        Line.put("Liege↦Bruxelles",0) ;

        Second_Line = new HashMap<>();
        Second_Line.put("Namur↦Bruxelles",0);
        Second_Line.put("Bruxelles↦Namur",0);
        Second_Line.put("Namur↦Liege",0) ;
        Second_Line.put("Liege↦Namur",0) ;
        Second_Line.put("Bruxelles↦Liege",0) ;
        Second_Line.put("Liege↦Bruxelles",0) ;

        Line_Urgence = new HashMap<>();
        Line_Urgence.put("Namur↦Bruxelles", Gare.Ottignies);
        Line_Urgence.put("Bruxelles↦Namur", Gare.Ottignies);
        Line_Urgence.put("Namur↦Liege", Gare.Huy);
        Line_Urgence.put("Liege↦Namur", Gare.Huy);
        Line_Urgence.put("Bruxelles↦Liege", Gare.Leuven);
        Line_Urgence.put("Liege↦Bruxelles", Gare.Leuven);

        In_First_Line = new HashMap<>();
        In_First_Line.put("Namur↦Bruxelles", false);
        In_First_Line.put("Bruxelles↦Namur", false);
        In_First_Line.put("Namur↦Liege", false);
        In_First_Line.put("Liege↦Namur", false);
        In_First_Line.put("Bruxelles↦Liege", false);
        In_First_Line.put("Liege↦Bruxelles", false);

        In_Second_Line = new HashMap<>();
        In_Second_Line.put("Namur↦Bruxelles", false);
        In_Second_Line.put("Bruxelles↦Namur", false);
        In_Second_Line.put("Namur↦Liege", false);
        In_Second_Line.put("Liege↦Namur", false);
        In_Second_Line.put("Bruxelles↦Liege", false);
        In_Second_Line.put("Liege↦Bruxelles", false);

        Aiguillages = new HashMap<>();
        Aiguillages.put("Namur↦Bruxelles", false);
        Aiguillages.put("Bruxelles↦Namur", false);
        Aiguillages.put("Namur↦Liege", false);
        Aiguillages.put("Liege↦Namur", false);
        Aiguillages.put("Bruxelles↦Liege", false);
        Aiguillages.put("Liege↦Bruxelles", false);

        Appli_Voyageur = new HashMap<>();
        Appli_Voyageur.put(Gare.Namur, false);
        Appli_Voyageur.put(Gare.Liege, false);
        Appli_Voyageur.put(Gare.Bruxelles, false);
        Appli_Voyageur.put(Gare.Huy, false);
        Appli_Voyageur.put(Gare.Leuven, false);
        Appli_Voyageur.put(Gare.Ottignies, false);
    }

    public void apparitionTrain(Gare selectGare) {
        if (garePrincipale.containsKey(selectGare)){
            if (nbTrain.get(selectGare) < 2 && nbTrainTotal < 4 && !freinageLine.get(selectGare) && !stateSystem && !stateBrake
                && !quai.get(selectGare).get(Quai.Quai1)) {
            nbTrain.put(selectGare, nbTrain.get(selectGare) + 1);
            nbTrainTotal++;
            quai.get(selectGare).put(Quai.Quai1, true);
            }
        }
        else {
            System.out.println("no222");
        }
    }
    //Manque une condition avec les feux
    public void MainStation_To_Line (Gare selectGareDepart, Gare selectGareDestination, Quai selectQuai ){
        if (nbTrain.get(selectGareDepart) >= 1 && Line.get(selectGareDepart.name()+"↦"+selectGareDestination.name()) < 1
                && !stateSystem && !stateBrake && (quai.get(selectGareDepart).get(selectQuai))){
        nbTrain.put(selectGareDepart, nbTrain.get(selectGareDepart) - 1) ;
        quai.get(selectGareDepart).put(selectQuai, false);
        Line.put(selectGareDepart.name()+"↦"+selectGareDestination.name(), Line.get(selectGareDepart.name()+"↦"+selectGareDestination.name()) + 1) ;
        if (nbTrain.get(selectGareDepart) == 1){
            Appli_Voyageur.put(selectGareDepart, false);}
        }
        else {
        System.out.println("no");
        }
    }
    //Manque condition avec les feux. MAUVAISE !
    public void Line_To_Secondary_Station(String SelectTrain, Gare SelectGareUrgence) {
        if (Line_Urgence.get(SelectTrain) == SelectGareUrgence) {
            if (Line.get(SelectTrain) >= 1 && nbTrainUrgence.get(SelectGareUrgence) < 1
                    &&!stateBrake && !stateSystem && !quai.get(SelectGareUrgence).get(Quai.Quai1) && Aiguillages.get(SelectTrain)
                     && Appli_Voyageur.get(SelectGareUrgence)) {
                Line.put(SelectTrain, Line.get(SelectTrain) - 1);
                nbTrainUrgence.put(SelectGareUrgence, nbTrainUrgence.get(SelectGareUrgence) + 1);
                In_First_Line.put(SelectTrain, false);
                quai.get(SelectGareUrgence).put(Quai.Quai1, true);
            }
        }
    }

    public void Secondary_Station_To_Second_Line (Gare SelectGare, String SelectTrain){
        if(Line_Urgence.get(SelectTrain) == SelectGare && nbTrainUrgence.get(SelectGare) >= 1 && Second_Line.get(SelectTrain) < 1
                && Aiguillages.get(SelectTrain) && !stateSystem && !stateBrake
                && quai.get(SelectGare).get(Quai.Quai1)){
            nbTrainUrgence.put(SelectGare, nbTrainUrgence.get(SelectGare) - 1);
            Second_Line.put(SelectTrain,Second_Line.get(SelectTrain)+1);
            In_Second_Line.put(SelectTrain,true) ;
            quai.get(SelectGare).put(Quai.Quai1,false) ;
            Appli_Voyageur.put(SelectGare,false) ;
        }
    }
    public void Second_Line_To_MainStation (Gare SelectGareDepart, Gare SelectGareArrivee){
        if(SelectGareDepart != SelectGareArrivee && Second_Line.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name())>=1
                && nbTrain.get(SelectGareArrivee)<1 && In_Second_Line.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name())
                && !In_First_Line.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name())
                && !stateSystem && !stateBrake && !quai.get(SelectGareArrivee).get(Quai.Quai1)
                && !Appli_Voyageur.get(SelectGareArrivee)){
            Second_Line.put(SelectGareDepart.name()+"↦"+SelectGareArrivee.name(), Second_Line.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name())-1 );
            nbTrain.put(SelectGareArrivee, nbTrain.get(SelectGareArrivee)+1);
            In_Second_Line.put(SelectGareDepart.name()+"↦"+SelectGareArrivee.name(),false);
            quai.get(SelectGareArrivee).put(Quai.Quai1,true) ;

        }
    }
    public void Line_To_Second_Line ( Gare SelectGareDepart,Gare SelectGareArrivee){
        if(SelectGareDepart != SelectGareArrivee && Line.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name()) == 1
                && Second_Line.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name())<1
                && !Aiguillages.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name())
                && !stateSystem && !stateBrake){}
    }

    public void Choose_Aiguillage( String SelectLine, Boolean SelectAction ){
        if(!Aiguillages.get(SelectLine)) {
            Aiguillages.put(SelectLine, SelectAction);
        }
    }
    public void Message_Application (String SelectTrain, Gare SelectGareArrivee){
        if ((Line_Urgence.get(SelectTrain) == SelectGareArrivee && Aiguillages.get(SelectTrain) && Line.get(SelectTrain)>=1) || Line_Gare.get(SelectTrain) == SelectGareArrivee && Second_Line.get(SelectTrain) >= 1){}
        if (!Appli_Voyageur.get(SelectGareArrivee)){
            Appli_Voyageur.put(SelectGareArrivee,true);
        }
    }
    // Autres méthodes de gestion des événements et de mise à jour des variables peuvent être ajoutées ici
    //  Secondary_TO_Line, Freinage_Urgence, Marche_arriere
    public static void main(String[] args) {
        TrainSystems trainSystems = new TrainSystems();
        trainSystems.apparitionTrain(TrainSystems.Gare.Namur);
        trainSystems.MainStation_To_Line(Gare.Namur, Gare.Bruxelles, Quai.Quai1);
        trainSystems.Choose_Aiguillage("Namur↦Bruxelles", true);
        trainSystems.Message_Application("Namur↦Bruxelles",Gare.Ottignies);
        trainSystems.Line_To_Secondary_Station("Namur↦Bruxelles",Gare.Ottignies);
        trainSystems.Secondary_Station_To_Second_Line(Gare.Ottignies, "Namur↦Bruxelles" );
        trainSystems.Second_Line_To_MainStation(Gare.Namur, Gare.Bruxelles);
        trainSystems.Message_Application("Namur↦Bruxelles",Gare.Bruxelles);
        Map valeurtest = trainSystems.Line;
        Map valeurtest2 = trainSystems.nbTrain;
        Map valeurtest3 = trainSystems.nbTrainUrgence;
        Map valeurtest4 = trainSystems.Second_Line;
        System.out.println(valeurtest) ;
        System.out.println(valeurtest4) ;
        System.out.println(valeurtest2) ;
        System.out.println(valeurtest3) ;
    }
    }
