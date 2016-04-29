/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dataone.cn.ldap;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.event.NamingExceptionEvent;
import javax.naming.ldap.UnsolicitedNotificationEvent;
import javax.naming.ldap.UnsolicitedNotificationListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
* 
* Close the dirContext if a namingException is thrown,
* this will force a reconnection to LDAP for the Service upon the next
* request.
* 
* namingExceptionThrown gets called multiple times when the slapd is shutdown
* notificationReceived is not called at all
* 
*/
class DirContextUnsolicitedNotificationListener implements UnsolicitedNotificationListener {
    public static Log log = LogFactory.getLog(DirContextUnsolicitedNotificationListener.class);
    DirContext dirContext = null;

    public DirContextUnsolicitedNotificationListener(DirContext ldap) {
        this.dirContext = ldap;
    }
    public void notificationReceived(UnsolicitedNotificationEvent evt) {
        log.warn("received: " + evt + "-" + evt.getNotification().getID());
    }

    public void namingExceptionThrown(NamingExceptionEvent evt) {
        log.warn(evt.getException());


    }

}
