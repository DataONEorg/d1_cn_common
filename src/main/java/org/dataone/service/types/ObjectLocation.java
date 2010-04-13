/**
 * Copyright 2010 Regents of the University of California and the
 *                National Center for Ecological Analysis and Synthesis
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.dataone.service.types;


/**
 * The DataONE Type to represent a location of an object on a DataONE node.
 *
 * @author Matthew Jones
 */
public class ObjectLocation 
{
    private NodeReference node;
    private String url;

    /**
     * Construct an object location on a given node with a given URL.
     * @param node the node on which the object exists
     * @param url the URL of the object on the node
     */
    public ObjectLocation(NodeReference node, String url) {
        super();
        this.node = node;
        this.url = url;
    }

    /**
     * @return the node
     */
    public NodeReference getNode() {
        return node;
    }

    /**
     * @param node the node to set
     */
    public void setNode(NodeReference node) {
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
