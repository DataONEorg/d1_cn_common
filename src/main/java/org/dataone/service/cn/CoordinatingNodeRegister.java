/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dataone.service.cn;

import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;
import org.dataone.service.types.AuthToken;
import org.dataone.service.types.Identifier;
import org.dataone.service.types.NodeList;
import org.dataone.service.types.Services;

/**
 * The register API methods are used to maintain a registry of nodes participating in the DataONE
 * infrastructure.
 *
 *  Note that the node registry is much the same as the Object collection with a restriction on the
 *  returned object formats to be Member Nodes or Coordinating Nodes. It may be prudent for the
 *  implementation of the registration API to leverage the existing functionality of the object
 *  collection rather than implementing a parallel data store. In this case, the “science metadata”
 *  could be a DC description of the node, and the “data” might be the detailed registration
 *  information including node capabilities, scheduling and so forth.
 *
 * @author waltz
 * @deprecated
 */
public interface CoordinatingNodeRegister {

    /**
    CN_register.listNodes() → NodeList

    Returns a list of nodes that have been registered with the DataONE infrastructure.
    Use Cases:	UC39
    Rest URL: GET /node

    Method: CN_register.listNodes
     *     Parameters:

    token (Types.AuthToken) – The authentication token returned by CN_authentication.login()
     *
    Returns:	List of nodes
    Return type:	Types.NodeList
    Raises:

     * Exceptions.NotImplemented – (errorCode=501, detailCode=4800)
     * Exceptions.ServiceFailure – (errorCode=500, detailCode=4801)
     *
     * @author waltz
     */
    public NodeList listNodes(AuthToken token) throws NotImplemented, ServiceFailure;

    /**
    CN_register.get(token, pid) → Node¶
    Rest URL: GET /node/pid

    Method: CN_register.get
    Parameters:

     * token (Types.AuthToken) – The authentication token returned by CN_authentication.login()
     * pid (Types.Identifier) –

    Returns:	A dataONE Node if operation is successful
    Return type:  Types.Node
    Raises:

     * Exceptions.NotImplemented – (errorCode=501, detailCode=4820)
     * Exceptions.NotAuthorized – (errorCode=401, detailCode=4821)
     * Exceptions.ServiceFailure – (errorCode=500, detailCode=4822)
     * Exceptions.InvalidRequest – (errorCode=400, detailCode=4823)
     *
     * @author waltz
     */
    // Services will need a root element of some sort.  currently Services is defined as a
    // sub-element of nodeList
    // public Node getNode(AuthToken token, Identifier pid) throws NotImplemented,NotAuthorized, ServiceFailure, InvalidRequest;
    /**
    CN_register.addNodeCapabilities(token, pid, capabilities) → boolean¶
    Rest URL: PUT /node/pid

    Method: CN_register.addNodeCapabilities
    Parameters:

     * token (Types.AuthToken) – The authentication token returned by CN_authentication.login()
     * pid (Types.Identifier) –
     * capabilities –

    Returns:	True if operation is successful
    Return type:  boolean
    Raises:

     * Exceptions.NotImplemented – (errorCode=501, detailCode=4820)
     * Exceptions.NotAuthorized – (errorCode=401, detailCode=4821)
     * Exceptions.ServiceFailure – (errorCode=500, detailCode=4822)
     * Exceptions.InvalidRequest – (errorCode=400, detailCode=4823)
     *
     * @author waltz
     */
    // Services will need a root element of some sort.  currently Services is defined as a 
    // sub-element of nodeList
    public boolean addNodeCapabilities(AuthToken token, Identifier pid, Services capabilities) throws NotImplemented, NotAuthorized, ServiceFailure, InvalidRequest;

    /**
     * CN_register.register(token, capabilities) → Identifier
    Rest URL: POST /node

    MN CN_register.register
    Parameters:

     * token (Types.AuthToken) – The authentication token returned by CN_authentication.login()
     * capabilities –

    Returns:	The identifier of the new node entry.
    Return type: Types.Identifier
    Raises:

     * Exceptions.NotImplemented – (errorCode=501, detailCode=4840)
     * Exceptions.NotAuthorized – (errorCode=401, detailCode=4841)
     * Exceptions.ServiceFailure – (errorCode=500, detailCode=4842)
     * Exceptions.InvalidRequest – (errorCode=400, detailCode=4843)
     *
     * @author waltz
     */
    public Identifier register(AuthToken token, Services capabilities) throws NotImplemented, NotAuthorized, ServiceFailure, InvalidRequest;
}
