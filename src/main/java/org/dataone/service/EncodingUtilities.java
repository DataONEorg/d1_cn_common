package org.dataone.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.BitSet;


/*
 Encoding routines Adapted from:
 (http://www.java2s.com/Code/Java/Network-Protocol/EncodeapathasrequiredbytheURLspecification.htm)
 
 Derby - Class org.apache.derby.iapi.util.PropertyUtil

 Licensed to the Apache Software Foundation (ASF) under one or more
 contributor license agreements.  See the NOTICE file distributed with
 this work for additional information regarding copyright ownership.
 The ASF licenses this file to you under the Apache License, Version 2.0
 (the "License"); you may not use this file except in compliance with
 the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 *
 *
 *  Decoding routines adapted from:	
 *  http://www.java2s.com/Code/Java/Network-Protocol/Requestparsingandencodingutilitymethods.htm	
 *
 *  distributed with the following license:
 *	
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/*
 * Useful resources for background on encoding 

 http://www.java-forums.org/new-java/350-how-obtain-ascii-code-character.html
 http://stackoverflow.com/questions/1527856/how-can-i-iterate-through-the-unicode-codepoints-of-a-java-string
 http://mule1.dataone.org/ArchitectureDocs/GUIDs.html
 http://download.oracle.com/javase/1.5.0/docs/api/java/lang/Character.html#unicode
 http://www.joelonsoftware.com/articles/Unicode.html


 http://www.utf8-chartable.de/unicode-utf8-table.pl

 */

public class EncodingUtilities {


	/**
	 * Array containing the allowable characters for 'pchar' set as defined by RFC 3986 ABNF:
	 *      pchar         = unreserved / pct-encoded / sub-delims / ":" / "@" 
	 *      unreserved  = ALPHA / DIGIT / "-" / "." / "_" / "~" 
	
	 */
	
	
	private static BitSet pcharUnescapedCharacters;
	private static BitSet fragmentUnescapedCharacters;
	private static BitSet queryUnescapedCharacters;

	private static final char[] hexadecimal = { '0', '1', '2', '3', '4', '5', '6', '7',
											    '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	static {
		pcharUnescapedCharacters = new BitSet(256);
		int i;

		// unreserved
		//   ALPHA
		for (i = 'a'; i <= 'z'; i++)     pcharUnescapedCharacters.set(i);
		for (i = 'A'; i <= 'Z'; i++)     pcharUnescapedCharacters.set(i);
		//   DIGIT
		for (i = '0'; i <= '9'; i++)     pcharUnescapedCharacters.set(i);
		//   other
		pcharUnescapedCharacters.set('-');
		pcharUnescapedCharacters.set('_');
		pcharUnescapedCharacters.set('.');
		pcharUnescapedCharacters.set('~');

		// sub-delims
		pcharUnescapedCharacters.set('!');
		pcharUnescapedCharacters.set('$');
		pcharUnescapedCharacters.set('&');
		pcharUnescapedCharacters.set('\'');
		pcharUnescapedCharacters.set('(');
		pcharUnescapedCharacters.set(')');
		pcharUnescapedCharacters.set('*');
		// some older URL parsers replace '+' with a space
		// so we'll escape this character to avoid mis-interpretation by clients
		// (removing it from the unescaped list)
		// pcharUnescapedCharacters.set('+');
		pcharUnescapedCharacters.set(',');
		pcharUnescapedCharacters.set(';');
		pcharUnescapedCharacters.set('=');      

		// allowable from general delimiters set
		pcharUnescapedCharacters.set(':');
		pcharUnescapedCharacters.set('@');
	
		
		// set up fragmentUnescapedCharacters - a superset of pchar
		fragmentUnescapedCharacters = (BitSet) pcharUnescapedCharacters.clone();
		fragmentUnescapedCharacters.set('/');
		fragmentUnescapedCharacters.set('?');

		// set up queryUnescapedCharacters - in ABNF, is the same as frag
		// but have to remove a couple character to follow key-value pair convention
		// (the ABNF allows those, but they have a reserved purpose within the query part
		// of the URL that the ABNF does not define
		queryUnescapedCharacters = (BitSet) fragmentUnescapedCharacters.clone();
		queryUnescapedCharacters.clear('=');
		queryUnescapedCharacters.clear('&');
	}


