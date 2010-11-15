package org.dataone.cn.web;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.codec.binary.Hex;
import org.dataone.cn.rest.util.ResolveUtilities;
import org.junit.Test;

public class TestIdentifierEncoding 
{

	/*
	 * A test to see if the encodeIdentifier method works.  The one test loops through
	 * many identifiers to see if it produces expected results.
	 * Expected results were determined using an online resource that maps code points
	 * to their UTF-8 representations:
	 *      <a href="http://www.utf8-chartable.de/unicode-utf8-table.pl"/>
	 */
	@Test
	public final void testEncodeIdentifier() 
	{
		// finding the proper escaped string was the challenge here, as was finding a way to put supplementary
		// unicode characters into Java Strings
		// see http://www.utf8-chartable.de/unicode-utf8-table.pl for a great Unicode to UTF-8 reference
		// the O'Reilly Java in a Nutshell book (5th edition) pg. 209-210 provided the way to put supplementary
		// code points into Strings.
		
		HashMap<String, String> idEncodingPairs = new HashMap<String,String>();
		idEncodingPairs.put("allowed-abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ",
				"allowed-abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		idEncodingPairs.put("allowed-0123456789","allowed-0123456789");
		idEncodingPairs.put("allowed-;:@&=$-_.+!*()',~", "allowed-;:@&=$-_.+!*()',~");
		idEncodingPairs.put(" disallowed-ascii-spaces: x x ", "%20disallowed-ascii-spaces:%20x%20x%20");
		idEncodingPairs.put("disallowed-ascii-percent:%", "disallowed-ascii-percent:%25");

// can't get control character testing to work, for perhaps predictable reasons		
//		StringBuilder controlID = new StringBuilder(32);
//		for (int i = 0x00; i < 0x1F; i++)
//		{
//			controlID.append(Character.toChars(i));
//		}
//		String encodedControlID = "%00%01%02%03%04%05%06%07%08%09%0A%0B%0C%0D%0E%0F%10%11%12%13%14%15%16%17%18%19%1A%1B%1C%1D%1E%1F";
//		idEncodingPairs.put("disallowed-ascii-controls:" + controlID.toString(),"disallowed-ascii-controls:" + encodedControlID);
		
		idEncodingPairs.put("disallowed-ascii-printables:?/[]", "disallowed-ascii-printables:%3F%2F%5B%5D");
		idEncodingPairs.put("disallowed-nonAscii-BMP-umlaut:Ÿ",          "disallowed-nonAscii-BMP-umlaut:%C3%BC");
		idEncodingPairs.put("disallowed-nonAscii-BMP-euro:" + "\u20AC", "disallowed-nonAscii-BMP-euro:%E2%82%AC");

		idEncodingPairs.put("disallowed-nonAscii-BMP-thai:" + "\u0E09", "disallowed-nonAscii-BMP-thai:%E0%B8%89");


		// to get supplementary Unicode code points into Strings, a couple extra steps are needed
		// because these are beyond the 16 bit limit
		int codepoint = 0x1010C;  // An Aegean number
		char[] surrogatePair = Character.toChars(codepoint);
		String supp = new String(surrogatePair);
		idEncodingPairs.put("disallowed-nonAscii-supplementary-AegeanNums:" + supp,"disallowed-nonAscii-supplementary-AegeanNums:%F0%90%84%8C");
		
		
		SortedSet<String> ids = new TreeSet<String>(idEncodingPairs.keySet());
		Iterator i = ids.iterator();
		while (i.hasNext())
		{
			String id = (String) i.next();
			System.out.println("Identifier:    " + id );
			String encodedID = ResolveUtilities.encodeIdentifier(id);
			System.out.println("       got:    " + encodedID);
			assertTrue("identifier: " + id, encodedID.equals(idEncodingPairs.get(id)));	
		}
	}
}
