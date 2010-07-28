/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dataone.cn.batch.utils;

import java.util.List;
import org.dataone.service.types.Node;
import org.dataone.service.types.NodeList;

/**
 * determine for a batch process which MemberNode(s) are being processed by the Coordinating Node
 * @author rwaltz
 */
public class NodeReference {
    // these two need to be set after instantiation
    private String nodeListMergeDirPath = "";
    private NodeList mnNodeList = null;

    // thse two are derived from the above two
    private Node mnNode = null;
    private String mnNodeIdentifier = "";

    public Node getMnNode() {
        if (mnNode == null) {
            List<Node> nodes = mnNodeList.getNodes();
            for (Node node : nodes) {
                if (node.getIdentifier().getValue().contentEquals(this.mnNodeIdentifier)) {
                    this.mnNode = node;
                    break;
                }
            }
        }
        return this.mnNode;
    }

    public void setMnNode(Node mnNode) {
        this.mnNode = mnNode;
    }

    public String getMnNodeIdentifier() {
        return mnNodeIdentifier;
    }

    public void setMnNodeIdentifier(String mnNodeIdentifier) {
        this.mnNodeIdentifier = mnNodeIdentifier;
    }

    public NodeList getMnNodeList() {
        return mnNodeList;
    }

    public void setMnNodeList(NodeList mnNodeList) {
        this.mnNodeList = mnNodeList;
    }
/**
 * subdirectory of a member node in which all packaged files for indexing are stored
 * there maybe an addition subdir underneath in which different object formats are stored as well
 * @author rwaltz
 */
    public String getNodeListMergeDirPath() {
        if (this.nodeListMergeDirPath.isEmpty()) {
            this.nodeListMergeDirPath = this.getMnNode().getName().replaceAll("[\\W]+", "-");
        }
        return this.nodeListMergeDirPath;
    }

    public void setNodeListMergeDirPath(String nodeListMergeDirPath) {
        this.nodeListMergeDirPath = nodeListMergeDirPath;
    }
    public String getNodeBaseURL() {
        return this.getMnNode().getBaseURL();
    }
}
