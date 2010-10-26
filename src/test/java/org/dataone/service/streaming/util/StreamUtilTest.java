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
 */

package org.dataone.service.streaming.util;

import org.dataone.service.exceptions.BaseException;
import org.dataone.service.exceptions.BaseExceptionTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author berkley
 *  Test for StreamUtil
 */
public class StreamUtilTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public StreamUtilTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(StreamUtilTest.class);
    }
    
    public void setUp() 
    {

    }
    
    /**
     * test memory mapped IO
     */
    public void testMemoryMapping()
    {
        try 
        {
            //test mid string matches
            int[] x = StreamUtil.lookForMatch("boundary=", "asdflads f asdf asdf sdaf dlfkj  (*&( lkjsdafboundary=\"dsaflk\" das flkdasfjllkads f");
            //System.out.println("x: " + x[0] + " " + x[1]);
            assertTrue(x[0] == 45);
            assertTrue(x[1] == "boundary=".length());
            
            x = StreamUtil.lookForMatch("boundary=", "lask asdf flkajdflk3244lkj234@#$@#$@#$@# asokdfj boundaary=324@#$@#$l;kj asdf  dsaf");
            //System.out.println("x: " + x[0] + " " + x[1]);
            assertTrue(x[0] == -1);
            assertTrue(x[1] == -1);
            
            //test end cases
            x = StreamUtil.lookForMatch("boundary=", "asdflads f asdf asdf sdaf dlfkj  (*&( lkjsdafboun");
            //System.out.println("x: " + x[0] + " " + x[1]);
            assertTrue(x[0] == 45);
            assertTrue(x[1] == "boun".length());
            
            x = StreamUtil.lookForMatch("boundary=", "asdflads f asdf asdf sdaf dlfkj  (*&( lkjsdafb");
            //System.out.println("x: " + x[0] + " " + x[1]);
            assertTrue(x[0] == 45);
            assertTrue(x[1] == "b".length());
            
            x = StreamUtil.lookForMatch("boundary=", "asdflads f asdf asdf sdaf dlfkj  (*&( lkjsdafboundary=");
            //System.out.println("x: " + x[0] + " " + x[1]);
            assertTrue(x[0] == 45);
            assertTrue(x[1] == "boundary=".length());
            
            // test begin cases
            x = StreamUtil.lookForMatch("boundary=", "ary=asdflads f asdf asdf sdaf dlfkj  (*&( lkjsdafbossun");
            //System.out.println("x: " + x[0] + " " + x[1]);
            assertTrue(x[0] == 0);
            assertTrue(x[1] == "ary=".length());
            
            x = StreamUtil.lookForMatch("boundary=", "aary=asdflads f asdf asdf sdaf dlfkj  (*&( lkjsdafbossun");
            //System.out.println("x: " + x[0] + " " + x[1]);
            assertTrue(x[0] == -1);
            assertTrue(x[1] == -1);
            
            x = StreamUtil.lookForMatch("boundary=", "y=asdflads f asdf asdf sdaf dlfkj  (*&( lkjsdafbossun");
            //System.out.println("x: " + x[0] + " " + x[1]);
            assertTrue(x[0] == 0);
            assertTrue(x[1] == "y=".length());
            
            x = StreamUtil.lookForMatch("boundary=", "=asdflads f asdf asdf sdaf dlfkj  (*&( lkjsdafbossun");
            //System.out.println("x: " + x[0] + " " + x[1]);
            assertTrue(x[0] == 0);
            assertTrue(x[1] == 1);
            
            x = StreamUtil.lookForMatch("boundary=", "boundary=sdflads f asdf asdf sdaf dlfkj  (*&( lkjsdaf");
            //System.out.println("x: " + x[0] + " " + x[1]);
            assertTrue(x[0] == 0);
            assertTrue(x[1] == "boundary=".length());
        } 
        catch (Exception e) 
        {
            System.out.println("ERROR: " + e.getMessage());
            fail("ERROR in testMemoryMapping: " + e.getMessage());
        }
    }
}
