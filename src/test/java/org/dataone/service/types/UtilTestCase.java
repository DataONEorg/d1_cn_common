/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.service.types;

import org.dataone.service.types.util.ServiceTypeUtil;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author waltz
 */
public class UtilTestCase {
    @Test
    public void deserializeDateToUTCTest()  {

        boolean nullPointerThrown = false;
        assertNotNull(ServiceTypeUtil.deserializeDateToUTC("1985-102T23:50:30"));

        assertNotNull(ServiceTypeUtil.deserializeDateToUTC("Sun, 06 Nov 1994 08:49:37 GMT"));

        assertNotNull(ServiceTypeUtil.deserializeDateToUTC("Sunday, 06-Nov-94 08:49:37 GMT"));

        assertNotNull(ServiceTypeUtil.deserializeDateToUTC("Sun Nov 6 08:49:37 1994"));

        try {
            ServiceTypeUtil.deserializeDateToUTC("Sun Nov 6 08:49:37");
        } catch (NullPointerException ex) {
            nullPointerThrown = true;
        }

        assertTrue(nullPointerThrown);
    }
}
