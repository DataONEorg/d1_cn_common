/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.service.types.v1;

import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author waltz
 */
public class UniqueSetsOfTypesTestCase {

    @Test
    public void UniqueSetofIdentifiers () {
        Identifier a1 = new Identifier();
        a1.setValue("thisIsUnique");

        Identifier a2 = new Identifier();
        a2.setValue("thisIsUnique");


        assertTrue(a1.equals(a2));
        assertTrue(a1.compareTo(a2) == 0);

        HashMap<Identifier, String> idMap = new HashMap<Identifier, String>();

        idMap.put(a1, "Hello");
        idMap.put(a2, "Yello");


        assertTrue(idMap.get(a1).contentEquals("Yello"));
    }


    @Test
    public void UniqueSetofNodeReferences () {
        NodeReference a1 = new NodeReference();
        a1.setValue("thisIsUnique");

        NodeReference a2 = new NodeReference();
        a2.setValue("thisIsUnique");


        assertTrue(a1.equals(a2));
        assertTrue(a1.compareTo(a2) == 0);

        HashMap<NodeReference, String> idMap = new HashMap<NodeReference, String>();

        idMap.put(a1, "Hello");
        idMap.put(a2, "Yello");


        assertTrue(idMap.get(a1).contentEquals("Yello"));
    }

}
