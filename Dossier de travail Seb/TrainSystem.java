import java.util.HashMap;
import java.util.Map;

// **-SEB-*******************************************************
// LE CODE ICI REPREND LES VARIABLES ET INVARIANTS DE LA MACHINE

public class TrainSystem {
    private Map<String, Integer> Gares = new HashMap<>();
    private Map<String, Integer> Gares_urgence = new HashMap<>();
    private Map<String, Integer> nb_train = new HashMap<>();
    private Map<String, Integer> nb_train_urgence = new HashMap<>();
    private Map<String, Integer> Line = new HashMap<>();
    private Map<String, String> Line_urgence = new HashMap<>();
    private Map<String, Boolean> Aiguillages = new HashMap<>();
    private Map<String, Integer> Second_Line = new HashMap<>();
    private Map<String, Boolean> In_First_Line = new HashMap<>();
    private Map<String, Boolean> In_Second_Line = new HashMap<>();
    private Map<String, String> Line_Gare = new HashMap<>();
    private Map<String, Boolean> Freinage_Line = new HashMap<>();
    private int nb_train_total;
    private int temp;
    private int temp2;
    private boolean State_system;
    private boolean State_brake;
    private Map<String, Integer> GarePrincipale = new HashMap<>();
    private Map<String, Integer> GareSecondaire = new HashMap<>();
    private Map<String, Boolean> Quai = new HashMap<>();
    private Map<String, Boolean> Feux = new HashMap<>();
    private Map<String, Boolean> Appli_Voyageur = new HashMap<>();

// Définition des invariants

// Invariant 1: Gares ∈ {Bruxelles, Namur, Liege}→ℕ
	Map<String, Integer> Gares = new HashMap<>();
	Gares.put("Bruxelles", 0);
	Gares.put("Namur", 0);
	Gares.put("Liege", 0);

// Invariant 2: nb_train ∈ {Bruxelles, Namur, Liege, Huy, Ottignies, Leuven}→ℕ
	Map<String, Integer> nb_train = new HashMap<>();
	nb_train.put("Bruxelles", 0);
	nb_train.put("Namur", 0);
	nb_train.put("Liege", 0);
	nb_train.put("Huy", 0);
	nb_train.put("Ottignies", 0);
	nb_train.put("Leuven", 0);

    // @Invariant 3: ∀x ∈ Gares, ∀y ∈ nb_train, C(x,y) ∈ ℕ
	for (String gare : Gares.keySet()) {
		for (String ville : nb_train.keySet()) {
			int valeur = 0; // à remplacer par la valeur de C(x,y) si elle est définie
			if (valeur < 0) {
				throw new AssertionError("La valeur de C(" + gare + ", " + ville + ") est négative");
			}
		}
	}

// Invariant 4: ∀x ∈ Gares, ∑y ∈ nb_train, C(x,y) ≤ 1
	for (String gare : Gares.keySet()) {
		int somme = 0;
		for (String ville : nb_train.keySet()) {
			int valeur = 0; // à remplacer par la valeur de C(x,y) si elle est définie
			somme += valeur;
		}
		if (somme > 1) {
			throw new AssertionError("La somme des C(" + gare + ", y) est supérieure à 1");
		}
	}

// Invariant 5: ∀y ∈ nb_train, ∑x ∈ Gares, C(x,y) = 1
for (String ville : nb_train.keySet()) {
    int somme = 0;
    for (String gare : Gares.keySet()) {
        int valeur = 0; // à remplacer par la valeur de C(x,y) si elle est définie
        somme += valeur;
    }
    if (somme != 1) {
        throw new AssertionError("La somme des C(x, " + ville + ") est différente de 1");
    }
}

// @inv6 nb_train_urgence ∈ {Huy,Ottignies,Leuven} → ℕ
	Map<String, Integer> nb_train_urgence = new HashMap<>();
	nb_train_urgence.put("Huy", 0);
	nb_train_urgence.put("Ottignies", 0);
	nb_train_urgence.put("Leuven", 0);

// @inv7 Line_urgence ∈ {Namur↦Bruxelles,Bruxelles↦Namur, Namur↦Liege, Liege↦Namur, Bruxelles↦Liege, Liege↦Bruxelles}→dom(Gares_urgence)
	Map<String, String> Line_urgence = new HashMap<>();
	Line_urgence.put("Namur↦Bruxelles", "");
	Line_urgence.put("Bruxelles↦Namur", "");
	Line_urgence.put("Namur↦Liege", "");
	Line_urgence.put("Liege↦Namur", "");
	Line_urgence.put("Bruxelles↦Liege", "");
	Line_urgence.put("Liege↦Bruxelles", "");

