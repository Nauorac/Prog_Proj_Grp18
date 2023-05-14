import g4p_controls.*;
import java.awt.Font;

Train train1;

//création d'une array liste reprennant tout les trains créer
ArrayList<Train> trains = new ArrayList<Train>();
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

float SCALING = 1.2; //taille de la fenetre
int LIMITE_TRAIN = 3; //limite de train sur le circuit

boolean limit_reach = false;
//float train1.get_X(100); // position horizontale initiale du rectangle
//float train1.get_Y(100); // position verticale initiale du rectangle
//float rectSpeedX = 0; // vitesse de déplacement horizontale du rectangle
//float rectSpeedY = 0; // vitesse de déplacement verticale du rectangle
int[][] positions;

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


  size(1000, 700); //taille de la fenetre
  textSize(40); //taille de la police d'ecriture
  textAlign(CENTER, CENTER); //aligne le texte sur son centre et pas son coin haut gauche (qui est l'option par defaut)
  rectMode(CENTER); // aligne le rectangle en son point central et pas son coin gauche
  
  //Ajout des event quand on clique sur les boutons (lie le bouton à une fonction)
  buttonB_L.addEventHandler(this, "handleButtonB_L_click");
  buttonN_L.addEventHandler(this, "handleButtonN_L_click");
  buttonB_N.addEventHandler(this, "handleButtonB_N_click");
  secondary_button.addEventHandler(this, "handleButtonSecondary_click");
  spawn_button.addEventHandler(this, "handleButtonSpawn_click");
  GButton.useRoundCorners(false); // crée des boutons avec des coins non arrondi, plus esthétique je trouve
  
}

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

  //Coordonnées des gares
    //Bruxelles (100,100) ROUGE
    //Namur (300,300) VERT
    //Liege (500,100) BLEU
    //Ottignies(200,200) JAUNE
    //Leuven (300,100)  MAGENTA
    //Huy (400,200) CYAN
  
  // *******************       DESSIN DES GARES  *************************
  // Dessiner (LIEGE)
    strokeWeight(1);
    fill(0, 0, 255,127);
    rect(520,100,100,50);
    fill(0);
    String gare_L = "Liege";
    text(gare_L, 500, 130);
  
  // Dessiner (NAMUR)
    fill(0, 255, 0,127);
    rect(300,320,50,100);
    fill(0);
    String gare_N = "Namur";
    text(gare_N, 300, 260);
  
  // Dessiner (BRUXELLES)
    fill(255, 0, 0,127);
    rect(80,100,100,50);
    fill(0);
    String gare_B = "Bruxelles";
    text(gare_B, 100, 130);
  
  // Dessiner (OTTIGNIES)
    fill(255, 255, 0, 127);
    rect(200,200, 75, 75);
    fill(0);
    String gare_O = "Ott";
    text(gare_O, 185, 215);

  // Dessiner (HUY)
    fill(0, 255, 255, 127);
    rect(400,200, 75, 75);
    fill(0);
    String gare_H = "Huy";
    text(gare_H, 415, 215);
  
  // Dessiner (LEUVEN)
    fill(255, 0, 255, 127);
    rect(300,90, 100, 60);
    fill(0);
    String gare_Ln = "Lvn";
    text(gare_Ln, 300, 110);
  


  // ********************      DESSIN DES VOIES   *************************
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
    line(100, 100, 300, 300); // dessine la grande ligne entre Bruxelles et Namur
    line(300, 300, 500, 100); // dessine la grande ligne entre Namur et Liege
    line(100, 100, 500, 100); // dessine la grande ligne entre Bruxelles et Liege
  
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
  //train.get_X() += rectSpeedX;
  //train.get_Y() += rectSpeedY;
  //Si le train est à l'une des positions, il s'arrete
  //Affichage d'un message sur l'ecran pour avertir que le nombre maximal de train autorisé sur le circuit est deja dépassé
  if (limit_reach){
    fill(255,0,0);
    rect(500,425,200,50);
    fill(0);
    textSize(10);
    String limit = "NOMBRES DE TRAIN AUTORISE DEPASSE";
    text(limit, 500, 425);}
   
  // crée une boucle qui reprend tout les trains de l'array list, le train est défini par la variable "train"
  for (Train train : trains) {
  //colore le train en vert lorsqu'il bouge
  if (abs(train.get_speedX())>0 || abs(train.get_speedY()) > 0) {
    fill(0, 255, 0);} 
  else {
    fill(255, 0, 0);
  }
  
  //dessine le train et le fait bouger
  train.draw();
  train.move();
  

  //Toutes les positions où le train marque l'arret (donc aux gares, aiguillage)
  int[][] positions = {{100, 100}, {500, 100}, {300, 300}, {200, 100}, {300, 100}, {400, 100}, {150, 150}, {450, 150}, {200, 200}, {400, 200}, {300, 200}, {150, 250}, {250, 250}, {350, 250}, {450, 250}, {300, 300}};
  //Stop le train si ses coordonnées X Y correspondent a une coordonnées de la liste ci-dessus
  for (int i = 0; i < positions.length; i++) {
    if (train.get_X() == positions[i][0] && train.get_Y() == positions[i][1]) {
      train.change_speedX(0);
      train.change_speedY(0);
      break; // sortir de la boucle si une position correspond
      
    }
  }
  }//fin de la boucle reprenant tout les trains

  
  // **********************      APPLICATION    *************************
  //Application
  fill(220,220,220); // couleur gris
  rect(700,300,200,300);
  fill(0);
  String App_Titre = "Application";
  text(App_Titre, 700, 175);
  scale(1/SCALING); //permet d'eviter le bug des boutons avec la fonction scale()
}
}

