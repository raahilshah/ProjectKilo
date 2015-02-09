package uk.ac.cam.cl.kilo.lookup;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GoodReadsLookUp {

    private static final String GR_KEY = "JFp6OfWw4CyC62C9EAXJdw";
   
    // Item specific fields.
    private String ID_TYPE = "";
    private String bookIsbn = "";

    // Information fields.
    private String title, description;
    private List<String> authors;
    
    public GoodReadsLookUp(String idType, String itemId){
        
        ID_TYPE = idType;
        if (ID_TYPE.equals("isbn")){
            bookIsbn = itemId;
        }
        title = "";
        description = "";
        authors = new LinkedList<String>();
        
        String uri = String.format("https://www.goodreads.com/book/isbn?format=%s&key=%s&isbn=%s", "xml", GR_KEY, bookIsbn);
    
        fillContent(uri);
    }
    
    // Parsing XML response. 
    public void fillContent(String requestURL) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(requestURL);

            Node descriptionNode = doc.getElementsByTagName("description").item(0);
            Node reviewURLNode = doc.getElementsByTagName("IFrameURL").item(0);
            Node titleNode = doc.getElementsByTagName("title").item(0);
            Node itemAttrNode = doc.getElementsByTagName("author").item(0);

            for (Node child = itemAttrNode.getFirstChild(); child != null; child = child.getNextSibling())
                if (child.getNodeName().equalsIgnoreCase("name"))
                    authors.add(child.getTextContent());

            title = titleNode.getTextContent();
            description = descriptionNode.getTextContent();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public List<String> getAuthors() {
        return authors;
    }
    
}
