import javax.sound.sampled.Line;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
//Version 6
public class TrainSystems {
    /*
    CONTEXT EVENTB retranscrit en JAVA :
        Contexte contenant 3 constantes "Gare", "Quai", "Traffic_Light"
     */
    public enum Gare {
        Namur, Bruxelles, Liege, Huy, Ottignies, Leuven
    }
    public enum Quai {
        Quai1, Quai2
    }

    public enum Traffic_Light {
        Lvn_Lge, Lge_Lvn, Lvn_Bxl, Bxl_Lvn, Bxl_Ott, Ott_Bxl, Ott_Nam, Nam_Ott, Huy_Nam, Nam_Huy, Lge_Huy, Huy_Lge
    }
    /*
    Variables du code MACHINE. Ce code reprend tous les raffinements en un seul fichier JAVA
    Les variables proviennent donc de tous les raffinements.
    Type de variable : MAP, BOOL, Integer, ...
    Exemple avec Map <Gare, Integer> nbTrain => {Liege=0, Huy=0, Bruxelles=1, Namur=0, Ottignies=0, Leuven=0}
    Cela représente le nombre de train présent dans les gares.
     */
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
    private Map<Traffic_Light, Boolean> Feux ;
    private Map<String, Traffic_Light> Station_To_Line_Feux ;
    private Map<String, Traffic_Light> Secondary_Station_To_Line_Feux ;
    private Map<String, Traffic_Light> Line_To_Station_Feux ;
    private Map<String, Traffic_Light> Line_to_Secondary_Station_Feux ;

    private Map<Gare, Boolean> freinageLine;
    private boolean stateSystem;
    private boolean stateBrake;
    private Map<Gare, Boolean> Appli_Voyageur ;
    private Integer temp ;
    private Integer temp2 ;
    private Boolean temp3 ;
    private Boolean temp4 ;
	
	//Definition des  invariants 
	
	//inv1
	/*@ invariant Gares.keySet().containsAll(\old(Gares.keySet())) 
               && Gares.keySet().size() == 3 
               && (\forall String g : Gares.keySet(); 
                     (g.equals("Bruxelles") || g.equals("Namur") || g.equals("Liege")) ==> Gares.get(g) >= 0);
	*/
	
	//inv2
	/*@ invariant nb_train.keySet().equals(\old(nb_train.keySet())); @*/
	/*@ invariant (\forall String gare; nb_train.keySet().contains(gare);
                    nb_train.get(gare) >= 0); @*/
	/*@ invariant (\forall String gare; !nb_train.keySet().contains(gare) ||
                    (nb_train.keySet().contains(gare) &&
                     (gare.equals("Bruxelles") || gare.equals("Namur") ||
                      gare.equals("Liege") || gare.equals("Huy") ||
                      gare.equals("Ottignies") || gare.equals("Leuven")))); @*/
					  
	//inv3
	/*@Invariant 3: ∀x ∈ Gares, ∀y ∈ nb_train, C(x,y) ∈ ℕ
	// C(x,y) représente une valeur entière non négative associée à chaque paire (x,y) de gares et de villes.
	// Cet invariant spécifie que pour toute gare x dans l'ensemble Gares et pour toute ville y dans l'ensemble nb_train, la valeur C(x,y) est un entier naturel (c'est-à-dire un entier positif ou nul).
	// La boucle parcourt toutes les paires (x,y) et vérifie que la valeur de C(x,y) est non négative. Si la valeur est négative, une AssertionError est levée pour signaler une violation de l'invariant.@*/

	// @Invariant 4:
	// For all gare x in Gares and all ville y in nb_train, C(x,y) is a natural number
	// For all gare x in Gares, the sum of C(x,y) over all ville y in nb_train is at most 1
	/*@ 
	\forall String gare; Gares.keySet().contains(gare) ==> (\forall String ville; nb_train.keySet().contains(ville) ==> C(gare,ville) >= 0 && C(gare,ville) \in \nat);
	\forall String gare; Gares.keySet().contains(gare) ==> (\sum String ville; nb_train.keySet().contains(ville) ; C(gare,ville)) <= 1;
	@*/

