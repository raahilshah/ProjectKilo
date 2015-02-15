package cam.cl.kilo.lookup;

import cam.cl.kilo.nlp.ItemInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
import java.util.Map;

public class AmznItemLookup extends Lookup {

    // Amazon Advertising API details.
    private static final String AWS_ACCESS_KEY_ID = "AKIAI4LLUAWZMGNUW5NA";
    private static final String AWS_SECRET_KEY = "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";
    private static final String ENDPOINT = "webservices.amazon.com";
    
    public AmznItemLookup(String barcodeNo, String barcodeType, ItemInfo info) {
    	
    	super(barcodeNo, barcodeType, info);

        
    }

    // Parsing XML response. 
    public void fillContent(String requestURL) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(requestURL);

            Node descriptionNode = doc.getElementsByTagName("Content").item(0);
            Node reviewURLNode = doc.getElementsByTagName("IFrameURL").item(0);
            Node titleNode = doc.getElementsByTagName("Title").item(0);
            Node itemAttrNode = doc.getElementsByTagName("ItemAttributes").item(0);
            
            for (Node child = itemAttrNode.getFirstChild(); child != null; child = child.getNextSibling())
                if (child.getNodeName().equalsIgnoreCase("Author"))
                    info.addAuthor(child.getTextContent());
            
            info.setTitle(titleNode.getTextContent());
            info.addDescription(descriptionNode.getTextContent());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public void run() {
		
		SignedRequestsHelper helper;
        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        String requestURL = null;

        // Building API request parameters.
        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", "AWSECommerceService");
        params.put("Version", "2013-08-01");
        params.put("Operation", "ItemLookup");
        params.put("IdType", barcodeType);
        params.put("ItemId", barcodeNo);
        params.put("ResponseGroup", "Small,EditorialReview,Reviews");
        if (!barcodeNo.equals("ASIN"))
            params.put("SearchIndex", "All");
        // TODO: Get our own Associate Tag.
        params.put("AssociateTag", "drupal0a-20"); 

        // Get signed API request.
        requestURL = helper.sign(params);
        
        System.out.println("Signed Request:\n" + requestURL);

        fillContent(requestURL);
		
	}

}