	Set<String> Gares_urgence = new HashSet<>();
	Gares_urgence.add("Bruxelles");
	Gares_urgence.add("Namur");
	Gares_urgence.add("Liege");

// @inv8 Second_Line ∈ {Namur↦Bruxelles, Bruxelles↦Namur, Namur↦Liege, Liege↦Namur, Bruxelles↦Liege, Liege↦Bruxelles} → ℕ
	Map<String, Integer> Second_Line = new HashMap<>();
	Second_Line.put("Namur↦Bruxelles", 0);
	Second_Line.put("Bruxelles↦Namur", 0);
	Second_Line.put("Namur↦Liege", 0);
	Second_Line.put("Liege↦Namur", 0);
	Second_Line.put("Bruxelles↦Liege", 0);
	Second_Line.put("Liege↦Bruxelles", 0);

// @inv9 In_Second_Line ∈ {Namur↦Bruxelles, Bruxelles↦Namur, Namur↦Liege, Liege↦Namur, Bruxelles↦Liege, Liege↦Bruxelles} → BOOL
	Map<String, Boolean> In_Second_Line = new HashMap<>();
	In_Second_Line.put("Namur↦Bruxelles", false);
	In_Second_Line.put("Bruxelles↦Namur", false);
	In_Second_Line.put("Namur↦Liege", false);
	In_Second_Line.put("Liege↦Namur", false);
	In_Second_Line.put("Bruxelles↦Liege", false);
	In_Second_Line.put("Liege↦Bruxelles", false);

//  @inv10 Line_Gare ∈ {Namur↦Bruxelles, Bruxelles↦Namur, Namur↦Liege, Liege↦Namur, Bruxelles↦Liege, Liege↦Bruxelles} → dom(Gares)
    Map<String, String> Line_Gare = new HashMap<>();
    Line_Gare.put("Namur↦Bruxelles", "");
    Line_Gare.put("Bruxelles↦Namur", "");
    Line_Gare.put("Namur↦Liege", "");
    Line_Gare.put("Liege↦Namur", "");
    Line_Gare.put("Bruxelles↦Liege", "");
    Line_Gare.put("Liege↦Bruxelles", "");

    Set<String> Gares = new HashSet<>();
    Gares.add("Bruxelles");
    Gares.add("Namur");
    Gares.add("Liege");

//  @inv11 nb_train_total ∈ ℕ
    int nb_train_total = 0;

//  @inv12 Freinage_Line ∈ {Bruxelles, Namur, Liege} → BOOL
    Map<String, Boolean> Freinage_Line = new HashMap<>();
    Freinage_Line.put("Bruxelles", false);
    Freinage_Line.put("Namur", false);
    Freinage_Line.put("Liege", false);

//   @inv13 temp ∈ ℕ
    int temp = 0;

//   @inv14 temp2 ∈ ℕ
    int temp2 = 0;

//   @inv15 State_system ∈ BOOL
    boolean State_system = false;

//   @inv16 State_brake ∈ BOOL
    boolean State_brake = false;

//   @inv17 In_First_Line ∈ {Namur↦Bruxelles, Bruxelles↦Namur, Namur↦Liege, Liege↦Namur, Bruxelles↦Liege, Liege↦Bruxelles} → BOOL
    Map<String, Boolean> In_First_Line = new HashMap<>();
    In_First_Line.put("Namur↦Bruxelles", false);
    In_First_Line.put("Bruxelles↦Namur", false);
    In_First_Line.put("Namur↦Liege", false);
    In_First_Line.put("Liege↦Namur", false);
    In_First_Line.put("Bruxelles↦Liege", false);
    In_First_Line.put("Liege↦Bruxelles", false);

//   @inv18 temp3 ∈ BOOL
    boolean temp3 = false;

//   @inv19 temp4 ∈ BOOL
    boolean temp4 = false;

//   @inv20 GarePrincipale ∈ { Bruxelles, Namur, Liege}→ℕ
    Map<String, Integer> GarePrincipale = new HashMap<>();
    GarePrincipale.put("Bruxelles", 0);
    GarePrincipale.put("Namur", 0);
    GarePrincipale.put("Liege", 0);

//    @inv21 GareSecondaire ∈ { Huy, Ottignies, Leuven}→ℕ
    Map<String, Integer> GareSecondaire = new HashMap<>();
    GareSecondaire.put("Huy", 0);
    GareSecondaire.put("Ottignies", 0);
    GareSecondaire.put("Leuven", 0);
    
//   @inv22 Quai ∈ {Namur↦Quai1, Namur↦Quai2, Bruxelles↦Quai1, Bruxelles↦Quai2, Liege↦Quai1, Liege↦Quai2,Huy↦Quai1,Ottignies↦Quai1,Leuven↦Quai1}→BOOL
    Map<String, Boolean> Quai = new HashMap<>();
    Quai.put("Namur↦Quai1", false);
    Quai.put("Namur↦Quai2", false);
    Quai.put("Bruxelles↦Quai1", false);
    Quai.put("Bruxelles↦Quai2", false);
    Quai.put("Liege↦Quai1", false);
    Quai.put("Liege↦Quai2", false);
    Quai.put("Huy↦Quai1", false);
    Quai.put("Ottignies↦Quai1", false);
    Quai.put("Leuven↦Quai1", false);

//  @inv23 Feux ∈ {Lvn_Lge, Lge_Lvn, Lvn_Bxl, Bxl_Lvn, Bxl_Ott, Ott_Bxl, Ott_Nam, Nam_Ott, Huy_Nam, Nam_Huy, Lge_Huy, Huy_Lge} → BOOL
    Map<String, Boolean> Feux = new HashMap<>();
    Feux.put("Lvn_Lge", false);
    Feux.put("Lge_Lvn", false);
    Feux.put("Lvn_Bxl", false);
    Feux.put("Bxl_Lvn", false);
    Feux.put("Bxl_Ott", false);
    Feux.put("Ott_Bxl", false);
    Feux.put("Ott_Nam", false);
    Feux.put("Nam_Ott", false);
    Feux.put("Huy_Nam", false);
    Feux.put("Nam_Huy", false);
    Feux.put("Lge_Huy", false);
    Feux.put("Huy_Lge", false);

//  theorem @inv24 Station_to_Line_Feux ∈ {Namur↦Bruxelles, Namur↦Liege, Bruxelles↦Namur, Bruxelles↦Liege, Liege↦Bruxelles, Liege↦Namur} → dom(Feux)

//  @inv25 Secondary_Station_to_Line_Feux ∈ { Namur↦Bruxelles,Bruxelles↦Namur, Namur↦Liege, Liege↦Namur, Liege↦Bruxelles, Bruxelles↦Liege} → dom(Feux)

// @inv26 Line_to_Station_Feux ∈ {Namur↦Bruxelles, Namur↦Liege, Bruxelles↦Namur, Bruxelles↦Liege, Liege↦Bruxelles, Liege↦Namur} → dom(Feux)

// @inv27 Line_to_Secondary_Station_Feux ∈ {Namur↦Bruxelles, Namur↦Liege, Bruxelles↦Namur, Bruxelles↦Liege, Liege↦Bruxelles, Liege↦Namur} → dom(Feux)

// @inv28 Appli_Voyageur ∈ {Namur,Liege,Bruxelles,Huy,Leuven,Ottignies} → BOOL    
    Map<String, Boolean> Appli_Voyageur = new HashMap<>();
    Appli_Voyageur.put("Namur", false);
    Appli_Voyageur.put("Liege", false);
    Appli_Voyageur.put("Bruxelles", false);
    Appli_Voyageur.put("Huy", false);
    Appli_Voyageur.put("Leuven", false);
    Appli_Voyageur.put("Ottignies", false);




