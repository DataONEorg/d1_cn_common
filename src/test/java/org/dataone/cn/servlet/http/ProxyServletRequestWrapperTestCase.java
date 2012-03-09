/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dataone.cn.servlet.http;

import java.util.Enumeration;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.junit.*;
import static org.junit.Assert.*;
import org.springframework.mock.web.MockHttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author rwaltz
 */
public class ProxyServletRequestWrapperTestCase {

    public static Log logger = LogFactory.getLog(ProxyServletRequestWrapperTestCase.class);

    @Test
    public void testParameterMappings() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/Mock/object/?fromDate=2012-03-07T22:00:00%2B00:00");
        request.setParameter("fromDate", "2012-03-07T22:00:00+00:00");
        ProxyServletRequestWrapper proxyWrapper = new ProxyServletRequestWrapper(request);
        proxyWrapper.setQueryString("fromDate=2012-03-07T22:00:00%2B00:00");
        debugWrapper(request,proxyWrapper);

    }

    protected void debugWrapper(HttpServletRequest request, ProxyServletRequestWrapper proxyServletWrapper) {
        /*     see values with just a plain old request object being sent through */
        logger.info("proxy.request RequestURL: " + request.getRequestURL());
        logger.info("proxy.request RequestURI: " + request.getRequestURI());
        logger.info("proxy.request PathInfo: " + request.getPathInfo());
        logger.info("proxy.request PathTranslated: " + request.getPathTranslated());
        logger.info("proxy.request QueryString: " + request.getQueryString());
        logger.info("proxy.request ContextPath: " + request.getContextPath());
        logger.info("proxy.request ServletPath: " + request.getServletPath());
        logger.info("proxy.request Method: " + request.getMethod());
        logger.info("proxy.request toString:" + request.toString());
        /*      uncomment to see what the parameters of servlet passed in are  */
        Map<String, String> requestParameterMap = request.getParameterMap();
        for (String key : requestParameterMap.keySet()) {
            String[] values = request.getParameterValues(key);
            for (int i = 0; values.length > i; ++i) {
                logger.info("proxy.request.ParameterMap: " + key + " " + values[i]);
            }
        }
        logger.info("");
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            logger.info("proxy.request " + attributeName + ": " + request.getAttribute(attributeName));
        }
        /*      values of proxyServletWrapper request object to be sent through */
        logger.info("");
        logger.info("proxy.wrapper RequestURL: " + proxyServletWrapper.getRequestURL());
        logger.info("proxy.wrapper RequestURI: " + proxyServletWrapper.getRequestURI());
        logger.info("proxy.wrapper PathInfo: " + proxyServletWrapper.getPathInfo());
        logger.info("proxy.wrapper PathTranslated: " + proxyServletWrapper.getPathTranslated());
        logger.info("proxy.wrapper QueryString: " + proxyServletWrapper.getQueryString());
        logger.info("proxy.wrapper ContextPath: " + proxyServletWrapper.getContextPath());
        logger.info("proxy.wrapper ServletPath: " + proxyServletWrapper.getServletPath());
        logger.info("proxy.wrapper Method: " + proxyServletWrapper.getMethod());
        logger.info("proxy.wrapper toString: " + proxyServletWrapper.toString());

        /*      uncomment to see what the parameters of servlet passed in are  */
        Map<String, String> parameterMap = proxyServletWrapper.getParameterMap();

        for (String key : parameterMap.keySet()) {
            String[] values = proxyServletWrapper.getParameterValues(key);
            for (int i = 0; values.length > i; ++i) {
                logger.info("proxy.wrapper.ParameterMap: " + key + " " + values[i]);
            }
        }
        logger.info("");

        attributeNames = proxyServletWrapper.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            logger.info("proxy.wrapper " + attributeName + ": " + proxyServletWrapper.getAttribute(attributeName));
        }
        logger.info("");
    }
}
