package test.amazon;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ItemLookupTest {

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
        
        String requestUrl = null;
        String content = null;

        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", "AWSECommerceService");
        params.put("Version", "2013-08-01");
        params.put("Operation", "ItemLookup");
        params.put("IdType", ID_TYPE);
        params.put("ItemId", ITEM_ID);
        params.put("ResponseGroup", "Small,EditorialReview,Reviews");
        if (!ID_TYPE.equals("ASIN"))
            params.put("SearchIndex", "All");
        params.put("AssociateTag", "drupal0a-20");

        requestUrl = helper.sign(params);
        System.out.println("Signed Request:\n" + requestUrl);
        System.out.println();

        content = fetchContent(requestUrl);
        System.out.println("Product Description: \n" + content);
        System.out.println();
    }

    // Parse result html 
    private static String fetchContent(String requestUrl) {
        String title = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(requestUrl);

            Node description = doc.getElementsByTagName("Content").item(0);
            Node reviewURL = doc.getElementsByTagName("IFrameURL").item(0);
            Node title = doc.getElementsByTagName("Title").item(0);
            NodeList authors = doc.getElementsByTagName("Author");

            Node titleNode = doc.getElementsByTagName("Content").item(0);

            title = titleNode.getTextContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return title;
    }

}