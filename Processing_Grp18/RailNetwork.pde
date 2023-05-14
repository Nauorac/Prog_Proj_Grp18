// Vous pouvez définir des variables globales pour les gares et les voies ici, si nécessaire

// Fonction pour dessiner les gares
void drawStations() {
    //Coordonnées des gares
    // GARES PRINCIPALES - Bruxelles (100,100) ROUGE || Namur (300,300) VERT || Liege (500,100) BLEU
    // GARES SECONDAIRES - Ottignies(200,200) JAUNE ||  Leuven (300,100)  MAGENTA || Huy (400,200) CYAN
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
}
// Fonction pour dessiner les voies
void drawTracks() {
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
}

// Autres fonctions et code en rapport avec les gares et les voies
