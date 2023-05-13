class Train {
float X;
float Y;
float speedX;
float speedY;
boolean sens_B_N;
boolean sens_B_L;
boolean sens_N_L;
String gareDepart;
String gareDestination;

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

float get_X(){return X;}
float get_Y(){return Y;}
float get_speedX(){return speedX;}
float get_speedY(){return speedY;}
boolean get_sens_B_L(){return sens_B_L;}
boolean get_sens_B_N(){return sens_B_N;}
boolean get_sens_N_L(){return sens_N_L;}
String get_gareDepart(){return gareDepart;}
String get_gareDestination(){return gareDestination;}

void change_gareDepart(String gare){gareDepart = gare;}
void change_gareDestination(String gare){gareDestination = gare;}

void change_sens_B_L(boolean sens){sens_B_L = sens;}
void change_sens_B_N(boolean sens){sens_B_N = sens;}
void change_sens_N_L(boolean sens){sens_N_L = sens;}

void change_X(float z){
X = z;}
void change_Y(float z){
Y = z;}
void change_speedX(float z){
speedX = z;}

void change_speedY(float z){
speedY = z;}

void move(){
  X+=speedX;
  Y+=speedY;}

void draw(){
rect(X, Y, 40, 15,3);}
  
  
public String toString() {
  return "Train[X=" + X + ", Y=" + Y + ", speedX=" + speedX + ", speedY=" + speedY + ", sens_B_N=" + sens_B_N +", sens_B_L=" + sens_B_L +", sens_N_L=" + sens_N_L +"]";
}
}
