package org.dataone.service;

import java.io.*;
import java.net.*;
import java.util.*;

import static org.junit.Assert.*;
import org.junit.*;


/**
 * test the NodeListParser class
 * 
 * @author berkley
 */
public class NodeListParserTest 
{
    
    @Test
    /**
     * tests NodeListParser.parseNodeListFile()
     */
    public final void testParseNodeListFile()
    {
        try
        {
            URL url = new URL("http://cn.dataone.org/cn/node");
            InputStream is = url.openStream();
            Map m = NodeListParser.parseNodeListFile(is);

            assertTrue("knb-mn key", m.containsKey("http://knb-mn.ecoinformatics.org"));
            assertTrue("knb-mn value", m.get("http://knb-mn.ecoinformatics.org").equals("http://knb-mn.ecoinformatics.org/knb/d1/"));
        }
        catch(Exception e)
        {
            fail("unexpected exception in testParseNodeListFile: " + e.getMessage());
        }
    }
}
