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

package org.dataone.service.types.v1;

import org.dataone.service.util.Constants;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
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

        assertFalse(idMap.get(a1).contentEquals("Hello"));
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

        assertFalse(idMap.get(a1).contentEquals("Hello"));
        assertTrue(idMap.get(a1).contentEquals("Yello"));
    }
    
    
    @Test
    public void UniqueSetofObjectFormatIdentifiers () {
        ObjectFormatIdentifier a1 = new ObjectFormatIdentifier();
        a1.setValue("thisIsUnique");

        ObjectFormatIdentifier a2 = new ObjectFormatIdentifier();
        a2.setValue("thisIsUnique");


        assertTrue(a1.equals(a2));
        assertTrue(a1.compareTo(a2) == 0);

        HashMap<ObjectFormatIdentifier, String> idMap = new HashMap<ObjectFormatIdentifier, String>();

        idMap.put(a1, "Hello");
        idMap.put(a2, "Yello");

        assertFalse(idMap.get(a1).contentEquals("Hello"));
        assertTrue(idMap.get(a1).contentEquals("Yello"));
    } 
    
      
    @Test
    public void UniqueSetofSubjectReferences () {
        Subject a1 = new Subject();
        a1.setValue("CN=DEMO1,DC=dataone,DC=org");

        Subject a2 = new Subject();
        a2.setValue("cn=DEMO1, dc=dataone, dc=org");

        Subject a3 = new Subject();
        a3.setValue("CN=DEMO2,DC=dataone,DC=org");

        assertTrue(a1.equals(a2));
        assertTrue(a1.compareTo(a2) == 0);

        assertFalse(a3.equals(a1));
        List<Subject> idList = new ArrayList<Subject>();

        idList.add(a2);
        idList.add(a3);


        assertTrue(idList.contains(a1));
    }
    @Test
    public void PublicSetofSubjectReferences () {
        Subject a1 = new Subject();
        a1.setValue(Constants.SUBJECT_PUBLIC);

        Subject a2 = new Subject();
        a2.setValue("public");


        assertTrue(a1.equals(a2));
        assertTrue(a1.compareTo(a2) == 0);

    }
}
