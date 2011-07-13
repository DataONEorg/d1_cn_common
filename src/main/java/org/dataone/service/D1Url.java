package org.dataone.service;

import java.util.Date;
import java.util.Vector;
import org.dataone.service.types.util.ServiceTypeUtil;

public class D1Url {

    private String url;
    private String resource;
    private String baseUrl;
    private Vector<String> pathElements = new Vector<String>();
    private Vector<String> paramV = new Vector<String>();

    public D1Url(String baseUrl, String resource) throws IllegalArgumentException {
        setBaseUrl(baseUrl);
        setResource(resource);
    }

    public D1Url(String baseUrl) throws IllegalArgumentException {
        setBaseUrl(baseUrl);
    }

    public void setBaseUrl(String baseUrl) throws IllegalArgumentException {
        this.baseUrl = trimAndValidateString(baseUrl);
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public void setResource(String res) {
        this.resource = res.trim();
    }

    public String getResource() {
        return this.resource;
    }

    /**
     * adds the next path element to the path portion of the URL, encoding unsafe characters.
     * Empty values to the pathElement parameter throws an exception
     * @param pathElement
     * @throws IllegalArgumentException
     */
    public void addNextPathElement(String pathElement) throws IllegalArgumentException {

        pathElements.add(EncodingUtilities.encodeUrlPathSegment(trimAndValidateString(pathElement)));
    }

    /**
     * adds a single parameter to the query portion of the URL (not a key-value pair)
     * encoding unsafe characters
     * @param param
     */
    public void addNonEmptyParam(String param) {
        try {
            paramV.add(EncodingUtilities.encodeUrlQuerySegment(trimAndValidateString(param)));
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * adds a key value pair to the query portion of the URL, placing '=' character between them,
     * and encoding unsafe characters.
     * If either key or value is empty or null, quietly does not add anything to the url.
     * @param key
     * @param value
     */
    public void addNonEmptyParamPair(String key, String value) {
        try {
            paramV.add(EncodingUtilities.encodeUrlQuerySegment(trimAndValidateString(key)) + "="
                    + EncodingUtilities.encodeUrlQuerySegment(trimAndValidateString(value)));
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * convenience method for adding date object to query parameters.  The date is converted to
     * GMT and serialized as a string.  If date is null, nothing is added to the URL.
     * @param key
     * @param date
     * @throws IllegalArgumentException
     */
    public void addDateParamPair(String key, Date dateLocalTime) {
        if (dateLocalTime != null) {
            // XXX should be using serializeDatetoUTC, but MNs at this point are not
            // fully ISO 8601 compliant, and this should work...
            String dateString = ServiceTypeUtil.serializeDateToUTC(dateLocalTime);
            paramV.add(EncodingUtilities.encodeUrlQuerySegment(trimAndValidateString(key)) + "="
                    + EncodingUtilities.encodeUrlQuerySegment(trimAndValidateString(dateString)));
        }
    }

    /**
     * adds a key value pair to the query portion of the URL, placing '=' character between them,
     * and encoding unsafe characters.
     * If either key or value is empty or null, quietly does not add anything to the url.
     * @param key
     * @param integer
     */
    public void addNonEmptyParamPair(String key, Integer integer) {
        if (integer != null) {
            paramV.add(EncodingUtilities.encodeUrlQuerySegment(trimAndValidateString(key)) + "="
                    + EncodingUtilities.encodeUrlQuerySegment(trimAndValidateString(integer.toString())));
        }
    }


    /**
     * Method for adding query params that bypasses encoding. Use sparingly, as it is a bit unsafe,
     * but useful if the choice of params is too much to put in a method signature.
     * @param param
     */
    public void addPreEncodedNonEmptyQueryParams(String param) {
        try {
            paramV.add(trimAndValidateString(param));
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    public String getUrl() {
        assembleUrl();
        return this.url;
    }

    public String toString() {
        return getUrl();
    }

    protected void assembleUrl() {
        url = baseUrl;
        url += joinToUrlWith("/", resource);
        for (int i = 0; i < pathElements.size(); i++) {
            url += joinToUrlWith("/", pathElements.get(i));
        }
        if (paramV.size() > 0) {
            this.url.replaceFirst("/$", "");  // remove trailing slash if necessary
            url += joinToUrlWith("?", paramV.get(0));
        }
        for (int i = 1; i < paramV.size(); i++) {
            url += joinToUrlWith("&", paramV.get(i));
        }
    }

    protected static String trimAndValidateString(String s) throws IllegalArgumentException {
        if (s == null || s.trim().isEmpty()) {
            throw new IllegalArgumentException("the string '" + s + "' cannot be null or empty or only whitespace");
        }
        return s.trim();
    }

    protected String joinToUrlWith(String j, String s) {
        if (s == null) {
            return "";
        }
        String joined = null;
        if (this.url.endsWith(j)) {
            if (s.startsWith(j)) {
                joined = (String) s.subSequence(1, s.length());
            } else {
                joined = s;
            }
        } else if (s.startsWith(j)) {
            joined = s;
        } else {
            joined = j + s;
        }
        return joined;
    }
}
