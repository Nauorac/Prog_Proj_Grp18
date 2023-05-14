
// Variables pour les boutons
  GButton buttonB_L;
  GButton buttonN_L;
  GButton buttonB_N;
  GButton secondary_button;
  GButton spawn_button;
  GButton Bruxelles ; 
  GButton Namur;
  GButton Liege;

/* -------------------------------------------------------- */
/* *********      CREATION DES BOUTONS    ***************** */
/* -------------------------------------------------------- */
    void createButtons() {
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

    buttonB_L.addEventHandler(this, "handleButtonB_L_click");
    buttonN_L.addEventHandler(this, "handleButtonN_L_click");
    buttonB_N.addEventHandler(this, "handleButtonB_N_click");
    secondary_button.addEventHandler(this, "handleButtonSecondary_click");
    spawn_button.addEventHandler(this, "handleButtonSpawn_click");
    GButton.useRoundCorners(false);}

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
//Coordonnées des gares
    // GARES PRINCIPALES - Bruxelles (100,100) ROUGE || Namur (300,300) VERT || Liege (500,100) BLEU
    // GARES SECONDAIRES - Ottignies(200,200) JAUNE ||  Leuven (300,100)  MAGENTA || Huy (400,200) CYAN
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
            updateDropdownList();
        }}}

    public void handleButtonNamur(GButton button, GEvent event) {
        if (event == GEvent.CLICKED) {
        int taille = trains.size();
        if (taille >= LIMITE_TRAIN) {
            println("Trop de train");
            limit_reach = true;
        } else {
            Train nouveauTrain = new Train(300, 300, 0, 0, "Namur", "X");
            trains.add(nouveauTrain);
            println("Train ajouté");
            Bruxelles.dispose();
            Namur.dispose();
            Liege.dispose();
            updateDropdownList();
        }}}

    public void handleButtonLiege(GButton button, GEvent event) {
      if (event == GEvent.CLICKED) {
        int taille = trains.size();
        if (taille >= LIMITE_TRAIN) {
          println("Trop de train");
          limit_reach = true;
        } else {
          Train nouveauTrain = new Train(500, 100, 0, 0, "Liège", "X");
          trains.add(nouveauTrain);
          println("Train ajouté");
          Bruxelles.dispose();
          Namur.dispose();
          Liege.dispose();
          updateDropdownList();
        }}}
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
