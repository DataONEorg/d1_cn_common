package org.dataone.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

// import org.apache.commons.codec.binary.Hex;
import org.dataone.service.EncodingUtilities;
import org.junit.Test;

public class TestIdentifierEncoding 
{

	// finding the proper escaped string was the challenge here, as was finding a way to put supplementary
	// unicode characters into Java Strings
	// see http://www.utf8-chartable.de/unicode-utf8-table.pl for a thorough Unicode to UTF-8 reference
	// the O'Reilly Java in a Nutshell book (5th edition) pg. 209-210 provided the way to put supplementary
	// code points into Strings.

	
	private static HashMap<String, String> commonEncodingPairs = new HashMap<String, String>();
	static {
		commonEncodingPairs.put("allowed-abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ",
				                "allowed-abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		commonEncodingPairs.put("allowed-0123456789","allowed-0123456789");
		commonEncodingPairs.put("allowed-;:@$-_.+!*()',~", "allowed-;:@$-_.+!*()',~");
		commonEncodingPairs.put(" disallowed-ascii-spaces: x x ", "%20disallowed-ascii-spaces:%20x%20x%20");
		commonEncodingPairs.put("disallowed-ascii-percent:%", "disallowed-ascii-percent:%25");
		commonEncodingPairs.put("disallowed-ascii-printables:?/[]", "disallowed-ascii-printables:%3F%2F%5B%5D");
		commonEncodingPairs.put("disallowed-nonAscii-BMP-umlaut:Ÿ",          "disallowed-nonAscii-BMP-umlaut:%C3%BC");
		commonEncodingPairs.put("disallowed-nonAscii-BMP-euro:" + "\u20AC", "disallowed-nonAscii-BMP-euro:%E2%82%AC");

		commonEncodingPairs.put("disallowed-nonAscii-BMP-thai:" + "\u0E09", "disallowed-nonAscii-BMP-thai:%E0%B8%89");
		
		int codepoint = 0x1010C;  // An Aegean number
		char[] surrogatePair = Character.toChars(codepoint);
		String supp = new String(surrogatePair);
		commonEncodingPairs.put("disallowed-nonAscii-supplementary-AegeanNums:" + supp,"disallowed-nonAscii-supplementary-AegeanNums:%F0%90%84%8C");
		
		
		// can't get control character testing to work, for perhaps predictable reasons		
//		StringBuilder controlID = new StringBuilder(32);
//		for (int i = 0x00; i < 0x1F; i++)		controlID.append(Character.toChars(i));
//		String encodedControlID = "%00%01%02%03%04%05%06%07%08%09%0A%0B%0C%0D%0E%0F%10%11%12%13%14%15%16%17%18%19%1A%1B%1C%1D%1E%1F";
//		commonEncodingPairs.put("disallowed-ascii-controls:" + controlID.toString(),"disallowed-ascii-controls:" + encodedControlID);
	}
	
	
	
	@Test
	public final void testEncodeUrlPathSegment() 
	{
		System.out.println(" * * * * * * * testing URL Path Segment encoding * * * * * * ");
		System.out.println();
		SortedSet<String> ids = new TreeSet<String>(commonEncodingPairs.keySet());
		Iterator<String> i = ids.iterator();
		while (i.hasNext())
		{
			String id = (String) i.next();
			runAssertion(i.next(),
					 commonEncodingPairs.get(id),
					 EncodingUtilities.encodeUrlPathSegment(id)
					 );
		}
		
		// these are allowed in paths and fragments, but not queries
		runAssertion("allowed-&=&=",
					 "allowed-&=&=",
					 EncodingUtilities.encodeUrlPathSegment("allowed-&=&="));
	}


	@Test
	public final void testEncodeUrlFragment() 
	{
		System.out.println(" * * * * * * * testing URL Fragment encoding * * * * * * ");
		System.out.println();
		SortedSet<String> ids = new TreeSet<String>(commonEncodingPairs.keySet());
		Iterator<String> i = ids.iterator();
		while (i.hasNext())
		{
			String id = (String) i.next();
			runAssertion(i.next(),
						 commonEncodingPairs.get(id),
						 EncodingUtilities.encodeUrlFragment(id)
						 );
		}
		// these are allowed in paths and fragments, but not queries
		runAssertion("allowed-&=&=",
					 "allowed-&=&=",
					 EncodingUtilities.encodeUrlFragment("allowed-&=&="));

		
		// fragments and queries are allowed 2 extra characters to be in the unescaped set: "/" and "?"
		runAssertion("allowed-/?/?",
					 "allowed-/?/?",
					 EncodingUtilities.encodeUrlFragment("allowed-/?/?"));
	}

	@Test
	public final void testEncodeUrlQuerySegment() 
	{
		System.out.println(" * * * * * * * testing URL Query Segment encoding * * * * * * ");
		System.out.println();
		SortedSet<String> ids = new TreeSet<String>(commonEncodingPairs.keySet());
		Iterator<String> i = ids.iterator();
		while (i.hasNext())
		{
			String id = (String) i.next();
			runAssertion(i.next(),
						 commonEncodingPairs.get(id),
						 EncodingUtilities.encodeUrlQuerySegment(id)
						 );
		}
		// fragments and queries are allowed 2 extra characters to be in the unescaped set: "/" and "?"
		runAssertion("allowed-/?/?",
					 "allowed-/?/?",
					 EncodingUtilities.encodeUrlQuerySegment("allowed-/?/?"));
		
		// queries need to escape 2 characters that are reserved by convention for separating segments
		//  the characters "&" and "="
		runAssertion("escaped-&=&=",
					 "escaped-%26%3D%26%3D",
					 EncodingUtilities.encodeUrlQuerySegment("escaped-&=&="));
		// a meaningful case...
		runAssertion("escaped-this&that=theOther",
				 "escaped-this%26that%3DtheOther",
				 EncodingUtilities.encodeUrlQuerySegment("escaped-this&that=theOther"));
	}

	
	
	private void runAssertion(String id, String expected, String got)
	{
		System.out.println("Identifier:    " + id);
		System.out.println("    expect:    " + expected);
		System.out.println("       got:    " + got);
		System.out.println();
		assertTrue("identifier: " + id, got.equals(expected));		
	}
	
	

}
