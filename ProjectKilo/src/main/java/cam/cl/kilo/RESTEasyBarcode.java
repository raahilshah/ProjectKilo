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

import cam.cl.kilo.lookup.AmznItemLookup;
import cam.cl.kilo.lookup.GoodReadsLookup;
import cam.cl.kilo.lookup.OMDBLookup;
import cam.cl.kilo.nlp.ItemInfo;
import cam.cl.kilo.nlp.Summarizer;
import cam.cl.kilo.nlp.Summary;
import org.apache.commons.codec.binary.Base64;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

import java.util.List;

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


            // Summarize text in ItemInfo and create a Summary object
            Summary summary = prepareSummary(info);

            // Serialize Summary object
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

    /**
     *
     * @param info The itemInfo object being populated
     * @return Summary
     */
    public Summary prepareSummary(ItemInfo info) {
        Summarizer descriptionSummarizer, reviewSummarizer;
        String summarizedDescriptions, summarizedReviews;

        // Handle both summarizers in same try/catch, if there is an IO error from Mead, it is likely to affect both
        try {
            descriptionSummarizer = new Summarizer(
                    (info.getDescriptions().toArray(new String [0])), "P10", Summarizer.LOCALHOST);
            reviewSummarizer = new Summarizer(
                    (info.getReviews().toArray(new String[0])), "P5", Summarizer.LOCALHOST);

            if (descriptionSummarizer.getSummLength() != 0) {
                summarizedDescriptions = descriptionSummarizer.getSummResults();
                System.out.println("Description summarization successful");
            } else {
                try {
                    summarizedDescriptions = info.getDescriptions().firstElement();
                } catch (NoSuchElementException nsee) {
                    summarizedDescriptions = "No description available for this item.";
                }
                System.out.println("Empty description summary");
            }

            if (reviewSummarizer.getSummLength() != 0) {
                summarizedReviews = reviewSummarizer.getSummResults();
                System.out.println("Review summarization successful");
            } else {
                try {
                    summarizedReviews = info.getReviews().firstElement();
                } catch (NoSuchElementException nsee) {
                    summarizedReviews = "No reviews available for this item.";
                }
                System.out.println("Empty reviews summary");
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
            summarizedDescriptions = info.getDescriptions().firstElement();
            summarizedReviews = info.getReviews().firstElement();
        }

        System.out.println(summarizedDescriptions);
        System.out.println(summarizedReviews);
//        ppList(info.getDescriptions());
//        ppList(info.getReviews());

        return new Summary(info, summarizedDescriptions, summarizedReviews);
    }

    public static void ppList(List<String> l) {
        Iterator itr = l.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    public static void main(String[] args) {
        RESTEasyBarcode test = new RESTEasyBarcode();
        test.simpleResponse("0330511742","ISBN");
    }
}
