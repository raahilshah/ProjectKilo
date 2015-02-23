package cam.cl.kilo.lookup;

import cam.cl.kilo.RESTEasyBarcode;
import cam.cl.kilo.nlp.ItemInfo;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class OMDBLookup extends Lookup {

	private String title = "";
	private String year = "";
	private String genre = "";
	private String director = "";
	private String actors = "";
	private String plot = "";

	public OMDBLookup(String barcodeNo, String barcodeType, ItemInfo info) {

		super(barcodeNo, barcodeType, info);

	}

	// Parsing XML response.
	public void fillContent(String requestURL) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(requestURL);
			NodeList nodeList = doc.getElementsByTagName("movie");
	
	        for(int x=0,size= nodeList.getLength(); x<size; x++) {
	        	title = nodeList.item(x).getAttributes().getNamedItem("title").getNodeValue();
	            year = nodeList.item(x).getAttributes().getNamedItem("year").getNodeValue();
	            genre = nodeList.item(x).getAttributes().getNamedItem("genre").getNodeValue();
	            director = nodeList.item(x).getAttributes().getNamedItem("director").getNodeValue();
	            actors = nodeList.item(x).getAttributes().getNamedItem("actors").getNodeValue();
	            plot = nodeList.item(x).getAttributes().getNamedItem("plot").getNodeValue();
	        }
			info.setTitle(title);
			info.addAuthor(director);
			info.addAuthor(actors);
			info.addDescription(plot);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		title = "Titanic";
		String uri = String.format("http://www.omdbapi.com/?t=%s&y=&plot=full&r=xml", title);
		fillContent(uri);
	}
	
	
	/**
	public static void main(String[] args) {
		String titletest = "Titanic";
		String uri = String.format("http://www.omdbapi.com/?t=%s&y=&plot=full&r=xml", titletest);
		System.out.println(uri);
		try{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(uri);
		
		NodeList nodeList = doc.getElementsByTagName("movie");
		System.out.println("Test length: " + nodeList.getLength());
        for(int x=0,size= nodeList.getLength(); x<size; x++) {
            System.out.println(nodeList.item(x).getAttributes().getNamedItem("title").getNodeValue());
            System.out.println(nodeList.item(x).getAttributes().getNamedItem("year").getNodeValue());
            System.out.println(nodeList.item(x).getAttributes().getNamedItem("genre").getNodeValue());
            System.out.println(nodeList.item(x).getAttributes().getNamedItem("director").getNodeValue());
            System.out.println(nodeList.item(x).getAttributes().getNamedItem("actors").getNodeValue());
            System.out.println(nodeList.item(x).getAttributes().getNamedItem("plot").getNodeValue());
            
         }
		} catch (Exception e){}
    }
     **/
	
	
	
}
