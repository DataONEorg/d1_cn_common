package org.dataone.service;

import java.util.Map;
import java.util.Vector;

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
	

	
	public void addNonEmptyParamPair(String key, String value) throws IllegalArgumentException {
		paramV.add(EncodingUtilities.encodeUrlQuerySegment(trimAndValidateString(key)) + "=" +
				EncodingUtilities.encodeUrlQuerySegment(trimAndValidateString(value)));
	}
	
	public void addNonEmptyParam(String param) throws IllegalArgumentException {
		paramV.add(EncodingUtilities.encodeUrlQuerySegment(trimAndValidateString(param)));
	}
	/**
	 * Method for adding query params that bypasses encoding. Use sparingly, as it is a bit unsafe, 
	 * but useful if the choice of params is too much to put in a method signature. 
	 * @param param
	 */
	public void addPreEncodedNonEmptyQueryParams(String param) {
		paramV.add(trimAndValidateString(param));
	}
	
	public void addNextPathElement(String pathEl) throws IllegalArgumentException {
		pathElements.add(EncodingUtilities.encodeUrlPathSegment(trimAndValidateString(pathEl)));
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
		url += joinToUrlWith("/",resource);
		for (int i = 0; i < pathElements.size(); i++) {
			url += joinToUrlWith("/",pathElements.get(i));
		}
		if (paramV.size() > 0) {
			this.url.replaceFirst("/$", "");  // remove trailing slash if necessary
			url += joinToUrlWith("?",paramV.get(0));
		}
		for (int i = 1; i < paramV.size(); i++) {
			url += joinToUrlWith("&",paramV.get(i));
		}
	}
	
	protected static String trimAndValidateString(String s) throws IllegalArgumentException {
		if (s == null || s.trim().isEmpty()) 
			throw new IllegalArgumentException("the string '"+ s + "' cannot be null or empty or only whitespace");
		return s.trim();
	}
	
	protected String joinToUrlWith(String j, String s) {
		if (s == null)
			return "";
		String joined = null;
		if (this.url.endsWith(j))
			if (s.startsWith(j))
				joined = (String) s.subSequence(1, s.length());
			else
				joined = s;
		else
			if (s.startsWith(j))
				joined = s;
			else
				joined =  j + s;
		return joined;
	}

}


