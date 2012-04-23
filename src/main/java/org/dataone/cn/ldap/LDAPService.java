/**
 * This work was created by participants in the DataONE project, and is
 * jointly copyrighted by participating institutions in DataONE. For 
 * more information on DataONE, see our web site at http://dataone.org.
 *
 *   Copyright ${year}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 * 
 * $Id$
 */

package org.dataone.cn.ldap;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dataone.configuration.Settings;


/**
 * Base LDAP class for shared LDAP operations
 * Intended to be used by the Identity Manager and the Identifier Reservation systems
 * 
 * @author leinfelder
 *
 */
public abstract class LDAPService {
	
	public static Log log = LogFactory.getLog(LDAPService.class);
      
	protected DirContext context = null;
	
	// look up defaults from configuration
	protected String server = Settings.getConfiguration().getString("cn.ldap.server");
	protected String admin = Settings.getConfiguration().getString("cn.ldap.admin");
	protected String password = Settings.getConfiguration().getString("cn.ldap.password");
	protected String base = null; // this setting needs to be overridden in each of the inheriting classes

	public DirContext getContext() throws NamingException {
		if (context == null) {
			context = getDefaultContext();
		}
	    return context;
	}
	

	protected DirContext getDefaultContext() throws NamingException {
		Hashtable<String, String> env = new Hashtable<String, String>();
	    /*
	     * Specify the initial context implementation to use.
	     * This could also be set by using the -D option to the java program.
	     * For example,
	     *   java -Djava.naming.factory.initial=com.sun.jndi.ldap.LdapCtxFactory \
	     *       Modattrs
	     */
	    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	    /* Specify host and port to use for directory service */
	    env.put(Context.PROVIDER_URL, server);
	    /* specify authentication information */
	    env.put(Context.SECURITY_AUTHENTICATION, "simple");
	    env.put(Context.SECURITY_PRINCIPAL, admin);
	    env.put(Context.SECURITY_CREDENTIALS, password);

        /* get a handle to an Initial DirContext */
        DirContext ctx = new InitialDirContext(env);
	    return ctx;
	}
	
	
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
        
        public abstract void setBase(String base);

        public String getBase() {
            return base;
        }
        
	public boolean removeEntry(String dn) {
		try {
			DirContext ctx = getContext();
			ctx.destroySubcontext(dn);
	    	log.debug("Removed entry: " + dn);
	    } catch (NamingException e) {
	    	log.error("Error removing entry: " + dn, e);
	        return false;
	    }
	    
	    return true;
	}
	
	// check the attribute for a given subject
	public boolean checkAttribute(String dn, String attributeName, String attributeValue) {
		try {
			DirContext ctx = getContext();
			SearchControls ctls = new SearchControls();
		    ctls.setSearchScope(SearchControls.OBJECT_SCOPE);
		    ctls.setReturningAttributes(new String[0]);  // do not return any attributes
		    
		    String searchCriteria = attributeName + "=" + attributeValue;
		    
	        NamingEnumeration results = 
	            ctx.search(dn, searchCriteria, ctls);
	        
	        boolean result = (results != null && results.hasMoreElements());
	        if (result) {
	        	log.debug("Found matching attribute: " + searchCriteria);
	        } else {
	        	log.warn("Did not find matching attribute: " + searchCriteria);
	        }
	        return result;
	    } catch (NamingException e) {
	    	log.error("Problem checking attribute: " + attributeName, e);
	    }
	    return false;
		
	}
	
	public List<Object> getAttributeValues(String dn, String attributeName) throws NamingException {
		DirContext ctx = getContext();
		SearchControls ctls = new SearchControls();
	    ctls.setSearchScope(SearchControls.OBJECT_SCOPE);
	    ctls.setReturningAttributes(new String[] {attributeName});  // just the one we want
	    
	    String searchCriteria = attributeName + "=*" ;
	    
	    NamingEnumeration<SearchResult> results = 
            ctx.search(dn, searchCriteria, ctls);
        
        SearchResult result;
		if (results != null ) {
        	log.debug("Found matching attribute: " + searchCriteria);
    		List<Object> values = new ArrayList<Object>();
        	while (results.hasMore()) {
        		result = results.next();
        		NamingEnumeration<? extends Attribute> attributes = result.getAttributes().getAll();
        		while (attributes.hasMore()) {
        			Object value = attributes.next().get();
        			values.add(value);
        		}
        	}
        	return values;
        }
	    
	    return null;
	}
	
