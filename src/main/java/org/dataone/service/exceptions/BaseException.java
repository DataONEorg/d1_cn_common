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
 */

package org.dataone.service.exceptions;

import java.io.StringWriter;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.dataone.service.types.Identifier;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A BaseException is the root of all DataONE service class exception messages.
 * Each exception contains a code, which typically corresponds to a high-level
 * HTTP error code, a detail_code, which classifies the exception to a more
 * constrained subtype of error, and a description of the error.  It can also
 * contain additional trace information used to debug the problem, which is
 * stored as a set of name-value pairs.
 *  
 * @author Matthew Jones
 */
public class BaseException extends Exception {

    static Logger logger = Logger.getLogger(BaseException.class.getName());
    /** The major error code associated with this exception. */
    private int code;
    
    /** The detailed error subcode associated with this exception. */
    private String detail_code;
    
    /** The optional PID associated with this exception. */
    private Identifier pid;
    
    /** Additional trace-level debugging information, as name-value pairs. */
    private TreeMap<String, String> trace_information;

    public final static int FMT_XML = 0;
    public final static int FMT_JSON = 1;
    public final static int FMT_HTML = 2;
    private DocumentBuilder documentBuilder = null;
    private Transformer transformer = null;
    private StringWriter strWtr = new StringWriter();

    /**
     * Construct a BaseException with the given code, detail code, and description.
     * 
     * @param code the code used to classify the exception
     * @param detail_code the detailed code for this exception
     * @param description the description of this exception
     */
    protected BaseException(int code, String detail_code, String description) {
        super(description);

        this.trace_information = new TreeMap<String, String>();
        this.code = code;
        this.detail_code = detail_code;
        try {

            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml"); //xml, html, text
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        } catch (ParserConfigurationException ex) {
            logger.error(ex.getMessage());
        } catch (TransformerConfigurationException ex) {
            logger.error(ex.getMessage());
        }
    }
  
    
    /**
     * Construct a BaseException with the given code, detail code, description,
     * and trace_information.
     * 
     * @param code the code used to classify the exception
     * @param detail_code the detailed code for this exception
     * @param pid: the identifier associated with the exception, and usually with the request
     * @param description the description of this exception
     * @param trace_information containing a Map of key/value pairs
     */
    protected BaseException(int code, String detail_code, Identifier pid, String description, 
            TreeMap<String, String> trace_information) {
        this(code, detail_code, description);
        this.setPid(pid);
        if (trace_information == null)
        	this.trace_information = new TreeMap<String, String>();
        else 
        	this.trace_information = trace_information;
    }
  
    
    /**
     * Construct a BaseException with the given code, detail code, description,
     * and trace_information.
     * 
     * @param code the code used to classify the exception
     * @param detail_code the detailed code for this exception
     * @param description the description of this exception
     * @param trace_information containing a Map of key/value pairs
     */
    protected BaseException(int code, String detail_code, String description, 
            TreeMap<String, String> trace_information) {
        this(code, detail_code, description);
        this.trace_information = trace_information;
    }
    
    /**
     * @param code the code to set
     */
    protected void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param detail_code the detail_code to set
     */
    public void setDetail_code(String detail_code) {
        this.detail_code = detail_code;
    }

    /**
     * @return the detail_code
     */
    public String getDetail_code() {
        return detail_code;
    }

    public Identifier getPid() {
    	return pid;
    }
    
    public void setPid(Identifier p) {
    	this.pid = p;
    }
    
    /**
     * @return the description
     */
    public String getDescription() {
        return getMessage();
    }
    
    /**
     * Add new detailed trace information, storing it the value under a known key.
     * @param key the key to index the trace value
     * @param value that trace value to be stored
     */
    public void addTraceDetail(String key, String value) {
        trace_information.put(key, value);
    }
    
    /**
     * Get the value of one of the trace detail parametersm retrieved by its 
     * key value.
     * @param key the key of the trace information to be retrieved
     * @return the String key value associated with the key
     */
    public String getTraceDetail(String key) {
        return trace_information.get(key);
    }
    
    /**
     * Return a Set representation of all of the keys for the trace values in 
     * this exception.
     * @return Set representation of all of the keys for the trace values
     */
    public Set<String> getTraceKeySet() {
        return trace_information.keySet();
    }
    
