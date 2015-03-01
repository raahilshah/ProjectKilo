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

package cam.cl.kilo.parsing;

import cam.cl.kilo.NLP.ItemInfo;
import cam.cl.kilo.NLP.MashapeSummarizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Vector;


//the whole process of the parser: 1.find the review links in the gadget 2.Goto those links and extract
//the reviews from HTML 3. put them into a vector
//But since the whole process is API dependent,Amazon may have different pattern for word extraction in
//HTML. So far I only tested GoodRead.  

/**
 * Extracts reviews from HTML given the api review gadget url
 *
 *@author groupKilo
 *@author yg300
 */
public class Parser {

	//parse needs more than URL, it also needs pattern1 for extract links from URL, pattern2 to extract
	//reviews from the HTML of a given link
	
	/**
	 * General parse method, put the reviews extracted into the reviews field of a ItemInfo's object
	 *
	 *@param URL The review gadget url.
	 *@param pattern1 The pattern used for finding the links of reviews from HTML of the review gadget by
	 *Jsoup's Element's select method.
	 *@param pattern2 The pattern used for finding the review text from the review HTML.
	 *@param info The ItemInfo's object.
	 */

	public static void parse(String URL,String pattern1, String pattern2, ItemInfo info){
		
		Vector<String> vectorOfReviews = null;
		try {
			Document doc = Jsoup.connect(URL).get();
			Elements tagsWithLink = getLinks(doc,pattern1);
			int size = tagsWithLink.size();
			String[] linksArray = new String[size];
			for(int k = 0; k < size; k ++){
				linksArray[k] = stringExtractor(tagsWithLink.get(k).toString());
			}
			vectorOfReviews = reviewFromLinks(linksArray,pattern2);
			
		} catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());

			String message = "invalid URL, can't get reviews";
			Vector<String> vector = new Vector<String>();
			vector.add(message);
			info.setReviews(vector);
		}
		
		info.setReviews(vectorOfReviews);
	}
	
	//the arg pattern is API dependent so we might want to pass the type of api to parse()
	/**
	 * Gets links of individual reviews from the review gadget HTML.
	 *
	 *@param inputHTML Document object of the review gadget HTML.
	 *@param pattern The pattern used for finding the links of reviews from HTML of the review gadget by
	 *Jsoup's Element's select method.
	 *@return The links of reviews.
	 */
	public static Elements getLinks(Document inputHTML, String pattern){
	
		Elements links = inputHTML.select(pattern);
		
		return links;
		
	}
	
	//extract url from tag
	/**
	 * Extracts url from a string.
	 *
	 *@param input The given string.
	 *@return A string of url.
	 */
	public static String stringExtractor(String input){
		
		String output = "";
		
		int firstIndex = input.indexOf("\"http");
		int lastIndex = input.lastIndexOf("\"");
		output = input.substring(firstIndex +1, lastIndex); 
		
		return output;
	}
	
	/**
	 * Extracts text from HTML.
	 *
	 *@param tags The given HTML.
	 *@return A string without tags.
	 */
	//remove tags to get the review text
	public static String removeTags(Element tags){
		String newString = "";
		newString = tags.toString().replaceAll("<.*?>", "");
		newString = newString.replaceAll("\n", "");
		return newString;
	}
	
	/**
	 * General method for getting reviews from links.
	 *
	 *@param links url of individual reviews
	 *@param pattern The pattern used for finding the review text from the review HTML by Jsoup's Element's
	 * select method.
	 *@return A Vector of reviews.
	 */
	//general method for getting reviews into vector from an array of urls
	public static Vector<String> reviewFromLinks(String[] links, String pattern){
		
		int length = links.length;
		Vector<String> vector = new Vector<String>(length);
		LinkThread[] threads = new LinkThread[length];
		
		//put the links into Document for extractions
		for(int i = 0; i < length; i ++){
			
			threads[i] = new LinkThread(links[i], pattern);
			threads[i].start();
			
		}
		
		for(int i = 0; i < length; i ++){
			
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		for(int i = 0; i < length; i ++){
			
			vector.add(i, threads[i].review);
			
		}
		return vector;
		
	}
	/**
	 * Thread Class for allowing to get reviews in parellel.
	 */
	static class LinkThread extends Thread {
		
		public String review;
		public String link;
		public String pattern;
		
		public LinkThread(String link, String pattern) {
			this.link = link;
			this.pattern = pattern;
		}
		
		public void run() {
			
			Document page;
			try {
				page = Jsoup.connect(link).get();
				Elements elements = getLinks(page,pattern);
				review = removeTags(elements.get(0));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	public static void main(String[] args){
		long startTime = System.currentTimeMillis();
		
		String URL = "https://www.goodreads.com/api/reviews_widget_iframe?did=DEVELOPER_ID&amp;format=html&amp;isbn=0316160172&amp;links=660&amp;min_rating=&amp;review_back=fff&amp;stars=000&amp;text=000";
		String pattern1 = "link[itemprop=\"url\"][href]";
		String pattern2 = "div[class=\"reviewText mediumText description\"]";
		
		ItemInfo info = new ItemInfo();
		
		parse(URL,pattern1,pattern2,info);
		
		String key = "5xKQ69Ihz8msh84QD6YjRTBePwHRp1HIUJPjsnB22cVB9CdCZ9";
		int sentnum = 5;
		String text = info.getReviews().get(1);
		String newText = "";
		newText = MashapeSummarizer.summarize(text, sentnum, key);
		System.out.println(newText);
		
		//System.out.println(info.getReviews().get(0));

		
		
		long endTime = System.currentTimeMillis();
		long timeTaken = endTime - startTime;
		//System.out.println(timeTaken);

	}
}
