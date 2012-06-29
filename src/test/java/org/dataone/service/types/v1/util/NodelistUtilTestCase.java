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

package org.dataone.service.types.v1.util;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dataone.service.types.v1.Node;
import org.dataone.service.types.v1.NodeList;
import org.dataone.service.types.v1.NodeState;
import org.dataone.service.types.v1.NodeType;
import org.dataone.service.util.TypeMarshaller;
import org.jibx.runtime.JiBXException;
import org.junit.Test;


/**
 * test the NodelistUtil class
 * 
 * @author rnahf
 */
public class NodelistUtilTestCase
{
	private static Log log = LogFactory.getLog(NodelistUtilTestCase.class); 
   
	
    /**
     * tests NodelistUtil.parseNodeListFile()
     */
	@Test
    public final void testMapNodeList()
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

            assertTrue("knb-mn key", m.containsKey("urn:node:d1m2"));
            assertTrue("knb-mn value", m.get("urn:node:d1m2").equals("http://knb-mn.ecoinformatics.org/knb/d1/"));
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	fail("unexpected exception in testParseNodeListFile: " + e.getMessage());
        }
    }

    @Test
    public void testSelectNode_comparator() throws InstantiationException, IllegalAccessException, JiBXException, IOException {
    	
        InputStream is = this.getClass().getResourceAsStream("/org/dataone/service/samples/v1/nodeListSample2.xml");   
        NodeList nodeList = TypeMarshaller.unmarshalTypeFromStream(NodeList.class, is);
    	
    	Set<Node> nodeSet = NodelistUtil.selectNodes(nodeList, NodeType.MN);
    	Set<Node> nodeSet2 = NodelistUtil.selectNodes(nodeList, NodeState.UP);
    	Set<Node> nodeSet3 = NodelistUtil.selectNodesByService(nodeList, "MNCore","v1",true);
    	assertTrue("returned object should not be null", nodeSet != null);
    	assertTrue("returned object should not be null", nodeSet2 != null);
    	assertTrue("returned object should not be null", nodeSet3 != null);
    	
    }
}
