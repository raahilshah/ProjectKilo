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

    public final String BEGIN_REVIEWS;

    /**
     *
     * @param info An ItemInfo object
     * @param descriptions Summarised text of product descriptions
     * @param reviews Summarised text of product reviews
     */
    public Summary(ItemInfo info, String descriptions, String reviews) {
        this.title = info.getTitle();
        this.authors = info.getAuthors().toString();

        BEGIN_REVIEWS = "What people say about " + info.getTitle();

        this.text = stringToArrayList(descriptions);
        if (reviews.length() > 0) {
            this.text.add(BEGIN_REVIEWS);
            this.text.addAll(stringToArrayList(reviews));
        }
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

    public static void main(String[] args) {
        // Test cases for stringToArrayList
        String str1 = "[1] Blah.\n[2] Another sentence.\n[3]What is this even doing here?\n\n[4] Foobar.\n";
        String str2 = "The new edition of this successful and established textbook retains its two original intentions of explaining how to program in the ML language, and teaching the fundamentals of functional programming. The major change is the early and prominent coverage of modules, which the author extensively uses throughout. In addition, Paulson has totally rewritten the first chapter to make the book more accessible to students who have no experience of programming languages. The author describes the main features of new Standard Library for the revised version of ML, and gives many new examples, e.g. polynomial arithmetic and new ways of treating priority queues. Finally he has completely updated the references. Dr. Paulson has extensive practical experience of ML, and has stressed its use as a tool for software engineering; the book contains many useful pieces of code, which are freely available (via Internet) from the author. He shows how to use lists, trees, higher-order functions and infinite data structures.  He includes many illustrative and practical examples, covering sorting, matrix operations, and polynomial arithmetic. He describes efficient functional implementations of arrays, queues, and priority queues. Larger examples include a general top-down parser, a lambda-calculus reducer and a theorem prover. A chapter is devoted to formal reasoning about functional programs. The combination of careful explanation and practical advice will ensure that this textbook continues to be the preferred text for many courses on ML for students at all levels.";
        Summary summ1 = new Summary(new ItemInfo(), str1, "");
        Summary summ2 = new Summary(new ItemInfo(), str2, "This is a review. Here's a another one. I don't know if reviews will work well.");

        for (String s : summ1.getText()) {
            System.out.println(s);
        }
        for (String s : summ2.getText()) {
            System.out.println(s);
        }
    }
}