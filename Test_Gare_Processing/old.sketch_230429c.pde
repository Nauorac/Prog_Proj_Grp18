import g4p_controls.*;
import java.awt.Font;
import controlP5.*;

/* -------------------------------------------------------- */
/* **************      INITIALISATION     ***************** */
/* -------------------------------------------------------- */
// VARIABLES
  Train train0;
  boolean sens_B_L = true;
  boolean sens_N_L = true;
  boolean sens_B_N = true;
  GButton buttonB_L;
  GButton buttonN_L;
  GButton buttonB_N;
  GButton secondary_button;
  GButton spawn_button;
  GButton Bruxelles ; 
  GButton Namur;
  GButton Liege;
  String message = "Bienvenue dans notre système de transport";
  int index = 0;
  int x, y;
  int alpha = 0;
  int messageTime = 0; // variable pour suivre le temps écoulé depuis l'affichage du message
  boolean showMessage = true; // booléen pour indiquer si le message est affiché ou non

  float SCALING = 1.2;
  int LIMITE_TRAIN = 6;

  boolean limit_reach = false;
  //float train1.get_X(100); // position horizontale initiale du rectangle
  //float train1.get_Y(100); // position verticale initiale du rectangle
  //float rectSpeedX = 0; // vitesse de déplacement horizontale du rectangle
  //float rectSpeedY = 0; // vitesse de déplacement verticale du rectangle
  int[][] positions;


  // Liste des trains dans l'application
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
  buttonB_L = new GButton(this, 0, 150*SCALING, 50*SCALING, 50*SCALING, "Bruxelles-Liege");
  buttonN_L = new GButton(this, 0, 200*SCALING, 50*SCALING, 50*SCALING, "Namur-Liege");
  buttonB_N = new GButton(this, 0, 250*SCALING, 50*SCALING, 50*SCALING, "Bruxelles-Namur");
  secondary_button = new GButton(this, 0, 300*SCALING, 50*SCALING,50*SCALING, "Ligne secondaire");
  spawn_button = new GButton(this, 0, 350*SCALING, 50*SCALING,50*SCALING, "Spawn Train");
  Font font = new Font ("Times New Roman", Font.PLAIN, int(10*SCALING));
  buttonB_L.setFont(font);
  buttonN_L.setFont(font);
  buttonB_N.setFont(font);
  secondary_button.setFont(font);
  spawn_button.setFont(font);

  size(1000, 700);
  textSize(40);
  textAlign(CENTER, CENTER);
  rectMode(CENTER);
  //imageMode(CENTER); // définir le mode d'affichage de l'image
  buttonB_L.addEventHandler(this, "handleButtonB_L_click");
  buttonN_L.addEventHandler(this, "handleButtonN_L_click");
  buttonB_N.addEventHandler(this, "handleButtonB_N_click");
  secondary_button.addEventHandler(this, "handleButtonSecondary_click");
  spawn_button.addEventHandler(this, "handleButtonSpawn_click");
  GButton.useRoundCorners(false);
  
  // Création de la liste des trains
  trains = new ArrayList<Train>();
    
  // Création de la dropdownlist
  cp5 = new ControlP5(this);
  ddlTrains = cp5.addDropdownList("ddlTrains")
              .setPosition(700, 300)
              .setSize(200, 300)
              .setCaptionLabel("Sélectionnez un train")
              .setItemHeight(20);

  trains.add(train0 = new Train(0,0,0,0, "", ""));
  for (Train train : trains) {
    ddlTrains.addItem(train.get_gareDepart(), trains.indexOf(train));
  } 
}
/* -------------------------------------------------------- */
/* *********************      DRAW     ******************** */
/* -------------------------------------------------------- */
void draw() {
    //Affichage message de Bienvenue
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
      messageTime ++;
      if (messageTime >= 240) { // 240 frames = 4 secondes à 60 FPS
        showMessage = false;
        background(255);}
      //Fin du message de bienvenue
      if(showMessage == false){  
      textSize(18);  
      scale(SCALING);
      background(255);
      noStroke();
      }
    /* *** OK - FINI *******      DESSIN DES GARES   ********* OK - FINI *** */
      //Coordonnées des gares
        //Bruxelles (100,100) ROUGE
        //Namur (300,300) VERT
        //Liege (500,100) BLEU
        //Ottignies(200,200) JAUNE
        //Leuven (300,100)  MAGENTA
        //Huy (400,200) CYAN

      // Dessiner (lge)
        strokeWeight(1);
        fill(0, 0, 255,127);
        rect(520,100,100,50);
        fill(0);
        String gare_L = "Liege";
        text(gare_L, 500, 130);
      
      // Dessiner (namur)
        fill(0, 255, 0,127);
        rect(300,320,50,100);
        fill(0);
        String gare_N = "Namur";
        text(gare_N, 300, 260);
      
      // Dessiner (BXL)
        fill(255, 0, 0,127);
        rect(80,100,100,50);
        fill(0);
        String gare_B = "Bruxelles";
        text(gare_B, 100, 130);
      
      // Dessiner (ott)
      //noStroke();
        fill(255, 255, 0, 127);
        rect(200,200, 75, 75);
        fill(0);
        String gare_O = "Ott";
        text(gare_O, 185, 215);

      // Dessiner (huy)
      //noStroke();
        fill(0, 255, 255, 127);
        rect(400,200, 75, 75);
        fill(0);
        String gare_H = "Huy";
        text(gare_H, 415, 215);
      
      // Dessiner (lvn)
      //noStroke();
      fill(255, 0, 255, 127);
      rect(300,90, 100, 60);
      fill(0);
      String gare_Ln = "Lvn";
      text(gare_Ln, 300, 110);

    /* *** OK - FINI *******      DESSIN DES VOIES   ********** OK - FINI *** */
      // Dessiner les dédoublements de voies à chaque gare
        stroke(0);
        strokeWeight(1);
      
      //ligne de Ottignies
        line(150, 150, 150, 250);
        line(150, 250, 250, 250);
      
      //ligne de Huy
        line(350, 250, 450, 250);
        line(450, 250, 450, 150);
      
      //ligne de Leuven
        line(200, 100, 300, 200);
        line(400, 100, 300, 200);
        tint(255, 255);
        scale(1);
        stroke(0);
        strokeWeight(5);

      // Les grandes voies principales
        fill(0,255,0);
        line(100, 100, 300, 300);
        line(300, 300, 500, 100);
        line(100, 100, 500, 100);
      
        fill(255);
        strokeWeight(1);
      
      //Dédoublement voies Liege
        line(500, 100, 515,85);
        line(500, 100, 515,115);
        line(515,115,550,115);
        line(515,85,550,85);
      
      //Dédoublement Bruxelles
        line(100, 100, 85,85);
        line(100, 100, 85,115);
        line(85,115,50,115);
        line(85,85,50,85);
      
      //Dédoublement Namur
      line(300, 300, 285,315);
      line(300, 300, 315,315);
      line(285,315,285,345);
      line(315,315,315,345);
      strokeWeight(3);
    // *********************     DEPLACEMENT DU TRAIN   *********************
      //Toutes les positions où le train marque l'arret (donc aux gares, aiguillage)
        int[][] positions = {{100, 100}, {500, 100}, {300, 300}, {200, 100}, {300, 100}, {400, 100}, {150, 150}, {450, 150}, {200, 200}, {400, 200}, {300, 200}, {150, 250}, {250, 250}, {350, 250}, {450, 250}, {300, 300}};
      //train.get_X() += rectSpeedX;
      //train.get_Y() += rectSpeedY;
      //Si le train est à l'une des positions, il s'arrete
        if (limit_reach){
          fill(255,0,0);
          rect(500,425,200,50);
          fill(0);
          textSize(10);
          String limit = "NOMBRES DE TRAIN AUTORISE DEPASSE";
          text(limit, 500, 425);}
        for (Train train : trains) { 
          if (abs(train.get_speedX())>0 || abs(train.get_speedY()) > 0) {
            fill(0, 255, 0);} 
          else {
            fill(255, 0, 0);
          }
          train.draw();
          train.move();
        for (int i = 0; i < positions.length; i++) {
          if (train.get_X() == positions[i][0] && train.get_Y() == positions[i][1]) {
            train.change_speedX(0);
            train.change_speedY(0);
            break; // sortir de la boucle si une position correspond 
          }
        }
        }
    
    // **********************      APPLICATION    *************************
      fill(220, 220, 220);
      rect(700, 300, 200, 300);
      fill(0);
      String App_Titre = "Application";
      text(App_Titre, 700, 575);

      // Position de la liste déroulante à l'intérieur du cadre "Application"
      int xPos = 700 + 20; // Position x du cadre "Application" + 20 pixels
      int yPos = 300 + 40; // Position y du cadre "Application" + 40 pixels

      // Affiche une liste des trains existants sur le réseau (récupère les objets trains), permet d'en sélectionner un et affiche en dessous sa position
      System.out.println("Liste des trains existants sur le réseau :");
      for (Train train : trains) {
        ddlTrains.addItem(train.get_gareDepart() + " - " + train.get_gareDestination(), trains.indexOf(train));
      }
      text(trainInfo, 720, 320);
  
}
/* -------------------------------------------------------- */
/* *********      FONCTION DES BOUTONS    ***************** */
/* -------------------------------------------------------- */
  // SPAWN TRAIN
  public void handleButtonSpawn_click(GButton button, GEvent event) {
    if (event == GEvent.CLICKED) {
      Bruxelles = new GButton(this, 100, 500, 90, 50, "Bruxelles");
      Namur = new GButton(this, 200, 500, 90, 50, "Namur");
      Liege = new GButton(this, 300, 500, 90, 50, "Liege");
      Bruxelles.addEventHandler(this, "handleButtonBruxelles");
      Namur.addEventHandler(this, "handleButtonNamur");
      Liege.addEventHandler(this, "handleButtonLiege");
    }}
  // CHOIX DE LA GARE DE DEPART
  public void handleButtonBruxelles(GButton button, GEvent event) {
      if (event == GEvent.CLICKED) {
        int taille = trains.size();
        if (taille >= LIMITE_TRAIN) {
          println("Trop de train");
          limit_reach = true;
        } else {
          Train nouveauTrain = new Train(100, 100, 0, 0, "Bruxelles", "X");
          trains.add(nouveauTrain);
          println("Train ajouté");
          Bruxelles.dispose();
          Namur.dispose();
          Liege.dispose();
        }
      }}

  public void handleButtonNamur(GButton button, GEvent event) {
      if (event == GEvent.CLICKED) {
        int taille = trains.size();
        if (taille >= LIMITE_TRAIN) {
          println("Trop de train");
          limit_reach = true;
        } else {
          Train nouveauTrain = new Train(100, 100, 0, 0, "Namur", "X");
          trains.add(nouveauTrain);
          println("Train ajouté");
          Bruxelles.dispose();
          Namur.dispose();
          Liege.dispose();
        }
      }}
    
  public void handleButtonLiege(GButton button, GEvent event) {
      if (event == GEvent.CLICKED) {
        int taille = trains.size();
        if (taille >= LIMITE_TRAIN) {
          println("Trop de train");
          limit_reach = true;
        } else {
          Train nouveauTrain = new Train(100, 100, 0, 0, "Liège", "X");
          trains.add(nouveauTrain);
          println("Train ajouté");
          Bruxelles.dispose();
          Namur.dispose();
          Liege.dispose();
        }
      }}


  /* **********   DEPLACEMENTS   ********** */
  // MOVE Bxl <-> Liege
  public void handleButtonB_L_click(GButton button, GEvent event) {
    if (event == GEvent.CLICKED) {
      for (Train train : trains) {if((train.get_X() == 100 && train.get_Y() == 100)|| train.get_sens_B_L() && ((train.get_X() == 200 && train.get_Y() == 100)||(train.get_X() == 400 && train.get_Y() == 100)||(train.get_X() == 300 && train.get_Y() == 100))){
        train.change_speedX(1);
        train.change_sens_B_L(true);
          train.change_gareDestination("Bruxelles");
      train.change_gareDepart("Liege");}
    //Le else if permet de faire le chemin inverse (NAMUR->LIEGE)
    else if ((train.get_X() == 500 && train.get_Y() == 100)|| !train.get_sens_B_L() && ((train.get_X() == 200 && train.get_Y() == 100)||(train.get_X() == 400 && train.get_Y() == 100)||(train.get_X() == 300 && train.get_Y() == 100))){
        train.change_speedX(-1);
        train.change_sens_B_L(false);
            train.change_gareDestination("Liege");
        train.change_gareDepart("Bruxelles");
      }}}}

  // MOVE Namur <-> Liege
  public void handleButtonN_L_click(GButton button, GEvent event) {
    //Si le bouton est cliqué, grace a la position du train et une valeur booleenne qui permet de connaitre le sens du train, le if fait (LIEGE->NAMUR)
    //Tout les (train.get_X() == 350 && train.get_Y() == 150),... sont les arrets aux différentes gares et aiguillages
    if (event == GEvent.CLICKED) {for (Train train : trains) {if((train.get_X() == 500 && train.get_Y() == 100)|| train.get_sens_N_L() && ((train.get_X() == 450 && train.get_Y() == 150)||(train.get_X() == 400 && train.get_Y() == 200)||(train.get_X() == 350 && train.get_Y() == 250))){
      train.change_speedX(-1);
      train.change_speedY(1);
      train.change_sens_N_L(true);
      train.change_gareDestination("Namur");
      train.change_gareDepart("Liege");}
    //Le else if permet de faire le chemin inverse (NAMUR->LIEGE)
    else if ((train.get_X() == 300 && train.get_Y() == 300)|| !train.get_sens_N_L() &&((train.get_X() == 400 && train.get_Y() == 200)||(train.get_X() == 350 && train.get_Y() == 250)||(train.get_X() == 450 && train.get_Y() == 150))){
        train.change_speedX(1);
        train.change_speedY(-1);
        train.change_sens_N_L(false);
        train.change_gareDestination("Liege");
        train.change_gareDepart("Namur");
      }}}}

  // MOVE Bxl <-> Namur
  public void handleButtonB_N_click(GButton button, GEvent event) {
    if (event == GEvent.CLICKED) {for (Train train : trains) {
      println(train);
      if((train.get_X() == 100 && train.get_Y() == 100)|| train.get_sens_B_N() && ((train.get_X() == 150 && train.get_Y() == 150)||(train.get_X() == 200 && train.get_Y() == 200)||(train.get_X() == 250 && train.get_Y() == 250))){
      train.change_speedX(1);
      train.change_speedY(1);
      println(train);
      train.change_sens_B_N(true);
      train.change_gareDestination("Namur");
      train.change_gareDepart("Bruxelles");}
    //Le else if permet de faire le chemin inverse (NAMUR->LIEGE)
    else if ((train.get_X() == 300 && train.get_Y() == 300)|| !train.get_sens_B_N() &&((train.get_X() == 150 && train.get_Y() == 150)||(train.get_X() == 200 && train.get_Y() == 200)||(train.get_X() == 250 && train.get_Y() == 250))){
        train.change_speedX(-1);
        train.change_speedY(-1);
        train.change_sens_B_N(false);
        train.change_gareDestination("Bruxelles");
        train.change_gareDepart("Namur");;
        ;
      }println(train);}}}

  // *********** AUTRES FONCTIONS ***********
  //Gestion des lignes secondaires
  public void handleButtonSecondary_click(GButton button, GEvent event) {
    if (event == GEvent.CLICKED) {for (Train train : trains) {
    if(train.get_X() == 150 && train.get_Y() == 150 && train.get_sens_B_N()){
      train.change_speedY(1);}
    else if (train.get_X() == 200 && train.get_Y() == 100 && train.get_sens_B_L()){ 
      train.change_speedY(1);
      train.change_speedX(1);}
    else if (train.get_X() == 400 && train.get_Y() == 100 && !train.get_sens_B_L()){
      train.change_speedY(1);
      train.change_speedX(-1);;}
    else if (train.get_X() == 450 && train.get_Y() == 150 && train.get_sens_N_L()){
      train.change_speedY(1);}
    else if (train.get_X() == 350 && train.get_Y() == 250 && !train.get_sens_N_L()){
      train.change_speedX(1);;}
    else if (train.get_X() == 250 && train.get_Y() == 250 && !train.get_sens_B_N()){ 
      train.change_speedX(-1);}   
    else if (train.get_X() == 300 && train.get_Y() == 200){ //COIN B-L
      if(train.get_sens_B_L()){train.change_speedX(1); train.change_speedY(-1);}else{train.change_speedX(-1); train.change_speedY(-1);}}
    else if (train.get_X() == 450 && train.get_Y() == 250){ //COIN N-L
      if(train.get_sens_N_L()){train.change_speedX(-1);}else{train.change_speedY(-1);}}
    else if (train.get_X() == 150 && train.get_Y() == 250 ){ //COIN B-N  bon
      if(train.get_sens_B_N()){train.change_speedX(1);}else{train.change_speedY(-1);}
      }
    //Le else if permet de faire le chemin inverse (NAMUR->LIEGE) 
    println(train);}}}
  // Ajout listener concernant la liste des trains.
    void controlEvent(ControlEvent event) {
      if (event.isFrom(ddlTrains)) {
        int trainIndex = (int) event.getValue();
        Train selectedTrain = trains.get(trainIndex);
        // Affichez les informations du train sélectionné comme vous le souhaitez
        trainInfo = "Position du " + selectedTrain.get_gareDepart() + " - " + selectedTrain.get_gareDestination() + " : x = " + selectedTrain.get_X() + ", y = " + selectedTrain.get_Y();
      }
    } 
