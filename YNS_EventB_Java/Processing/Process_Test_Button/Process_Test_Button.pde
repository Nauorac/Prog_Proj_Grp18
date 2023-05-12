Button namurButton, bruxellesButton;
Button appTrainButton ; 
boolean namur = false , bruxelles = false;
String message = "Bienvenue dans notre système de transport";
int index = 0;
int x, y;
int alpha = 0;
int messageTime = 0; // variable pour suivre le temps écoulé depuis l'affichage du message
boolean showMessage = true; // booléen pour indiquer si le message est affiché ou non
boolean check = false ; 
TrainSystems trainSystems = new TrainSystems();
void setup() {
  size(1900, 800);
  namurButton = new Button("Namur", 50, 100, 100, 50);
  bruxellesButton = new Button("Bruxelles", 250, 100, 100, 50);
  appTrainButton = new Button("", width/2-50, height/2-25, 100, 50);
  background(0);
  x = width / 2;
  y = height / 2;
  textAlign(CENTER, CENTER);
  textSize(40);
  fill(200, alpha);
  /*Initialise code EventB en JAVA
  TrainSystems trainSystems = new TrainSystems();
  //Attribution de la constante Namur dans SelectGare
  TrainSystems.Gare SelectGare = TrainSystems.Gare.Namur ;
  //Apparition d'un train grâce au paramètre fourni
  trainSystems.apparitionTrain(SelectGare);
  //Affiche du nombre de train dans les gares dans la console ! 
  print(trainSystems.nbTrain);*/
  
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
    background(0);
    TrainSystems.Gare SelectGare = TrainSystems.Gare.Namur ;
    String trainMessage = "Nombre de trains dans les gares : " + trainSystems.nbTrain;
    textSize(20);
    fill(200);
    text(trainMessage, width/2, height-50);
    appTrainButton.display();
    text("Spawn", width/2, height/2);
  }
  if (check == true){
    namurButton.display();
    bruxellesButton.display();
    fill(200);
    text("Namur", 150, 300);
    text("Bruxelles", 350, 300);
  }
  if (namur == true) {
    trainSystems.apparitionTrain(TrainSystems.Gare.Namur);
    textSize(20);
    fill(200);
    String trainMessage = "Nombre de trains dans les gares : " + trainSystems.nbTrain;
    text(trainMessage, width/2, height-50);
  }
 
    if (bruxelles == true) {
    trainSystems.apparitionTrain(TrainSystems.Gare.Bruxelles);
    String trainMessage = "Nombre de trains dans les gares : " + trainSystems.nbTrain;
    textSize(20);
    fill(200);
    text(trainMessage, width/2, height-50);
  }
  
  //Affichage, même chose mais en processing et non dans la console   
  //trainSystems.apparitionTrain(TrainSystems.Gare.Namur);
  //int nbTrains = trainSystems.nbTrain.get(TrainSystems.Gare.Namur);
  //String trainMessage = "Nombre de trains dans les gares : " + trainSystems.nbTrain;
}
void mousePressed() {
  if (namurButton.isPressed()) {
    namur = true;
  }
  if (bruxellesButton.isPressed()) {
    bruxelles = true;
  }
  if (appTrainButton.isPressed()){
    check = true ; }
    
}  
class Button {
  String label;
  int x, y, w, h;

  Button(String label, int x, int y, int w, int h) {
    this.label = label;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }

  void display() {
    rect(x, y, w, h);
    textAlign(CENTER, CENTER);
    fill(255);
    text(label, x + w / 2, y + h / 2);
  }

  boolean isPressed() {
    return mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h;
  }
}
