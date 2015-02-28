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

package cam.cl.kilo.nlp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contains the text of summarised product descriptions and reviews.
 * @author groupKilo
 * @author dc561
 */
public class Summary implements Serializable {

    private static final long serialVersionUID = 2900729725728472406L;
    private String title;
    private String authors;
    private ArrayList<String> text;

    public static final String BEGIN_REVIEWS = "### BEGIN REVIEWS ###";

    /**
     *
     * @param info An ItemInfo object
     * @param descriptions Summarised text of product descriptions
     * @param reviews Summarised text of product reviews
     */
    public Summary(ItemInfo info, String descriptions, String reviews) {
        this.title = info.getTitle();
        this.authors = info.getAuthors().toString();

        this.text = stringToArrayList(descriptions);
        this.text.add(BEGIN_REVIEWS);
        this.text.addAll(stringToArrayList(reviews));
    }

    /**
     *
     * @return The item's title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return Item's authors/artists/producers
     */
    public String getAuthors() {
        return authors;
    }

    /**
     *
     * @return The summarised text
     */
    public ArrayList<String> getText() {
        return text;
    }

    /**
     *
     * @param text Multi-sentence output from the summarizer
     * @return A sanitised ArrayList of sentences
     */
    public ArrayList<String> stringToArrayList(String text) {

        ArrayList<String> v = new ArrayList<String>();

        // Remove line numbers, e.g. [1], [2], ...
        text = text.replaceAll("\\[\\d+\\]", "\n");

        // Add a newline at the end of each sentence
        // Problem with abbreviations: Dr. Paulson becomes [Dr.], [Paulson]
        text = text.replaceAll("(?<=[\\?\\.;!])\\s+(?=[A-Z]+)", "\n");

        // Split on newlines, compose Vector of non-empty strings
        for (String s : text.split("\\s*\\n\\s*")) {
            if (!s.matches("^\\s*$"))
                v.add(s);
        }

        return v;
    }
}
