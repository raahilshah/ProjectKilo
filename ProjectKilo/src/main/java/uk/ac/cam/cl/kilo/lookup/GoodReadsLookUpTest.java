package uk.ac.cam.cl.kilo.lookup;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GoodReadsLookUpTest {

	public GoodReadsLookUpTest() {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGoodReadsLookUp() {
		GoodReadsLookUp test = new GoodReadsLookUp("isbn", "978-0764570681");
		
		fail("Not yet implemented");
	}

	@Test
	public void testFillContent() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTitle() {
		GoodReadsLookUp test = new GoodReadsLookUp("isbn", "978-0764570681");
		assertEquals("C for Dummies", test.getTitle());
	}

	@Test
	public void testGetDescription() {
		GoodReadsLookUp test = new GoodReadsLookUp("isbn", "978-0764570681");
		assertEquals("description", test.getDescription());
	}

	/*
	 * Need to perform a check on multiple authors (ordering)
	 * Only 1 author has been tested so far
	 */
	@Test
	public void testGetAuthors() {
		GoodReadsLookUp test = new GoodReadsLookUp("isbn", "978-0764570681");
		List<String> authors = new LinkedList<String>();
		authors.add("Dan Gookin");
		assertEquals(authors, test.getAuthors());
	}

}
