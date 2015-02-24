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
		
		String newString = new HtmlToPlainText().getPlainText(tags);
		newString = newString.replaceAll("<.*?>", "");
		newString = newString.replaceAll("\n", "");
		return newString;
	}
	
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
		
		System.out.println(info.getReviews().get(0));
		
		long endTime = System.currentTimeMillis();
		long timeTaken = endTime - startTime;
		System.out.println(timeTaken);

	}
}
