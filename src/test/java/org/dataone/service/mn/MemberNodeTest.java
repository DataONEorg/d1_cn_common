package org.dataone.service.mn;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Member Node interface
 */
public class MemberNodeTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MemberNodeTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( MemberNodeTest.class );
    }

    /**
     * Not much to test for an interface definition.
     */
    public void testMemberNode()
    {
        assertTrue( true );
    }
}
