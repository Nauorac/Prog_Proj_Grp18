class Train {

  //Position X et Y du train
float X;
float Y;

//speedX =  vitesse du train en axe X, si speedx>0, le train se déplace vers la droite, si speedX<0, le train se deplace vers la gauche, si speedX = 0, alors il ne bouge pas sur l'axe  X
float speedX;
//speedY =  vitesse du train en axe Y, si speedy>0, le train se déplace vers le bas, si speedY<0, le train se deplace vers le haut, si speedX = 0, alors il ne bouge pas sur l'axe  Y
float speedY;

//Détermine le sens du train sur une voie (ex: sens_B_N permet de savoir si le train se dirige vers Bruxelles ou vers Namur)
boolean sens_B_N; //si true, le train se dirige vers Namur, si false alors se dirige vers Bruxelles
boolean sens_B_L; //si true, le train se dirige vers Liege, si false alors se dirige vers Bruxelles
boolean sens_N_L; //si true, le train se dirige vers Namur, si false alors se dirige vers Liege

//Noms de la gare de départ et d'arrivée
String gareDepart;
String gareDestination;

//Constructeur
Train (float X, float Y, float speedX, float speedY, String gareDepart, String gareDestination){
  this.X = X;
  this.Y = Y;
  this.speedX = speedX;
  this.speedY = speedY;
  this.sens_B_N = true;
  this.sens_B_L = true;
  this.sens_N_L = true;
  this.gareDepart = gareDepart;
  this.gareDestination = gareDestination;
}

//Toutes les fonctions get_
float get_X(){return X;}
float get_Y(){return Y;}
float get_speedX(){return speedX;}
float get_speedY(){return speedY;}
boolean get_sens_B_L(){return sens_B_L;}
boolean get_sens_B_N(){return sens_B_N;}
boolean get_sens_N_L(){return sens_N_L;}
String get_gareDepart(){return gareDepart;}
String get_gareDestination(){return gareDestination;}

//Change la gare de depart et de destination
void change_gareDepart(String gare){gareDepart = gare;}
void change_gareDestination(String gare){gareDestination = gare;}

//change le sens du train
void change_sens_B_L(boolean sens){sens_B_L = sens;}
void change_sens_B_N(boolean sens){sens_B_N = sens;}
void change_sens_N_L(boolean sens){sens_N_L = sens;}

//change la valeur X et Y
void change_X(float z){
X = z;}
void change_Y(float z){
Y = z;}

//change la vitesse du train
void change_speedX(float z){
speedX = z;}
void change_speedY(float z){
speedY = z;}

//fais bouger le train
void move(){
  X+=speedX;
  Y+=speedY;}

//dessine le rectangle qui représentera le train
void draw(){
rect(X, Y, 40, 15,3);}
  
//permet de println un train et d'avoir les informations le concernant
public String toString() {
  return "Train[X=" + X + ", Y=" + Y + ", speedX=" + speedX + ", speedY=" + speedY + ", sens_B_N=" + sens_B_N +", sens_B_L=" + sens_B_L +", sens_N_L=" + sens_N_L +"]";
}
}
