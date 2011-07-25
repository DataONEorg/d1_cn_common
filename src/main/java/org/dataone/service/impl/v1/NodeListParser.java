package org.dataone.service.impl.v1;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dataone.service.types.v1.Node;
import org.dataone.service.types.v1.NodeList;
import org.dataone.service.types.util.TypeMarshaller;
import org.jibx.runtime.JiBXException;


/**
 * @author berkley, rwaltz
 * A parser to deal with the NodeList
 *
 * Convenience method of transferring nodeId and nodeBaseURL into a HashMap
 */
public class NodeListParser
{
    
    public static Map<String, String> parseNodeListFile(InputStream nodeListStream)
        throws InstantiationException, IllegalAccessException, JiBXException, IOException
    {
       // method originally used Hashtable. I've substituted ConcurrentHashMap to
       // allow for thread safe operations and to increase performance
       ConcurrentHashMap<String, String> baseUrlMap = new ConcurrentHashMap<String, String>();
        NodeList nodeList = TypeMarshaller.unmarshalTypeFromStream(NodeList.class, nodeListStream);
        for (Node node : nodeList.getNodeList()) {
            baseUrlMap.put(node.getIdentifier().getValue(), node.getBaseURL());
        }
        return baseUrlMap;
    }
 
}
