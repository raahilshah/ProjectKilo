package uk.ac.cam.cl.kilo.lookup;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import uk.ac.cam.cl.kilo.nlp.ItemInfo;

public class GoodReadsLookup extends Lookup {

    private static final String GR_KEY = "JFp6OfWw4CyC62C9EAXJdw";
    
    public GoodReadsLookup(String barcodeNo, String barcodeType, ItemInfo info){
    	
    	super(barcodeNo, barcodeType, info);
        
    }
    
    // Parsing XML response. 
    public void fillContent(String requestURL) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(requestURL);

            Node descriptionNode = doc.getElementsByTagName("description").item(0);
            System.out.println(descriptionNode.getTextContent());
            Node reviewURLNode = doc.getElementsByTagName("IFrameURL").item(0);
            Node titleNode = doc.getElementsByTagName("title").item(0);
            Node itemAttrNode = doc.getElementsByTagName("author").item(0);

            for (Node child = itemAttrNode.getFirstChild(); child != null; child = child.getNextSibling())
                if (child.getNodeName().equalsIgnoreCase("name"))
                    info.addAuthor(child.getTextContent());

            info.setTitle(titleNode.getTextContent());
            info.addDescription(descriptionNode.getTextContent());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	@Override
	public void run() {
		
		 String uri = String.format("https://www.goodreads.com/book/isbn?format=%s&key=%s&isbn=%s", "xml", GR_KEY, barcodeNo);
	     fillContent(uri);
	     
	}
    
}