	for (String gare : Gares.keySet()) {
		int somme = 0;
		for (String ville : nb_train.keySet()) {
		int valeur = 0; // à remplacer par la valeur de C(x,y) si elle est définie
		somme += valeur;
		}
		if (somme > 1) {
		throw new AssertionError("La somme des C(" + gare + ", y) est supérieure à 1");
		
	}
	
	// @Invariant 5: ∀y ∈ nb_train, ∑x ∈ Gares, C(x,y) = 1
	/*@ \forall String ville; nb_train.keySet().contains(ville) ==> (\sum String gare; Gares.keySet().contains(gare) ; C(gare,ville)) == 1;
	@*/

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

	// @Invariant 6: nb_train_urgence ∈ {Huy,Ottignies,Leuven} → ℕ
	/*@ \forall String ville; nb_train_urgence.keySet().contains(ville) ==> (ville.equals("Huy") || ville.equals("Ottignies") || ville.equals("Leuven")) && nb_train_urgence.get(ville) \in \nat;
	@*/
		Map<String, Integer> nb_train_urgence = new HashMap<>();
		nb_train_urgence.put("Huy", 0);
		nb_train_urgence.put("Ottignies", 0);
		nb_train_urgence.put("Leuven", 0);
		
	// @inv7 Line_urgence ∈ {Namur↦Bruxelles,Bruxelles↦Namur, Namur↦Liege, Liege↦Namur, Bruxelles↦Liege, Liege↦Bruxelles}→dom(Gares_urgence)
	// Toutes les gares d'urgence appartiennent à l'image de la ligne d'urgence
	for (String gare : Gares_urgence) {
		boolean belongsToLine = false;
		for (String line : Line_urgence.keySet()) {
			if (line.contains(gare)) {
				belongsToLine = true;
				break;
			}
		}
		if (!belongsToLine) {
			throw new AssertionError("La gare d'urgence " + gare + " n'appartient pas à l'image de la ligne d'urgence");
		}
	}

	// @inv8 Second_Line ∈ {Namur↦Bruxelles, Bruxelles↦Namur, Namur↦Liege, Liege↦Namur, Bruxelles↦Liege, Liege↦Bruxelles} → ℕ
	// La seconde ligne est une des lignes de la carte
	boolean isSecondLine = false;
	for (String line : Line_urgence.keySet()) {
		if (!line.equals("Namur↦Bruxelles") && !line.equals("Bruxelles↦Namur") && !line.equals("Namur↦Liege") && !line.equals("Liege↦Namur") && !line.equals("Bruxelles↦Liege") && !line.equals("Liege↦Bruxelles")) {
		continue;
		}
		if (line.equals("Namur↦Bruxelles") || line.equals("Bruxelles↦Namur") || line.equals("Namur↦Liege") || line.equals("Liege↦Namur") || line.equals("Bruxelles↦Liege") || line.equals("Liege↦Bruxelles")) {
			isSecondLine = true;
			break;
		}
	}
	if (!isSecondLine) {
		throw new AssertionError("La seconde ligne n'est pas une des lignes de la carte");
	}
	
	//@inv9 In_Second_Line ∈ {Namur, Bruxelles, Liege} → boolean
		Map<String, Boolean> In_Second_Line = new HashMap<>();
		In_Second_Line.put("Namur", false);
		In_Second_Line.put("Bruxelles", false);
		In_Second_Line.put("Liege", false);
	

	//@inv10 Line_Gare ∈ {Namur↦Bruxelles, Bruxelles↦Namur, Namur↦Liege, Liege↦Namur, Bruxelles↦Liege, Liege↦Bruxelles} → dom(Gares)
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

	//@inv11 nb_train_total ∈ ℕ
		int nb_train_total = 0;

	//@inv12 Freinage_Line ∈ {Bruxelles, Namur, Liege} → BOOL
		Map<String, Boolean> Freinage_Line = new HashMap<>();
		Freinage_Line.put("Bruxelles", false);
		Freinage_Line.put("Namur", false);
		Freinage_Line.put("Liege", false);
	
	//@inv13 temp ∈ ℕ
		int temp = 0;

	//@inv14 temp2 ∈ ℕ
		int temp2 = 0;

	//@inv15 State_system ∈ BOOL
		boolean State_system = false;

	//@inv16 State_brake ∈ BOOL
		boolean State_brake = false;

	//@inv17 In_First_Line ∈ {Namur↦Bruxelles, Bruxelles↦Namur, Namur↦Liege, Liege↦Namur, Bruxelles↦Liege, Liege↦Bruxelles} → BOOL
		Map<String, Boolean> In_First_Line = new HashMap<>();
		In_First_Line.put("Namur↦Bruxelles", false);
		In_First_Line.put("Bruxelles↦Namur", false);
		In_First_Line.put("Namur↦Liege", false);
		In_First_Line.put("Liege↦Namur", false);
		In_First_Line.put("Bruxelles↦Liege", false);
		In_First_Line.put("Liege↦Bruxelles", false);
	
	//@inv18 temp3 ∈ BOOL
		boolean temp3 = false;

	//@inv19 temp4 ∈ BOOL
		boolean temp4 = false;

	//@inv20 GarePrincipale ∈ { Bruxelles, Namur, Liege}→ℕ
		Map<String, Integer> GarePrincipale = new HashMap<>();
		GarePrincipale.put("Bruxelles", 0);
		GarePrincipale.put("Namur", 0);
		GarePrincipale.put("Liege", 0);

	//@inv21 GareSecondaire ∈ { Huy, Ottignies, Leuven}→ℕ
		Map<String, Integer> GareSecondaire = new HashMap<>();
		GareSecondaire.put("Huy", 0);
		GareSecondaire.put("Ottignies", 0);
		GareSecondaire.put("Leuven", 0);

	//@inv22 Quai ∈ {Namur↦Quai1, Namur↦Quai2, Bruxelles↦Quai1, Bruxelles↦Quai2, Liege↦Quai1, Liege↦Quai2,Huy↦Quai1,Ottignies↦Quai1,Leuven↦Quai1}→BOOL
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
	
	//@inv23 Feux ∈ {Lvn_Lge, Lge_Lvn, Lvn_Bxl, Bxl_Lvn, Bxl_Ott, Ott_Bxl, Ott_Nam, Nam_Ott, Huy_Nam, Nam_Huy, Lge_Huy, Huy_Lge} → BOOL
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

	//theorem @inv24 Station_to_Line_Feux ∈ {Namur↦Bruxelles, Namur↦Liege, Bruxelles↦Namur, Bruxelles↦Liege, Liege↦Bruxelles, Liege↦Namur} → dom(Feux)

	// @inv25 Secondary_Station_to_Line_Feux ∈ { Namur↦Bruxelles,Bruxelles↦Namur, Namur↦Liege, Liege↦Namur, Liege↦Bruxelles, Bruxelles↦Liege} → dom(Feux)

	//@inv26 Line_to_Station_Feux ∈ {Namur↦Bruxelles, Namur↦Liege, Bruxelles↦Namur, Bruxelles↦Liege, Liege↦Bruxelles, Liege↦Namur} → dom(Feux)

	//@inv27 Line_to_Secondary_Station_Feux ∈ {Namur↦Bruxelles, Namur↦Liege, Bruxelles↦Namur, Bruxelles↦Liege, Liege↦Bruxelles, Liege↦Namur} → dom(Feux)

	//@inv28 Appli_Voyageur ∈ {Namur,Liege,Bruxelles,Huy,Leuven,Ottignies} → BOOL    
    		Map<String, Boolean> Appli_Voyageur = new HashMap<>();
   		Appli_Voyageur.put("Namur", false);
    		Appli_Voyageur.put("Liege", false);
    		Appli_Voyageur.put("Bruxelles", false);
    		Appli_Voyageur.put("Huy", false);
    		Appli_Voyageur.put("Leuven", false);
    		Appli_Voyageur.put("Ottignies", false);
		
	
    //Lancement de l'initialisation
    public TrainSystems() {
        initialisation();
    }
    //Définition des variables à  l'initialisation
    private void initialisation() {
        gares = new HashMap<>();
        gares.put(Gare.Namur, 1);
        gares.put(Gare.Bruxelles, 2);
        gares.put(Gare.Liege, 3);

        garePrincipale = new HashMap<>();
        garePrincipale.put(Gare.Bruxelles, 0);
        garePrincipale.put(Gare.Namur, 0);
        garePrincipale.put(Gare.Liege, 0);

        garesUrgence = new HashMap<>();
        garesUrgence.put(Gare.Ottignies, 0);
        garesUrgence.put(Gare.Huy, 0);
        garesUrgence.put(Gare.Leuven, 0);

        gareSecondaire = garesUrgence;

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

        Feux = new HashMap<>();
        Feux.put(Traffic_Light.Lvn_Lge,true);
        Feux.put(Traffic_Light.Lge_Lvn,true);
        Feux.put(Traffic_Light.Lvn_Bxl,true);
        Feux.put(Traffic_Light.Bxl_Lvn,true);
        Feux.put(Traffic_Light.Bxl_Ott,true);
        Feux.put(Traffic_Light.Ott_Bxl,true);
        Feux.put(Traffic_Light.Ott_Nam,true);
        Feux.put(Traffic_Light.Nam_Ott,true);
        Feux.put(Traffic_Light.Huy_Nam,true);
        Feux.put(Traffic_Light.Nam_Huy,true);
        Feux.put(Traffic_Light.Lge_Huy,true);
        Feux.put(Traffic_Light.Huy_Lge,true);

        Station_To_Line_Feux = new HashMap<>();
        Station_To_Line_Feux.put("Namur↦Bruxelles", Traffic_Light.Nam_Ott);
        Station_To_Line_Feux.put("Bruxelles↦Namur", Traffic_Light.Bxl_Ott);
        Station_To_Line_Feux.put("Namur↦Liege", Traffic_Light.Nam_Huy);
        Station_To_Line_Feux.put("Liege↦Namur", Traffic_Light.Lge_Huy);
        Station_To_Line_Feux.put("Bruxelles↦Liege", Traffic_Light.Bxl_Lvn);
        Station_To_Line_Feux.put("Liege↦Bruxelles", Traffic_Light.Lge_Lvn);

        Secondary_Station_To_Line_Feux= new HashMap<>();
        Secondary_Station_To_Line_Feux.put("Namur↦Bruxelles", Traffic_Light.Ott_Bxl);
        Secondary_Station_To_Line_Feux.put("Bruxelles↦Namur", Traffic_Light.Ott_Nam);
        Secondary_Station_To_Line_Feux.put("Namur↦Liege", Traffic_Light.Huy_Lge);
        Secondary_Station_To_Line_Feux.put("Liege↦Namur", Traffic_Light.Huy_Nam);//Corrigé
        Secondary_Station_To_Line_Feux.put("Bruxelles↦Liege", Traffic_Light.Lvn_Lge);//Corrigé
        Secondary_Station_To_Line_Feux.put("Liege↦Bruxelles", Traffic_Light.Lvn_Bxl);

        Line_To_Station_Feux = new HashMap<>();
        Line_To_Station_Feux.put("Namur↦Bruxelles", Traffic_Light.Bxl_Ott);
        Line_To_Station_Feux.put("Bruxelles↦Namur", Traffic_Light.Nam_Ott);
        Line_To_Station_Feux.put("Namur↦Liege", Traffic_Light.Lge_Huy);
        Line_To_Station_Feux.put("Liege↦Namur", Traffic_Light.Nam_Huy);
        Line_To_Station_Feux.put("Bruxelles↦Liege", Traffic_Light.Lge_Lvn);
        Line_To_Station_Feux.put("Liege↦Bruxelles", Traffic_Light.Bxl_Lvn);


        Line_to_Secondary_Station_Feux = new HashMap<>();
        Line_to_Secondary_Station_Feux.put("Namur↦Bruxelles", Traffic_Light.Ott_Nam);
        Line_to_Secondary_Station_Feux.put("Bruxelles↦Namur", Traffic_Light.Ott_Bxl);
        Line_to_Secondary_Station_Feux.put("Namur↦Liege", Traffic_Light.Huy_Nam);
        Line_to_Secondary_Station_Feux.put("Liege↦Namur", Traffic_Light.Huy_Lge);
        Line_to_Secondary_Station_Feux.put("Bruxelles↦Liege", Traffic_Light.Lvn_Bxl);
        Line_to_Secondary_Station_Feux.put("Liege↦Bruxelles", Traffic_Light.Lvn_Lge);//Corrigé

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

    /**
     *  Faire apparaitre un train dans une gare principale et occupe le quai 1.
     *
     * @param selectGare La gare où doit apparaître le train de type Gare(Constante)
     * @requires La gare apparient à une garePrincipale et non Secondaire.
     *           Nombre total de train dans le système strictement inférieur à 4
     *           Nombre total de train dans la gare strictement inférieur à 2
     *           Etat des freins à FALSE
     *           Etat du système à FALSE
     *           Quai1 numéro doit être libre
     * @modifies nbTrain : Le nombre de train dans la gare
     *           nbTrainTotal : Le nombre de train total dans le système
     *           quai : Changement d'état à true déisgnant que le quai 1 est occupé.
     */
    public void apparitionTrain(Gare selectGare) {
        if (garePrincipale.containsKey(selectGare)){
            if (nbTrain.get(selectGare) < 2 && nbTrainTotal < 4 && !freinageLine.get(selectGare) && !stateSystem && !stateBrake
                && !quai.get(selectGare).get(Quai.Quai1)) {
            nbTrain.put(selectGare, nbTrain.get(selectGare) + 1);
            nbTrainTotal++;
            quai.get(selectGare).put(Quai.Quai1, true);
            }
        }
    }
    /**
     *  Introduit un train présent dans une garePrincipale sur une ligne de convoi
     *
     * @param selectGareDepart La gare de départ de type Gare(Constante)
     * @param selectGareDestination La gare de destination de type Gare(Constante)
     * @param selectQuai Le quai où se trouve le train dans une gare de type Quai(Constante)
     * @requires La gare de départ et de déstination appartiennet à une garePrincipale et non Secondaire.
     *           Nombre de train de la gare de départ doit être supérieur ou égal à 1
     *           Nombre de train présent sur la ligne doit être strictement inférieur à 1
     *           Le feux de la gare pour circuler sur la voie doit indiquer true (FeuVert)
     *           Etat des freins à FALSE
     *           Etat du système à FALSE
     *           Le quai sélectionné doit contenir un train (true)
     * @modifies nbTrain : Le nombre de train dans la gare
     *           quai : Changement d'état à true déisgnant que le quai 1 est maintenant libre
     *           Line : La ligne de convoi est incrémenté de + 1
     *           Feux : Le feux se met à jour (false => feuRouge) indiquant un train sur la ligne de convois
     */
    public void MainStation_To_Line (Gare selectGareDepart, Gare selectGareDestination, Quai selectQuai ){
        if (garePrincipale.containsKey(selectGareDepart) && garePrincipale.containsKey(selectGareDestination)
                && nbTrain.get(selectGareDepart) >= 1 && Line.get(selectGareDepart.name()+"↦"+selectGareDestination.name()) < 1
                && Feux.get(Station_To_Line_Feux.get(selectGareDepart.name()+"↦"+selectGareDestination.name()))
                && !stateSystem && !stateBrake && (quai.get(selectGareDepart).get(selectQuai))){
            nbTrain.put(selectGareDepart, nbTrain.get(selectGareDepart) - 1) ;
            quai.get(selectGareDepart).put(selectQuai, false);
            Line.put(selectGareDepart.name()+"↦"+selectGareDestination.name(), Line.get(selectGareDepart.name()+"↦"+selectGareDestination.name()) + 1) ;
            Feux.put(Station_To_Line_Feux.get(selectGareDepart.name()+"↦"+selectGareDestination.name()), false) ;
        }
        else { if (!quai.get(selectGareDepart).get(selectQuai)){System.out.println("Il n'y aucun train sur ce quai ");}}

    }
    /**
     *  Introduit un train présent sur une ligne de convoi dans une gare Secondaire
     *
     * @param SelectTrain Le nom du train de type String
     * @param SelectGareUrgence La gare secondaire de destination de type Gare(constante)
     * @requires La gare de destination doit être une gare secondaire
     *           Le nom du train doit correspondre au nom d'une ligne de convoi
     *           La ligne de convoi du train choisi doit correspondre à la gare secondaire auquel la ligne est lié
     *           La ligne de convoi doit être supérieur ou égal à 1
     *           Le nombre de train présent dans la gare doit être strictement inférieur à 1
     *           Etat des freins à FALSE
     *           Etat du système à FALSE
     *           Le quai sélectionné doit être libre (false)
     *           L'aiguillages associé à la ligne de convoi doit être actionné (true) pour permettre le passage du train
     *           Les voyageurs doivent avoir été avertis avant que le train entre en gare
     * @modifies Line : Le nombre de train présent sur la voie est décrémenté de 1
     *           nbTrainUrgence : Le nombre de train dans la gare secondaire doit être décrémenté de 1
     *           In_First_Line : Les bornes de contact signalent que le train n'est plus sur la voie
     *           quai : Changement d'état à true déisgnant que le quai 1 est occupé
     *           Feux : Le feux se met à jour (true => feuVert) indiquant que la ligne ne contient aucun train
     *           Appli : L'application se met à jour lorsque un train rentre dans une gare
     */
    public void Line_To_Secondary_Station(String SelectTrain, Gare SelectGareUrgence) {
        if (garesUrgence.containsKey(SelectGareUrgence) && Line_Urgence.containsKey(SelectTrain)
                && Line_Urgence.get(SelectTrain) == SelectGareUrgence) {
            if (Line.get(SelectTrain) >= 1 && nbTrainUrgence.get(SelectGareUrgence) < 1
                    &&!stateBrake && !stateSystem && !quai.get(SelectGareUrgence).get(Quai.Quai1) && Aiguillages.get(SelectTrain)
                     && Appli_Voyageur.get(SelectGareUrgence)) {
                Line.put(SelectTrain, Line.get(SelectTrain) - 1);
                nbTrainUrgence.put(SelectGareUrgence, nbTrainUrgence.get(SelectGareUrgence) + 1);
                In_First_Line.put(SelectTrain, false);
                quai.get(SelectGareUrgence).put(Quai.Quai1, true);
                Feux.put(Line_to_Secondary_Station_Feux.get(SelectTrain), true) ;//A corrgier ?
                Appli_Voyageur.put(SelectGareUrgence,false) ;
            }
        }
    }

    /**
     *  Introduit un train présent dans une gareSecondaire sur une ligne de convoi
     *
     * @param SelectGareDepart La gare de départ de type Gare(constante)
     * @param SelectTrain Le nom du train de type String
     * @requires La gare de départ doit être une gare secondaire
     *           Le nom du train doit correspondre au nom de la ligne de convoi du train choisi doit correspondre à la gare secondaire auquel la ligne est lié
     *           Le nom du train choisi doit correspondre à la gare de départ auquel la ligne est lié
     *           Le nombre de train présent dans la gare choisi doit être supérieur ou égal à 1
     *           Le nombre de train présent sur la ligne de convoi secondaire doit être strictement inférieur à 1
     *           Etat des freins à FALSE
     *           Etat du système à FALSE
     *           Le quai 1 de la gare choisi doit être occupé (true)
     *           L'aiguillages associé à la ligne de convoi secondaire doit être actionné (true) pour permettre le passage du train
     *           L'état des feux doit indiquer que le feux est vert pour sortir de la gare
     * @modifies nbTrainUrgence : Le nombre de train dans la gare secondaire doit être décrémenté de 1
     *           Second_Line : La seconde ligne de convoi est incrémenté de + 1
     *           In_Second_Line : Les bornes de contact signalent que le train est sur la voie
     *           quai : Changement d'état à true déisgnant que le quai 1 est libre
     *           Feux : Le feux se met à jour (false => feuRouge) indiquant que la ligne  contient un train
     */
    public void Secondary_Station_To_Second_Line (Gare SelectGareDepart, String SelectTrain){
        String line = SelectTrain;
        String[] gares = line.split("↦");
        String gareDepartTrain = gares[0];
        String gareArriveeTrain = gares[1];
        if(garesUrgence.containsKey(SelectGareDepart) && Second_Line.containsKey(SelectTrain)
                && Line_Urgence.get(SelectTrain) == SelectGareDepart && nbTrainUrgence.get(SelectGareDepart) >= 1 && Second_Line.get(SelectTrain) < 1
                && Aiguillages.get(gareArriveeTrain+"↦"+gareDepartTrain) && !stateSystem && !stateBrake
                && quai.get(SelectGareDepart).get(Quai.Quai1)
                && Feux.get(Secondary_Station_To_Line_Feux.get(SelectTrain))){
            nbTrainUrgence.put(SelectGareDepart, nbTrainUrgence.get(SelectGareDepart) - 1);
            Second_Line.put(SelectTrain,Second_Line.get(SelectTrain)+1);
            In_Second_Line.put(SelectTrain,true) ;
            quai.get(SelectGareDepart).put(Quai.Quai1,false) ;
            Feux.put(Secondary_Station_To_Line_Feux.get(SelectTrain), false) ;
        }
    }
    /**
     *  Introduit un train présent sur une ligne de convoi secondaire dans une gare Principale
     *
     * @param SelectGareDepart La gare principale de départ de type Gare(constante)
     * @param SelectGareArrivee La gare principale de destination de type Gare(constante)
     * @requires La gare de départ doit être une gare principale
     *           La gare de destination doit être une gare principale
     *           La gare de départ doit être différentre de la gare de destination
     *           Le nombre de train présent dans la gare choisi doit être strictement inférieur à 1
     *           Le nombre de train présent sur la ligne de convoi secondaire doit être supérieur ou égal à 1
     *           Les bornes de contact doivent indiquer qu'un train se trouve sur la seconde ligne de convoi
     *           Les bornes de contact doivent indiquer qu'aucun train se trouve sur la premiere ligne de convoi
     *           Etat des freins à FALSE
     *           Etat du système à FALSE
     *           Le quai 1 de la gare choisi doit être libre (false)
     *           L'application doit avoir signalé au préalable qu'un train arrive en gare
     * @modifies nbTrain : Le nombre de train dans la gare principale doit être incrémenté de + 1
     *           Second_Line : La seconde ligne de convoi est décrémenté de - 1
     *           In_Second_Line : Les bornes de contact signalent que le train n'est plus sur la ligne de convoi
     *           quai : Changement d'état à true déisgnant que le quai 1 est occupé
     *           Appli: Le train est entré en gare donc fin de la notification d'approche d'un train dans une gare
     *           Feux : Le feux se met à jour (true => feuVert) indiquant que la ligne  ne contient aucun train
     */
    public void Second_Line_To_MainStation (Gare SelectGareDepart, Gare SelectGareArrivee){
        if(garePrincipale.containsKey(SelectGareDepart) && garePrincipale.containsKey(SelectGareArrivee)
                && SelectGareDepart != SelectGareArrivee && Second_Line.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name())>=1
                && nbTrain.get(SelectGareArrivee)<1 && In_Second_Line.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name())
                && !In_First_Line.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name())
                && !stateSystem && !stateBrake && !quai.get(SelectGareArrivee).get(Quai.Quai1)
                && Appli_Voyageur.get(SelectGareArrivee)){
            Second_Line.put(SelectGareDepart.name()+"↦"+SelectGareArrivee.name(), Second_Line.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name())-1 );
            nbTrain.put(SelectGareArrivee, nbTrain.get(SelectGareArrivee)+1);
            In_Second_Line.put(SelectGareDepart.name()+"↦"+SelectGareArrivee.name(),false);
            quai.get(SelectGareArrivee).put(Quai.Quai1,true) ;
            Appli_Voyageur.put(SelectGareArrivee, false);
            System.out.println(Feux.get(Line_To_Station_Feux.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name())));
            Feux.put(Line_To_Station_Feux.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name()), true) ;
            System.out.println(Feux.get(Line_To_Station_Feux.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name())));
        }
    }
    /**
     *  Introduit un train présent sur la première ligne de convoi vers la seconde ligne de convoi
     *
     * @param SelectGareDepart La gare principale de départ de type Gare(constante)
     * @param SelectGareArrivee La gare principale de destination de type Gare(constante)
     * @requires La gare de départ doit être une gare principale
     *           La gare de destination doit être une gare principale
     *           La gare de départ doit être différentre de la gare de destination
     *           Le nombre de train présent sur la premiere ligne de convoi  doit être égal à 1
     *           Le nombre de train présent sur la seconde ligne de convoi  doit strictement inférieur à 1
     *           Les aiguillages correspondant à la permière ligne de convoi ne sont pas actionné (false)
     *           Etat des freins à FALSE
     *           Etat du système à FALSE
     *           (La premiere ligne de convoi est différente de 1 ET la seconde différente de 1 OU
     *           La premiere ligne de convoi est différente de 1 ET la seconde différente de 0 OU
     *           La premiere ligne de convoi est différente de 0 ET la seconde différente de 1)
     * @modifies In_Second_Line : Les bornes de contact signalent que le train est sur la seconde ligne de convoi
     *           Line : Le nombre de train sur La premiere ligne de convoi est décrémenté de - 1
     *           Second_Line : Le nombre de train sur la seconde ligne de convoi est incrémenté de + 1
     *           In_First_Line : Les bornes de contact signalent que le train n'est plus sur la première ligne de convoi
     *           Appli : Si il y a un train sur la même ligne de convoi => StateSystem = true
     *           Appli : Si il y a aucun train sur la même ligne de convoi => StateSystem = false
     */
    public void Line_To_Second_Line ( Gare SelectGareDepart,Gare SelectGareArrivee){
        if(garePrincipale.containsKey(SelectGareDepart) && garePrincipale.containsKey(SelectGareArrivee)
                && SelectGareDepart != SelectGareArrivee && Line.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name()) == 1
                && Second_Line.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name())<1
                && !Aiguillages.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name())
                && !stateSystem && !stateBrake
                && (Line.get((SelectGareDepart.name()+"↦"+SelectGareArrivee.name())) != 1 && Second_Line.get(SelectGareArrivee.name()+"↦"+SelectGareDepart.name()) != 1)
                || (Line.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name()) != 1 && Second_Line.get(SelectGareArrivee.name()+"↦"+SelectGareDepart.name()) != 0)
                || (Line.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name()) != 0 && Second_Line.get(SelectGareArrivee.name()+"↦"+SelectGareDepart.name()) != 1 )) {
            In_Second_Line.put(SelectGareDepart.name()+"↦"+SelectGareArrivee.name(), true);
            Line.put(SelectGareDepart.name()+"↦"+SelectGareArrivee.name(), Line.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name())- 1) ;
            Second_Line.put(SelectGareDepart.name()+"↦"+SelectGareArrivee.name(), Second_Line.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name())+1) ;
            In_First_Line.put(SelectGareDepart.name()+"↦"+SelectGareArrivee.name(), false);
            if ((Line.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name()) == 1 && Second_Line.get(SelectGareArrivee.name()+"↦"+SelectGareDepart.name()) == 1 )){
                stateSystem = true;
            }
            if ((Line.get(SelectGareDepart.name()+"↦"+SelectGareArrivee.name()) != 1 && Second_Line.get(SelectGareArrivee.name()+"↦"+SelectGareDepart.name()) != 1 )){
                stateSystem = false;
            }
        }
    }

    /**
     *  Modifier la postion d'un aiguillage (true or false).
     *
     * @param SelectLine Le nom de la ligne associé à l'aiguillages de type String
     * @param SelectAction L'action, soit true ou false de type BOOL
     * @requires La ligne choisi doit appartenir aux lignes d'aiguillages ou l'action de changer l'aiguillage est possible
     *           L'action doit être différente de l'état de l'aiguillages
     * @modifies Aiguillages : Modifie la position de l'aiguillage choisi (true or false)
     */
    public void Choose_Aiguillage( String SelectLine, Boolean SelectAction ){
        if(Aiguillages.containsKey(SelectLine) && SelectAction != Aiguillages.get(SelectLine)) {
            Aiguillages.put(SelectLine, SelectAction);
        }
    }
    /**
     *  Notifie les voyageurs qu'un train est en approche en modifiant l'état de l'application
     *
     * @param SelectTrain Le nom du train de type String
     * @param SelectGareArrivee La gare d'arrivée de type Gare
     * @requires La notification du train en approche de la gare doit être sur false
     *           Le nom du train doit correspondre aux noms des trains disponibles dans l'application
     *           (La gare d'arrivée doit correspondre à ligne de convoi secondaire
     *           ET La gare d'arrivée doit correspondre à la l'aiguillages de cette même ligne
     *           ET la ligne de convois secondaire doit comporter au moins un train)
     *           OU (La gare d'arrivée doit correspondre à ligne de convoi principale
     *           ET La ligne de convois principale doit comporter au moins un train)
     * @modifies Appli : Notifie les voyageurs que le train choisi est en approche de la gare choisi
     */
    public void Message_Application (String SelectTrain, Gare SelectGareArrivee){
        if (!Appli_Voyageur.get(SelectGareArrivee) && Appli_Voyageur.containsKey(SelectGareArrivee)){
            if ((Line_Urgence.get(SelectTrain) == SelectGareArrivee && Aiguillages.get(SelectTrain) && Line.get(SelectTrain)>=1)
                    || Line_Gare.get(SelectTrain) == SelectGareArrivee && Second_Line.get(SelectTrain) >= 1){
                Appli_Voyageur.put(SelectGareArrivee,true);
            }
        }
    }
    /**
     *  Modifier l'état du système pour enclecher plus tard une marche arrière
     *
     * @param SelectTrain Le nom du train de type String
     * @param SelectSecondTrain La nom du second train de type String
     * @param SelectGare Le nom de la gare ou l'un des trains effectuera une marche arrière prochainement
     * @requires Le premier train doit être différent du second train
     *           Le nombre de train présent sur la premiere ligne de convoi  doit être supérieur ou égal à 1
     *           Le nombre de train présent sur la seconde ligne de convoi  doit être supérieur ou égal à 1
     *           Le nombre de train de la gare choisi doit être strictement inférieur 2
     *           L'état des freins doit être false
     *           Le nombre train figurant sur la seconde ligne doit correspondre au nombre du second train se trouvant sur la même ligne
     *           Le quai numéro 2 doit être libre
     *           (Le nombre de train présent sur la seconde ligne doit être strictement inférieur à 1
     *           ET la gare choisi doit correspondre à la ligne de convoi où se trouve le premier train sur laquelle se trouve la gare)
     *           OU( la gare choisi doit correspondre à la ligne de convoi où se trouve le second train sur laquelle se trouve la gare)
     * @modifies Etat des freins true
     *           Etat du système true
     *           Etat des freins du train choisi true
     *           Modifie les variables temporaires en fonction de la gare choisi pour actionner la marche arrière
     */
    public void Freinage_Urgence (String SelectTrain, String SelectSecondTrain, Gare SelectGare){
        if( SelectTrain != SelectSecondTrain && Line.get(SelectTrain) >= 1 && Second_Line.get(SelectSecondTrain)>=1
        && nbTrain.get(SelectGare) < 2 && !stateBrake && Line_Urgence.get(SelectTrain) == Line_Urgence.get(SelectSecondTrain) && !quai.get(SelectGare).get(Quai.Quai2)){
            if ((Line.get(SelectSecondTrain) < 1 && SelectGare == Line_Gare.get(SelectTrain)) || (SelectGare == Line_Gare.get(SelectSecondTrain))) {
                freinageLine.put(SelectGare, true);
                stateSystem = true;
                stateBrake = true;
                if (SelectGare == Line_Gare.get(SelectTrain)) {
                    temp = 0;
                    temp2 = 1 ;
                    temp3 = true;
                    temp4 = false;
                }
                if (SelectGare == Line_Gare.get(SelectSecondTrain)) {
                    temp = 1;
                    temp2 = 0;
                    temp3 = false;
                    temp4 = true;
                }
            }
        }
    }
    /**
     *  Effectue une marche arrière de l'un des deux trains vers une gare principale
     *
     * @param SelectTrain Le nom du train de type String
     * @param SelectSecondTrain La nom du second train de type String
     * @param SelectGare Le nom de la gare ou l'un des trains effectuera une marche arrière prochainement
     * @requires Le premier train doit être différent du second train
     *           Le nombre de train présent sur la premiere ligne de convoi  doit être supérieur ou égal à 1
     *           Le nombre de train présent sur la seconde ligne de convoi  doit être supérieur ou égal à 1
     *           Le nombre de train de la gare choisi doit être strictement inférieur 2
     *           La gare doit correspondre aux lignes de convoi disponible pour une marche arrière
     *           L'état des freins doit être true
     *           Le nombre train figurant sur la seconde ligne doit correspondre au nombre du second train se trouvant sur la même ligne
     *           Le quai numéro 2 doit être libre
     *           (Le nombre de train présent sur la seconde ligne doit être strictement inférieur à 1
     *           ET la gare choisi doit correspondre à la ligne de convoi où se trouve le premier train sur laquelle se trouve la gare)
     *           OU( la gare choisi doit correspondre à la ligne de convoi où se trouve le second train sur laquelle se trouve la gare)
     * @modifies Décremente de -1 le nombre de train sur la ligne de convoi choisi
     *           Incrémente de +1 le nombre de train dans la gare choisi
     *           Etat des freins à false
     *           Etat du système à false
     *           Etat des freins du train choisi true
     *           Mets à jour les bornes de contact en fonction du train choisi pour la marche arrière
     *           Le quai numéro 2 de la gare choisi devient occupé (true)
     *
     */
    public void Marche_Arriere_To_MainStation(String SelectTrain, String SelectSecondTrain, Gare SelectGare){
        if(Line.get(SelectTrain) >= 1 && Second_Line.get(SelectSecondTrain) >= 1
                && SelectTrain != SelectSecondTrain && nbTrain.get(SelectGare) < 2
                && freinageLine.get(SelectGare) && !quai.get(SelectGare).get(Quai.Quai2) && ((SelectGare == Line_Gare.get(SelectTrain)) || SelectGare == Line_Gare.get(SelectSecondTrain))){
            Line.put(SelectTrain,Line.get(SelectTrain) - temp );
            Second_Line.put(SelectSecondTrain,Second_Line.get(SelectSecondTrain) - temp2 );
            nbTrain.put(SelectGare, nbTrain.get(SelectGare) + 1) ;
            freinageLine.put(SelectGare, false);
            stateBrake = false ;
            stateSystem = false ;
            In_First_Line.put(SelectTrain, temp3);
            In_Second_Line.put(SelectSecondTrain, temp4);
            quai.get(SelectGare).put(Quai.Quai2, true);
        }
    }
    public void Lancement_Interaction (){
        Scanner clavier = new Scanner(System.in);
        //if on appuie sur le bouton => button_Apparition_Train == true {
            Boolean CheckSaisie2 = false;
            Boolean CheckSaisie1 = false ;
            if (nbTrainTotal < 4) {
                do {
                    try {
                        System.out.print("Veuillez saisir le nom d'une Gare Principale : ");
                        String name = clavier.next();
                        Gare SelectGare = Gare.valueOf(name);
                        CheckSaisie1 = true;
                        if (garePrincipale.containsKey(SelectGare)) {
                            apparitionTrain(SelectGare);
                            CheckSaisie2 = true;
                            /*
                             *
                             * Rajouter le code processing ici juste après l'appel de fonction
                             *
                             */
                        } else {
                            System.out.println("La gare choisie n'est pas une gare principale !");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erreur : La gare saisie ne correspond pas aux gares disponible");
                        System.out.println("Voici les gares possibles : Namur, Bruxelles, Liege, Huy, Ottignies, Leuven");
                    }
                }while (!CheckSaisie1 || !CheckSaisie2);
            }


        //if on appuie sur le bouton => button_MainStation_To_Line == true {
            Boolean CheckM = false;
            for (Gare g : Gare.values()) {
                if (nbTrain.get(g) >= 1 ){CheckM = true;}
            }
            if (CheckM) {
                CheckSaisie1 = false;
                CheckSaisie2 = false;
                do {
                    try {
                        System.out.print("Veuillez saisir le nom d'une Gare Principale : ");
                        String name = clavier.next();
                        Gare SelectGare = Gare.valueOf(name);
                        System.out.print("Veuillez saisir le nom d'une seconde Gare Principale : ");
                        String name2 = clavier.next();
                        Gare SelectGare2 = Gare.valueOf(name2);
                        CheckSaisie1 = true;
                        if (garePrincipale.containsKey(SelectGare) && garePrincipale.containsKey(SelectGare2)) {
                            CheckSaisie2 = true;
                            CheckSaisie1 = false;
                            do {
                                System.out.print("Veuillez saisir le quai où se trouve le tain (1 ou 2) : ");
                                int q = clavier.nextInt();
                                if (q == 1 || q == 2) {
                                    CheckSaisie1 = true;
                                    if (q == 1) {
                                        Quai SelectQuai = Quai.Quai1;
                                        MainStation_To_Line(SelectGare, SelectGare2, SelectQuai);
                                        /*
                                         *
                                         * Rajouter le code processing ici juste après l'appel de fonction
                                         * Pour le Quai 1
                                         *
                                         */
                                    }
                                    if (q == 2) {
                                        Quai SelectQuai = Quai.Quai2;
                                        MainStation_To_Line(SelectGare, SelectGare2, SelectQuai);
                                        /*
                                         *
                                         * Rajouter le code processing ici juste après l'appel de fonction
                                         * Pour le Quai 2
                                         */
                                    }
                                }
                            } while (!CheckSaisie1);
                        } else {
                            System.out.println("L'une des gares choisie n'est pas une gare principale !");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erreur : Les gare saisie ne correspondent pas aux gares disponible");
                        System.out.println("Voici les gares possibles : Namur, Bruxelles, Liege, Huy, Ottignies, Leuven");
                    }
                } while (!CheckSaisie1 || !CheckSaisie2);
            }
            else{System.out.println(("Il n'y actuellement aucun train dans les gares principales"));}
    }

    public static void main(String[] args) {
        TrainSystems trainSystems = new TrainSystems();
        trainSystems.Lancement_Interaction();
        trainSystems.apparitionTrain(Gare.Namur);
        /*Scanner clavier = new Scanner(System.in) ;
        System.out.print("Veuillez saisir une gare de départ: ");
        String depart = clavier.next();
        Gare GareDepart = Gare.valueOf(depart);
        System.out.print("Veuillez saisir une gare de destination: ");
        String destination = clavier.next();
        Gare GareDestination = Gare.valueOf(destination);
        TrainSystems trainSystems = new TrainSystems();
        trainSystems.apparitionTrain(Gare.Namur);
        trainSystems.apparitionTrain(GareDestination);
        trainSystems.MainStation_To_Line(Gare.Bruxelles, Gare.Namur, Quai.Quai1);
        trainSystems.MainStation_To_Line(Gare.Namur, Gare.Bruxelles, Quai.Quai1);
        trainSystems.Choose_Aiguillage("Namur↦Bruxelles", true);
        trainSystems.Message_Application("Namur↦Bruxelles", Gare.Ottignies);
        trainSystems.Line_To_Secondary_Station("Namur↦Bruxelles",Gare.Ottignies);
        trainSystems.Choose_Aiguillage("Bruxelles↦Namur", true);
        trainSystems.Secondary_Station_To_Second_Line(Gare.Ottignies, "Namur↦Bruxelles" );
        //trainSystems.Message_Application("Namur↦Bruxelles", Gare.Bruxelles);
        //trainSystems.Second_Line_To_MainStation(Gare.Namur, Gare.Bruxelles);
        //trainSystems.MainStation_To_Line(Gare.Bruxelles, Gare.Namur, Quai.Quai1);
        //trainSystems.Line_To_Second_Line(Gare.Namur, Gare.Bruxelles);
        trainSystems.Freinage_Urgence("Bruxelles↦Namur", "Namur↦Bruxelles",Gare.Bruxelles);
        trainSystems.Marche_Arriere_To_MainStation("Bruxelles↦Namur", "Namur↦Bruxelles",Gare.Bruxelles);
        Map valeurtest = trainSystems.Line;
        Map valeurtest2 = trainSystems.nbTrain;
        Map valeurtest3 = trainSystems.nbTrainUrgence;
        Map valeurtest4 = trainSystems.Second_Line;
        Map valeurtest5 = trainSystems.freinageLine;
        boolean valeurtest6 = trainSystems.stateSystem;
        boolean valeurtest7 = trainSystems.stateBrake;

        System.out.println(valeurtest) ;
        System.out.println(valeurtest4) ;
        System.out.println(valeurtest2) ;
        System.out.println(valeurtest3) ;
        System.out.println(valeurtest5);
        System.out.println(valeurtest6);
        System.out.println(valeurtest7);
*/

    }
    }
