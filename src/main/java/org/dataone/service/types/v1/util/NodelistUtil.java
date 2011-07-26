package org.dataone.service.types.v1.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dataone.service.types.v1.Node;
import org.dataone.service.types.v1.NodeList;
import org.dataone.service.util.TypeMarshaller;
import org.jibx.runtime.JiBXException;


/**
 *
 * Methods for convenient access to Nodelist
 *
 * @author berkley
 * @author waltz
 *
 */
public class NodelistUtil
{
    
    /**
     * Convenience method of transferring nodeId and nodeBaseURL into a Map
     *
     * @author berkley
     * @author waltz
     * @param InputStream
     * @return Map
     * @exception InstantiationException
     * @exception IOException
     * @exception IllegalAccessException
     * @exception JiBXException
     */
    public static Map<String, String> mapNodeList(InputStream nodeListStream)
        throws InstantiationException, IllegalAccessException, JiBXException, IOException
    {
       // method originally used Hashtable. I've substituted ConcurrentHashMap to
       // continue thread safe operations and, additionally, to increase performance
       ConcurrentHashMap<String, String> baseUrlMap = new ConcurrentHashMap<String, String>();
        NodeList nodeList = TypeMarshaller.unmarshalTypeFromStream(NodeList.class, nodeListStream);
        for (Node node : nodeList.getNodeList()) {
            baseUrlMap.put(node.getIdentifier().getValue(), node.getBaseURL());
        }
        return baseUrlMap;
    }
 
}