    /**
     * Serialize the format in XML, JSON, or HTML format. If format is unknown,
     * then return in XML format.
     * @param format an integer code indicating the format
     */
    public String serialize(int format) {
        switch(format) {
        case FMT_XML:
            return serializeXML();
        case FMT_JSON:
            return serializeJSON();
        case FMT_HTML:
            return serializeHTML();
        default:
            return serializeXML();
        }
    }
    
    /** Serialize the exception in XML format. */
    private String serializeXML() {
            /*        StringBuffer sb = new StringBuffer();
            sb.append("<?xml version=\"1.0\"?>\n");
            sb.append("<error errorCode='").append(getCode()).append("' ");
            sb.append("name='").append(getName()).append("' ");
            sb.append("detailCode='").append(getDetail_code()).append("'>\n");
            sb.append("  <description>").append(getDescription()).append("</description>\n");
            sb.append("  <traceInformation>\n");
            for (String key : this.getTraceKeySet()) {
            sb.append("    <value key='").append(key).append("'>");
            sb.append(trace_information.get(key)).append("</value>\n");
            }
            sb.append("  </traceInformation>\n");
            sb.append("</error>\n");
            return sb.toString(); */
        Document dom = documentBuilder.newDocument();
        // create the root node of the dom
        Element errorNode = dom.createElement("error");
        
        errorNode.setAttribute("name", getName());
        errorNode.setAttribute("detailCode", getDetail_code());
        errorNode.setAttribute("errorCode", Integer.toString(getCode()));
        if (getPid() != null)
        	errorNode.setAttribute("pid", getPid().getValue());
        Element description = dom.createElement("description");
        description.setTextContent(getDescription());
        
        dom.appendChild(errorNode);
        errorNode.appendChild(description);        
        if (!trace_information.isEmpty()) {
            Element traceInformation = dom.createElement("traceInformation");
            for (String key : this.getTraceKeySet()) {
                Element value = dom.createElement("value");
                value.setAttribute("key", key);
                value.setTextContent(trace_information.get(key));
                traceInformation.appendChild(value);
            }
            errorNode.appendChild(traceInformation);
        }

        try {
            return this.domToString(dom);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ex.getMessage();
        }

    }
    
    private String getName() {
    	String c = this.getClass().getName();
    	return  c.substring(c.lastIndexOf(".")+1);
    }
    
    /** Serialize the exception in JSON format.
     * TODO: Implement JSON serialization 
     */
    private String serializeJSON() {
        StringBuffer sb = new StringBuffer();
        sb.append("{'errorCode': ").append(getCode()).append(",\n");
        sb.append(" 'detailCode': ").append(getDetail_code()).append(",\n");
        if (getPid() != null)
        	sb.append(" 'pid': '").append(getPid().getValue()).append("',\n");
        sb.append(" 'description': '").append(getDescription()).append("',\n");
        sb.append(" 'traceInformation': {\n");
        for (String key : this.getTraceKeySet()) {
            sb.append("    '").append(key).append("': '");
            sb.append(trace_information.get(key)).append("',\n");
        }
        sb.append("  }\n");
        sb.append("}\n");
        return sb.toString();
    }
    
    /** Serialize the exception in HTML format.
     * TODO: Implement HTML serialization
     */
    private String serializeHTML() {
        StringBuffer sb = new StringBuffer();
        sb.append("<html>\n<body>\n"); 
        sb.append("  <p>\n");
        sb.append("    <dl>\n");
        sb.append("      <dt>Code</dt><dd class='errorCode'>").append(getCode()).append("</dd>\n");
        sb.append("      <dt>Detail Code</dt><dd class='detailCode'>").append(getDetail_code()).append("</dd>\n");
        sb.append("      <dt>PID</dt><dd class='pid'>").append(getPid()).append("</dd>\n");
        sb.append("    </dl>\n");
        sb.append("  </p>\n");
        sb.append("  <p class='description'>").append(getDescription()).append("</p>\n");
        sb.append("  <div class='traceInformation'>\n");
        for (String key : this.getTraceKeySet()) {
            sb.append("    <dt>").append(key).append("</dt>\n");
            sb.append("    <dd>").append(trace_information.get(key)).append("</dd>\n");
        }
        sb.append("  </div>\n");
        sb.append("</body>\n</html>\n");
        return sb.toString();
    }
    
    private String domToString(Document document) throws Exception {
        String result = null;
        if (document != null) {
            StreamResult strResult = new StreamResult(strWtr);
            transformer.transform(new DOMSource(document.getDocumentElement()), strResult);
            result = strResult.getWriter().toString();
        }
        return result;
    }
    
}
