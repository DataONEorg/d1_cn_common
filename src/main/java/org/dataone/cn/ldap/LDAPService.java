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
import java.util.Arrays;
import java.util.List;
import javax.naming.InvalidNameException;

import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Base LDAP class for shared LDAP operations. Intended to be used by the
 * Identity Manager and the Identifier Reservation systems
 * Also used by Cn Node Registry
 * 
 * @author leinfelder
 * 
 */
public abstract class LDAPService {

    public static Log log = LogFactory.getLog(LDAPService.class);
    private String base;

    protected boolean removeEntry(DirContext ctx, String dn) {
        try {

            ctx.destroySubcontext(new LdapName(dn));
            log.debug("Removed entry: " + dn);
        } catch (NamingException e) {
            log.error("Error removing entry: " + dn, e);
            return false;
        }

        return true;
    }

    /**
     * check the attribute for a given subject
     * @see http://www.ietf.org/rfc/rfc2254.txt for considerations on special characters
     * @param dn
     * @param attributeName
     * @param attributeValue
     * @return
     */
    
    protected boolean checkAttribute(DirContext ctx, String dn, String attributeName, String attributeValue) {
        NamingEnumeration results = null;
        boolean result = false;
        try {

            SearchControls ctls = new SearchControls();
            ctls.setSearchScope(SearchControls.OBJECT_SCOPE);
            ctls.setReturningAttributes(new String[0]); // do not return any
                                                        // attributes
            
            // Handle escaping the escape character in filter
            // @see http://www.ietf.org/rfc/rfc2254.txt
            attributeValue = attributeValue.replace("\\", "\\5c");
            String searchCriteria = attributeName + "=" + attributeValue;

            results = ctx.search(new LdapName(dn), searchCriteria, ctls);

            result = (results != null && results.hasMoreElements());
            if (result) {
                log.debug("Found matching attribute: " + searchCriteria);
            } else {
                log.warn("Did not find matching attribute: " + searchCriteria);
            }

        } catch (NamingException e) {
            log.warn("Problem checking attribute: " + attributeName, e);
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (NamingException ex) {
                    log.error("Problem closing results search for " + attributeName, ex);
                }
            }
        }
        return result;

    }

    protected List<Object> getAttributeValues(DirContext ctx, String dn, String attributeName) throws NamingException {

        SearchControls ctls = new SearchControls();
        ctls.setSearchScope(SearchControls.OBJECT_SCOPE);
        ctls.setReturningAttributes(new String[] { attributeName }); // just the
                                                                     // one we
                                                                     // want

        String searchCriteria = attributeName + "=*";

        NamingEnumeration<SearchResult> results = null;
        try {
        	results = ctx.search(new LdapName(dn), searchCriteria, ctls);
        } catch (NameNotFoundException nnfe) {
        	// certainly won't have attributes
        	log.warn("Could not find LDAP entry for DN: " + dn);
        	return null;
        }

        SearchResult result;
        if (results != null) {
            log.debug("Found matching attribute: " + searchCriteria);
            List<Object> values = new ArrayList<Object>();
            while (results.hasMore()) {
                result = results.next();
                NamingEnumeration<? extends Attribute> attributes = result.getAttributes().getAll();
                while (attributes.hasMore()) {
                	Attribute attribute = attributes.next();
                	NamingEnumeration<?> attributeValues = attribute.getAll();
                    while (attributeValues.hasMore()) {
	                    Object value = attributeValues.next();
	                    values.add(value);
	                    log.debug("Attribute value: " + value);
                    }
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
            if (end < 0) {
                temp = temp.substring(start);
            } else {
            	temp = temp.substring(start, end);
            }
            start = temp.indexOf("=") + 1;
            temp = temp.substring(start);
            result = temp;
            // for escaped characters in the RDN
            result = (String) Rdn.unescapeValue(result);
        } catch (Exception e) {
            log.warn("could not parse attribute from string: " + original, e);
        }
        return result;
    }

    /**
     * Constructs the necessary LDAP tree for the given DN Assuming CILogon
     * format:
     * "CN=Benjamin Leinfelder A515,O=University of Chicago,C=US,DC=cilogon,DC=org"
     * or LDAP format:
     * "uid=kepler,o=unaffiliated,dc=ecoinformatics,dc=org"
     * 
     * @param dn
     *            the full DN from CILogon
     * @return true if successfully added
     * @throws NamingException
     */
    protected boolean constructTree(DirContext ctx, String dn) throws NamingException {
        
    	// get the attributes
    	LdapName ldapName = new LdapName(dn);
    	List<Rdn> rdns = ldapName.getRdns();
    	// iterate to build missing tree components
    	LdapName partialDn = null;
    	for (int i = 0 ; i < rdns.size(); i++) {
    		Rdn rdn = rdns.get(i);
    		if (partialDn == null) {
    			partialDn = new LdapName(Arrays.asList(rdn));
    		} else {
        		partialDn.add(rdn);
    		}
    		boolean exists = false;
            // check for the the dn
            try {
                exists = checkAttribute(ctx, partialDn.toString(), rdn.getType(), rdn.getValue().toString());
            } catch (Exception e) {
                exists = false;
            }
            if (!exists) {
            	String type = rdn.getType();
            	if (type.equalsIgnoreCase("c")) {
            		addCountry(ctx,partialDn.toString());
            	}
            	if (type.equalsIgnoreCase("o")) {
            		addOrg(ctx,partialDn.toString());
            	}
            	if (type.equalsIgnoreCase("ou")) {
            		addOrgUnit(ctx,partialDn.toString());
            	}
            	if (type.equalsIgnoreCase("dc")) {
            		addDc(ctx,partialDn.toString());
            	}
            }
    		
    	}

        return true;
    }

    /**
     * Adds the organization branch of the given DN to the Context.
     * For example, "O=University of Chicago,C=US,DC=cilogon,DC=org",
     * add "O=University of Chicago" to "DC=cilogon,DC=org"
     * 
     * @param dn: the DN for the Organization being added
     * @return true if added sucessfully
     * @throws NamingException
     */
    protected boolean addOrg(DirContext ctx, String dn) throws NamingException {
        // Values we'll use in creating the entry
        Attribute objClasses = new BasicAttribute("objectclass");
        // objClasses.add("top");
        objClasses.add("organization");

        // get the parts
        String org = parseAttribute(dn, "o");
        Attribute oAttribute = new BasicAttribute("o", org);

        Attributes orig = new BasicAttributes();
        orig.put(objClasses);
        orig.put(oAttribute);

        // Add the entry
        ctx.createSubcontext(dn, orig);
        log.debug("Added entry " + dn);

        return true;
    }
    
    /**
     * Adds the organization unit of the given DN to the Context.
     * For example, "OU=Account,DC=ecoinformatics,DC=org",
     * add "OU=Account" to "DC=ecoinformatics,DC=org"
     * 
     * @param dn: the DN for the Organizational Unit being added
     * @return true if added sucessfully
     * @throws NamingException
     */
    protected boolean addOrgUnit(DirContext ctx, String dn) throws NamingException {
        // Values we'll use in creating the entry
        Attribute objClasses = new BasicAttribute("objectclass");
        // objClasses.add("top");
        objClasses.add("organizationalUnit");

        // get the parts
        String orgUnit = parseAttribute(dn, "ou");
        Attribute oAttribute = new BasicAttribute("ou", orgUnit);

        Attributes orig = new BasicAttributes();
        orig.put(objClasses);
        orig.put(oAttribute);

        // Add the entry
        ctx.createSubcontext(dn, orig);
        log.debug("Added entry " + dn);

        return true;
    }
    
    /**
     * Adds the DC branch of the given DN to the Context.
     * For example, "DC=ecoinformatics,DC=org",
     * add ""DC=ecoinformatics" to "DC=org"
     * 
     * @param dn: the DN for the DC being added
     * @return true if added successfully
     * @throws NamingException
     */
    protected boolean addDc(DirContext ctx, String dn) throws NamingException {
        // Values we'll use in creating the entry
        Attribute objClasses = new BasicAttribute("objectclass");
        objClasses.add("dcObject");
        objClasses.add("organization");

        // get the parts
        String dc = parseAttribute(dn, "dc");
        Attribute dcAttribute = new BasicAttribute("dc", dc);
        Attribute oAttribute = new BasicAttribute("o", dc);

        Attributes orig = new BasicAttributes();
        orig.put(objClasses);
        orig.put(dcAttribute);
        orig.put(oAttribute);

        // Add the entry
        ctx.createSubcontext(dn, orig);
        log.debug("Added entry " + dn);

        return true;
    }

    /**
     * Adds the country branch of the given DN to the Context.  for example:
     * "C=US,DC=cilogon,DC=org" adds 'C=US' to 'DC=cilogon,DC=org' 
     * 
     * @param dn
     *            the DN of the country being added
     * @return true if added successfully
     * @throws NamingException
     */
    protected boolean addCountry(DirContext ctx, String dn) throws NamingException {
        // Values we'll use in creating the entry
        Attribute objClasses = new BasicAttribute("objectclass");
        // objClasses.add("top");
        objClasses.add("country");

        // get the parts
        String country = parseAttribute(dn, "c");
        Attribute cAttribute = new BasicAttribute("c", country);

        Attributes orig = new BasicAttributes();
        orig.put(objClasses);
        orig.put(cAttribute);

        // Add the entry
        ctx.createSubcontext(dn, orig);
        log.debug("Added entry " + dn);

        return true;
    }

    /**
     * return the next value of a NamingEnumeration, or empty String if there are
     * not any more.  (Guaranteed not to return null).  
     * @param namingEnum
     * @return
     * @throws NamingException
     */
    public String getEnumerationValueString(NamingEnumeration namingEnum) throws NamingException {
        if (namingEnum.hasMore()) {
            return (String) namingEnum.next();
        } else {
            return "";
        }
    }
    

    public String getBase() {
        return this.base;
    }

    public void setBase(String base) {
        this.base = base;
    }
    
}
