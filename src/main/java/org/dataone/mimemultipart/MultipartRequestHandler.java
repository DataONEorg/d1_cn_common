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
package org.dataone.mimemultipart;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Vector;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.dataone.service.util.Constants;

/**
 * @author berkley
 * 
 * This class is being deprecated in favor of SimpleMultipartEntity 
 * and org.dataone.client.RestClient. Use of RestClient offers configuration
 * of security and connection management. 
 * 
 * A class to handle writing MIME multipart messages
 *  see http://hc.apache.org/httpcomponents-client-dev/httpclient/apidocs/org/apache/http/client/methods/package-tree.html
 */
@Deprecated
public class MultipartRequestHandler
{
	private static Logger logger = Logger.getLogger(MultipartRequestHandler.class);
	
	private Vector<String> tempfileNames = new Vector<String>();
	
        HttpClient httpclient;
        HttpEntityEnclosingRequestBase request;  // superclass of HttpPost and HttpGet 
        MultipartEntity entity;
    



    /**
     * constructor
     * @param url
     */
    public MultipartRequestHandler(String url, String httpMethod, HttpClient httpClient )
    {
        this.httpclient = httpClient;
        if (httpMethod == Constants.POST) 
            request = new HttpPost(url);        	
        if (httpMethod == Constants.PUT) 
            request = new HttpPut(url);       
        
        entity = new MultipartEntity();
        request.setEntity(entity);
    }




    /**
     * constructor
     * @param url
     */
    public MultipartRequestHandler(String url, String httpMethod )
    {
	this(url, httpMethod, new DefaultHttpClient());
    }

    
// complexities of getting the uri into the method make it likely that this constructor 
// will never be used.
    
//    public MultipartRequestHandler(String url, HttpEntityEnclosingRequestBase httpMethod )
//    {
//        httpclient = new DefaultHttpClient();
//        method = httpMethod;
//        entity = new MultipartEntity();
//        method.setEntity(entity);
//
//    }
    
    public HttpClient getHttpClient() {
	return this.httpclient;
    }


    /**
     *  replace the DefaultHttpClient created at time of instantiation
     *  with the specified one.
     */
    public void setHttpClient(HttpClient httpClient) {
	this.httpclient = httpClient;
    }


    /**
     * add a file part to the MMP
     * @param f
     * @param name
     */
    public void addFilePart(String name,File f)
    {
        FileBody fileBody = new FileBody(f);
        entity.addPart(name, fileBody);
    }
    
    /**
     * add a file part to the MMP, using InputStream
     * This method writes the contents of the stream to 
     * a temp file and sends it.  
     * @param is
     * @param name
     * @throws IOException 
     */
    public void addFilePart(String name,InputStream is) throws IOException
    {
		File outputFile = generateTempFile();

		FileOutputStream os = new FileOutputStream(outputFile);	
		// transfer input stream to temp file
		try {
			IOUtils.copy(is, os);
			//				byte[] buffer = new byte[255];  
			//				int bytesRead;  
			//				while ((bytesRead = is.read(buffer)) != -1) {  
			//					os.write(buffer, 0, bytesRead);  
			//				}
		} finally {	
			//				is.close();  
		}	
		os.flush();
		os.close();
		
		FileBody fBody = new FileBody(outputFile);
		entity.addPart(name, fBody);
    }
   
    /**
     * This method generates a temp files for sending, containing
     * the value parameter.  Encoding is in UTF-8.
     * @param name
     * @param value
     * @throws IOException 
     */
    public void addFilePart(String name, String value) throws IOException
    {	
    	File outputFile = generateTempFile();
 
    	FileOutputStream os = new FileOutputStream(outputFile);
    	OutputStreamWriter osw = new OutputStreamWriter(os,"UTF-8");
    	osw.write(value);
    	osw.flush();
    	osw.close();
    	
    	FileBody fileBody = new FileBody(outputFile);
        entity.addPart(name, fileBody);
    }

    
    /**
     * add a param part to the MMP
     * @param name
     * @param value
     */
    public void addParamPart(String name, String value)
    {
        try
        {
            entity.addPart(name, new StringBody(value, Charset.forName("UTF-8")));
        }
        catch(UnsupportedEncodingException uee)
        {
            throw new RuntimeException("UTF-8 is not supported in MultipartRequestHandler.addParamPart: " + 
                    uee.getMessage());
        }
    }
    
    /**
     * Returns the request for use by any org.apache.http.client.HttpClient
     * @return
     */
    public HttpUriRequest getRequest() {
    	return this.request;
    }
    /**
     * execute the request
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public HttpResponse executeRequest() 
        throws ClientProtocolException, IOException
    {
        HttpResponse response = httpclient.execute(request);
        logger.info("Response from MultipartRequestHandler.executeRequest: " + 
                response.getStatusLine());
        cleanupTempFiles();
        return response;
    }
         
    
    private File generateTempFile() throws IOException
    {
		File outputFile =  File.createTempFile("mmp.output.", null);
		String afp = outputFile.getAbsolutePath();
		tempfileNames.add(afp);
		logger.info("temp outputFile is: " + outputFile.getAbsolutePath());
		return outputFile;
    }
    
    /**
     * calling this method attempts to delete the client-side 
     * temp files from the system.  Safest if called after
     * response received from the request.
     * @return boolean
     * 	      [false] if not all files were deleted
     *        [true]  otherwise
     */
    public boolean cleanupTempFiles() {
    	boolean areAllFilesGone = true;
    	for (String f: tempfileNames) {
    		File fileToDelete = new File(f);
    		if (fileToDelete.exists()) {
    			if (!fileToDelete.delete()) {
    				areAllFilesGone = false;
    			}
    		}
    	}
    	return areAllFilesGone;
    }
    
    
}
