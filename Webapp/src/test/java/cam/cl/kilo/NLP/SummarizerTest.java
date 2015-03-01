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

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author groupKilo
 * @author dc561
 */
public class SummarizerTest {
    Summarizer summarizer;
    String barcodeNo;
    ItemInfo info;


    @Test
    public void emptyWhenWhitespace() throws Exception {
        String[] testArray = { "   ", "\n\n\n\n", "\t\t\t\t", "    \n    \t   "};
        summarizer = new MEADSummarizer(testArray, "P10", MEADSummarizer.LOCALHOST);

        assertTrue(summarizer.isEmpty());
    }

    @Test
    public void emptyWhenBlank() throws Exception {
        String[] testArray = { "", "", ""};
        summarizer = new MEADSummarizer(testArray, "P10", MEADSummarizer.LOCALHOST);

        assertTrue(summarizer.isEmpty());
    }

    @Test
    public void emptyWhenNull() throws Exception {
        String[] testArray = null;
        summarizer = new MEADSummarizer(testArray, "P10", MEADSummarizer.LOCALHOST);

        assertTrue(summarizer.isEmpty());
    }


    @Test
    public void testPreprocessText() throws Exception {
        String[] testArray = {
                "<h1 id='bold'>This is a bold paragraph</h1>",
                "    -. Foo.",
                "   .    .",
                "\t \t     \t"
        };

        String[] expected = {
                "This is a bold paragraph",
                "Foo.",
                "",
                ""
        };

        assertArrayEquals(expected, MEADSummarizer.preprocessText(testArray));


    }

    @Test
    public void testParseSummtype() throws Exception {
        assertTrue(true);
    }
}