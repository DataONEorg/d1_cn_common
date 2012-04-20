/**
 * This work was created by participants in the DataONE project, and is
 * jointly copyrighted by participating institutions in DataONE. For 
 * more information on DataONE, see our web site at http://dataone.org.
 *
 *   Copyright ${year}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 * 
 * $Id$
 */

package org.dataone.service.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

/**
 * This is a special purpose subclass of PushbackInputStream
 * useful for determining whether or not an input stream
 * contains a Dataone type, a Dataone exception, or other.
 * 
 * Performance-wise, there is only buffering done on the first 
 * lookAhead read to determine the contents of the stream.
 *  
 * 
 * @author rnahf
 *
 */
public class ExceptionalInputStream extends PushbackInputStream {
	private final static String D1_XML_ERROR = "<error";
	private final static String D1_TYPE_INDICATOR = "xmlns:d1=\"http://ns.dataone.org/service/types";
	private static int lookAheadBytes = 1000;
	private static int lookAheadIncrement = 50;

	private Boolean isException = null;
	private int bracketCount = 0;
	
	public ExceptionalInputStream(InputStream inputStream) {
		super(inputStream,lookAheadBytes);
	}


	public boolean isException() throws IOException {

		if (isException == null) {
			byte[] b = new byte[lookAheadBytes];
			int totalRead = read(b,0,5);
			if (totalRead <1) {
				isException = new Boolean(false);
				return isException.booleanValue();
			}
			
			String readString = new String(b,"UTF-8");
			if (readString.startsWith(D1_XML_ERROR)) {
				isException = new Boolean(true);
			} else {
				isException = new Boolean(true);
				int newlyRead = 0;
				while (newlyRead != -1 && bracketCount < 2 && totalRead < lookAheadBytes - lookAheadIncrement) {
					newlyRead = read(b,totalRead,lookAheadIncrement);
					if (newlyRead > -1) {
						totalRead += newlyRead;
						readString = new String(b,"UTF-8");
						if (readString.contains(D1_TYPE_INDICATOR)) {
							isException = new Boolean(false);
							break;
						}
						if (readString.contains(">")) {
							bracketCount++;
						}
					}
				}
			}
			unread(b,0,totalRead);
		}
		return isException.booleanValue();
	}
}