    // *-SEB-***********************************************************************************************
    // LE CODE CI DESSOUS REPREND DEPUIS LE CONTEXT, ET LE DEBUT DE LA MACHINE, TOUT CE QUI CONCERNE L EVENEMENT
    // APPARITION TRAIN
    // ********************************************************************

    public class TrainSystem {
        public enum Gare {
            Namur, Bruxelles, Liege, Huy, Ottignies, Leuven
        }
    
        public enum Quai {
            Quai1, Quai2
        }
    
        private Map<Gare, Integer> gares;
        private Map<Gare, Integer> garesUrgence;
        private Map<Gare, Integer> nbTrain;
        private int nbTrainTotal;
        private Map<Gare, Boolean> freinageLine;
        private Map<Gare, Map<Quai, Boolean>> quai;
        private boolean stateSystem;
        private boolean stateBrake;
        private Map<Gare, Integer> garePrincipale;
    
        public TrainSystem() {
            initialisation();
        }
    
        private void initialisation() {
            gares = new HashMap<>();
            gares.put(Gare.Namur, 1);
            gares.put(Gare.Bruxelles, 2);
            gares.put(Gare.Liege, 3);
    
            nbTrain = new HashMap<>();
            for (Gare g : Gare.values()) {
                nbTrain.put(g, 0);
            }
    
            nbTrainTotal = 0;
            freinageLine = new HashMap<>();
            for (Gare g : new Gare[]{Gare.Bruxelles, Gare.Namur, Gare.Liege}) {
                freinageLine.put(g, false);
            }
    
            stateSystem = false;
            stateBrake = false;
    
            garePrincipale = new HashMap<>();
            garePrincipale.put(Gare.Bruxelles, 0);
            garePrincipale.put(Gare.Namur, 0);
            garePrincipale.put(Gare.Liege, 0);
    
            quai = new HashMap<>();
            for (Gare g : Gare.values()) {
                Map<Quai, Boolean> quaiGare = new HashMap<>();
                quaiGare.put(Quai.Quai1, false);
                if (g == Gare.Namur || g == Gare.Bruxelles || g == Gare.Liege) {
                    quaiGare.put(Quai.Quai2, false);
                }
                quai.put(g, quaiGare);
            }
        }

    // Evenement APPARITION TRAIN
    public void apparitionTrain(Gare selectGare) {
        if (nbTrain.get(selectGare) < 2 && nbTrainTotal < 4 && !freinageLine.get(selectGare) && !stateSystem && !stateBrake
                && garePrincipale.containsKey(selectGare) && !quai.get(selectGare).get(Quai.Quai1)) {
            nbTrain.put(selectGare, nbTrain.get(selectGare) + 1);
            nbTrainTotal++;
            quai.get(selectGare).put(Quai.Quai1, true);
        }
    }
    // Autres méthodes de gestion des événements et de mise à jour des variables peuvent être ajoutées ici
        
    public static void main(String[] args) {
        TrainSystem trainSystem




