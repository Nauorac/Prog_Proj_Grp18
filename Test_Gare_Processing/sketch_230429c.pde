import g4p_controls.*;

boolean buttonState = false;
GButton button;

PImage voie;
PImage gare;

float rectX = 130; // position horizontale initiale du rectangle
float rectSpeed = 0; // vitesse de déplacement horizontale du rectangle


void setup() {
  button = new GButton(this, 0, 0, 50, 50);
  button.setText("Cliquez-moi !");
  size(600, 400);
  textSize(18);
  textAlign(CENTER, CENTER);
  rectMode(CENTER);
  imageMode(CENTER); // définir le mode d'affichage de l'image
  
  voie = loadImage("download1.png"); // chargement de l'image
  gare = loadImage("gare (1).png"); // chargement de l'image
}

void draw() {
  background(255);
  //Bruxelles (100,100) ROUGE
  //Namur (300,300) VERT
  //Liege (500,100) BLEU
  //Ottignies(200,200) JAUNE
  //Leuven (300,100)  MAGENTA
  //Huy (400,200) CYAN
  // Dessiner la voie entre Bruxelles et Namur

  
  // Dessiner (lge)
  noStroke();
  fill(0, 0, 255);
  //ellipse(500, 100, 75, 75);
  image(gare,500,100);
  fill(0);
  String gare_L = "Liege";
  text(gare_L, 500, 130);
  
  // Dessiner (namur)
  noStroke();
  fill(0, 255, 0);
  //ellipse(300, 300, 75, 75);
  image(gare, 300, 300);
  fill(0);
  String gare_N = "Namur";
  text(gare_N, 300, 330);
  
  // Dessiner (BXL)
  noStroke();
  fill(255, 0, 0);
  //ellipse(100,100, 75, 75);
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
  line(200, 100, 300, 5);
  line(400, 100, 300, 5);
  tint(255, 255);
  scale(1);
    stroke(0);
  strokeWeight(10);


  // affichage de l'image
  fill(0,255,0);
  line(130, 130, 270, 300);
  line(330, 300, 470, 130);
  line(130, 100, 470, 100);
  strokeWeight(3);

  rectX += rectSpeed;

  
  
  if (rectX > 470 || rectX< 130) {
    rectSpeed = 0; // recommencer le déplacement à gauche de la fenêtre
  }


if (buttonState && rectX <= 131) {
    rectSpeed = 2;}

  
if (buttonState && rectX >= 470) {
    rectSpeed = -2;


}
if (buttonState) {
    fill(0, 255, 0);
  } else {
    fill(255, 0, 0);
  }
  rect(rectX, 100, 50, 25,10);
}

void handleButtonEvents(GButton button, GEvent event) {
  if (event == GEvent.CLICKED) {
    buttonState = !buttonState;

  }}
