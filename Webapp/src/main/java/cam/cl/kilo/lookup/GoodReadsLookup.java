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

package cam.cl.kilo.lookup;

import cam.cl.kilo.NLP.ItemInfo;
import cam.cl.kilo.parsing.Parser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class GoodReadsLookup extends Lookup {

	private static final String GR_KEY = "JFp6OfWw4CyC62C9EAXJdw";

	/**
	 * GoodReads Constructor
	 *
	 * @param  barcodeNo    The barcode number of the book to look up
	 * @param  barcodeType  The type of the barcode (ISBN in this case)
	 * @param  info         The ItemInfo object to store the relevant information
	 */
	public GoodReadsLookup(String barcodeNo, String barcodeType, ItemInfo info) {

		super(barcodeNo, barcodeType, info);

	}

	/**
	 * Fills in the ItemInfo object with the title, author(s)
	 * description and reviews, from a XML File containing
	 * book information by parsing the relevant information from it
	 *
	 * @param  requestURL  the URL of the XML File that needs to be parsed
	 */
	public void fillContent(String requestURL) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(requestURL);

			Node descriptionNode = doc.getElementsByTagName("description")
					.item(0);
			Node reviewURLNode = doc.getElementsByTagName("IFrameURL").item(0);
			Node titleNode = doc.getElementsByTagName("title").item(0);
			Node itemAttrNode = doc.getElementsByTagName("author").item(0);
			Node reviewsBlock = doc.getElementsByTagName("reviews_widget").item(0);

			for (Node child = itemAttrNode.getFirstChild(); child != null; child = child
					.getNextSibling())
				if (child.getNodeName().equalsIgnoreCase("name"))
					info.addAuthor(child.getTextContent());

			info.setTitle(titleNode.getTextContent());
			info.addDescription(descriptionNode.getTextContent());
            System.out.println("GOODREADS DESCRIPTION FETCHED:");
            System.out.println(info.getDescriptions().lastElement());

			Parser.parse(extract(reviewsBlock.getTextContent()), "link[itemprop=\"url\"][href]", "div[class=\"reviewText mediumText description\"]", info);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the link of the reviews widget from a large
	 * block of text containing the link. This is done because
	 * the XML parser cannot distinguish the "reviews_widget" element
	 *
	 * @param  string a string containing the link to the Reviews_widget
	 * @return  the link of the reviews widget
	 */
	public String extract(String string) {

		String output = "no match";
		String[] stringArray = string.split("\"");
		for (String element : stringArray) {
			if (element.startsWith("https://www.goodreads.com/api/reviews"))
				output = element;
		}
		return output;
	}

	
	@Override
	public void run() {

		String uri = String.format(
				"https://www.goodreads.com/book/isbn?format=%s&key=%s&isbn=%s",
				"xml", GR_KEY, barcodeNo);
		fillContent(uri);

	}

}
