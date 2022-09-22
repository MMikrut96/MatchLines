package pl.edu.wat.kulki.pz.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pl.edu.wat.kulki.pz.exceptions.NoGameBoardDataException;
import pl.edu.wat.kulki.pz.exceptions.NoPropertyInFileException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class ResourceLoader {

    private static final ClassLoader classLoader = ResourceLoader.class.getClassLoader();
    private static final String pathToConfig = "config/config.properties";
    private static final String xmlFile = "config.xmlFile";
    private static final String board = "gameBoard";
    private static Properties propertiesInstance;

    public static Parent getParentForFXML(ResourceBundle resourceBundle, String pathToFXML) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle(resourceBundle.getBaseBundleName(), Locale.getDefault()));
        return loader.load(classLoader.getClass().getResource(pathToFXML).openStream());
    }

    public static Properties getNewPropertiesInstance(){
        Properties properties = new Properties();
        try {
            properties.load(classLoader.getResourceAsStream(pathToConfig));
        } catch (IOException e) {
            e.printStackTrace();
        }
        propertiesInstance = properties;
        return propertiesInstance;
    }

    public static String property(String key) {
        String property = propertiesInstance.getProperty(key);
        if(property==null) throw new NoPropertyInFileException(key);
        return property;
    }

    public static String localeProperty(String key,String locale){
        Properties properties = new Properties();
        try {
            properties.load(classLoader.getResourceAsStream(propertiesInstance.getProperty(locale)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }

    public static int getIntFromXML(String key) {
        int value = 0;
        InputStream file = classLoader.getResourceAsStream(propertiesInstance.getProperty(xmlFile));
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName(board);
            Node nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String attribute = eElement.getElementsByTagName(key).item(0).getTextContent();
                value = Integer.parseInt(attribute);
            }
        } catch (SAXException | IOException | ParserConfigurationException e){
            e.printStackTrace();
        }
        if(value == 0) throw new NoGameBoardDataException();
        return value;
    }

    public static double getDoubleFromXML(String key){
        double value = 0;
        InputStream file = classLoader.getResourceAsStream(propertiesInstance.getProperty(xmlFile));
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName(board);
            Node nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String attribute = eElement.getElementsByTagName(key).item(0).getTextContent();
                value = Double.parseDouble(attribute);
            }
        } catch (SAXException | IOException | ParserConfigurationException e){
            e.printStackTrace();
        }
        if(value == 0) throw new NoGameBoardDataException();
        return value;
    }

}
