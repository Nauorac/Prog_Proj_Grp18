import processing.data.XML;

XML xmlContext;
/*XML xmlthirdrefine;*/
ExtractionXML extraction;
//Variables images
PImage imgRail_gauche_droite;
PImage imgRond_bd;
PImage imgRond_bg;
PImage imgRond_brut;
PImage imgRond_hg;
PImage imgSwitch;
PImage imgSwitch_;
PImage imgSwitch_droite;
PImage imgSwitch_gauche;
PImage imgSwitch_droite_1;
PImage imgSwitch_droite_2;
PImage imgSwitch_gauche_1;
PImage imgSwitch_gauche_2;
PImage imgTrain_droite;
PImage imgTrain_droite_pt;
PImage imgTrain_gauche;
PImage imgTrain_gauche_pt;

void setup() {
     size(1450, 675); // Définir la taille de la fenêtre (largeur, hauteur)
     background(255); // Définir la couleur d'arrière-plan en blanc
    
    //Charger les images 
    imgRail_gauche_droite = loadImage("pictures/rail_gauchedroite.png");
    imgRond_bd = loadImage("pictures/rond_bd.png");
    imgRond_bg = loadImage("pictures/rond_bg.png");
    imgRond_brut = loadImage("pictures/rond_brut.png");
    imgRond_hg = loadImage("pictures/rond_hg.png");
    imgSwitch = loadImage("pictures/switch.png");
    imgSwitch_ = loadImage("pictures/Switch_.png");
    imgSwitch_droite = loadImage("pictures/Switch_droite.png");
    imgSwitch_droite_1 = loadImage("pictures/Switch_droite_1.png");
    imgSwitch_gauche = loadImage("pictures/Switch_gauche.png");
    imgSwitch_droite_2 = loadImage("pictures/Switch_droite_2.png");
    imgSwitch_gauche_1 = loadImage("pictures/Switch_gauche_1.png");
    imgSwitch_gauche_2 = loadImage("pictures/Switch_gauche_2.png");
    imgTrain_droite = loadImage("pictures/train_droite.png");
    imgTrain_gauche = loadImage("pictures/train_gauche.png");
    imgTrain_droite_pt = loadImage("pictures/train_droite_pt.png");
    imgTrain_gauche_pt = loadImage("pictures/train_gauche_pt.png");
    
    
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
  //Gare de Bruxelles
  fill (255,0,0);
  rect (10,10,150,150);
  
  //Gare de Liège
  fill(255, 0, 0); 
  rect(1200, 10, 150, 150);
  
  //Gare de Leuven
  fill(0, 0, 255); 
  rect(505, 85, 150, 46);
  
  //Nom des quais
  fill(0);
  textAlign(CENTER, CENTER);
  textFont(createFont("Arial", 16), 16);
  text("Quai 2 - Emergency", 85, 25);
  text("Quai 1 - Main Line", 85, 140);
  text("Quai 1 - Main Line", 1275, 140);
  text("Quai 2 - Emergency", 1275, 25);
  
  //Nom des gares principales
  fill(255,0,0);
  textAlign(CENTER, CENTER);
  textFont(createFont("Arial", 16), 16);
  text("Gare Principale Bruxelles", 95 ,175);
  text("Gare Principale Liège", 1270 ,175);
   

  // Déssiner les rails horizontales 
  image(imgRail_gauche_droite, 55, 39);
  image(imgRail_gauche_droite, 55, 85);
  image(imgRail_gauche_droite, 205, 85);
  image(imgRail_gauche_droite, 355, 85);
  image(imgRail_gauche_droite, 655, 85);
  image(imgRail_gauche_droite, 805, 85);
  image(imgRail_gauche_droite, 955, 85);
  image(imgRail_gauche_droite, 1105, 85);
  image(imgRail_gauche_droite, 1180, 85);
  image(imgRail_gauche_droite, 1180, 39);
  image(imgSwitch_gauche_1, 205, 35);
  image(imgSwitch_gauche, 330, 85);
  
}
