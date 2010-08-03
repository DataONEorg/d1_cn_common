/**
 * This work was created by participants in the DataONE project, and is jointly
 * copyrighted by participating institutions in DataONE. For more information on
 * DataONE, see our web site at http://dataone.org.
 *
 *   Copyright ${year}
 *
 * This work is Licensed under the Apache License, Version 2.0 (the "License");
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
