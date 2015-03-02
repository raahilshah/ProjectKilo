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

package cam.cl.kilo.NLP;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.List;

public class MashapeSummarizer extends Summarizer {
    private String[] texts;
    private int sentenceNumber;
    private String summary;


    private static String apiKey = "5xKQ69Ihz8msh84QD6YjRTBePwHRp1HIUJPjsnB22cVB9CdCZ9";

    public MashapeSummarizer(List<String> texts, int sentenceNumber) throws IOException {
        this.texts = texts.toArray(new String[texts.size()]);
        this.sentenceNumber = sentenceNumber;
        summary = summarize("", sentenceNumber, apiKey);

    }

	public String summarize(String text, int sentnum, String key){
		
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

    public String getSummary() {
        return summary;
    }

    public double getSummLength() {
        return summary.length();
    }

    public boolean isEmpty() {
        return (getSummLength() == 0);
    }

    public String[] getTexts() {
        return texts;
    }


}