// **********************      FONCTION DES BOUTONS    *************************
// Bouger le train entre Bruxelles et Liege
public void handleButtonB_L_click(GButton button, GEvent event) {
  // si le bouton est cliqué
  if (event == GEvent.CLICKED) {
    //Pour chaques train, si s'il est a une gare, change son sens et le fait partir, s'il est sur la voie entre deux gares principales, on verifie son sens et sa position pour le faire continuer
    for (Train train : trains) {if((train.get_X() == 100 && train.get_Y() == 100)|| train.get_sens_B_L() && ((train.get_X() == 200 && train.get_Y() == 100)||(train.get_X() == 400 && train.get_Y() == 100)||(train.get_X() == 300 && train.get_Y() == 100))){
      //Envoie le train vers Liege
      train.change_speedX(1);
      //si sens_B_L = true, se dirige vers Liege
      train.change_sens_B_L(true);
      train.change_gareDestination("Liege");
    train.change_gareDepart("Bruxelles");}
  //Le else if permet de faire le chemin inverse (LIEGE->BRUXELLES)
  else if ((train.get_X() == 500 && train.get_Y() == 100)|| !train.get_sens_B_L() && ((train.get_X() == 200 && train.get_Y() == 100)||(train.get_X() == 400 && train.get_Y() == 100)||(train.get_X() == 300 && train.get_Y() == 100))){
    //envoie le train vers Bruxelles
    train.change_speedX(-1);
    train.change_sens_B_L(false);
    train.change_gareDestination("Bruxelles");
    train.change_gareDepart("Liege");
    }}}}

//Entre Namur et Liege
public void handleButtonN_L_click(GButton button, GEvent event) {
  //Si le bouton est cliqué, grace a la position du train et une valeur booleenne qui permet de connaitre le sens du train, le if fait (LIEGE->NAMUR)
  //Tout les (train.get_X() == 350 && train.get_Y() == 150),... sont les arrets aux différentes gares et aiguillages
  if (event == GEvent.CLICKED) {for (Train train : trains) {if((train.get_X() == 500 && train.get_Y() == 100)|| train.get_sens_N_L() && ((train.get_X() == 450 && train.get_Y() == 150)||(train.get_X() == 400 && train.get_Y() == 200)||(train.get_X() == 350 && train.get_Y() == 250))){
    train.change_speedX(-1);
    train.change_speedY(1);
    // sens_N_L = true -> le train va de Liege vers Namur
    train.change_sens_N_L(true);
    train.change_gareDestination("Namur");
    train.change_gareDepart("Liege");}
  //Le else if permet de faire le chemin inverse (NAMUR->LIEGE)
  else if ((train.get_X() == 300 && train.get_Y() == 300)|| !train.get_sens_N_L() &&((train.get_X() == 400 && train.get_Y() == 200)||(train.get_X() == 350 && train.get_Y() == 250)||(train.get_X() == 450 && train.get_Y() == 150))){
      train.change_speedX(1);
      train.change_speedY(-1);
      // sens_N_L = false -> le train va de Liege vers Namur
      train.change_sens_N_L(false);
      train.change_gareDestination("Liege");
      train.change_gareDepart("Namur");
    }}}}

