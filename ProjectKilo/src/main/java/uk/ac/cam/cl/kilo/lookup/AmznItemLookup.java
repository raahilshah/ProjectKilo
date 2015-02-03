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

public class AmznItemLookup {

	// Amazon Advertising API details.
    private static final String AWS_ACCESS_KEY_ID = "AKIAI4LLUAWZMGNUW5NA";
    private static final String AWS_SECRET_KEY = "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";
    private static final String ENDPOINT = "webservices.amazon.com";

    // Item specific fields.
    private String ITEM_ID = "";
    private String ID_TYPE = "";

    // Information fields.
    private String title, description;
    
    public AmznItemLookup(String idType, String itemId) {
    	
        ID_TYPE = idType;
        ITEM_ID = itemId;

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
        params.put("IdType", ID_TYPE);
        params.put("ItemId", ITEM_ID);
        params.put("ResponseGroup", "Small,EditorialReview,Reviews");
        if (!ID_TYPE.equals("ASIN"))
            params.put("SearchIndex", "All");
        // TODO: Get our own Associate Tag.
        params.put("AssociateTag", "drupal0a-20"); 

        // Get signed API request.
        requestURL = helper.sign(params);
        
        System.out.println("Signed Request:\n" + requestURL);
        System.out.println();

        fillContent(requestURL);
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
            NodeList authorsNode = doc.getElementsByTagName("Author");

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

}