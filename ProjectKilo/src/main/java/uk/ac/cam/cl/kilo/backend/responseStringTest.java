package uk.ac.cam.cl.kilo.backend;

import javax.ws.rs.core.Response;

import junit.framework.TestCase;

import org.junit.Test;

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
