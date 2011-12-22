package org.dataone.service.types.v1.util;

import java.util.List;

import org.dataone.service.types.v1.Node;
import org.dataone.service.types.v1.NodeType;
import org.dataone.service.types.v1.Service;
import org.dataone.service.types.v1.ServiceMethodRestriction;
import org.dataone.service.types.v1.Subject;

/**
 *
 * Methods for interpreting ServiceMethodRestriction rules
 *
 * @author leinfelder
 *
 */
public class ServiceMethodRestrictionUtil {

	/**
	 * Interprets the CN's ServiceMethodRestriction for a given Subject+serviceName+methodName
	 * @param subject the subject that may be allowed to invoke the service method
	 * @param nodeList the nodelist to use as reference for the restrictions
	 * @param serviceName the name of the CN service
	 * @param methodName the name of the method having the restriction rules
	 * @return true if allowed, otherwise false
	 */
    public static boolean isMethodAllowed(Subject subject, List<Node> nodeList, String serviceName, String methodName) {
    	// checks if we are allowed to call this method -- should be very restricted
        boolean isAllowed = false;
        // labeled break gets us out as soon as there's a match
        subjectSearch:
        for (Node node: nodeList) {
        	// check if the caller is a CN
        	for (Subject nodeSubject: node.getSubjectList()) {
        		if (nodeSubject.equals(subject)) {
        			if (node.getType().equals(NodeType.CN)) {
        				// the CN is always allowed
        				isAllowed = true;
            			break subjectSearch;
        			}
        		}
        	}
        	// check that the CN node allows us to call it
        	if (node.getType().equals(NodeType.CN)) {
        		// check if it's in the service method allowed list
            	for (Service service: node.getServices().getServiceList()) {
            		if (service.getName().equals(serviceName)) {
            			if (service.getRestrictionList() != null) {
	                		for (ServiceMethodRestriction restriction: service.getRestrictionList()) {
	                			if (restriction.getMethodName().equals(methodName)) {
	                				if (restriction.getSubjectList() != null) {
		                				for (Subject restrictedSubject: restriction.getSubjectList()) {
		                					if (restrictedSubject.equals(subject)) {
		                						isAllowed = true;
		                						break subjectSearch;
		                					}
		                				}
	                				}
	                			}
	                		}
            			}
            		}
            	}
        	}
        }
        
        return isAllowed;
    }
}
