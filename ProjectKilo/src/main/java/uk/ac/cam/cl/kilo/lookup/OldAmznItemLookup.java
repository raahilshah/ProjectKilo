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

public class OldAmznItemLookup {

    // Amazon Advertising API details.
    private static final String AWS_ACCESS_KEY_ID = "AKIAI4LLUAWZMGNUW5NA";
    private static final String AWS_SECRET_KEY = "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";
    private static final String ENDPOINT = "webservices.amazon.com";

    private static String ITEM_ID = "";
    private static String ID_TYPE = "";

    public static void main(String[] args) {

        if (args.length < 2) {
            System.err.println("Required arguments: IdType ItemId");
            return;
        }

        ID_TYPE = args[0];
        ITEM_ID = args[1];

        SignedRequestsHelper helper;
        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        String requestURL = null;
        String content = null;

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

        requestURL = helper.sign(params);
        System.out.println("Signed Request:\n" + requestURL);
        System.out.println();

        List<String> data = fetchContent(requestURL);

        for (String s : data) {
            System.out.println(s + "\n");
        }
    }

    // Parsing XML response. 
    private static List<String> fetchContent(String requestURL) {
        List<String> info = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(requestURL);

            Node description = doc.getElementsByTagName("Content").item(0);
            Node reviewURL = doc.getElementsByTagName("IFrameURL").item(0);
            Node title = doc.getElementsByTagName("Title").item(0);
            NodeList authors = doc.getElementsByTagName("Author");

            info = new LinkedList<String>();
            info.add(title.getTextContent());
            for (int i = 0; i < authors.getLength(); i++) {
                Node a = authors.item(i);
                info.add(a.getTextContent());
            }
            info.add(description.getTextContent());
            info.add(reviewURL.getTextContent());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return info;
    }

}