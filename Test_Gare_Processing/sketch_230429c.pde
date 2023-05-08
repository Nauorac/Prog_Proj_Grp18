import g4p_controls.*;

boolean sens_B_L = true;
boolean sens_N_L = true;
boolean sens_B_N = true;
GButton buttonB_L;
GButton buttonN_L;
GButton buttonB_N;
GButton secondary_button;
PImage gare;


float rectX = 100; // position horizontale initiale du rectangle
float rectY = 100; // position verticale initiale du rectangle
float rectSpeedX = 0; // vitesse de déplacement horizontale du rectangle
float rectSpeedY = 0; // vitesse de déplacement verticale du rectangle
int[][] positions;

void setup() {
  buttonB_L = new GButton(this, 0, 0, 50, 50, "Bruxelles-Liege");

  buttonN_L = new GButton(this, 0, 50, 50, 50, "Namur-Liege");
  buttonB_N = new GButton(this, 0, 100, 50, 50, "Bruxelles-Namur");
  secondary_button = new GButton(this, 0, 150, 50,50, "Ligne secondaire");

  size(600, 400);
  textSize(18);
  textAlign(CENTER, CENTER);
  rectMode(CENTER);
  imageMode(CENTER); // définir le mode d'affichage de l'image
  buttonB_L.addEventHandler(this, "handleButtonB_L_click");
  buttonN_L.addEventHandler(this, "handleButtonN_L_click");
  buttonB_N.addEventHandler(this, "handleButtonB_N_click");
  secondary_button.addEventHandler(this, "handleButtonSecondary_click");
  gare = loadImage("gare.png"); // chargement de l'image
  GButton.useRoundCorners(false);
}

void draw() {
  
  background(255);
  
  //Coordonnées des gares (ne pas preter attention aux couleurs)
  //Bruxelles (100,100) ROUGE
  //Namur (300,300) VERT
  //Liege (500,100) BLEU
  //Ottignies(200,200) JAUNE
  //Leuven (300,100)  MAGENTA
  //Huy (400,200) CYAN
  
  //DESSIN DES GARES
  
  // Dessiner (lge)
  noStroke();
  fill(0, 0, 255);
  image(gare,500,100);
  fill(0);
  String gare_L = "Liege";
  text(gare_L, 500, 130);
  
  // Dessiner (namur)
  noStroke();
  fill(0, 255, 0);
  image(gare, 300, 300);
  fill(0);
  String gare_N = "Namur";
  text(gare_N, 300, 330);
  
  // Dessiner (BXL)
  noStroke();
  fill(255, 0, 0);
  image(gare,100,100);
  fill(0);
  String gare_B = "Bruxelles";
  text(gare_B, 100, 130);
  
  // Dessiner (ott)
  noStroke();
  fill(255, 255, 0);
  ellipse(200,200, 50, 50);
  fill(0);
  String gare_O = "Ott";
  text(gare_O, 200, 200);

  // Dessiner (huy)
  noStroke();
  fill(0, 255, 255);
  ellipse(400,200, 50, 50);
  fill(0);
  String gare_H = "Huy";
  text(gare_H, 400, 200);
  
  // Dessiner (lvn)
  noStroke();
  fill(255, 0, 255);
  ellipse(300,100, 50, 50);
  fill(0);
  String gare_Ln = "Lvn";
  text(gare_Ln, 300, 100);
  
  //DESSIN DES VOIES
  
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
  strokeWeight(3);
  
  
  //DEPLACEMENT DU TRAIN
  
  //Toutes les positions où le train marque l'arret (donc aux gares, aiguillage)
  int[][] positions = {{100, 100}, {500, 100}, {300, 300}, {200, 100}, {300, 100}, {400, 100}, {150, 150}, {450, 150}, {200, 200}, {400, 200}, {300, 200}, {150, 250}, {250, 250}, {350, 250}, {450, 250}, {300, 300}};

  rectX += rectSpeedX;
  rectY += rectSpeedY;

for (int i = 0; i < positions.length; i++) {
  if (rectX == positions[i][0] && rectY == positions[i][1]) {
    rectSpeedX = 0;
    rectSpeedY = 0;
    break; // sortir de la boucle si une position correspond
  }
}



if (abs(rectSpeedY)>0 || abs(rectSpeedX) > 0) {
    fill(0, 255, 0);
  } else {
    fill(255, 0, 0);
  }
  buttonB_L.setEnabled(false);
  buttonB_N.setEnabled(false);
  buttonN_L.setEnabled(false);
  secondary_button.setEnabled(false);
  
  //Activation des boutons quand nécessaire
  
  if(rectX == 100 && rectY == 100 || rectX == 200 && rectY == 100|| rectX == 300 && rectY == 100|| rectX == 400 && rectY == 100|| rectX == 500 && rectY == 100){
    buttonB_L.setEnabled(true);}
  if(rectX == 100 && rectY == 100 || rectX == 150 && rectY == 150|| rectX == 200 && rectY == 200|| rectX == 250 && rectY == 250|| rectX == 300 && rectY == 300){
    buttonB_N.setEnabled(true);}
  if(rectX == 300 && rectY == 300 || rectX == 350 && rectY == 250|| rectX == 400 && rectY == 200|| rectX == 450 && rectY == 150|| rectX == 500 && rectY == 100){
    buttonN_L.setEnabled(true);}
  if((rectX == 200 && rectY == 100 && sens_B_L) || (rectX == 400 && rectY == 100 &&!sens_B_L)|| (rectX == 150 && rectY == 150 && sens_B_N)|| (rectX == 450 && rectY == 150 && sens_N_L)|| (rectX == 300 && rectY == 200) || (rectX == 150 && rectY == 250)|| (rectX == 250 && rectY == 250 &&!sens_B_N)|| (rectX == 350 && rectY == 250 && !sens_N_L)|| (rectX == 450 && rectY == 250)){
    secondary_button.setEnabled(true);}
  rect(rectX, rectY, 50, 25,3);
  
}


