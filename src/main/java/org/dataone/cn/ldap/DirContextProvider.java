/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.cn.ldap;

import java.util.Date;
import java.util.NoSuchElementException;
import javax.naming.directory.DirContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObjectInfo;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.dataone.configuration.Settings;

/**
 * Initialize a pool to manage LDAP Contexts.  The class is a wrapper
 * and a singleton for an Apache Generic Object Pool that contains the
 * Dir Context instances.
 * 
 * @author waltz
 * 
 * http://stackoverflow.com/questions/70689/what-is-an-efficient-way-to-implement-a-singleton-pattern-in-java
 * http://svn.apache.org/viewvc/directory/apacheds/trunk/test-framework/src/main/java/org/apache/directory/server/core/integ/CreateLdapConnectionPoolRule.java?view=markup
 */
public class DirContextProvider {
    public static Log log = LogFactory.getLog(DirContextProvider.class);
    private static boolean BLOCK_WHEN_EXHAUSTED =  Settings.getConfiguration().getBoolean("cn.ldap.pool.block_when_exhausted", true);
    private static long MAX_WAIT_MILLIS = Settings.getConfiguration().getLong("cn.ldap.pool.max_wait_millis", 2000);
    private static long MIN_EVICTABLE_IDLE_TIME_MILLIS = Settings.getConfiguration().getLong("cn.ldap.pool.min_evictable_idle_time_millis", -1);
    private static long TIME_BETWEEN_EVICTION_RUNS_MILLIS = Settings.getConfiguration().getLong("cn.ldap.pool.time_between_eviction_runs_millis", -1);
    private static int MAX_TOTAL_POOL_OBJECTS = Settings.getConfiguration().getInt("cn.ldap.pool.max_total", 20);
    private static int MAX_IDLE_POOL_OBJECTS = Settings.getConfiguration().getInt("cn.ldap.pool.max_idle", -1);
    private static int MIN_IDLE_POOL_OBJECTS = Settings.getConfiguration().getInt("cn.ldap.pool.min_idle", 0);
    private static final long serialVersionUID = 1L;
    private GenericObjectPool<DirContext> dirContextPool ;
    static private volatile DirContextProvider dirContextPoolProvider;
    
    private DirContextProvider() {
       DirContextPooledObjectFactory dirContextPooledObjectFactory = new DirContextPooledObjectFactory();
       GenericObjectPoolConfig conf = new GenericObjectPoolConfig();
       conf.setBlockWhenExhausted(BLOCK_WHEN_EXHAUSTED);
       conf.setMaxWaitMillis(MAX_WAIT_MILLIS);
       conf.setMinEvictableIdleTimeMillis(MIN_EVICTABLE_IDLE_TIME_MILLIS);
       conf.setSoftMinEvictableIdleTimeMillis(MIN_EVICTABLE_IDLE_TIME_MILLIS);
       conf.setTimeBetweenEvictionRunsMillis(TIME_BETWEEN_EVICTION_RUNS_MILLIS);
       conf.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
       conf.setMaxTotal(MAX_TOTAL_POOL_OBJECTS);
       conf.setMaxIdle(MAX_IDLE_POOL_OBJECTS);
       conf.setMinIdle(MIN_IDLE_POOL_OBJECTS);
       conf.setTestWhileIdle(true);
       conf.setTestOnReturn(true);
       conf.setTestOnBorrow(true);
       dirContextPool  = new GenericObjectPool<DirContext>(dirContextPooledObjectFactory,conf);
    }

    public static DirContextProvider getInstance() {
        if (dirContextPoolProvider == null) {
            synchronized(DirContextProvider.class) {
                if (dirContextPoolProvider == null) {
                    dirContextPoolProvider = new DirContextProvider();
                }
            }
        }
        return dirContextPoolProvider;
    }
    
    public DirContext borrowDirContext() throws NoSuchElementException, Exception {
        return dirContextPool.borrowObject() ;
    }
    
    public void returnDirContext(DirContext context) {
        
        for (DefaultPooledObjectInfo defaultPooledObject : dirContextPool.listAllObjects()) {

            Date borrowedDate = new Date(defaultPooledObject.getLastBorrowTime());
            Date returnedDate = new Date(defaultPooledObject.getLastReturnTime());
            log.info("Before ObjectID " + defaultPooledObject.getPooledObjectToString()  + (borrowedDate.before(returnedDate) ? " returned" : " notreturned") );
        }
        dirContextPool.returnObject(context);
        for (DefaultPooledObjectInfo defaultPooledObject : dirContextPool.listAllObjects()) {
            Date borrowedDate = new Date(defaultPooledObject.getLastBorrowTime());
            Date returnedDate = new Date(defaultPooledObject.getLastReturnTime());
            log.info("After ObjectID " + defaultPooledObject.getPooledObjectToString()  + (borrowedDate.before(returnedDate) ? " returned" : " notreturned") );
        }
    }
    
    public int getNumDirContextActive() {
        return dirContextPool.getNumActive();
    }
    public int getNumDirContextIdle() {
        return dirContextPool.getNumIdle();
    }
    @SuppressWarnings("unused")
    private DirContextProvider readResolve() {
        return dirContextPoolProvider;
    }
}
