/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.service.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author waltz
 */
public class DateTimeMarshallerTestCase {
    @Test
    public void deserializeDateToUTCTest()  {

        boolean nullPointerThrown = false;
        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("1985-102T23:50:30"));

        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("Sun, 06 Nov 1994 08:49:37 GMT"));

        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("Sunday, 06-Nov-94 08:49:37 GMT"));

        assertNotNull(DateTimeMarshaller.deserializeDateToUTC("Sun Nov 6 08:49:37 1994"));

        try {
            DateTimeMarshaller.deserializeDateToUTC("Sun Nov 6 08:49:37");
        } catch (NullPointerException ex) {
            nullPointerThrown = true;
        }

        assertTrue(nullPointerThrown);
    }
}
