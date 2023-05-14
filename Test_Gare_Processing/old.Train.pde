class Train {
  private float X; // Position X du train
  private float Y; // Position Y du train
  private float speedX; // Vitesse en X du train
  private float speedY; // Vitesse en Y du train
  private boolean sens_B_N; // Sens entre les gares B et N
  private boolean sens_B_L; // Sens entre les gares B et L
  private boolean sens_N_L; // Sens entre les gares N et L
  private String gareDepart; // Gare de départ du train
  private String gareDestination; // Gare de destination du train

  // Constructeur de la classe Train
    Train(float X, float Y, float speedX, float speedY, String gareDepart, String gareDestination) {
      this.X = X;
      this.Y = Y;
      this.speedX = speedX;
      this.speedY = speedY;
      this.sens_B_N = true;
      this.sens_B_L = true;
      this.sens_N_L = true;
      this.gareDepart = gareDepart;
      this.gareDestination = gareDestination;}
  // Méthodes d'accès (getters)
    float get_X() { return X; }
    float get_Y() { return Y; }
    float get_speedX() { return speedX; }
    float get_speedY() { return speedY; }
    boolean get_sens_B_L() { return sens_B_L; }
    boolean get_sens_B_N() { return sens_B_N; }
    boolean get_sens_N_L() { return sens_N_L; }
    String get_gareDepart() { return gareDepart; }
    String get_gareDestination() { return gareDestination; }

  // Méthodes de modification (setters)
    void change_gareDepart(String gare) { gareDepart = gare; }
    void change_gareDestination(String gare) { gareDestination = gare; }
    void change_sens_B_L(boolean sens) { sens_B_L = sens; }
    void change_sens_B_N(boolean sens) { sens_B_N = sens; }
    void change_sens_N_L(boolean sens) { sens_N_L = sens; }
    void change_X(float nouvelleValeur) { X = nouvelleValeur; }
    void change_Y(float nouvelleValeur) { Y = nouvelleValeur; }
    void change_speedX(float nouvelleValeur) { speedX = nouvelleValeur; }
    void change_speedY(float nouvelleValeur) { speedY = nouvelleValeur; }

  // Méthode pour déplacer le train
    void move() {
      X += speedX;
      Y += speedY;
    }

  // Méthode pour dessiner le train
    void draw() {
      rect(X, Y, 40, 15, 3);
    }

// Méthode pour afficher les informations du train sous forme de chaîne de caractères
  public String toString() {
    return "Train[X=" + X + ", Y=" + Y + ", gareDepart=" + gareDepart + ", gareDestination=" + gareDestination + "]";
  }
}