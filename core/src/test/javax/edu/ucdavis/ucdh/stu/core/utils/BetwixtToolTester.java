package edu.ucdavis.ucdh.stu.core.utils;

import junit.framework.TestCase;

import org.junit.Test;

public class BetwixtToolTester extends TestCase {
	private static final String LF = System.getProperty("line.separator");

	/**
	 * Sets up the test fixture. 
	 * (Called before every test case method.) 
	 */ 
	protected void setUp() throws Exception { 
		super.setUp();
	} 

	/**
	 * Tears down the test fixture. 
	 * (Called after every test case method.) 
	 */ 
	protected void tearDown() throws Exception { 
		super.tearDown(); 
	} 

	/**
	 * Tests the plain version of the call (Object only) 
	 */ 
	@Test
	public void testPlain() {
		// set up the expected results
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buffer.append(LF);
		buffer.append("<String>Test</String>");
		buffer.append(LF);
		String expected = buffer.toString();

		// make the call
		String results = BetwixtTool.toXml("Test");

		// set up the error message
		buffer = new StringBuffer();
		buffer.append("-->");
		buffer.append(results);
		buffer.append("<-- is not equal to -->");
		buffer.append(expected);
		buffer.append("<--");

		// do the comparison
		assertTrue(buffer.toString(), expected.equals(results));
	} 

	/**
	 * Tests the version of the call that accepts a boolean to control
	 * whether or not the declaration is included 
	 */ 
	@Test
	public void testUsingBoolean() {
		// set up the expected results (for true)
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buffer.append(LF);
		buffer.append("<String>Test</String>");
		buffer.append(LF);
		String expected = buffer.toString();

		// make the call
		String results = BetwixtTool.toXml("Test", true);

		// set up the error message
		buffer = new StringBuffer();
		buffer.append("-->");
		buffer.append(results);
		buffer.append("<-- is not equal to -->");
		buffer.append(expected);
		buffer.append("<--");

		// do the comparison
		assertTrue(buffer.toString(), expected.equals(results));

		// set up the expected results (for false)
		buffer = new StringBuffer();
		buffer.append("<String>Test</String>");
		buffer.append(LF);
		expected = buffer.toString();

		// make the call
		results = BetwixtTool.toXml("Test", false);

		// set up the error message
		buffer = new StringBuffer();
		buffer.append("-->");
		buffer.append(results);
		buffer.append("<-- is not equal to -->");
		buffer.append(expected);
		buffer.append("<--");

		// do the comparison
		assertTrue(buffer.toString(), expected.equals(results));
	} 

	/**
	 * Tests the version of the call that accepts an optional String
	 * to be used as the path to an XSL style sheet 
	 */ 
	@Test
	public void testUsingString() {
		// set up the expected results (for empty string)
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buffer.append(LF);
		buffer.append("<String>Test</String>");
		buffer.append(LF);
		String expected = buffer.toString();

		// make the call
		String results = BetwixtTool.toXml("Test", "");

		// set up the error message
		buffer = new StringBuffer();
		buffer.append("-->");
		buffer.append(results);
		buffer.append("<-- is not equal to -->");
		buffer.append(expected);
		buffer.append("<--");

		// do the comparison
		assertTrue(buffer.toString(), expected.equals(results));

		// set up the expected results (for actual path)
		buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buffer.append(LF);
		buffer.append("<?xml-stylesheet type=\"text/xsl\" href=\"/path/to/the.xsl\"?>");
		buffer.append(LF);
		buffer.append("<String>Test</String>");
		buffer.append(LF);
		expected = buffer.toString();

		// make the call
		results = BetwixtTool.toXml("Test", "/path/to/the.xsl");

		// set up the error message
		buffer = new StringBuffer();
		buffer.append("-->");
		buffer.append(results);
		buffer.append("<-- is not equal to -->");
		buffer.append(expected);
		buffer.append("<--");

		// do the comparison
		assertTrue(buffer.toString(), expected.equals(results));
	} 

	/**
	 * Tests the version of the call that accepts both a boolean to
	 * control whether or not the declaration is included and an
	 * optional String to be used as the path to an XSL style sheet
	 */ 
	@Test
	public void testUsingBoth() {
		// set up the expected results
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buffer.append(LF);
		buffer.append("<String>Test</String>");
		buffer.append(LF);
		String expected = buffer.toString();

		// make the call
		String results = BetwixtTool.toXml("Test", "", true);

		// set up the error message
		buffer = new StringBuffer();
		buffer.append("-->");
		buffer.append(results);
		buffer.append("<-- is not equal to -->");
		buffer.append(expected);
		buffer.append("<--");

		// do the comparison
		assertTrue(buffer.toString(), expected.equals(results));
	} 
}