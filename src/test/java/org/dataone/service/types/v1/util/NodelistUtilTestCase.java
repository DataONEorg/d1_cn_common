package org.dataone.service.types.v1.util;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;


/**
 * test the NodeListParser class
 * 
 * @author berkley
 */
public class NodelistUtilTestCase
{
	private static Log log = LogFactory.getLog(NodelistUtilTestCase.class); 
    @Test
    /**
     * tests NodeListParser.parseNodeListFile()
     */
    public final void testParseNodeListFile()
    {
        try
        {

            InputStream is = this.getClass().getResourceAsStream("/org/dataone/service/samples/v1/nodeListSample2.xml");
            String nodeDoc = IOUtils.toString(is);
            
            assertTrue("Node document null.", (nodeDoc != null));
            assertTrue("Node document has 0 content", nodeDoc.length() > 0);
            log.info(nodeDoc);
            
            InputStream ndIs = IOUtils.toInputStream(nodeDoc);
            Map<String, String> m = NodelistUtil.mapNodeList(ndIs);

            assertTrue("knb-mn key", m.containsKey("d1m2"));
            assertTrue("knb-mn value", m.get("d1m2").equals("http://knb-mn.ecoinformatics.org/knb/d1/"));
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	fail("unexpected exception in testParseNodeListFile: " + e.getMessage());
        }
    }

}
