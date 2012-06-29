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

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.dataone.service.types.v1.Node;
import org.dataone.service.types.v1.NodeList;
import org.dataone.service.types.v1.NodeReference;
import org.dataone.service.types.v1.NodeState;
import org.dataone.service.types.v1.NodeType;
import org.dataone.service.types.v1.Service;
import org.dataone.service.types.v1.Subject;
import org.dataone.service.util.TypeMarshaller;
import org.jibx.runtime.JiBXException;

/**
 *
 * Convenience methods for common ways of accessing nodes and node information 
 * in the given nodelist.  The selectNode methods return Set objects to allow further
 * set logic between multiple selects results.
 * 
 * For more powerful querying of this complex data type, use of apache.commons.
 * CollectionUtils is recommended.  
 * For example: the findNode method could be also accomplished with the following:
 * 
 *         CollectionUtils.find(nodeList.getNodeList(),
 *                  new Predicate() 
 *                  {
 * 						public boolean evaluate(Object o) {
 * 							Node node = (Node) o;
 * 							return n.getIdentifier() == nodeRef;
 * 						}
 *                  }
 *              );
 *
 * @author berkley
 * @author waltz
 * @author rnahf
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
     * Returns the first node with identifier of the provided node reference
     * @param nodeList - the nodeList to be searched
     * @param nodeRef - the node reference for the node of interest
     * @return  the matching Node, or null if not found
     */
    public static Node findNode(NodeList nodeList, NodeReference nodeRef)
    {
    	Node node = null;
    	for(int i=0; i < nodeList.sizeNodeList(); i++) 
    	{
    		node = nodeList.getNode(i);
    		if (node.getIdentifier() == nodeRef) {
    			break;
    		}
    		node = null;
    	}
    	return node;
    }
    
    /**
     * Finds the nodes in the NodeList that contain the provided Subject.
     * Not to be confused with any subject contained in the 
     * ServiceMethodRestriction field
     * A set is returned to allow set logic with other select methods
     * @param nodeList - the nodeList to be searched
     * @param nodeRef - the node reference for the node of interest
     * @return a set of Nodes that match the criteria
     */
    public static Set<Node> selectNode(NodeList nodeList, Subject subject)
    {
    	Set<Node> nodeSet = new HashSet<Node>();
    	for(int i=0; i < nodeList.sizeNodeList(); i++) 
    	{
    		Node node = nodeList.getNode(i);
    		if (node.getSubjectList().contains(subject)) {
    			nodeSet.add(node);
    		}
    	}
    	return nodeSet;
    }
    
    
    /**
     * Finds the nodes in the NodeList that match the provided NodeType. 
     * A set is returned to allow set logic with other select methods.
     * @param nodeList - the dataONE NodeList to search in for matching nodes
     * @param nodeType - if null, returns all nodes, otherwise
     * @return a set of Nodes that match the criteria
     */
    public static Set<Node> selectNodes(NodeList nodeList, NodeType nodeType) 
    {
    	Set<Node> nodeSet = new HashSet<Node>();
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
     * A set is returned to allow set logic with other select methods.
     * @param nodeList - the dataONE NodeList to search in for matching nodes
     * @param nodeState - if null, returns all nodes, otherwise
     * @return a set of Nodes that match the criteria
     */
    public static Set<Node> selectNodes(NodeList nodeList, NodeState nodeState) 
    {
    	Set<Node> nodeSet = new HashSet<Node>();
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
     * A set is returned to allow set logic with other select methods.
     * @param nodeList - the dataONE NodeList to search in for matching nodes
     * @param serviceName - the name of the service to find, case-insensitive
     * @param version - the name of the version to find, case-insensitive.  If null, criteria is ignored
     * @param isAvailable - if true, will return only nodes where available value is true or null
     *                    - if false, will return only nodes where available value is false
     * @return a set of Nodes that match the criteria
     */
    public static Set<Node> selectNodesByService(NodeList nodeList, String serviceName, String version, boolean isAvailable)
    {
    	Set<Node> nodeSet = new HashSet<Node>();
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
    						break;
    					}
    				}
    			}
    		}
        }
		return nodeSet;
	}
    
 // TODO: cannot finish these until ruling on servicemethodrestriction
    // implementation   
//    /**
//     * A method is callable by a subject if there either is no service restriction on
//     * the method, or if the method is restricted but the subject is on the 
//     * allowed subject list.  There can be multiple entries for a method, so will
//     * need to check them all. 
//     * @param node
//     * @param methodName
//     * @param subject
//     * @param version
//     * @return
//     */
//    public static boolean checkMethodIsCallable(Node node, String methodName, 
//    		Subject subject, String version) 
//    {
//    	boolean callable = true;
//    	outer:
//    		for (Service service: node.getServices().getServiceList())
//    		{
//    			for (ServiceMethodRestriction restrictedTo: service.getRestrictionList())
//    			{
//    				// if the method is restricted and the subject is NOT on the allowed list...
//    				if (restrictedTo.getMethodName().equals(methodName) 
//    						&& !restrictedTo.getSubjectList().contains(subject)) {
//						
//    					// and if we don't care about version...
//    					if (version == null) {
//    						callable = false;
//    						break outer;
//    					} else if (service.getVersion().equalsIgnoreCase(version)) {
//    						callable = false;
//    						break outer;
//    					}
//    				}
//    			}
//    		}
//    	return callable;
//    }
// 
//   
//    /**
//     * Enforces business rules about the form of the content in the Node.
//     * Currently, makes sure that all versions of a service have the same 
//     * ServiceMethodRestriction entries.
//     * @param node
//     * @return
//     * @throws InvalidRequest
//     */
//    public static boolean validateNodeContent(Node node) throws InvalidRequest 
//    {
//		// group services by name && create parallel map for each service's restriction lists
//    	HashMap<String,Service[]> serviceMap = new HashMap<String,Service[]>();
//    	HashMap<String,List[]> restrictionMap = new HashMap<String,List[]>();
//    	for (Service service: node.getServices().getServiceList())
//		{
//    		String serviceName = service.getName();
//    		if (serviceMap.containsKey(serviceName)) {
//    			serviceMap.get(serviceName)[serviceMap.get(serviceName).length] = service;
//    			restrictionMap.get(serviceName)[serviceMap.get(serviceName).length] = 
//    				service.getRestrictionList();
//    		} else {
//    			serviceMap.put(serviceName,new Service[]{service});
//    			restrictionMap.put(serviceName,new List[]{service.getRestrictionList()});
//    		}
//		}
//    	
//    	// validate restrictionlists within each subset of services
//    	for (String serviceName : serviceMap.keySet()) {
//    		// 
//    		Set methodRestrictions = null;
//    		for (int i=0; i < serviceMap.get(serviceName).length; i++) {
//    			
//    			if (methodRestrictions == null) {
//    				// first time through, so no comparison
// //   				methodRestrictions = new TreeSet(restrictionMap.get(serviceName));
//    			} else {
//    				// compare to methodRestrictions;
//    			}
//    		}
//    	}
//    	return true;
//    }
//
//    
//    public static boolean validateServiceMethodRestrictions(List<ServiceMethodRestriction>[] restrictionLists)
//    {
//    	Set<Subject> restrictedTo = null;
//    	for (List<ServiceMethodRestriction> versionRestrictions: restrictionLists) {
//    		if (restrictedTo == null) {
//    			restrictedTo = new TreeSet<Subject>(versionRestrictions);
//    		}
//    	}
//    	
//    	
//    	
//    	return true;
//    }
//    
}

