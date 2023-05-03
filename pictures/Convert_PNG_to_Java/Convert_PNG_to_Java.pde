PImage img;
int tailleRectangle = 10;

void setup() {
  size(800, 600);
  img = loadImage("Rendu_BMotion.PNG");
  img.resize(width, height);
  noLoop();
}

void draw() {
  background(255);
  for (int y = 0; y < img.height; y += tailleRectangle) {
    for (int x = 0; x < img.width; x += tailleRectangle) {
      color c = img.get(x, y);
      float luminosite = brightness(c);
      float hauteurRectangle = map(luminosite, 0, 255, tailleRectangle, 0);
      fill(c);
      rect(x, y, tailleRectangle, hauteurRectangle);
    }
  }
}
