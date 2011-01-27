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

import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;

import org.dataone.service.types.Checksum;
import org.dataone.service.types.ChecksumAlgorithm;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

/**
 * @author berkley
 * Service Type utility methods
 */
public class ServiceTypeUtil
{
    /**
     * serialize an object of type to out
     * @param type the class of the object to serialize (i.e. SystemMetadata.class)
     * @param object the object to serialize
     * @param out the stream to serialize it to
     * @throws JiBXException
     */
    public static void serializeServiceType(Class type, Object object, OutputStream out)
      throws JiBXException
    {
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
      throws JiBXException
    {
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
     * @throws Exception
     */
    public static Checksum checksum(InputStream is, ChecksumAlgorithm algorithm)
        throws Exception
    {        
        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance(algorithm.toString());
        int numRead;

        do 
        {
            numRead = is.read(buffer);
            if (numRead > 0) 
            {
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
    private static String getHex( byte [] raw ) 
    {
        final String HEXES = "0123456789ABCDEF";
        if ( raw == null ) {
          return null;
        }
        final StringBuilder hex = new StringBuilder( 2 * raw.length );
        for ( final byte b : raw ) {
          hex.append(HEXES.charAt((b & 0xF0) >> 4))
             .append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }
}
