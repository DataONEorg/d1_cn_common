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

package org.dataone.cn.servlet.http;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author rwaltz
 */
public class ProxyServletRequestWrapper extends HttpServletRequestWrapper implements HttpServletRequest, ServletRequest {

    public static Log logger = LogFactory.getLog(ProxyServletRequestWrapper.class);
    private String proxyPathTranslated = null;
    private String proxyQueryString = null;
    private String proxyRequestURI = null;
    private String proxyContextPath = null;
    private String proxyPathInfo = null;
    private String proxyServletPath = null;
    private String proxyHttpMethod = null;
    public HashMap<String, String[]> proxyParameterMap = new HashMap<String, String[]>();
    
    public ProxyServletRequestWrapper(HttpServletRequest request) {
        super(request);
        if (!request.getParameterMap().isEmpty()) {
            this.proxyParameterMap.putAll(request.getParameterMap());
        }
    }

    @Override
    public String getPathTranslated() {
        if (this.proxyPathTranslated == null) {
            return super.getPathTranslated();
        } else {
            return this.proxyPathTranslated;
        }
    }

    public void setPathTranslated(String pathTranslated) {
        this.proxyPathTranslated = pathTranslated;
    }

    @Override
    public String getQueryString() {
        if (this.proxyQueryString == null) {
            return super.getQueryString();
        } else {
            return this.proxyQueryString;
        }
    }

    public void setQueryString(String queryString) {
        this.proxyQueryString = queryString;

    }

    @Override
    public String getRequestURI() {
        if (this.proxyRequestURI == null) {
            return super.getRequestURI();
        } else {
            return this.proxyRequestURI;
        }
    }

    public void setRequestURI(String requestURI) {
        this.proxyRequestURI = requestURI;
    }

    @Override
    public String getContextPath() {
        if (this.proxyContextPath == null) {
            return super.getContextPath();
        } else {
            return this.proxyContextPath;
        }
    }

    public void setContextPath(String contextPath) {
        this.proxyContextPath = contextPath;
    }

    @Override
    public String getPathInfo() {
        if (this.proxyPathInfo == null) {
            return super.getPathInfo();
        } else {
            return this.proxyPathInfo;
        }
    }

    public void setPathInfo(String pathInfo) {
        this.proxyPathInfo = pathInfo;
    }

    @Override
    public String getServletPath() {
        if (this.proxyServletPath == null) {
            return super.getServletPath();
        } else {
            return this.proxyServletPath;
        }
    }

    public void setServletPath(String servletPath) {
        this.proxyServletPath = servletPath;
    }

    @Override
    public Enumeration getParameterNames() {
        if (this.proxyParameterMap.isEmpty()) {
            return super.getParameterNames();
        }
        StringBuffer parameterKeysString = new StringBuffer();
        for (String key : this.proxyParameterMap.keySet()) {
            parameterKeysString.append(key + " ");
        }
        parameterKeysString.deleteCharAt(parameterKeysString.lastIndexOf(" "));
        if (parameterKeysString.length() > 0) {
            return new StringTokenizer(parameterKeysString.toString());
        }
        return null;
    }

    @Override
    public String getParameter(String name) {

        if (this.proxyParameterMap.isEmpty()) {
            return super.getParameter(name);
        }
        if (this.proxyParameterMap.containsKey(name)) {
            String[] value = this.proxyParameterMap.get(name);
            return (value[0]);
        }
        return null;


    }

    @Override
    public String[] getParameterValues(String name) {
        if (this.proxyParameterMap.isEmpty()) {
            return super.getParameterValues(name);
        }
        if (this.proxyParameterMap.containsKey(name)) {
            return this.proxyParameterMap.get(name);
        }
        return null;
    }

    public void setParameterValues(String name, String[] values) {
        this.proxyParameterMap.put(name,values);
    }
    @Override
    public Map getParameterMap() {
        if (this.proxyParameterMap.isEmpty()) {
            return super.getParameterMap();
        }
        return this.proxyParameterMap;
    }

    @Override
    public String getMethod() {
        if (this.proxyHttpMethod == null) {
            return super.getMethod();
        } else {
            return this.proxyHttpMethod;
        }
    }
    public void setMethod(String method) {
        this.proxyHttpMethod = method;
    }
}
