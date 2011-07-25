/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.service.types.v1;


import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author waltz
 */
public class JavaEnumCompatibilityTestCase {

    @Test
    public void checksumTest() {
        ChecksumAlgorithm checksumAlgorithm = ChecksumAlgorithm.MD5;
        assertTrue(checksumAlgorithm.toString().contentEquals(checksumAlgorithm.xmlValue()));

    }
}
