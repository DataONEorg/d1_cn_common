/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dataone.cn.ldap;

import java.io.IOException;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.CommunicationException;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.event.EventContext;
import javax.naming.event.EventDirContext;
import javax.naming.ldap.*;

import javax.net.ssl.SSLSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.dataone.configuration.Settings;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 *
 * Wraps a DirContext as a PooledObject in order to be managed By Apache's GenericObjectPool or DirContextObjectPool.
 *
 * The DirContext may be a Secured or NonSecure connection. NonSecured connections are used for Testing purposes only.
 * The wrapped object is created and also may be validated or destroyed by the class.
 *
 * @author waltz
 */
public class DirContextPooledObjectFactory extends BasePooledObjectFactory<DirContext> {

    public static Log log = LogFactory.getLog(DirContextPooledObjectFactory.class);
    // look up defaults from configuration
    protected String server = Settings.getConfiguration().getString("cn.ldap.server");
    protected String admin = Settings.getConfiguration().getString("cn.ldap.admin");
    protected String password = Settings.getConfiguration().getString("cn.ldap.password");

    protected boolean useTLS = Boolean.parseBoolean(Settings.getConfiguration().getString(
            "cn.ldap.useTLS"));

    private static ConcurrentHashMap<DirContext, DirContextStash> tlsHashMap = new ConcurrentHashMap<>();

    @Override
    public DirContext create() throws Exception {
        DirContext context = null;
        log.debug("creating new context");
        if (useTLS) {
            try {
                context = getSecureContext();
            } catch (Throwable e) {
                log.error("Could not set up TLS connection, using non-secure communication", e);
                context = getDefaultContext();
            }
        } else {
            context = getDefaultContext();
        }
        DirContextUnsolicitedNotificationListener d1Listener = new DirContextUnsolicitedNotificationListener(context);

        // Register listener with context (all targets equivalent)
        
        EventDirContext eventDirContext = (EventDirContext) (context.lookup(""));
        eventDirContext.addNamingListener("", EventContext.ONELEVEL_SCOPE, d1Listener);
        if (tlsHashMap.containsKey(context)) {
            tlsHashMap.get(context).eventDirContext = eventDirContext;
            tlsHashMap.get(context).d1Listener = d1Listener;
        }
        return context;
    }

    private DirContext getDefaultContext() throws NamingException {
        Hashtable<String, String> env = new Hashtable<String, String>();
        /*
         * Specify the initial context implementation to use. This could also be
         * set by using the -D option to the java program. For example, java
         * -Djava.naming.factory.initial=com.sun.jndi.ldap.LdapCtxFactory \
         * Modattrs
         */
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        /* Specify host and port to use for directory service */
        env.put(Context.PROVIDER_URL, server);
        /* specify authentication information */
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, admin);
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put("com.sun.jndi.ldap.read.timeout", "1000");
        env.put("com.sun.jndi.ldap.connect.timeout", "1000");
        // Tried with both pooling and without - doesn't solve problems
        env.put("com.sun.jndi.ldap.connect.pool", "false");
        /* get a handle to an Initial DirContext */
        DirContext ctx = new InitialDirContext(env);
        return ctx;
    }

    private DirContext getSecureContext() throws NamingException, IOException {
        Hashtable<String, String> env = new Hashtable<String, String>();

        // the basic connection details
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        // Specify host and port to use for directory service
        env.put(Context.PROVIDER_URL, server);

        // get a handle to an Initial Context
        // InitialDirContext ctx = new InitialDirContext(env);
        LdapContext ctx = new InitialLdapContext(env, null);

        // set up TLS
        StartTlsResponse tls = (StartTlsResponse) ctx.extendedOperation(new StartTlsRequest());
        // install simple hostname verifier for localhost
        if (server.contains("localhost")) {
            tls.setHostnameVerifier(new AllowAllHostnameVerifier());
        }
        // start it
        SSLSession sess = tls.negotiate();

        // add the authentication information
        ctx.addToEnvironment(Context.SECURITY_AUTHENTICATION, "simple");
        ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, admin);
        ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
        DirContextStash dirContextStash = new DirContextStash(tls);
        tlsHashMap.put(ctx, dirContextStash);
        return ctx;
    }

    @Override
    public PooledObject<DirContext> wrap(DirContext dirContext) {
        return new DefaultPooledObject<DirContext>(dirContext);
    }

    @Override
    public boolean validateObject(PooledObject<DirContext> p) {
        log.debug(p.getObject().toString() + " has a state of " + p.getState().name());
        DirContext dirContext = p.getObject();
        try {
            // check if return client in current service list if

            dirContext.getNameInNamespace();
        } catch (NamingException ex) {
            log.error(ex, ex);
            return false;
        }

        return super.validateObject(p);
    }

    @Override
    public void destroyObject(PooledObject<DirContext> p) throws Exception {
        log.debug("Destroying context");
        DirContext dirContext = p.getObject();

        try {
            dirContext.close();
        } catch (NamingException ex) {
            if (ex instanceof CommunicationException) {
                log.warn(ex.getMessage());
            } else {
                log.error(ex.getMessage(), ex);
                throw ex;
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        } finally {

            if (tlsHashMap.containsKey(dirContext)) {
                DirContextStash dirContextStash = tlsHashMap.remove(dirContext);
                
                // turn off the listener so that when we close the TLS connection
                // we don't get spammed with a Warning Log message
                dirContextStash.eventDirContext.removeNamingListener(dirContextStash.d1Listener);

                try {
                    dirContextStash.startTlsResponse.close();
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }

            }
        }
        // currently,  in BasePooledObjectFactory, this method is a no-op
        super.destroyObject(p);
    }

    /* The information is needed for when a DirContext is closed by the pooling manager.
       startTlsResponse allows access to the TLS connection that needs to be closed.
       d1Listener was attached to the EventDirContext to report any events on 
       the DirContext such as javax.naming.CommunicationException.
       When the TLS connection closes, regardless of if the DirContext is closed,
       a CommunicationException event will be fired and logged by the d1Listener.
       Having such a warning in the log file sets up a false flag for investigation,
       since the code intends to close the TLS connection gracefully.
       So, in the destroy method, attempt to deregister the listener from the
       EventDirContext before closing the  TLS connection to avoid the log message.
    */
    private class DirContextStash {
        public StartTlsResponse startTlsResponse;
        public DirContextUnsolicitedNotificationListener d1Listener;
        EventDirContext eventDirContext;
        
        public DirContextStash(StartTlsResponse startTlsResponse) {
            this.startTlsResponse = startTlsResponse;
        }
    }
}
