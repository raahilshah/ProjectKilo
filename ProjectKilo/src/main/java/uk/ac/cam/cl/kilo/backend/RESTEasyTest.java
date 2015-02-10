package uk.ac.cam.cl.kilo.backend;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;  

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
			} else {
				itemInfo.addDescription("This is a description. It has two sentences!");
			}
			
			String summarised = Summariser.summarise(itemInfo);
			String title = amzn.getTitle();
			List<String> authors = amzn.getAuthors();
			
            responseString =
                    "Barcode number: " + barcodeNo   + "<br>" + 
                    "Barcode type: "   + barcodeType + "<br>" + 
                    "Product title: "  + title       + "<br>";
            responseString += "Author(s): ";
            for (String a : authors) 
                responseString += a + ", ";
            responseString += "<br>Product description: " + summarised + "<br>";          
        } else {
            responseString = "Missing barcode number.";
        }
        
        return Response.ok(responseString).build();
    }
	
	public static void main(String[] args) {
		
		RESTEasyTest test = new RESTEasyTest();
		test.simpleResponse("052156543X","ISBN");
		
	}
}
