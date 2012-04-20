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

import java.math.BigInteger;
import org.jibx.runtime.JiBXException;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author waltz
 */
public class BigIntegerMashallerTestCase {

    @Test
    public void deserializeBigIntegerTestSuccess() {
        String someBigNumber = "12345123451234512345";
        BigInteger someBigInteger = null;
        try {
            someBigInteger = BigIntegerMarshaller.deserializeBigInteger(someBigNumber);
        } catch (JiBXException ex) {
           ex.printStackTrace();
           fail(ex.getMessage());
        }
        String anotherBigNumber = "0";
        try {
            anotherBigNumber = BigIntegerMarshaller.serializeBigInteger(someBigInteger);
        } catch (JiBXException ex) {
           ex.printStackTrace();
           fail(ex.getMessage());
        }
        assertTrue(someBigNumber.equals(anotherBigNumber));
    }
    @Test
    public void deserializeBigIntegerTestFailures() {
        String someBigNumber = "512345123451234512345";

        BigInteger someBigInteger = null;
        boolean failed = false;
        try {
            someBigInteger = BigIntegerMarshaller.deserializeBigInteger(someBigNumber);
        } catch (JiBXException ex) {
           assertTrue(ex.getMessage().contains("unsignedLong must be greater"));
           failed = true;
        }
        assertTrue(failed);
        failed = false;
        someBigInteger = BigInteger.valueOf(Long.MAX_VALUE);
        someBigInteger = someBigInteger.multiply(someBigInteger);

        try {
            someBigNumber = BigIntegerMarshaller.serializeBigInteger(someBigInteger);
        } catch (JiBXException ex) {
           assertTrue(ex.getMessage().contains("unsignedLong must be greater"));
           failed = true;
        }

        failed = false;
        String someNegativeNumber = "-1";
        try {
            someBigInteger = BigIntegerMarshaller.deserializeBigInteger(someNegativeNumber);
        } catch (JiBXException ex) {
           assertTrue(ex.getMessage().contains("unsignedLong must be greater"));
           failed = true;
        }
        assertTrue(failed);

        failed = false;
        someBigInteger = BigInteger.valueOf(-1L);
        try {
            someBigNumber = BigIntegerMarshaller.serializeBigInteger(someBigInteger);
        } catch (JiBXException ex) {
           assertTrue(ex.getMessage().contains("unsignedLong must be greater"));
           failed = true;
        }
        assertTrue(failed);
    }
}
