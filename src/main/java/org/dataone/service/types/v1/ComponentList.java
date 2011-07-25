
package org.dataone.service.types.v1;

import java.io.Serializable;

/** 
 * A ComponentList is the structure returned
 from the version() method. It provides a list of DataONE software stack
 components and their corresponding version numbers.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ComponentList">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Component" name="component"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ComponentList implements Serializable
{
    private Component component;

    /** 
     * Get the 'component' element value.
     * 
     * @return value
     */
    public Component getComponent() {
        return component;
    }

    /** 
     * Set the 'component' element value.
     * 
     * @param component
     */
    public void setComponent(Component component) {
        this.component = component;
    }
}
