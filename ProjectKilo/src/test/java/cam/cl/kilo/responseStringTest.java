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

package cam.cl.kilo;

import junit.framework.TestCase;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class responseStringTest extends TestCase {

	public responseStringTest(String name) {
		super(name);
	}

	/*
	 * We need to compare local variables which is difficult
	 * Method currently not functioning - needs fixing
	 */
	
	@Test
	public void responseStringTest() {
		RESTEasyBarcode test = new RESTEasyBarcode();
		//String value = "XXXXX";
		// assertEquals(Response.ok(value).build(), test.simpleResponse("XXXXX", "XXXXX"));
		
		assertTrue(Response.ok("Missing barcode number.").build().equals(test.simpleResponse(null, "XXXXX")));
		
		//assertEquals("Missing barcode number.", test.simpleResponse(null, "XXXXX").getEntity());
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
