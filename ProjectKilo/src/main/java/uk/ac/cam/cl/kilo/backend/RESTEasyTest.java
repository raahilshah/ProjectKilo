package uk.ac.cam.cl.kilo.backend;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/test")
public class RESTEasyTest {
	
	@GET
	public Response simpleResponse(@QueryParam("barcodeNo") String barcodeNo) {
		
		String responseString;
		
		if (barcodeNo != null) {
			
			responseString = "Your barcode was: " + barcodeNo;
			
		} else {
		
			responseString = "Please supply a barcode number";
		
		}
			
		return Response.status(200).entity(responseString).build();
	}
}