//FONCTION DES BOUTONS

// Bouger le train entre Bruxelles et Liege
public void handleButtonB_L_click(GButton button, GEvent event) {
  
  if (event == GEvent.CLICKED) {if((rectX == 100 && rectY == 100)|| sens_B_L && ((rectX == 200 && rectY == 100)||(rectX == 400 && rectY == 100)||(rectX == 300 && rectY == 100))){
    rectSpeedX = 1;
    sens_B_L = true;}
//Le else if permet de faire le chemin inverse (NAMUR->LIEGE)
else if ((rectX == 500 && rectY == 100)|| !sens_B_L && ((rectX == 200 && rectY == 100)||(rectX == 400 && rectY == 100)||(rectX == 300 && rectY == 100))){
    rectSpeedX = -1;
    sens_B_L = false;
    
    }}}

//Entre Namur et Liege
public void handleButtonN_L_click(GButton button, GEvent event) {
  //Si le bouton est cliqué, grace a la position du train et une valeur booleenne qui permet de connaitre le sens du train, le if fait (LIEGE->NAMUR)
  //Tout les (rectX == 350 && rectY == 150),... sont les arrets aux différentes gares et aiguillages
  
  if (event == GEvent.CLICKED) {if((rectX == 500 && rectY == 100)|| sens_N_L && ((rectX == 450 && rectY == 150)||(rectX == 400 && rectY == 200)||(rectX == 350 && rectY == 250))){
    rectSpeedX = -1;
    rectSpeedY = 1;
    sens_N_L = true;}
//Le else if permet de faire le chemin inverse (NAMUR->LIEGE)
else if ((rectX == 300 && rectY == 300)|| !sens_N_L &&((rectX == 400 && rectY == 200)||(rectX == 350 && rectY == 250)||(rectX == 450 && rectY == 150))){
    rectSpeedX = 1;
    rectSpeedY = -1;
    sens_N_L = false;
    
    }}}

//Bruxelles et Namur
public void handleButtonB_N_click(GButton button, GEvent event) {
  if (event == GEvent.CLICKED) {if((rectX == 100 && rectY == 100)|| sens_B_N && ((rectX == 150 && rectY == 150)||(rectX == 200 && rectY == 200)||(rectX == 250 && rectY == 250))){
    rectSpeedX = 1;
    rectSpeedY = 1;
    sens_B_N = true;}
//Le else if permet de faire le chemin inverse (NAMUR->LIEGE)
else if ((rectX == 300 && rectY == 300)|| !sens_B_N &&((rectX == 150 && rectY == 150)||(rectX == 200 && rectY == 200)||(rectX == 250 && rectY == 250))){
    rectSpeedX = -1;
    rectSpeedY = -1;
    sens_B_N = false;
    }}}
    
//Gestion des lignes secondaires

public void handleButtonSecondary_click(GButton button, GEvent event) {
  if (event == GEvent.CLICKED) {
    println(rectX);
    println(rectY);
  if(rectX == 150 && rectY == 150 && sens_B_N){
    rectSpeedY = 1;
    }
  else if (rectX == 200 && rectY == 100 && sens_B_L){ 
    rectSpeedY = 1;
    rectSpeedX = 1;}
  else if (rectX == 400 && rectY == 100 && !sens_B_L){
    rectSpeedY = 1;
    rectSpeedX = -1;}
  else if (rectX == 450 && rectY == 150 && sens_N_L){
    rectSpeedY = 1;}
  else if (rectX == 350 && rectY == 250 && !sens_N_L){
    rectSpeedX = 1;}
  else if (rectX == 250 && rectY == 250 && !sens_B_N){ 
    rectSpeedX = -1;}
  else if (rectX == 150 && rectY == 150 && sens_B_N){
    rectSpeedY = 1;}
    
  else if (rectX == 300 && rectY == 200){ //COIN B-L
    if(sens_B_L){rectSpeedX = 1;rectSpeedY = -1;}else{rectSpeedX = -1;rectSpeedY = -1;}}
    
  else if (rectX == 450 && rectY == 250){ //COIN N-L
    if(sens_N_L){rectSpeedX = -1;}else{rectSpeedY = -1;}}
    
  else if (rectX == 150 && rectY == 250 ){ //COIN B-N  bon
    if(sens_B_N){rectSpeedX = 1;}else{rectSpeedY = -1;}
    }
//Le else if permet de faire le chemin inverse (NAMUR->LIEGE)

    
    }}
