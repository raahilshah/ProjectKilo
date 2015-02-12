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
import uk.ac.cam.cl.kilo.lookup.GoodReadsLookup;
import uk.ac.cam.cl.kilo.nlp.ItemInfo;
import uk.ac.cam.cl.kilo.nlp.Summariser;

@Path("/barcode")
public class RESTEasyBarcode {
	
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
			
			ItemInfo info = new ItemInfo();
			
			Thread tAmzn = new Thread(new AmznItemLookup(barcodeNo, barcodeType, info));
			Thread tGR = new Thread(new GoodReadsLookup(barcodeNo, barcodeType, info));
			
			tAmzn.start();
			tGR.start();
			
			while(tAmzn.isAlive() || tGR.isAlive());
			
			System.out.println(info.getTitle());
			for (String d : info.getDescriptions())
				System.out.println(d);
			for (String a : info.getAuthors())
				System.out.println(a);
			
			Summariser.summarise(info);
			
			try {
				responseString = toString(info);
			} catch (IOException e) {
				e.printStackTrace();
				responseString = e.getMessage();
			}
			
        } else {
            responseString = "Missing barcode number.";
        }
		
		//System.out.println(responseString);
        
        return Response.ok(responseString).build();
    }
	
	private static String toString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.encodeBase64String(baos.toByteArray());
    }
	
	public static void main(String[] args) {
		
		RESTEasyBarcode test = new RESTEasyBarcode();
		test.simpleResponse("1612184081","ISBN");
		
	}
}
