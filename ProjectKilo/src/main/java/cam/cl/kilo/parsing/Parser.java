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

import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cam.cl.kilo.nlp.ItemInfo;


//the whole process of the parser: 1.find the review links in the gadget 2.Goto those links and extract
//the reviews from HTML 3. put them into a vector
//But since the whole process is API dependent,Amazon may have different pattern for word extraction in
//HTML. So far I only tested GoodRead.  
public class Parser {

	//parse needs more than URL, it also needs pattern1 for extract links from URL, pattern2 to extract
	//reviews from the HTML of a given link
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
			String message = "invalid URL, can't get reviews";
			Vector<String> vector = new Vector<String>();
			vector.add(message);
			info.setReviews(vector);
		}
		
		info.setReviews(vectorOfReviews);
	}
	
	//the arg pattern is API dependent so we might want to pass the type of api to parse()
	public static Elements getLinks(Document inputHTML, String pattern){
	
		Elements links = inputHTML.select(pattern);
		
		return links;
		
	}
	
	//extract url from tag
	public static String stringExtractor(String input){
		
		String output = "";
		
		int firstIndex = input.indexOf("\"http");
		int lastIndex = input.lastIndexOf("\"");
		output = input.substring(firstIndex +1, lastIndex); 
		
		return output;
	}
	
	//remove tags to get the review text
	public static String removeTags(Element tags){
		String newString = "";
		newString = tags.toString().replaceAll("<.*?>", "");
		newString = newString.replaceAll("\n", "");
		return newString;
	}
	
	//general method for getting reviews into vector from an array of urls
	public static Vector<String> reviewFromLinks(String[] links,String pattern){
		
		int length = links.length;
		Vector<String> vector = new Vector<String>(length);
		
		try {

			//put the links into Document for extractions
			for(int i = 0; i < length; i ++){

				Document page = Jsoup.connect(links[i]).get();
				Elements elements = getLinks(page,pattern);
				String review = removeTags(elements.get(0));
				vector.add(i,review);
			}		
						
		} catch (IOException e) {
			e.printStackTrace();
		}
		return vector;
		
	}
	
	
	public static void main(String[] args){
		long startTime = System.currentTimeMillis();
		
		String URL = "https://www.goodreads.com/api/reviews_widget_iframe?did=DEVELOPER_ID&amp;format=html&amp;isbn=0316160172&amp;links=660&amp;min_rating=&amp;review_back=fff&amp;stars=000&amp;text=000";
		String pattern1 = "link[itemprop=\"url\"][href]";
		String pattern2 = "div[class=\"reviewText mediumText description\"]";
		
		ItemInfo info = new ItemInfo();
		
		parse(URL,pattern1,pattern2,info);
		
		long endTime = System.currentTimeMillis();
		long timeTaken = endTime - startTime;
		System.out.println(timeTaken);

	}
}