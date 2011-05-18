package org.dataone.service;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import org.dataone.service.EncodingUtilities;
import org.junit.Before;
import org.junit.Test;

public class TestIdentifierEncoding 
{
	private static boolean verbose = true;
	
	private static String testUnicodeIdentifiersFile = "/org/dataone/service/encodingTestSet/testUnicodeStrings.utf8.txt";
	private static HashMap<String,String> StandardTests = new HashMap<String,String>();
	
	@Before
	public void generateStandardTests() {
		if (StandardTests.size() == 0) {
			verbose(" * * * * * * * Unicode Test Strings * * * * * * ");
			
			InputStream is = this.getClass().getResourceAsStream(testUnicodeIdentifiersFile);
			Scanner s = new Scanner(is,"UTF-8");
			String[] temp;
			try
			{
				while (s.hasNextLine()) 
				{
					String line = s.nextLine();
					verbose(line);
					temp = line.split("\t");
					if (temp.length > 1)
						StandardTests.put(temp[0], temp[1]);
				}
				verbose("");
			} finally {
				s.close();
			}
			verbose("");
		}
	}
	

	@Test
	public final void testEncodeUrlPathSegment()
	{
		verbose(" * * * * * * * testing URL Path Segment encoding * * * * * * ");

		SortedSet<String> ids = new TreeSet<String>(StandardTests.keySet());
		Iterator<String> i = ids.iterator();
		while (i.hasNext())
		{
			String id = (String) i.next();
			if (id.startsWith("common-") || 
					id.startsWith("path-")) {
				runAssertion(id,
							StandardTests.get(id),
							EncodingUtilities.encodeUrlPathSegment(id)
							);
			}
		}
		verbose("");
	}

	
	@Test
	public final void testEncodeUrlQuerySegment()
	{
		verbose(" * * * * * * * testing URL Query Segment encoding * * * * * * ");
		SortedSet<String> ids = new TreeSet<String>(StandardTests.keySet());
		Iterator<String> i = ids.iterator();
		while (i.hasNext())
		{
			String id = (String) i.next();
			if (id.startsWith("common-") || 
					id.startsWith("query-")) {
				runAssertion(id,
							StandardTests.get(id),
							EncodingUtilities.encodeUrlQuerySegment(id)
							);
			}
		}
		verbose("");
	}

	
	@Test
	public final void testEncodeUrlFragment()
	{
		verbose(" * * * * * * * testing URL Fragment encoding * * * * * * ");
		SortedSet<String> ids = new TreeSet<String>(StandardTests.keySet());
		Iterator<String> i = ids.iterator();
		while (i.hasNext())
		{
			String id = (String) i.next();
			if (id.startsWith("common-") || 
					id.startsWith("fragment-")) {
				runAssertion(id,
							StandardTests.get(id),
							EncodingUtilities.encodeUrlFragment(id)
							);
			}
		}
		verbose("");
	}

	@Test
	public final void testDecodeString() throws UnsupportedEncodingException
	{
		verbose(" * * * * * * * testing Decoding * * * * * * ");

		SortedSet<String> ids = new TreeSet<String>(StandardTests.keySet());
		Iterator<String> i = ids.iterator();
		while (i.hasNext())
		{
			String id = (String) i.next();
			String encodedString = StandardTests.get(id);
			runDecodeAssertion(encodedString,
						 id,
						 EncodingUtilities.decodeString(encodedString)
						 );
		}
		verbose("");
	}

//	@Test
	public final void testDecodeStringAlternate() throws UnsupportedEncodingException
	{
		verbose(" * * * * * * * testing Decoding using URLDecoder * * * * * * ");

		SortedSet<String> ids = new TreeSet<String>(StandardTests.keySet());
		Iterator<String> i = ids.iterator();
		while (i.hasNext())
		{
			String id = (String) i.next();
			String encodedString = StandardTests.get(id);
			runDecodeAssertion(encodedString,
						 id,
						 URLDecoder.decode(encodedString, "UTF-8")
						 );
		}
		verbose("");
	}

	
	@Test
	public final void testEncodeDecode() throws UnsupportedEncodingException
	{
		verbose(" * * * * * * * testing Encode - Decode roundtrip  * * * * * * ");

		SortedSet<String> ids = new TreeSet<String>(StandardTests.keySet());
		Iterator<String> i = ids.iterator();
		while (i.hasNext())
		{
			String id = (String) i.next();
			if (id.startsWith("common-") || id.startsWith("path-")) 
			{	
				String encodedString = EncodingUtilities.encodeUrlPathSegment(id);
				runDecodeAssertion(
						encodedString,
						id,
						EncodingUtilities.decodeString(encodedString)
				);
			}
		}	
		verbose("");
	}

