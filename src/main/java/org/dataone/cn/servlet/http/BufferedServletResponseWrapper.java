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

/**
 *
 * @author rwaltz
 */
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Arrays;
import org.dataone.service.exceptions.*;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.dataone.service.util.ExceptionHandler;
/**
 * A custom response wrapper that captures all output in a buffer.
 */
public class BufferedServletResponseWrapper  extends HttpServletResponseWrapper implements HttpServletResponse, ServletResponse {
    private BufferedServletOutputStream bufferedServletOut
            = new BufferedServletOutputStream( );

    private PrintWriter printWriter = null;
    private ServletOutputStream outputStream = null;
    private int status = -1;

    private BaseException d1Exception = null;
    private String contentType;
    public BufferedServletResponseWrapper(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
    }

    public byte[] getBuffer( ) {
        return this.bufferedServletOut.getBuffer( );
    }

    public PrintWriter getWriter( ) throws IOException {
        if (this.outputStream != null) {
            throw new IllegalStateException(
                    "The Servlet API forbids calling getWriter( ) after"
                    + " getOutputStream( ) has been called");
        }

        if (this.printWriter == null) {
            this.printWriter = new PrintWriter(this.bufferedServletOut);
        }
        return this.printWriter;
    }

    public ServletOutputStream getOutputStream( ) throws IOException {
        if (this.printWriter != null) {
            throw new IllegalStateException(
                "The Servlet API forbids calling getOutputStream( ) after"
                + " getWriter( ) has been called");
        }

        if (this.outputStream == null) {
            this.outputStream = this.bufferedServletOut;
        }
        return this.outputStream;
    }

    // override methods that deal with the response buffer

    public void flushBuffer( ) throws IOException {
        if (this.outputStream != null) {
            this.outputStream.flush( );
        } else if (this.printWriter != null) {
            this.printWriter.flush( );
        }
    }

    public int getBufferSize( ) {
        return this.bufferedServletOut.getBuffer( ).length;
    }

    public void reset( ) {
        this.bufferedServletOut.reset( );
    }

    public void resetBuffer( ) {
        this.bufferedServletOut.reset( );
    }

    public void setBufferSize(int size) {
        this.bufferedServletOut.setBufferSize(size);
    }
    
    // overriding to make the status gettable.
    public void setStatus(int sc) {
    	this.status = sc;
    	super.setStatus(sc);
    }
    
    public int getStatus() {
    	return this.status;
    }
    public Boolean isException()  {
        String errorCheck = new String(Arrays.copyOfRange(getBuffer(), 0, 100));
        BaseException d1Exception = null;
        Boolean isException = null;
        if (errorCheck.contains("<error")) {
            isException = true;
            try {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(getBuffer());
                
                // get the exception from getSystemMetadata in order to re-cast it as a resolve error
                String statusInt = String.valueOf(getStatus());
                d1Exception = (BaseException) ExceptionHandler.deserializeXml(inputStream, statusInt + ": ");

                setD1Exception(d1Exception);
            } catch (IllegalStateException ex) {
                d1Exception = new ServiceFailure("0", "BaseExceptionHandler.deserializeXml: " + ex.getMessage());
            } catch (ParserConfigurationException ex) {
                d1Exception = new  ServiceFailure("0", "BaseExceptionHandler.deserializeXml: " + ex.getMessage());
            } catch (SAXException ex) {
               d1Exception = new ServiceFailure("0", "BaseExceptionHandler.deserializeXml: " + ex.getMessage());
            } catch (IOException ex) {
               d1Exception = new  ServiceFailure("0", "BaseExceptionHandler.deserializeXml: " + ex.getMessage());
            }
            setD1Exception(d1Exception);
        } else {
            isException = false;
        }
        return isException;
    }

    public BaseException getD1Exception() {
        return this.d1Exception;
    }
    public void setD1Exception(BaseException d1Exception) {
        this.d1Exception = d1Exception;
    }
    @Override
    public void setContentType(String type) {
        this.contentType = type;
        super.setContentType(type);
    }

    @Override
    public String getContentType() {
        return contentType;
    }
} 
