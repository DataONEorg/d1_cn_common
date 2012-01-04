package org.dataone.service.types.v1.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.dataone.service.types.v1.Node;
import org.dataone.service.types.v1.NodeList;
import org.dataone.service.types.v1.NodeState;
import org.dataone.service.types.v1.NodeType;
import org.dataone.service.types.v1.Service;
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
public class NodelistUtil {

    /**
     * Convenience method of transferring nodeId and nodeBaseURL into a Map
     *
     * @author berkley
     * @author waltz
     * @param nodeListStream
     * @return Map
     * @exception InstantiationException
     * @exception IOException
     * @exception IllegalAccessException
     * @exception JiBXException
     */
    public static Map<String, String> mapNodeList(InputStream nodeListStream)
            throws InstantiationException, IllegalAccessException, JiBXException, IOException {
        NodeList nodeList = TypeMarshaller.unmarshalTypeFromStream(NodeList.class, nodeListStream);
        return mapNodeList(nodeList);
    }

    /**
     * Convenience method of transferring nodeId and nodeBaseURL into a Map
     *
     * @author berkley
     * @author waltz
     * @param nodeList
     * @return Map
     * @exception InstantiationException
     * @exception IOException
     * @exception IllegalAccessException
     * @exception JiBXException
     */
    public static Map<String, String> mapNodeList(NodeList nodeList) {
        // method originally used Hashtable. I've substituted ConcurrentHashMap to
        // continue thread safe operations and, additionally, to increase performance
        ConcurrentHashMap<String, String> baseUrlMap = new ConcurrentHashMap<String, String>();
        for (Node node : nodeList.getNodeList()) {
            baseUrlMap.put(node.getIdentifier().getValue(), node.getBaseURL());
        }
        return baseUrlMap;
    }
 
    
    /**
     * Finds the nodes in the NodeList that match the provided NodeType. 
     * @see org.dataone.service.types.v1.Node.getType() 
     * A set is returned to allow set logic with other find methods.
     * @param nodeList - the dataONE NodeList to search in for matching nodes
     * @param nodeType - if null, returns all nodes, otherwise
     * @return a set of Nodes that match the criteria
     */
    public static Set<Node> findNodes(NodeList nodeList, NodeType nodeType) 
    {
		Set<Node> nodeSet = new TreeSet<Node>();
		for(int i=0; i < nodeList.sizeNodeList(); i++) {
			Node node = nodeList.getNode(i);
			if (nodeType == null) {
				nodeSet.add(node);
			} else if (node.getType() == nodeType) {
				nodeSet.add(node);
			} 
		}		
		return nodeSet;
    }
    
    /**
     * Finds the nodes in the NodeList that are in the provided state. 
     * @see org.dataone.service.types.v1.Node.getState() 
     * A set is returned to allow set logic with other find methods.
     * @param nodeList - the dataONE NodeList to search in for matching nodes
     * @param nodeState - if null, returns all nodes, otherwise
     * @return a set of Nodes that match the criteria
     */
    public static Set<Node> findNodes(NodeList nodeList, NodeState nodeState) 
    {
		Set<Node> nodeSet = new TreeSet<Node>();
		for(int i=0; i < nodeList.sizeNodeList(); i++) {
			Node node = nodeList.getNode(i);
			if (nodeState == null) {
				nodeSet.add(node);
			} else if (node.getState() == nodeState) {
				nodeSet.add(node);
			}
		}		
		return nodeSet;
    }    

    /**
     * Finds the nodes in the NodeList that contain services that match the 
     * provided serviceName and availability.
     * (@see org.dataone.service.types.v1.Service.getName() for available values)
     * @param nodeList - the dataONE NodeList to search in for matching nodes
     * @param serviceName - the name of the service to find, case-insensitive
     * @param version - the name of the version to find, case-insensitive.  If null, criteria is ignored
     * @param isAvailable - if true, will return only nodes where available value is true or null
     *                    - if false, will return only nodes where available value is false
     * @return
     */
    public static Set<Node> findNodesByService(NodeList nodeList, String serviceName, String version, boolean isAvailable)
    {
    	Set<Node> nodeSet = new TreeSet<Node>();
    	for(int i=0; i < nodeList.sizeNodeList(); i++) 
    	{
    		Node node = nodeList.getNode(i);
    		for (Service service: node.getServices().getServiceList())
    		{
    			if (service.getName().equalsIgnoreCase(serviceName)) {
    				boolean availability = true;
    				if (service.getAvailable() != null) {
    					availability = service.getAvailable().booleanValue();
    				}
    				if (availability == isAvailable) {
    					if (version != null) {
    						if (service.getVersion().equalsIgnoreCase(version)) {
    							nodeSet.add(node);
    						}
    					} else {
    						nodeSet.add(node);
    					}
    				}
    			}
    		}
        }
		return nodeSet;
	}
}