//Bruxelles et Namur
public void handleButtonB_N_click(GButton button, GEvent event) {
  if (event == GEvent.CLICKED) {for (Train train : trains) {
    if((train.get_X() == 100 && train.get_Y() == 100)|| train.get_sens_B_N() && ((train.get_X() == 150 && train.get_Y() == 150)||(train.get_X() == 200 && train.get_Y() == 200)||(train.get_X() == 250 && train.get_Y() == 250))){
    train.change_speedX(1);
    train.change_speedY(1);
    // sens_B_N = true -> le train va de Bruxelles vers Namur
    train.change_sens_B_N(true);
    train.change_gareDestination("Namur");
    train.change_gareDepart("Bruxelles");}
  //Le else if permet de faire le chemin inverse (NAMUR->LIEGE)
  else if ((train.get_X() == 300 && train.get_Y() == 300)|| !train.get_sens_B_N() &&((train.get_X() == 150 && train.get_Y() == 150)||(train.get_X() == 200 && train.get_Y() == 200)||(train.get_X() == 250 && train.get_Y() == 250))){
      train.change_speedX(-1);
      train.change_speedY(-1);
      // sens_B_N = false -> le train va de Namur vers Bruxelles
      train.change_sens_B_N(false);
      train.change_gareDestination("Bruxelles");
      train.change_gareDepart("Namur");
      
    }}}}
    
    
//Gestion des lignes secondaires
public void handleButtonSecondary_click(GButton button, GEvent event) {
    //Si le bouton est cliqué, on va vérifier chaque sens de trains pour savoir s'il peut partir sur la voie secondaire, si oui alors on lui donne la speedX et speedY pour bouger
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
    }}}

public void handleButtonSpawn_click(GButton button, GEvent event) {
  if (event == GEvent.CLICKED) {
    Bruxelles = new GButton(this, 100, 500, 90, 50, "Bruxelles");
    Namur = new GButton(this, 200, 500, 90, 50, "Namur");
    Liege = new GButton(this, 300, 500, 90, 50, "Liege");
    Bruxelles.addEventHandler(this, "handleButtonBruxelles");
    Namur.addEventHandler(this, "handleButtonNamur");
    Liege.addEventHandler(this, "handleButtonLiege");
    }}

  /*public void handleButtonBruxelles_click(GButton button, GEvent event){
    //if(event == GEvent.CLICKED){
      int taille = trains.size();
      if (taille >= LIMITE_TRAIN){println("trop de train");limit_reach = true;}
    else {train1 = new Train(100,100,0,0, "X", "X");
    trains.add(train1);println("train ajouté");}
    //}
  }*/

    //Namur (300,300) VERT
    //Liege (500,100) BLEU

public void handleButtonBruxelles(GButton button, GEvent event){
  if (event == GEvent.CLICKED) {
    int taille = trains.size();
    if (taille >= LIMITE_TRAIN){println("trop de train");limit_reach = true;}
    else {train1 = new Train(100,100,0,0, "X", "X");
    trains.add(train1);println("train ajouté");
    Bruxelles.dispose();
    Namur.dispose();
    Liege.dispose();}}}

public void handleButtonNamur(GButton button, GEvent event){
  if (event == GEvent.CLICKED) {
    int taille = trains.size();
    if (taille >= LIMITE_TRAIN){println("trop de train");limit_reach = true;}
    else {train1 = new Train(300,300,0,0, "X", "X");
    trains.add(train1);println("train ajouté");
    Bruxelles.dispose();
    Namur.dispose();
    Liege.dispose();}}}
  
public void handleButtonLiege(GButton button, GEvent event){
  if (event == GEvent.CLICKED) {
    int taille = trains.size();
    if (taille >= LIMITE_TRAIN){println("trop de train");limit_reach = true;}
    else {train1 = new Train(500,100,0,0, "X", "X");
    trains.add(train1);println("train ajouté");
    Bruxelles.dispose();
    Namur.dispose();
    Liege.dispose();}}}
    
