package org.dataone.service;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
	private static String testUnicodeIdentifiersFile = "/org/dataone/service/encodingTestSet/testUnicodeStrings.utf8.txt";
	private static HashMap<String,String> StandardTests = new HashMap<String,String>();
	
	@Before
	public void generateStandardTests() {
		if (StandardTests.size() == 0) {
			log(" * * * * * * * Unicode Test Strings * * * * * * ");
			
			InputStream is = this.getClass().getResourceAsStream(testUnicodeIdentifiersFile);
			Scanner s = new Scanner(is,"UTF-8");
			String[] temp;
			try
			{
				while (s.hasNextLine()) 
				{
					String line = s.nextLine();
					log(line);
					temp = line.split("\t");
					if (temp.length > 1)
						StandardTests.put(temp[0], temp[1]);
				}
				log("");
			} finally {
				s.close();
			}
			log("");
		}
	}
	

	@Test
	public final void testEncodeUrlPathSegment()
	{
		log(" * * * * * * * testing URL Path Segment encoding * * * * * * ");

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
		log("");
	}

	
	@Test
	public final void testEncodeUrlQuerySegment()
	{
		log(" * * * * * * * testing URL Query Segment encoding * * * * * * ");
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
		log("");
	}

	
	@Test
	public final void testEncodeUrlFragment()
	{
		log(" * * * * * * * testing URL Fragment encoding * * * * * * ");
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
		log("");
	}

	@Test
	public final void testDecodeString() throws UnsupportedEncodingException
	{
		log(" * * * * * * * testing Decoding * * * * * * ");

		SortedSet<String> ids = new TreeSet<String>(StandardTests.keySet());
		Iterator<String> i = ids.iterator();
		while (i.hasNext())
		{
			String id = (String) i.next();
			runDecodeAssertion(StandardTests.get(id),
						 id,
						 EncodingUtilities.decodeString(StandardTests.get(id))
						 );
		}
		log("");
	}

	@Test
	public final void testDecodeError1() throws UnsupportedEncodingException
	{
		log(" * * * * * * * testing Decoding Error 1 * * * * * * ");
		log("String to decode: testMalformedEscape-%3X");
		try {
			String s = EncodingUtilities.decodeString("testMalformedEscape-%3X");
		} catch (IllegalArgumentException iae) {
			assertThat("Malformed hex error caught",iae, instanceOf(IllegalArgumentException.class));
			log("caught the error (bad hex character)");
			return;
		}
		fail("did not catch malformed hex error (bad hex character)");
	}
		
	@Test
	public final void testDecodeError2() throws UnsupportedEncodingException
	{
		log(" * * * * * * * testing Decoding Error 2 * * * * * * ");
		log("String to decode: testMalformedEscape-%3");
		try {
			String s = EncodingUtilities.decodeString("testMalformedEscape-%3");
		} catch (IllegalArgumentException iae) {
			assertThat("Malformed hex error caught",iae, instanceOf(IllegalArgumentException.class));
			log("caught the error (truncated hex pattern)");
			return;
		}
		fail("did not catch malformed hex error (incomplete hex string)");
	}
	
	
	@Test
	public final void testDecodeError3() throws UnsupportedEncodingException
	{
		log(" * * * * * * * testing Decoding Error 3 * * * * * * ");
		log("String to decode: testMalformedEscape-%");
		try {
			String s = EncodingUtilities.decodeString("testMalformedEscape-%");
		} catch (IllegalArgumentException iae) {
			assertThat("Malformed hex error caught",iae, instanceOf(IllegalArgumentException.class));
			log("caught the error (truncated hex pattern)");
			return;
		}
		fail("did not catch malformed hex error (incomplete hex string)");
	}


	private void runAssertion(String id, String expected, String got)
	{
		System.out.println("Identifier:    " + id);
		System.out.println("    expect:    " + expected);
		System.out.println("       got:    " + got);
		System.out.println();

		assertTrue("identifier: " + id, got.equals(expected));		
	}

	private void runDecodeAssertion(String id, String expected, String got)
	{
		System.out.println("Encoded Id:    " + id);
		System.out.println("    expect:    " + expected);
		System.out.println("       got:    " + got);
		System.out.println();

		assertTrue("identifier: " + id, got.equals(expected));		
	}

	
	  private static void log(Object aObject){
		  System.out.println(String.valueOf(aObject));
	  }	  
}
