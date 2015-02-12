package uk.ac.cam.cl.kilo.lookup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.cam.cl.kilo.nlp.ItemInfo;

public class GoodReadsLookupTest {

	public GoodReadsLookupTest() {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGoodReadsLookUp() {
		ItemInfo info = new ItemInfo();
		GoodReadsLookup test = new GoodReadsLookup("isbn", "978-0764570681", info);
		
		fail("Not yet implemented");
	}

	@Test
	public void testFillContent() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTitle() {
		ItemInfo info = new ItemInfo();
		GoodReadsLookup test = new GoodReadsLookup("isbn", "978-0764570681", info);
		assertEquals("C for Dummies", test.info.getTitle());
	}

	@Test
	public void testGetDescription() {
		ItemInfo info = new ItemInfo();
		GoodReadsLookup test = new GoodReadsLookup("isbn", "978-0764570681", info);
		assertEquals("description", test.info.getDescriptions().get(0));
	}

	/*
	 * Need to perform a check on multiple authors (ordering)
	 * Only 1 author has been tested so far
	 */
	@Test
	public void testGetAuthors() {
		ItemInfo info = new ItemInfo();
		GoodReadsLookup test = new GoodReadsLookup("isbn", "978-0764570681", info);
		List<String> authors = new LinkedList<String>();
		authors.add("Dan Gookin");
		assertEquals(authors, test.info.getAuthors());
	}

}
