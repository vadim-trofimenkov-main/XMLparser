package xml.hometask;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MyDOMParser {
    private static ArrayList<Album> albums = new ArrayList<>();
    static final String TITLE_TAG = "title";
    static final String ARTIST_TAG = "artist";
    private static String title;
    private static String artist;


    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("cd_catalog.xml"));
        NodeList albumElements = document.getDocumentElement().getElementsByTagName("cd");
        for (int i = 0; i < albumElements.getLength(); i++) {
            NodeList nodeElements = albumElements.item(i).getChildNodes();
            for (int j = 0; j < nodeElements.getLength(); j++) {
                Node element = nodeElements.item(j);
                if (element.getNodeName().equals(TITLE_TAG)) {
                    title = element.getTextContent();
                } else if (element.getNodeName().equals(ARTIST_TAG)) {
                    artist = element.getTextContent();
                } else continue;
            }
            if (title != null && artist != null) {
                albums.add(new Album(title, artist));
            }
        }
        for (Album album : albums) {
            System.out.printf("Название альбома: %s, исполнитель: %s \n", album.getTitle(), album.getArtist());
        }
    }
}
