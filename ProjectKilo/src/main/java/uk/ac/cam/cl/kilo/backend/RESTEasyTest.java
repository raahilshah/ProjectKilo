package uk.ac.cam.cl.kilo.backend;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import uk.ac.cam.cl.kilo.lookup.AmznItemLookup;

@Path("/test")
public class RESTEasyTest {
	
	@GET
	public Response simpleResponse(
			@QueryParam("barcodeNo") String barcodeNo,
			@DefaultValue("ISBN") @QueryParam("barcodeType") String barcodeType) {
		
		String responseString;
		
		if (barcodeNo != null) {	
			AmznItemLookup amzn = new AmznItemLookup(barcodeType, barcodeNo);
			
			String title = amzn.getTitle();
			String desc = amzn.getDescription();
			
			responseString =
					"Barcode number: " + barcodeNo   + "<br>" +
                    "Barcode type: "   + barcodeType + "<br>" + 
					"Product title: "  + title		 + "<br>" +
                    "Product description" + desc	 + "<br>";			
		} else {
			responseString = "Missing barcode number.";
		}
		
		return Response
				.status(200)
				.entity(responseString).build();
	}
}
