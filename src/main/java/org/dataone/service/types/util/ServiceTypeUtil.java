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
package org.dataone.service.types.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dataone.service.types.Checksum;
import org.dataone.service.types.ChecksumAlgorithm;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author berkley
 * Service Type utility methods
 */
@Deprecated
public class ServiceTypeUtil {

    static final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
    static final DateTimeFormatter zFmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    /**
     * serialize an object of type to out
     * @param type the class of the object to serialize (i.e. SystemMetadata.class)
     * @param object the object to serialize
     * @param out the stream to serialize it to
     * @throws JiBXException
     */
    public static void serializeServiceType(Class type, Object object, OutputStream out)
            throws JiBXException {
        IBindingFactory bfact = BindingDirectory.getFactory(type);
        IMarshallingContext mctx = bfact.createMarshallingContext();
        mctx.marshalDocument(object, "UTF-8", null, out);
    }

    /**
     * deserialize an object of type from is
     * @param type the class of the object to serialize (i.e. SystemMetadata.class)
     * @param is the stream to deserialize from
     * @throws JiBXException
     */
    public static Object deserializeServiceType(Class type, InputStream is)
            throws JiBXException {
        IBindingFactory bfact = BindingDirectory.getFactory(type);
        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
        Object o = (Object) uctx.unmarshalDocument(is, null);
        return o;
    }

    /**
     * return a checksum based on the input of the stream
     * @param is
     * @param algorithm
     * @return
     * @throws NoSuchAlgorithmException 
     * @throws IOException 
     */
    public static Checksum checksum(InputStream is, ChecksumAlgorithm algorithm) throws NoSuchAlgorithmException, IOException {
        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance(algorithm.toString());
        int numRead;

        do {
            numRead = is.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        String csStr = getHex(complete.digest());
        Checksum checksum = new Checksum();
        checksum.setValue(csStr);
        checksum.setAlgorithm(algorithm);
        return checksum;
    }

    /**
     * convert a byte array to a hex string
     */
    private static String getHex(byte[] raw) {
        final String HEXES = "0123456789ABCDEF";
        if (raw == null) {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

    /**
     * convert a date to GMT
     *
     * @param d
     * @return dateTime string that is ISO 8601 compliant
     */
    public static String serializeDateToUTC(Date d) {
        DateTime dt = new DateTime(d);
        DateTime dtUTC = dt.withZone(DateTimeZone.UTC);
        return fmt.print(dtUTC);

    }


    /**
     * convert a String to GMT date
     * The STring must either be ISO 8601 compliant or
     * http://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.3.1 compliant
     * @param d
     * @return date in UTC
     */
    public static Date deserializeDateToUTC(String dt) {
        // if the string can not be parsed, then a Null Pointer Exception will be thrown
        DateTime dateTime = null;
        if (Character.isDigit(dt.charAt(0))) {
            // Assume it is ISO 8601 compliant
            dateTime = new DateTime(dt);

        } else {
            if (Character.isSpaceChar(dt.charAt(3))) {
                // it better be a string that looks something like
                // Sun Nov  6 08:49:37 1994       ; ANSI C's asctime() format
                SimpleDateFormat format = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy");
                try {
                    dateTime = new DateTime(format.parse(dt));
                } catch (ParseException ex) {
                    dateTime = null;
                }
            } else if (Character.isLetter(dt.charAt(3))) {
                // it better be a string that looks something like
                // Sunday, 06-Nov-94 08:49:37 GMT ; RFC 850, obsoleted by RFC 1036
                SimpleDateFormat format = new SimpleDateFormat("EEEE, dd-MMM-yy HH:mm:ss zzz");
                try {
                    dateTime = new DateTime(format.parse(dt));
                } catch (ParseException ex) {
                    dateTime = null;
                }
            } else {
                // it better be a string that looks something like
                // Sun, 06 Nov 1994 08:49:37 GMT  ; RFC 822, updated by RFC 1123
                SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
                try {
                    dateTime = new DateTime(format.parse(dt));
                } catch (ParseException ex) {
                    dateTime = null;
                }
            }
        }
        dateTime = dateTime.withZone(DateTimeZone.UTC);
        return dateTime.toDate();
    }
}
