package cam.cl.kilo.NLP;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MashapeSummarizer {

	public static String summarize(String text, int sentnum, String key){
		
		String output = "";
		
		try {
			HttpResponse<JsonNode> response = Unirest.post("https://textanalysis-text-summarization.p.mashape.com/text-summarizer-text")
					.header("X-Mashape-Key", key)
					.header("Content-Type", "application/x-www-form-urlencoded")
					.header("Accept", "application/json")
					.field("sentnum", sentnum)
					.field("text", text)
					.asJson();
			
			output = response.getBody().toString();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
}
