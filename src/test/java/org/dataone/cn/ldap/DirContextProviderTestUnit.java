/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dataone.cn.ldap;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.naming.directory.DirContext;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import org.dataone.configuration.Settings;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author waltz
 */
public class DirContextProviderTestUnit {

    private static final Logger log = LoggerFactory.getLogger(DirContextProviderTestUnit.class);

    @Test
    public void testBorrowReturnPool() {
        DirContext dirContext = null;
        DirContextProvider dirContextProvider = DirContextProvider.getInstance();
        try {
            dirContext = dirContextProvider.borrowDirContext();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            fail(ex.getMessage());
        }

        assertNotNull(dirContext);
        dirContextProvider.returnDirContext(dirContext);
        assert (dirContextProvider.getNumDirContextActive() == 0);
    }

    @Test
    public void testMultipleBorrowReturnPool() {
        List<DirContext> dirContextList = new ArrayList<>();
        DirContextProvider dirContextProvider = DirContextProvider.getInstance();
        log.debug("Active Dir Contexts: " + dirContextProvider.getNumDirContextActive());
        log.debug("Idle Dir Contexts: " + dirContextProvider.getNumDirContextIdle());
        int i = 0;
        while (i < 5) {
            try {
                dirContextList.add(dirContextProvider.borrowDirContext());
                log.debug("added dirContext #" + i);
                log.debug("After Adding: Active Dir Contexts: " + dirContextProvider.getNumDirContextActive());
                log.debug("After Adding: Idle Dir Contexts: " + dirContextProvider.getNumDirContextIdle());
                ++i;
            } catch (NoSuchElementException ex) {
                // the pool threw an exception because
                // there were no idle connections available 
                // after the default timeout period
                // purposefully free up a connection and retry!
                log.warn(ex.getMessage());
                DirContext dirContext = dirContextList.remove(0);
                assertNotNull(dirContext);
                dirContextProvider.returnDirContext(dirContext);
                log.debug("After Exception: Active Dir Contexts: " + dirContextProvider.getNumDirContextActive());
                log.debug("After Exception: Idle Dir Contexts: " + dirContextProvider.getNumDirContextIdle());
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
                fail(ex.getMessage());
            }
        }
        for (DirContext dirContext : dirContextList) {
            assertNotNull(dirContext);
            dirContextProvider.returnDirContext(dirContext);
            log.debug("After Returning: Active Dir Contexts: " + dirContextProvider.getNumDirContextActive());
            log.debug("After Returning: Idle Dir Contexts: " + dirContextProvider.getNumDirContextIdle());
        }
        assert (dirContextProvider.getNumDirContextActive() == 0);
    }
}
