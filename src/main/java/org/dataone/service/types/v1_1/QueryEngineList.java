
package org.dataone.service.types.v1_1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * A list of query engine names that indicate the possible values for 
 CNRead.getQueryEngineDescription and CNRead.query REST API endpoints.
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="QueryEngineList">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="xs:string" name="queryEngine" minOccurs="0" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class QueryEngineList implements Serializable
{
    private static final long serialVersionUID = 10000000;
    private List<String> queryEngineList = new ArrayList<String>();

    /** 
     * Get the list of 'queryEngine' element items. The name of a queryEngine. This value will be used as a path element in 
              REST API calls and so should not contain characters that will need to be escaped.
     * 
     * @return list
     */
    public List<String> getQueryEngineList() {
        return queryEngineList;
    }

    /** 
     * Set the list of 'queryEngine' element items. The name of a queryEngine. This value will be used as a path element in 
              REST API calls and so should not contain characters that will need to be escaped.
     * 
     * @param list
     */
    public void setQueryEngineList(List<String> list) {
        queryEngineList = list;
    }

    /** 
     * Get the number of 'queryEngine' element items.
     * @return count
     */
    public int sizeQueryEngineList() {
        if (queryEngineList == null) {
            queryEngineList = new ArrayList<String>();
        }
        return queryEngineList.size();
    }

    /** 
     * Add a 'queryEngine' element item.
     * @param item
     */
    public void addQueryEngine(String item) {
        if (queryEngineList == null) {
            queryEngineList = new ArrayList<String>();
        }
        queryEngineList.add(item);
    }

    /** 
     * Get 'queryEngine' element item by position.
     * @return item
     * @param index
     */
    public String getQueryEngine(int index) {
        if (queryEngineList == null) {
            queryEngineList = new ArrayList<String>();
        }
        return queryEngineList.get(index);
    }

    /** 
     * Remove all 'queryEngine' element items.
     */
    public void clearQueryEngineList() {
        if (queryEngineList == null) {
            queryEngineList = new ArrayList<String>();
        }
        queryEngineList.clear();
    }
}
