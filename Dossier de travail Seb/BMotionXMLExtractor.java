import processing.data.XML;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BMotionXMLExtractor {

    public static void main(String[] args) {
        XML visualization = XML.load("bmotion.xml");
        XML[] children = visualization.getChildren("children/*");

        List<HashMap<String, Object>> graphicElements = new ArrayList<HashMap<String, Object>>();

        for (XML child : children) {
            HashMap<String, Object> elementInfo = extractGraphicElementInfo(child);
            graphicElements.add(elementInfo);
        }

        saveGraphicElementsToFile(graphicElements, "output.txt");
    }

    public static HashMap<String, Object> extractGraphicElementInfo(XML element) {
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        XML[] entries = element.getChildren("attributes/entry");

        for (XML entry : entries) {
            String key = entry.getString("string");
            switch (key) {
                case "de.bmotionstudio.gef.editor.attribute.BAttributeID":
                    String id = entry.getChild("de.bmotionstudio.gef.editor.attribute.BAttributeID/value").getContent();
                    attributes.put("ID", id);
                    break;
                case "de.bmotionstudio.gef.editor.attribute.BAttributeX":
                    int x = entry.getChild("de.bmotionstudio.gef.editor.attribute.BAttributeX/value").getIntContent();
                    attributes.put("posX", x);
                    break;
                // ... ajoutez ici d'autres cas pour les autres attributs
            }
        }
        return attributes;
    }

    public static void saveGraphicElementsToFile(List<HashMap<String, Object>> graphicElements, String filename) {
        File file = new File(filename);

        try (FileWriter writer = new FileWriter(file)) {
            for (HashMap<String, Object> element : graphicElements) {
                writer.write(element.toString() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }
}