	@Test
	public final void testEncodeDecodeAlternate() throws UnsupportedEncodingException
	{
		verbose(" * * * * * * * testing Encode - Decode roundtrip using URLDecoder * * * * * * ");

		SortedSet<String> ids = new TreeSet<String>(StandardTests.keySet());
		Iterator<String> i = ids.iterator();
		while (i.hasNext())
		{
			String id = (String) i.next();
			if (id.startsWith("common-") || id.startsWith("path-")) 
			{	
				String encodedString = EncodingUtilities.encodeUrlPathSegment(id);
				runDecodeAssertion(
						encodedString,
						id,
						URLDecoder.decode(encodedString, "UTF-8")
				);
			}
		}	
		verbose("");
	}

	
	
	
	@Test
	public final void testDecodeError1() throws UnsupportedEncodingException
	{
		verbose(" * * * * * * * testing Decoding Error 1 * * * * * * ");
		verbose("String to decode: testMalformedEscape-%3X");
		try {
			String s = EncodingUtilities.decodeString("testMalformedEscape-%3X");
		} catch (IllegalArgumentException iae) {
			assertThat("Malformed hex error caught",iae, instanceOf(IllegalArgumentException.class));
			verbose("caught the error (bad hex character)");
			return;
		}
		fail("did not catch malformed hex error (bad hex character)");
	}
		
	@Test
	public final void testDecodeError2() throws UnsupportedEncodingException
	{
		verbose(" * * * * * * * testing Decoding Error 2 * * * * * * ");
		verbose("String to decode: testMalformedEscape-%3");
		try {
			String s = EncodingUtilities.decodeString("testMalformedEscape-%3");
		} catch (IllegalArgumentException iae) {
			assertThat("Malformed hex error caught",iae, instanceOf(IllegalArgumentException.class));
			verbose("caught the error (truncated hex pattern)");
			return;
		}
		fail("did not catch malformed hex error (incomplete hex string)");
	}
	
	
	@Test
	public final void testDecodeError3() throws UnsupportedEncodingException
	{
		verbose(" * * * * * * * testing Decoding Error 3 * * * * * * ");
		verbose("String to decode: testMalformedEscape-%");
		try {
			String s = EncodingUtilities.decodeString("testMalformedEscape-%");
		} catch (IllegalArgumentException iae) {
			assertThat("Malformed hex error caught",iae, instanceOf(IllegalArgumentException.class));
			verbose("caught the error (truncated hex pattern)");
			return;
		}
		fail("did not catch malformed hex error (incomplete hex string)");
	}


	private void runAssertion(String id, String expected, String got)
	{
		if (!got.equals(expected)) {
			verbose("Identifier:    " + id);
			verbose("    expect:    " + expected);
			verbose("       got:    " + got);
			verbose("");
		}
		assertEquals("identifier: " + id, expected, got);		
	}

	private void runDecodeAssertion(String id, String expected, String got)
	{
		if (!got.equals(expected)) {
			verbose("Encoded Id:    " + id);
			verbose("    expect:    " + expected);
			verbose("       got:    " + got);
			verbose("");
		}
		assertEquals("identifier: " + id, expected, got);		
	} 
	  
	private static void verbose(Object aObject){
		if (verbose) {
			System.out.println(String.valueOf(aObject));
		}		
	}	 
	  
}
