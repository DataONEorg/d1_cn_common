package org.dataone.service.types;


/**
 * The DataONE Type to represent a location of an object on a DataONE node.
 *
 * @author Matthew Jones
 */
public class ObjectLocation 
{
    private NodeReferenceType node;
    private String url;

    /**
     * Construct an object location on a given node with a given URL.
     * @param node the node on which the object exists
     * @param url the URL of the object on the node
     */
    public ObjectLocation(NodeReferenceType node, String url) {
        super();
        this.node = node;
        this.url = url;
    }

    /**
     * @return the node
     */
    public NodeReferenceType getNode() {
        return node;
    }

    /**
     * @param node the node to set
     */
    public void setNode(NodeReferenceType node) {
        this.node = node;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
