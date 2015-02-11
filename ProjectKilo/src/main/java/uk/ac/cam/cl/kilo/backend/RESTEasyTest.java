package uk.ac.cam.cl.kilo.backend;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;

import uk.ac.cam.cl.kilo.lookup.AmznItemLookup;
import uk.ac.cam.cl.kilo.nlp.ItemInfo;
import uk.ac.cam.cl.kilo.nlp.Summariser;

@Path("/test")
public class RESTEasyTest {
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response simpleResponse(
			@QueryParam("barcodeNo") String barcodeNo,
			@DefaultValue("ISBN") @QueryParam("barcodeType") String barcodeType) {
		
		String responseString;
		
		if (barcodeNo != null) {	
			/*
			 * TODO: change constructor to pass an instance of ItemInfo
			 * by reference and populate that object.
			 */
			
			ItemInfo itemInfo = new ItemInfo();
			
			AmznItemLookup amzn = new AmznItemLookup(barcodeType, barcodeNo);
			if (amzn.getDescription() != null) {
				itemInfo.addDescription(amzn.getDescription());
				for (String a : amzn.getAuthors()) {
					itemInfo.addAuthor(a);
				}
				itemInfo.setTitle(amzn.getTitle());
			} else {
				itemInfo.addDescription("This is a description. It has two sentences!");
			}
			
			Summariser.summarise(itemInfo);
			//String title = amzn.getTitle();
			//List<String> authors = amzn.getAuthors();
			
           /* responseString =
                    "Barcode number: " + barcodeNo   + "<br>" + 
                    "Barcode type: "   + barcodeType + "<br>" + 
                    "Product title: "  + title       + "<br>";
            responseString += "Author(s): ";
            for (String a : authors) 
                responseString += a + ", ";
            responseString += "<br>Product description: " + summarised + "<br>";      */
			
			try {
				responseString = toString(itemInfo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				responseString = e.getMessage();
			}
			
        } else {
            responseString = "Missing barcode number.";
        }
		
		System.out.println(responseString);
        
        return Response.ok(responseString).build();
    }
	
	private static String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        for (byte b : baos.toByteArray()) {
        	System.out.println(b);
        }
        return Base64.encodeBase64String(baos.toByteArray());
    }
	
	public static void main(String[] args) {
		
		RESTEasyTest test = new RESTEasyTest();
		test.simpleResponse("9781907773242","ISBN");
		
	}
}
