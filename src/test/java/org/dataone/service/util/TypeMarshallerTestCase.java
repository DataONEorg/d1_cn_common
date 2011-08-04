/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.service.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dataone.service.types.v1.SystemMetadata;
import org.jibx.runtime.JiBXException;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author waltz
 */
public class TypeMarshallerTestCase {
    @Test
    public void deserializeSystemMetadata() {
        try {
            InputStream is = this.getClass().getResourceAsStream("/org/dataone/service/samples/v1/systemMetadataSample1.xml");
            SystemMetadata systemMetadata = TypeMarshaller.unmarshalTypeFromStream(SystemMetadata.class, is);
        } catch (IOException ex) {
            fail("Test misconfiguration" +  ex);
        } catch (InstantiationException ex) {
            fail("Test misconfiguration" + ex);
        } catch (IllegalAccessException ex) {
            fail("Test misconfiguration" +  ex);
        } catch (JiBXException ex) {
            fail("Test misconfiguration" +  ex);
        }


    }
}
