package uk.ac.cam.cl.kilo.backend;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/test")
public class RESTEasyTest {
	
	@GET
	public Response simpleResponse(
			@QueryParam("barcodeNo") String barcodeNo,
			@DefaultValue("It works") @QueryParam("barcodeType") String barcodeType) {
		
		String responseString;
		
		if (barcodeNo != null) {	
			responseString =
					"Barcode number: " + barcodeNo   + "<br>" +
					"Barcode type: "   + barcodeType;			
		} else {
			responseString = "Missing barcode number.";
		}
		
		return Response
				.status(200)
				.entity(responseString).build();
	}
}
