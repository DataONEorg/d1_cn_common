package org.dataone.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.Test;


/**
 * test the NodeListParser class
 * 
 * @author berkley
 */
public class NodeListParserTest 
{
    private static final String TEST_CN_URL = "http://cn-dev.dataone.org/cn/";
    
    @Test
    /**
     * tests NodeListParser.parseNodeListFile()
     */
    public final void testParseNodeListFile()
    {
        try
        {
            URL url = new URL(TEST_CN_URL + "node");
            InputStream is = url.openStream();
            String nodeDoc = IOUtils.toString(is);
            
            assertTrue("Node document null.", (nodeDoc != null));
            assertTrue("Node document has 0 content", nodeDoc.length() > 0);
            System.out.println(nodeDoc);
            
            InputStream ndIs = IOUtils.toInputStream(nodeDoc);
            Map<String, String> m = NodeListParser.parseNodeListFile(ndIs);

            assertTrue("knb-mn key", m.containsKey("http://knb-mn.ecoinformatics.org"));
            assertTrue("knb-mn value", m.get("http://knb-mn.ecoinformatics.org").equals("http://knb-mn.ecoinformatics.org/knb/d1/"));
        }
        catch(Exception e)
        {
            fail("unexpected exception in testParseNodeListFile: " + e.getMessage());
        }
    }
}
