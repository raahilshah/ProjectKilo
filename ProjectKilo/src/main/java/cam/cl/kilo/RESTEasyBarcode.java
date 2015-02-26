/*
 * Copyright (C) 2015 Group Kilo (Cambridge Computer Lab)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cam.cl.kilo;

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

import cam.cl.kilo.lookup.AmznItemLookup;
import cam.cl.kilo.lookup.GoodReadsLookup;
import cam.cl.kilo.lookup.OMDBLookup;
import cam.cl.kilo.nlp.ItemInfo;
import cam.cl.kilo.nlp.Summarizer;
import cam.cl.kilo.nlp.Summary;

@Path("/barcode")
public class RESTEasyBarcode {
	
	private static String toString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.encodeBase64String(baos.toByteArray());
    }

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response simpleResponse(
			@QueryParam("barcodeNo") String barcodeNo,
			@DefaultValue("ISBN") @QueryParam("barcodeType") String barcodeType) {

		String responseString;

		if (barcodeNo != null) {

			ItemInfo info = new ItemInfo();

			Thread tAmzn = new Thread(new AmznItemLookup(barcodeNo, barcodeType, info));
			Thread tGR = new Thread(new GoodReadsLookup(barcodeNo, barcodeType, info));

			tAmzn.start();
			
			if (barcodeType == "ISBN") tGR.start();

			while(tAmzn.isAlive() || tGR.isAlive());
			
			if (barcodeType != "ISBN") {
				Thread tOMDB = new Thread(new OMDBLookup(barcodeNo, barcodeType, info));
				while(tOMDB.isAlive());
			}

//			System.out.println(info.getTitle());
//			for (String d : info.getDescriptions())
//				//System.out.println(d);
//			for (String a : info.getAuthors())
//				System.out.println(a);


            // Summarize text in ItemInfo and create a Summary object
            // If it fails, just return the full text of the first description
            Summarizer summarizer;
            String summarisedText = null;
            Summary summary;
            try {
                summarizer = new Summarizer(info);

                if (summarizer.getSummLength() != 0) {
                    summarisedText = summarizer.getSummResults();
                    System.out.println("Summarisation complete");
                } else {
                    summarisedText = summarizer.getText();
                    System.out.println("Empty summary");
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
                //TODO: It would be better if it returned text from the Summary object...
                summarisedText = info.getDescriptions().firstElement();
            } finally {
                summary = new Summary(info, summarisedText);
                System.out.println(summary.getText());
            }

			try {
				responseString = toString(summary);
			} catch (IOException e) {
				e.printStackTrace();
				responseString = e.getMessage();
			}

        } else {
            responseString = "Missing barcode number";
        }

		System.out.println(responseString);

        return Response.ok(responseString).build();
    }

    public static void main(String[] args) {
        RESTEasyBarcode test = new RESTEasyBarcode();
        test.simpleResponse("052156543X","ISBN");

    }
}
