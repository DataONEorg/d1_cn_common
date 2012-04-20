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

import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.DateTime;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author waltz
 */
public class DateTimeMarshallerTestCase {

    @Test
    public void deserializeDateToUTCTest() {

        boolean nullPointerThrown = false;
        // Combinations of calendar date and local time No TimeZones
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("19910806T145620"));// Basic format
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("19910806T145620.123"));
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("1991-08-06T14:56:20"));// Extended format
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("1991-08-06T14:56:20.123"));


        // Combinations of calendar date and local time with timezone no milliseconds
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("19910806T155620+0100"));// Basic format
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("19910806T155620+01:00"));// Basic format
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("19910806T095620-0500"));// Basic format
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("19910806T095600-05:00"));// Basic format
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("1991-08-06T15:56:20+0100"));// Extended format
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("1991-08-06T15:56:20+0100"));// Extended format
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("1991-08-06T09:56:20-05:00"));// Extended format
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("1991-08-06T09:56:20-0500"));// Extended format



        // Combinations of calendar date and local time with milliseconds and timezone
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("19910806T155620.123+01:00"));
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("19910806T155620.123+0100"));
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("19910806T095620.123-05:00"));
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("19910806T095620.123-0500"));
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("1991-08-06T15:56:20.123+01:00"));
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("1991-08-06T15:56:20.123+0100"));
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("1991-08-06T09:56:20.123-05:00"));
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("1991-08-06T09:56:20.123-0500"));

        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("Tue, 06 Aug 1991 14:56:20 GMT"));

        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("Tuesday, 06-Aug-91 14:56:20 GMT"));

        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("Tue Aug 6 14:56:20 1991"));

        try {
            DateTimeMarshaller.deserializeDateToUTC("Tue Aug 6 14:56:20");
        } catch (NullPointerException ex) {
            nullPointerThrown = true;
        }

        assertTrue(nullPointerThrown);
    }
}
