String message = "Bienvenue dans notre système de transport";
int index = 0;
int x, y;
int alpha = 0;
int messageTime = 0; // variable pour suivre le temps écoulé depuis l'affichage du message
boolean showMessage = true; // booléen pour indiquer si le message est affiché ou non
void setup() {
  size(800, 600);
  background(0);
  x = width / 2;
  y = height / 2;
  textAlign(CENTER, CENTER);
  textSize(40);
  fill(200, alpha);
  //Initialise code EventB en JAVA
  TrainSystems trainSystems = new TrainSystems();
  //Attribution de la constante Namur dans SelectGare
  TrainSystems.Gare SelectGare = TrainSystems.Gare.Namur ;
  //Apparition d'un train grâce au paramètre fourni
  trainSystems.apparitionTrain(SelectGare);
  //Affiche du nombre de train dans les gares dans la console ! 
  print(trainSystems.nbTrain);
  
}
void draw() {
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
    background(0);}
  //Affichage, même chose mais en processing et non dans la console   
  TrainSystems trainSystems = new TrainSystems();
  TrainSystems.Gare SelectGare = TrainSystems.Gare.Namur ;
  trainSystems.apparitionTrain(TrainSystems.Gare.Namur);
  int nbTrains = trainSystems.nbTrain.get(TrainSystems.Gare.Namur);
  String trainMessage = "Nombre de trains dans les gares : " + trainSystems.nbTrain;
  textSize(20);
  fill(200);
  text(trainMessage, width/2, height-50);  
}
