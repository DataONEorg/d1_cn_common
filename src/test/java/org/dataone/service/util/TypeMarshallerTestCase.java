/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.service.util;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.dataone.service.types.v1.Node;
import org.dataone.service.types.v1.ObjectList;
import org.dataone.service.types.v1.SystemMetadata;
import org.jibx.runtime.JiBXException;
import org.junit.Ignore;
import org.junit.Test;


/**
 *
 * @author waltz
 */
public class TypeMarshallerTestCase {
	
	private static Logger log = Logger.getLogger(TypeMarshallerTestCase.class);

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
    @Test
    public void deserializeNode() {
        try {
            InputStream is = this.getClass().getResourceAsStream("/org/dataone/service/samples/v1/mnNode1.xml");
            Node node = TypeMarshaller.unmarshalTypeFromStream(Node.class, is);
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
    @Test
    public void serializeEmptyObjectList() {
        ObjectList objectList = new ObjectList();
        assertNotNull(objectList);
        assertNotNull(objectList.getObjectInfoList());
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            TypeMarshaller.marshalTypeToOutputStream(objectList, os);
            String xmlObjectList = os.toString();
            assertNotNull(xmlObjectList);
        } catch (IOException ex) {
            fail("Test misconfiguration" +  ex);
        } catch (JiBXException ex) {
            fail("Test misconfiguration" +  ex);
        }
    }

    @Test
    public void deserializeEmptyObjectListSize() {
        try {
            InputStream is = this.getClass().getResourceAsStream("/org/dataone/service/samples/v1/objectListSample2.xml");
            ObjectList objectList = TypeMarshaller.unmarshalTypeFromStream(ObjectList.class, is);
            assertNotNull(objectList);
            assertNotNull(objectList.sizeObjectInfoList());
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
    
    @Test
    public void serializeNodeStylesheet() {
        try {
            InputStream is = this.getClass().getResourceAsStream("/org/dataone/service/samples/v1/mnNode1.xml");
            Node node = TypeMarshaller.unmarshalTypeFromStream(Node.class, is);
            String styleSheet = "test.xsl";
            ByteArrayOutputStream os = new ByteArrayOutputStream();
			TypeMarshaller.marshalTypeToOutputStream(node, os , styleSheet);
			String result = os.toString("UTF-8");
			log.debug("Stylesheet result: \n" + result);
			assertTrue(result.contains(styleSheet));
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
