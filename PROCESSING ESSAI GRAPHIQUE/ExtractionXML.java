import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import processing.data.XML;

public class ExtractionXML {
    
    private String sketchFolderPath;

    public ExtractionXML(String sketchFolderPath) {
        this.sketchFolderPath = sketchFolderPath;
    }
  
  
    public void chargerFichierXML(XML xml) {
        XML[] elements = xml.getChildren("attributes/entry");
        HashMap<String, HashMap<String, Object>> listeElements = new HashMap<>();

        for (XML element : elements) {
            String id = extraireAttributString(element, "de.bmotionstudio.gef.editor.attribute.BAttributeID");
            HashMap<String, Object> attributs = extraireAttributs(element);
            listeElements.put(id, attributs);
        }

        String cheminSortie = "valeurs_bmotion.txt";
        enregistrerDictionnaire(cheminSortie, listeElements);
    }

    public HashMap<String, Object> extraireAttributs(XML element) {
        HashMap<String, Object> attributs = new HashMap<>();
        XML[] entries = element.getChildren("attributes/entry");
        for (XML entry : entries) {
            String cle = entry.getChild("string").getContent();
            XML valeurElement = entry.getChild(1);
            switch (cle) {
                case "de.bmotionstudio.gef.editor.attribute.BAttributeX":
                    attributs.put("posX", valeurElement.getInt("value"));
                    break;
                case "de.bmotionstudio.gef.editor.attribute.BAttributeY":
                    attributs.put("posY", valeurElement.getInt("value"));
                    break;
                case "de.bmotionstudio.gef.editor.attribute.BAttributeWidth":
                    attributs.put("width", valeurElement.getInt("value"));
                    break;
                case "de.bmotionstudio.gef.editor.attribute.BAttributeHeight":
                    attributs.put("height", valeurElement.getInt("value"));
                    break;
                case "de.bmotionstudio.gef.editor.attribute.BAttributeBackgroundColor":
                    XML rgb = valeurElement.getChild("value");
                    int r = rgb.getInt("red");
                    int g = rgb.getInt("green");
                    int b = rgb.getInt("blue");
                    String couleur = "rgb(" + r + "," + g + "," + b + ")";
                    attributs.put("backgroundColor", couleur);
                    break;
                default:
                    break;
            }
        }
        return attributs;
    }

      private String combinePaths(String fileName) {
          return sketchFolderPath + File.separator + fileName;
      }

      public void enregistrerDictionnaire(String chemin, HashMap<String, HashMap<String, Object>> dictionnaire) {
    try {
        String cheminFichier = combinePaths(chemin);
        File fichierSortie = new File(cheminFichier);
        FileWriter writer = new FileWriter(fichierSortie);

        if (fichierSortie.exists()) {
            System.out.println("Le fichier a été créé avec succès : " + fichierSortie.getAbsolutePath());
        } else {
            System.out.println("Erreur lors de la création du fichier.");
        }

        for (Map.Entry<String, HashMap<String, Object>> entry : dictionnaire.entrySet()) {
            writer.write(entry.getKey() + " : " + entry.getValue().toString() + "\n");
        }
        writer.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public static String extraireAttributString(XML element, String cle) {
        XML entry = element.getChild("attributes/entry[string='" + cle + "']");
        if (entry != null) {
            XML valeurElement = entry.getChild(1);
            return valeurElement.getString("value");
        }
        return null;
    }
}
