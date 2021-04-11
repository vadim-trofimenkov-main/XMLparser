package xml.hometask;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MySAXParser {
    private static ArrayList<Album> albums = new ArrayList<>();

    public static void main(String[] args) throws ParserConfigurationException, SAXException,
            IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler handler = new XMLHandler();
        File file = new File("cd_catalog.xml");
        parser.parse(file, handler);
        for (Album album : albums) {
            System.out.printf("Название альбома: %s, исполнитель: %s \n", album.getTitle(), album.getArtist());
        }
    }

    private static class XMLHandler extends DefaultHandler {
        static final String TITLE_TAG = "title";
        static final String ARTIST_TAG = "artist";
        private String currentElement;
        private String title;
        private String artist;


        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currentElement = qName;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String text = new String(ch, start, length);
            text = text.replace("\n", "").trim();
            if (!text.isEmpty()) {
                if (currentElement.equals(TITLE_TAG))
                    title = text;
                if (currentElement.equals(ARTIST_TAG))
                    artist = text;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ((title != null && !title.isEmpty()) && (artist != null && !artist.isEmpty())) {
                albums.add(new Album(title, artist));
                title = null;
                artist = null;
            }
        }
    }
}
