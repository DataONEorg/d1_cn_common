package org.dataone.service.exceptions;

import java.util.TreeMap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Base exceptions
 */
public class BaseExceptionTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public BaseExceptionTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( BaseExceptionTest.class );
    }
    
    /**
     * Test creation of an exception, and serialization of the fields into XML.
     */
    public void testSerializeXML()
    {
        String msg = "The specified object does not exist on this node.";
        TreeMap<String, String> trace = new TreeMap<String, String>();
        trace.put("identifier", "123XYZ");
        trace.put("method", "mn.get");
        BaseException e = new BaseException(404, 14001, msg, trace);
        assertNotNull(e);
        
        String xml = e.serialize(BaseException.FMT_XML);
        System.out.println(xml);
        
        assertNotNull(xml);
        assertTrue(xml.indexOf("<error") != -1);
        assertTrue(xml.indexOf("'404'") != -1);
        assertTrue(xml.indexOf("'14001'") != -1);
        assertTrue(xml.indexOf(msg) != -1);
        assertTrue(xml.indexOf("identifier") != -1);
        assertTrue(xml.indexOf("123XYZ") != -1);
        assertTrue(xml.indexOf("method") != -1);
        assertTrue(xml.indexOf("mn.get") != -1);
    }
}
