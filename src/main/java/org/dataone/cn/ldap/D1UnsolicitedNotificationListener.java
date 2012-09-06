/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dataone.cn.ldap;

import javax.naming.event.NamingExceptionEvent;
import javax.naming.ldap.UnsolicitedNotificationEvent;
import javax.naming.ldap.UnsolicitedNotificationListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
* 
* Close the ldapService if a namingException is thrown,
* this will force a reconnection to LDAP for the Service upon the next
* request.
* 
* namingExceptionThrown gets called multiple times when the slapd is shutdown
* notificationReceived is not called at all
* 
*/
class D1UnsolicitedNotificationListener implements UnsolicitedNotificationListener {
    public static Log log = LogFactory.getLog(D1UnsolicitedNotificationListener.class);
    LDAPService ldapService = null;

    public D1UnsolicitedNotificationListener(LDAPService ldap) {
        this.ldapService = ldap;
    }
    public void notificationReceived(UnsolicitedNotificationEvent evt) {
        log.warn("received: " + evt + "-" + evt.getNotification().getID());
    }

    public void namingExceptionThrown(NamingExceptionEvent evt) {
        log.warn(evt.getException());
        ldapService.closeContext();
        evt.getException().printStackTrace();
    }

}
