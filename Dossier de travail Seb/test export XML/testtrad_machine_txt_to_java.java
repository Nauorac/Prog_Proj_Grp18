import java.util.HashMap;
import java.util.Map;

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

// Invariant 3: ∀x ∈ Gares, ∀y ∈ nb_train, C(x,y) ∈ ℕ
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