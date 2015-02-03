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

@Path("/test")
public class RESTEasyTest {
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response simpleResponse(
			@QueryParam("barcodeNo") String barcodeNo,
			@DefaultValue("ISBN") @QueryParam("barcodeType") String barcodeType) {
		
		String responseString;
		
		if (barcodeNo != null) {	
			AmznItemLookup amzn = new AmznItemLookup(barcodeType, barcodeNo);
			
			String title = amzn.getTitle();
			String desc = amzn.getDescription();
			List<String> authors = amzn.getAuthors();
			
			responseString =
					"Barcode number: " + barcodeNo   + "<br>" +
                    "Barcode type: "   + barcodeType + "<br>" + 
					"Product title: "  + title		 + "<br>";
			responseString += "Author(s): ";
			for (String a : authors) 
				responseString += a + ", ";
			responseString += "<br>Product description: " + desc	 + "<br>";			
		} else {
			responseString = "Missing barcode number.";
		}
		
		return Response.ok(responseString).build();
	}
}
