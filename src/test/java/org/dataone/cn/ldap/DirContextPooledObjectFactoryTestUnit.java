/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.cn.ldap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import static junit.framework.Assert.fail;
import org.apache.commons.pool2.PooledObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author waltz
 */
public class DirContextPooledObjectFactoryTestUnit {
    private static final Logger log = LoggerFactory.getLogger(DirContextPooledObjectFactoryTestUnit.class);

    private static long sleepMS = 1000L;
    
    @Test
    public void testCreateDirContext()  {
        DirContextPooledObjectFactory dirContextPooledObjectFactory = new DirContextPooledObjectFactory();
        try {
            DirContext dirContext = dirContextPooledObjectFactory.create();
            
            dirContext.close();
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            fail(ex.getMessage());
        }
        
    }
    @Test
    public void testWrapDirContext()  {
        DirContextPooledObjectFactory dirContextPooledObjectFactory = new DirContextPooledObjectFactory();
        DirContext dirContext = null;
        try {
            dirContext = dirContextPooledObjectFactory.create();
            
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            fail(ex.getMessage());
        }
        
        PooledObject<DirContext> pooledObject = dirContextPooledObjectFactory.wrap(dirContext);
        try {
            Thread.sleep(sleepMS);
        } catch (InterruptedException ex) {
            
        }
        long idleMS = pooledObject.getIdleTimeMillis();
        long createTime = pooledObject.getCreateTime();
        Date createDate = new Date(createTime);
        Date currentDate = new Date();
        assert(createDate.before(currentDate));
        assert(idleMS >= sleepMS);
	SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss.sss");

	
        log.info("Pooled Object Create Date = " + format.format(createDate) + 
                " Current Time " + format.format(new Date()) +
                " pooled object idle MS " + idleMS) ;
        try {
            dirContext.close();
        } catch (NamingException ex) {
            log.error(ex.getMessage(),ex);
            fail(ex.getMessage());
        }
        
    }
    
    @Test
    public void testDestroyPooledObject()  {
        DirContextPooledObjectFactory dirContextPooledObjectFactory = new DirContextPooledObjectFactory();
        DirContext dirContext = null;
        try {
            dirContext = dirContextPooledObjectFactory.create();
            
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            fail(ex.getMessage());
        }
        
        PooledObject<DirContext> pooledObject = dirContextPooledObjectFactory.wrap(dirContext);

        try {
            dirContextPooledObjectFactory.destroyObject(pooledObject);
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            fail(ex.getMessage());
        }
        
        
    }
    @Test
    public void testValidateObject()  {
        DirContextPooledObjectFactory dirContextPooledObjectFactory = new DirContextPooledObjectFactory();
        DirContext dirContext = null;
        try {
            dirContext = dirContextPooledObjectFactory.create();
            
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            fail(ex.getMessage());
        }
        
        PooledObject<DirContext> pooledObject = dirContextPooledObjectFactory.wrap(dirContext);
        if (!dirContextPooledObjectFactory.validateObject(pooledObject)) {
            fail("Dir Context should be valid");
        }
        try {
            dirContextPooledObjectFactory.destroyObject(pooledObject);
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            fail(ex.getMessage());
        }
        
        
    }
}
