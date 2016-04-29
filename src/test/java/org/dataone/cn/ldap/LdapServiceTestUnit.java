/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.cn.ldap;

import java.util.List;
import java.util.logging.Level;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import org.dataone.configuration.Settings;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author waltz
 */
public class LdapServiceTestUnit {
    private static final Logger log = LoggerFactory.getLogger(LdapServiceTestUnit.class);
    
    private MockLDAPService mockLDAPService = new MockLDAPService();
    private String DC_TEST_DN = "DC=test,DC=org";
    private String COUNTRY_TEST_DN = "C=GH,DC=test,DC=org";
    private String ORG_TEST_DN = "O=Clydestone,C=GH,DC=test,DC=org";
    private String ORG_UNIT_TEST_DN = "OU=Sales,O=Clydestone,C=GH,DC=test,DC=org";
    private String TREE_TEST_DN = "CN=Benjamin Leinfelder A515,O=University of Chicago,C=US,DC=cilogon,DC=org";
    private String ATTRIBUTE_VALUES_DN = "CN=urn:node:testcn,DC=dataone,DC=org";
    private String ALT_TREE_TEST_DN = Settings.getConfiguration().getString("test.primarySubject");
    
    @Test
    public void testBase() {
        String base = Settings.getConfiguration().getString("mock.ldap.base");
        assertTrue(base.equals(mockLDAPService.getBase()));
    }
    
    @Test 
    public void testAddDc() {
        DirContext dirContext = null;
        DirContextProvider dirContextProvider = DirContextProvider.getInstance();
        try {
            dirContext = dirContextProvider.borrowDirContext();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            fail(ex.getMessage());
        }

        assertNotNull(dirContext);
        try {
            mockLDAPService.addDc(dirContext, DC_TEST_DN);
        } catch (NamingException ex) {
            log.error(ex.getMessage(), ex);
            fail(ex.getMessage());
        }
        
        dirContextProvider.returnDirContext(dirContext);
        assert (dirContextProvider.getNumDirContextActive() == 0);
    }
    @Test
    public void testAddCountry() {
        DirContext dirContext = null;
        DirContextProvider dirContextProvider = DirContextProvider.getInstance();
        try {
            dirContext = dirContextProvider.borrowDirContext();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            fail(ex.getMessage());
        }

        assertNotNull(dirContext);
        try {
            mockLDAPService.addCountry(dirContext, COUNTRY_TEST_DN);
        } catch (NamingException ex) {
            log.error(ex.getMessage(), ex);
            fail(ex.getMessage());
        }
        
        dirContextProvider.returnDirContext(dirContext);
        assert (dirContextProvider.getNumDirContextActive() == 0);
    }
    @Test
    public void testAddOrg() {
        DirContext dirContext = null;
        DirContextProvider dirContextProvider = DirContextProvider.getInstance();
        try {
            dirContext = dirContextProvider.borrowDirContext();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            fail(ex.getMessage());
        }

        assertNotNull(dirContext);
        try {
            mockLDAPService.addOrg(dirContext, ORG_TEST_DN);
        } catch (NamingException ex) {
            log.error(ex.getMessage(), ex);
            fail(ex.getMessage());
        }
        
        dirContextProvider.returnDirContext(dirContext);
        assert (dirContextProvider.getNumDirContextActive() == 0);
    }
    @Test
    public void testAddUnit() {
        DirContext dirContext = null;
        DirContextProvider dirContextProvider = DirContextProvider.getInstance();
        try {
            dirContext = dirContextProvider.borrowDirContext();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            fail(ex.getMessage());
        }

        assertNotNull(dirContext);
        try {
            mockLDAPService.addOrgUnit(dirContext, ORG_UNIT_TEST_DN);
        } catch (NamingException ex) {
            log.error(ex.getMessage(), ex);
            fail(ex.getMessage());
        }
       boolean check = mockLDAPService.removeEntry(dirContext, ORG_UNIT_TEST_DN);
        assertTrue(check);
        
        dirContextProvider.returnDirContext(dirContext);
        assert (dirContextProvider.getNumDirContextActive() == 0);
    }
    
    public void testConstructTree() {
        DirContext dirContext = null;
        DirContextProvider dirContextProvider = DirContextProvider.getInstance();
        try {
            dirContext = dirContextProvider.borrowDirContext();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            fail(ex.getMessage());
        }

        assertNotNull(dirContext);
        try {
            boolean success = mockLDAPService.constructTree(dirContext, TREE_TEST_DN);
            assertTrue(success);
        } catch (NamingException ex) {
            log.error(ex.getMessage(), ex);
            fail(ex.getMessage());
        }
        try {
            boolean success = mockLDAPService.constructTree(dirContext, ALT_TREE_TEST_DN);
            assertTrue(success);
        } catch (NamingException ex) {
            log.error(ex.getMessage(), ex);
            fail(ex.getMessage());
        }        
        dirContextProvider.returnDirContext(dirContext);
        assert (dirContextProvider.getNumDirContextActive() == 0);
    }
    
    @Test
    public void testGetAttributeValues() {
        DirContext dirContext = null;
        DirContextProvider dirContextProvider = DirContextProvider.getInstance();
        try {
            dirContext = dirContextProvider.borrowDirContext();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            fail(ex.getMessage());
        }

        assertNotNull(dirContext);
        try {
            List<Object> attributeValues = mockLDAPService.getAttributeValues(dirContext, ATTRIBUTE_VALUES_DN, "d1NodeApproved");
            assertFalse(attributeValues.isEmpty());
            String d1NodeApprovalValue = (String) attributeValues.get(0);
            assert(d1NodeApprovalValue.equalsIgnoreCase("TRUE"));
        } catch (NamingException ex) {
            log.error(ex.getMessage(), ex);
            fail(ex.getMessage());
        }
        
        dirContextProvider.returnDirContext(dirContext);
        assert (dirContextProvider.getNumDirContextActive() == 0); 
    }
    @Test
    public void testCheckAttributeValue() {
        DirContext dirContext = null;
        DirContextProvider dirContextProvider = DirContextProvider.getInstance();
        try {
            dirContext = dirContextProvider.borrowDirContext();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            fail(ex.getMessage());
        }

        assertNotNull(dirContext);

        boolean check = mockLDAPService.checkAttribute(dirContext, ATTRIBUTE_VALUES_DN, "d1NodeApproved", "TRUE");
        assertTrue(check);


        
        dirContextProvider.returnDirContext(dirContext);
        assert (dirContextProvider.getNumDirContextActive() == 0); 
    }
    
}