	protected String parseAttribute(String original, String attribute) {
		String result = null;
		try {
			String temp = original;
			// ignore case
			int start = temp.toLowerCase().indexOf(attribute.toLowerCase() + "=");
			int end = temp.indexOf(",", start);
			temp = temp.substring(start, end);
			start = temp.indexOf("=") + 1;
			temp = temp.substring(start);
			result = temp;
		} catch (Exception e) {
			log.warn("could not parse attribute from string");
		}
		return result;
	}

	
	/**
	 * Constructs the necessary LDAP tree for the given DN
	 * Assuming CILogon format of
	 * 		CN=Benjamin Leinfelder A515,O=University of Chicago,C=US,DC=cilogon,DC=org
	 * @param dn the full DN from CILogon
	 * @return true if successfully added
	 * @throws NamingException
	 */
	protected boolean constructTree(String dn) throws NamingException {
	    // get the attributes
		String org = parseAttribute(dn, "o");
	    String country = parseAttribute(dn, "c");

	    // get the partial DNs
		String orgDN = dn.substring(dn.indexOf(",") + 1);
		String countryDN = orgDN.substring(orgDN.indexOf(",") + 1);

	    boolean exists = false;
	    // check for the country
    	try {
    		exists = checkAttribute(countryDN, "c", country);
    	} catch (Exception e) {
			exists = false;
		}
	    if (!exists) {
	    	addCountry(countryDN);
	    }
	    
	    // check for the org
    	try {
    		exists = checkAttribute(orgDN, "o", org);
    	} catch (Exception e) {
			exists = false;
		}
	    if (!exists) {
	    	addOrg(orgDN);
	    }
	    return true;
	}
	
	/**
	 * Adds the organization branch
	 * 	O=University of Chicago,C=US,DC=cilogon,DC=org
	 * @param dn the DN for the Organization being added
	 * @return true if added sucessfully
	 * @throws NamingException
	 */
	protected boolean addOrg(String dn) throws NamingException {
		// Values we'll use in creating the entry
	    Attribute objClasses = new BasicAttribute("objectclass");
	    //objClasses.add("top");
	    objClasses.add("organization");
	    
	    // get the parts
	    String org = parseAttribute(dn, "o");
	    Attribute oAttribute = new BasicAttribute("o", org);
	    
	    DirContext ctx = getContext();
        Attributes orig = new BasicAttributes();
        orig.put(objClasses);
        orig.put(oAttribute);

        // Add the entry
        ctx.createSubcontext(dn, orig);
        log.debug( "Added entry " + dn);

		return true;	
	}
	
	/**
	 * Adds the country branch
	 * 	C=US,DC=cilogon,DC=org
	 * @param dn the DN of the country being added
	 * @return true if added successfully
	 * @throws NamingException
	 */
	protected boolean addCountry(String dn) throws NamingException {
		// Values we'll use in creating the entry
	    Attribute objClasses = new BasicAttribute("objectclass");
	    //objClasses.add("top");
	    objClasses.add("country");
	    
	    // get the parts
	    String country = parseAttribute(dn, "c");
	    Attribute cAttribute = new BasicAttribute("c", country);
	    
	    DirContext ctx = getContext();
        Attributes orig = new BasicAttributes();
        orig.put(objClasses);
        orig.put(cAttribute);

        // Add the entry
        ctx.createSubcontext(dn, orig);
        log.debug( "Added entry " + dn);

		return true;	
	}
    public String getEnumerationValueString(NamingEnumeration namingEnum) throws NamingException {
        if (namingEnum.hasMore()) {
            return (String) namingEnum.next();
        } else {
            return "";
        }
    }
}
