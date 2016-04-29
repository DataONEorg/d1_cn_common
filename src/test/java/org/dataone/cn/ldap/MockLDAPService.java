/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.cn.ldap;

import org.dataone.configuration.Settings;

/**
 * Test Public and Protected members of the LDAPService class
 * 
 * @author waltz
 */
public class MockLDAPService extends LDAPService {
    
    public MockLDAPService () {
         this.setBase(Settings.getConfiguration().getString("mock.ldap.base"));
    }
}