	/**
	 * Encode a URL path segment as required by the URL specifications (<a href="http://tools.ietf.org/html/rfc3986">
	 * RFC 3986</a> and <a href="http://www.ietf.org/rfc/rfc1738#section-3.3"> RFC 1738 section 3.3</a>). 
	 * This differs from <code>java.net.URLEncoder.encode()</code> which encodes according
	 * to the <code>x-www-form-urlencoded</code> MIME format.
	 *
	 * @param segmentString: the http URL path segment to encode 
	 * @return the URL-path-segment-safe UTF-8 encoded string
	 */
	public static String encodeUrlPathSegment(String segmentString) {
		return encodeString(pcharUnescapedCharacters, segmentString);
	}

	/**
	 * Encode a URL query segment following the item-list / key-value convention:
	 *   query = "?" q-item *( "&" q-item)
	 *   q=item = q-segment [ "=" q-segment ]
	 *   
	 *   @param segmentString: the http URL query segment to encode
	 *   @return the URL-query-segment-safe UTF-8 encoded string
	 */
	public static String encodeUrlQuerySegment(String segmentString) {
		return encodeString(queryUnescapedCharacters, segmentString);
	}

	/**
	 * Encode the URL fragment section following <a href="http://tools.ietf.org/html/rfc3986">
	 * RFC 3986</a>.  
	 * 
	 * @param fragmentString
	 * @return the URL-fragment-safe UTF-8 encoded string
	 */
	public static String encodeUrlFragment(String fragmentString) {
		return encodeString(fragmentUnescapedCharacters, fragmentString);
	}
		
	
	/**
	 * Encode a path segment as required by the URL specification (<a href="http://www.ietf.org/rfc/rfc3986.txt">
	 * RFC 3986</a>). This differs from <code>java.net.URLEncoder.encode()</code> which encodes according
	 * to the <code>x-www-form-urlencoded</code> MIME format.
	 *
	 * @param idString the identifier to encode (operating as a segment, according to the ABNF)
	 * @return the URL-safe UTF-8 encoded identifier
	 */
	private static String encodeString(BitSet unescapedSet, String idString) {
		// replaced StringBuffer with StringBuilder, as per recommendation in javadocs, for higher performance


		int maxBytesPerChar = 10;
		StringBuilder rewrittenPathSegment = new StringBuilder(idString.length());
		ByteArrayOutputStream buf = new ByteArrayOutputStream(maxBytesPerChar);
		OutputStreamWriter writer;
		try {
			writer = new OutputStreamWriter(buf, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			// probably don't want to continue
			writer = new OutputStreamWriter(buf);
		}

		for (int i = 0; i < idString.length(); i++) {
			int c = idString.charAt(i);
			if (unescapedSet.get(c)) {
				rewrittenPathSegment.append((char)c);
			} else {
				// convert to external encoding (UTF-8) before hex conversion
				try {
					writer.write(c);
					writer.flush();
				} catch(IOException e) {
					buf.reset();
					continue;
				}
				byte[] ba = buf.toByteArray();
				for (int j = 0; j < ba.length; j++) {
					// Converting each byte in the buffer
					byte toEncode = ba[j];
					rewrittenPathSegment.append('%');
					int low = (toEncode & 0x0f);
					int high = ((toEncode & 0xf0) >> 4);
					rewrittenPathSegment.append(hexadecimal[high]);
					rewrittenPathSegment.append(hexadecimal[low]);
				}
				buf.reset();
			}
		}
		return rewrittenPathSegment.toString();
	}

	/**
	 * Decode and return the specified pct-encoded String.  UTF-8 encoding
	 * is assumed.  For compatibility with older encoders, '+' characters
	 * in the input String will be decoded as spaces.  Has no effect on 
	 * 
	 * @param string
	 * @return the decoded string 
	 * @throws UnsupportedEncodingException
	 * @exception IllegalArgumentException if a '%' character is not followed
	 * by a valid 2-digit hexadecimal number
	 */
	public static String decodeString(String string) throws UnsupportedEncodingException 
	{
		// a string containing a '+' should not reach here
		// but if it does, don't want to fail
		string = string.replace("+", "%2B");
		return URLDecoder.decode(string,"UTF-8");
	}
	
	
	
	// TODO: this was built speculatively, and might not be used, so consider removing
	//   (there are no tests for it)
	public static String decodeXmlDataItems(String dataString)
	{
		String decodedString;
		
		decodedString = dataString.replaceAll("&gt;",">");
		decodedString = decodedString.replaceAll("&lt;","<");
		decodedString = decodedString.replaceAll("&amp;","&");
		decodedString = decodedString.replaceAll("&apos;","'");
		decodedString = decodedString.replaceAll("&quot;","\"");
		
		return decodedString;
	}
	
	
	
	
}