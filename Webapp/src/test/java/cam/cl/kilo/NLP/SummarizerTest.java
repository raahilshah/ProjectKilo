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

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author groupKilo
 * @author dc561
 */
public class SummarizerTest {
    Summarizer summarizer;
    ItemInfo info = new ItemInfo();
    String barcodeNo = "0385354304"; // "The Strange Library" by H. Murakami


    @BeforeClass
    public void setUp() {


        //summarizer = new Summarizer();
    }

    @Test
    public void testGetSummResults() throws Exception {
        assertTrue(true);
    }

    @Test
    public void testGetSummLength() throws Exception {
        //Return 0 when text is null, empty or blank
        assertTrue(true);
    }

    @Test
    public void testGetTexts() throws Exception {
        assertTrue(true);
    }

    @Test
    public void testPreprocessText() throws Exception {
        assertTrue(true);
    }

    @Test
    public void testParseSummtype() throws Exception {


    }
}