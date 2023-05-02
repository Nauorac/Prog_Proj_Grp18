import processing.data.XML;

XML xmlContext;
/*XML xmlthirdrefine;*/
ExtractionXML extraction;


void setup() {
    size(800, 600); // Définir la taille de la fenêtre (largeur, hauteur)
    background(255); // Définir la couleur d'arrière-plan en blanc
    xmlContext = loadXML("context.xml");
    
    // Parcourir les ensembles
    XML[] ensembles = xmlContext.getChildren("org.eventb.core.carrierSet");
    for (XML ensemble : ensembles) {
        String nomEnsemble = ensemble.getString("org.eventb.core.identifier");
        println("Nom de l'ensemble : " + nomEnsemble);
    }

    // Parcourir les constantes
    XML[] constantes = xmlContext.getChildren("org.eventb.core.constant");
    for (XML constante : constantes) {
        String nomConstante = constante.getString("org.eventb.core.identifier");
        println("Nom de la constante : " + nomConstante);
    }

    // Parcourir les axiomes
    XML[] axiomes = xmlContext.getChildren("org.eventb.core.axiom");
    for (XML axiome : axiomes) {
        String labelAxiome = axiome.getString("org.eventb.core.label");
        String predicatAxiome = axiome.getString("org.eventb.core.predicate");
        println("Label de l'axiome : " + labelAxiome);
        println("Prédicat de l'axiome : " + predicatAxiome);
    }

        
    /*xmlthirdrefine = loadXML("third_refine.xml");*/
    extraction = new ExtractionXML(sketchPath(""));
    String cheminXML = "bmotion.xml";
    extraction.chargerFichierXML(xmlContext);
}


void draw() {
    }
