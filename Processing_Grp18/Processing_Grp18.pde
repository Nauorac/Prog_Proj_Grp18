import g4p_controls.*;
import java.awt.Font;
import controlP5.*;

/* -------------------------------------------------------- */
/* **************      INITIALISATION     ***************** */
/* -------------------------------------------------------- */
// Variables liées aux trains
    Train train0;
    boolean sens_B_L = true;
    boolean sens_N_L = true;
    boolean sens_B_N = true;

// Variables liées aux messages
    String message = "Bienvenue dans notre système de transport";
    int index = 0;
    int x, y;
    int alpha = 0;
    int messageTime = 0; // variable pour suivre le temps écoulé depuis l'affichage du message
    boolean showMessage = true; // booléen pour indiquer si le message est affiché ou non

// Variables liées aux paramètres du système
    float SCALING = 1.2;
    int LIMITE_TRAIN = 6;
    boolean limit_reach = false;
    int[][] positions;

// Variables liées à la liste déroulante des trains
    ControlP5 cp5;
    DropdownList ddlTrains;
    ArrayList<Train> trains;
    String trainInfo = "";

/* -------------------------------------------------------- */
/* ********************      SETUP     ******************** */
/* -------------------------------------------------------- */
void setup() {
  x = width / 2;
  y = height / 2;
  windowResizable(true);
  // Création des boutons
    createButtons();
  // Configuration de l'affichage
    size(1000, 700);
    textSize(40);
    textAlign(CENTER, CENTER);
    rectMode(CENTER);
  // Création de la liste des trains
        trains = new ArrayList<Train>();  

  // Création de la dropdownlist
    cp5 = new ControlP5(this);
    ddlTrains = cp5.addDropdownList("ddlTrains")
                    .setPosition(600, 300)
                    .setSize(200, 300)
                    .setCaptionLabel("Sélectionnez un train");
}
void updateDropdownList() {
    // Supprimez tous les éléments de la liste déroulante
    ddlTrains.clear();

    // Ajoutez les trains existants à la liste déroulante
    for (Train train : trains) {
        ddlTrains.addItem(train.get_gareDepart() + " - " + train.get_gareDestination(), trains.indexOf(train));
    }
}

// Fonction pour afficher le message de bienvenue
    void displayWelcomeMessage() {
        if (showMessage) {
            background(0);
            if (index < message.length()) {
            alpha += 5;
            if (alpha > 255) {
                alpha = 255;
            }
            fill(200, alpha);
            text(message.substring(0, index + 1), x, y);
            } else {
            fill(200);
            text(message, x, y);
            }
            index++;
            messageTime++;
            if (messageTime >= 240) { // 240 frames = 4 secondes à 60 FPS
            showMessage = false;
            background(255);
            }
        } else {
            textSize(18);
            scale(SCALING);
            background(255);
            noStroke();
            }
        }



/* -------------------------------------------------------- */
/* *********************      DRAW     ******************** */
/* -------------------------------------------------------- */
void draw() {
    //Affichage message de Bienvenue
        displayWelcomeMessage();
    /* *** OK - FINI *******      DESSIN DES GARES ET VOIES  ********* OK - FINI *** */
        drawStations();
        drawTracks();
    // *********************     DEPLACEMENT DU TRAIN   *********************
        moveTrains();

    // **********************      APPLICATION    *************************
      fill(220, 220, 220);
      rect(700, 300, 200, 300);
      fill(0);
      String App_Titre = "Application";
      text(App_Titre, 700, 325);

      // Position de la liste déroulante à l'intérieur du cadre "Application"
        int xPos = 700 + 20; // Position x du cadre "Application" + 20 pixels
        int yPos = 300 + 40; // Position y du cadre "Application" + 40 pixels

      // Affiche une liste des trains existants sur le réseau (récupère les objets trains), permet d'en sélectionner un et affiche en dessous sa position
        //System.out.println("Liste des trains existants sur le réseau :");
        //System.out.println(trains.size());
        for (Train train : trains) {
            ddlTrains.addItem(train.get_gareDepart() + " - " + train.get_gareDestination(), trains.indexOf(train));
        }
        text(trainInfo, 720, 320);
}
