package org.dataone.service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author berkley
 * A parser to deal with the NodeList
 */
public class NodeListParser
{
    private static String nodelistSchemaLocation = "https://repository.dataone.org/software/cicore/branches/D1_SCHEMA_0_6_2/dataoneTypes.xsd";
    
    public static Map<String, String> parseNodeListFile(InputStream nodeListStream)
        throws SAXException, IOException, XPathExpressionException, ParserConfigurationException
    {
        return parseNodeListFile(nodeListStream, nodelistSchemaLocation, true);
    }
    
    /**
     * Parses the stream for node information.  returns a map of identifier -> url.
     * returns null if the nodelist is not found or does not produce any node information
     */
    public static Map<String,String> parseNodeListFile(InputStream nodeListStream, String xsdUrlString, boolean useSchema)
        throws SAXException, IOException, XPathExpressionException, ParserConfigurationException
    { 
        if (nodeListStream == null) 
        {
            return null;
        }
        
        Hashtable<String, String> baseUrlMap = new Hashtable<String, String>();
        // -------------build the XML parser and use it
        DocumentBuilder parser = createNSDOMParser();
        Document document = parser.parse(new InputSource(nodeListStream));
        
        
        // ----------- validate the document against the schema
        if (useSchema) {
        	Schema schema = createXsdSchema(xsdUrlString, useSchema);
        	Validator v = schema.newValidator();
        	v.validate(new DOMSource(document));
        }
        
        // ---------- compile the XPath expression that selects nodes
        XPathFactory xFactory = XPathFactory.newInstance();
        Object result;
        XPath xpath = xFactory.newXPath();
        XPathExpression expr = xpath.compile("//node");
        result = expr.evaluate(document, XPathConstants.NODESET);
        org.w3c.dom.NodeList targetNodes = (org.w3c.dom.NodeList) result;

        for (int i = 0; i < targetNodes.getLength(); i++) 
        {
            org.w3c.dom.NodeList d1nodeElements = targetNodes.item(i).getChildNodes(); 
            String nodeID = null;
            String baseURL = null;
            for (int j = 0; j < d1nodeElements.getLength(); j++) 
            {
                if (d1nodeElements.item(j).getNodeName() == "identifier") 
                {
                    nodeID = d1nodeElements.item(j).getTextContent();
                }
                if (d1nodeElements.item(j).getNodeName() == "baseURL") 
                {
                    baseURL = d1nodeElements.item(j).getTextContent();
                }
            }
            if (nodeID == null || nodeID.isEmpty()) 
            {
                return null;
            } 
            else if (baseURL == null || baseURL.isEmpty()) 
            {
                return null;
            } 
            else 
            {
                baseUrlMap.put(nodeID, baseURL);
            }
        }
        
        if (baseUrlMap.isEmpty()) 
        {
            return null;
        }       
        
        return baseUrlMap;
    }
    
    private static Schema createXsdSchema(String xsdUrlString, boolean useSchema)
        throws MalformedURLException, IOException, SAXException
    {
        Schema schema;
        
        if (!useSchema) return null;
        
        // create a SchemaFactory capable of understanding WXS schemas
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile; 
        if (xsdUrlString.startsWith("http")) 
        {
            URL xsdUrl = new URL(xsdUrlString);
            URLConnection xsdUrlConnection = xsdUrl.openConnection();
            InputStream xsdUrlStream = xsdUrlConnection.getInputStream();
            schemaFile = new StreamSource(xsdUrlStream);
        } 
        else 
        {
            schemaFile = new StreamSource(new File(xsdUrlString));
        }
        schema = factory.newSchema(schemaFile);         
        return schema;
    }
    
    /**
     * create a DOMParser
     * @return
     * @throws ParserConfigurationException
     */
    private static DocumentBuilder createNSDOMParser() throws ParserConfigurationException
    {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setIgnoringElementContentWhitespace(true);
        documentBuilderFactory.setNamespaceAware(true);
        DocumentBuilder parser = documentBuilderFactory.newDocumentBuilder();
        return parser;
    }
}
