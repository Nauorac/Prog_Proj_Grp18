import java.util.HashMap;
import java.util.Map;

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
