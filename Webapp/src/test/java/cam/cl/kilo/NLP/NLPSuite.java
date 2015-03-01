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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for all NLP classes
 *
 * @author groupKilo
 * @author dc561
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        SummarizerTest.class,
        Summary.class
})
public class NLPSuite {
}

