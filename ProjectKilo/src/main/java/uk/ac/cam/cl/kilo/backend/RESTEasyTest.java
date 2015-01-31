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
			@DefaultValue("UPC") @QueryParam("barcodeType") String barcodeType) {
		
		String responseString;
		
		if (barcodeNo != null) {	
			responseString =
					"Barcode number: " + barcodeNo   + "\n" +
					"Barcode type: "   + barcodeType + "\n";			
		} else {
			responseString = "Missing barcode number.";
		}
		
		return Response
				.status(200)
				.entity(responseString).build();
	}
}