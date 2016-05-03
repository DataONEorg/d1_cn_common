/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dataone.cn.ldap;

import java.io.IOException;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import static org.dataone.cn.ldap.LDAPService.log;
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

    private static ConcurrentHashMap<DirContext, StartTlsResponse> tlsHashMap = new ConcurrentHashMap<>();

    @Override
    public DirContext create() throws Exception {
        DirContext context = null;
        log.info("creating new context");
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
        tlsHashMap.put(ctx, tls);
        return ctx;
    }

    @Override
    public PooledObject<DirContext> wrap(DirContext dirContext) {
        return new DefaultPooledObject<DirContext>(dirContext);
    }

    @Override
    public boolean validateObject(PooledObject<DirContext> p) {
        log.info(p.getObject().toString() + " has a state of " + p.getState().name());
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
        log.info("Destroying context");
        DirContext dirContext = p.getObject();
        if (tlsHashMap.containsKey(dirContext)) {
            StartTlsResponse tls = tlsHashMap.get(dirContext);
            if (tls != null) {
                try {
                    tls.close();
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
            }
        }
        try {
            p.getObject().close();
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
        }
        // currently,  in BasePooledObjectFactory, this method is a no-op
        super.destroyObject(p);
    }

}
