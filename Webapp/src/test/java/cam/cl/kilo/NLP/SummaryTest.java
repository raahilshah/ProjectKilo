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

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author groupKilo
 * @author dc561
 */
public class SummaryTest {

    @Test
    public void testStringToArrayList1() throws Exception {
        String pre = "[1] Blah.\n[2] Another sentence.\n[3]What is this even doing here?\n\n[4] Foobar.\n";

        String[] post = {
                "Blah.",
                "Another sentence.",
                "What is this even doing here?",
                "Foobar."
        };
        assertEquals(new ArrayList<String>(Arrays.asList(post)), Summary.stringToArrayList(pre));
    }

    @Test
    public void testStringToArrayList2() throws Exception {
        String pre = "The new edition of this successful and established textbook retains its two original intentions of explaining how to program in the ML language, and teaching the fundamentals of functional programming. The major change is the early and prominent coverage of modules, which the author extensively uses throughout. In addition, Paulson has totally rewritten the first chapter to make the book more accessible to students who have no experience of programming languages. The author describes the main features of new Standard Library for the revised version of ML, and gives many new examples, e.g. polynomial arithmetic and new ways of treating priority queues. Finally he has completely updated the references. Dr Paulson has extensive practical experience of ML, and has stressed its use as a tool for software engineering; the book contains many useful pieces of code, which are freely available (via Internet) from the author. He shows how to use lists, trees, higher-order functions and infinite data structures.  He includes many illustrative and practical examples, covering sorting, matrix operations, and polynomial arithmetic. He describes efficient functional implementations of arrays, queues, and priority queues. Larger examples include a general top-down parser, a lambda-calculus reducer and a theorem prover. A chapter is devoted to formal reasoning about functional programs. The combination of careful explanation and practical advice will ensure that this textbook continues to be the preferred text for many courses on ML for students at all levels.";

        String[] post = {
                "The new edition of this successful and established textbook retains its two original intentions of explaining how to program in the ML language, and teaching the fundamentals of functional programming.",
                "The major change is the early and prominent coverage of modules, which the author extensively uses throughout.",
                "In addition, Paulson has totally rewritten the first chapter to make the book more accessible to students who have no experience of programming languages.",
                "The author describes the main features of new Standard Library for the revised version of ML, and gives many new examples, e.g. polynomial arithmetic and new ways of treating priority queues.",
                "Finally he has completely updated the references.",
                "Dr Paulson has extensive practical experience of ML, and has stressed its use as a tool for software engineering; the book contains many useful pieces of code, which are freely available (via Internet) from the author.",
                "He shows how to use lists, trees, higher-order functions and infinite data structures.",
                "He includes many illustrative and practical examples, covering sorting, matrix operations, and polynomial arithmetic.",
                "He describes efficient functional implementations of arrays, queues, and priority queues.",
                "Larger examples include a general top-down parser, a lambda-calculus reducer and a theorem prover.",
                "A chapter is devoted to formal reasoning about functional programs.",
                "The combination of careful explanation and practical advice will ensure that this textbook continues to be the preferred text for many courses on ML for students at all levels."
        };
        assertEquals(new ArrayList<String>(Arrays.asList(post)), Summary.stringToArrayList(pre));
    }
}