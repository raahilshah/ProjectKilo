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

import java.io.IOException;
import java.util.List;

/**
 * Summarizer which uses with the MEAD client.
 * It is initialised with documents as an array of String, and a type of summary.
 * It uses an instance of MEADClient to exchange data with a local or remote Perl MEAD server.
 *
 * @author groupKilo
 * @author dc561
 */
public class MEADSummarizer extends Summarizer {
    public static final String LOCALHOST = "localhost";

    private String[] texts;
    private int sentenceNumber;
    private String summary;

    private double summLength;
    private String sys=null;
    private int comp = 20;

    public MEADSummarizer(List<String> texts, int sentenceNumber) throws IOException {
        this(
                texts.toArray(new String[texts.size()]),
                "P" + sentenceNumber,
                LOCALHOST);
    }

    /**
     *
     * @param texts Original text to summarize
     * @param summtype Type of summary requested
     * @param host Location of MEAD server; LOCALHOST looks for local server
     * @throws IOException
     */
    public MEADSummarizer(String[] texts, String summtype, String host) throws IOException {

        this.texts = texts;
        this.summLength = 0;

        if (texts == null) {
            this.summary = "";
            return;
        }

        parseSummtype(summtype);

        texts = preprocessText(texts);

        if(sys.equals("fulldocs")) {
            // Return the full original text
            StringBuilder sb = new StringBuilder();
            for (String s: texts) sb.append(s);
            summary= sb.toString();
        }
        else {
            if (sys.equals("CENTROID")) {
                // Produce centroid scorings
                MEADClient.Policy.output_mode = "centroid";
            } else {
                // Produce a regular summary
                MEADClient.Policy.output_mode = "summary";
            }
            MEADClient MC = new MEADClient(host);
            MEADClient.Policy.compressionAmt = comp;

            summary = MC.Exchange(texts);

            if(summary.matches("^\\s*$")){
                summLength = 0;
                summary = null;
            } else if(summary.startsWith("io problem")) {
                throw new IOException("The Mead server on " + host + " is having problems summarizing");
            } else {
                this.summLength = summary.length();
            }
        }
    }

    /**
     * Sets the summary type for the object
     *
     * @param summtype String indicating the type of summary requested
     */
    public void parseSummtype(String summtype) {
        if(summtype.toUpperCase().startsWith("RAND")) {
            sys = "RANDOM";
        }
        else if(summtype.toUpperCase().startsWith("FULLDOC")) {
            sys = "fulldocs";
        }
        else if(summtype.toUpperCase().startsWith("LEAD")) {
            sys = "LEADBASED";
        }
        else if(summtype.toUpperCase().startsWith("CENT")) {
            sys = "CENTROID";
        }
        else {
            sys = "";
        }

        if(summtype.endsWith("10")) {
            comp = 10;
        }
        else if(summtype.endsWith("5")) {
            comp = 5;
        }
        else {
            comp = 20;
        }
    }

    /**
     *
     * @return The summarised output
     */
    public String getSummary() {
        return summary;
    }

    /**
     *
     * @return The length of the summary
     */
    public double getSummLength() {
        return summLength;
    }

    /**
     *
     * @return Whether the summary is empty or blank
     */
    public boolean isEmpty() {
        return (summLength == 0);
    }

    /**
     *
     * @return An array of String with the original text
     */
    public String[] getTexts() {
        return texts;
    }

    /**
     * Removes stray HTML tags and excessive whitespace
     *
     * @param texts Original text
     * @return Sanitised text
     */
    public static String[] preprocessText(String[] texts) {
        for (int i = 0; i < texts.length; i++)
            texts[i] = texts[i].
                    replaceAll("</?.*?>", "").
                    replaceAll("^\\W+\\.", "").
                    replaceAll("\\s*\\.\\s+\\.", "\\.").
                    replaceAll("^\\s+", "").
                    replaceAll("\\s+", " ");

        return texts;
    }
}
