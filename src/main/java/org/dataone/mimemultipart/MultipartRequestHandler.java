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

import java.io.*;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dataone.service.Constants;

/**
 * @author berkley
 * A class to handle writing MIME multipart messages
 */
public class MultipartRequestHandler
{

	DefaultHttpClient httpclient;
    HttpEntityEnclosingRequestBase method;
    MultipartEntity entity;
    
    /**
     * cunstructor
     * @param url
     */
    public MultipartRequestHandler(String url, String httpMethod )
    {
        httpclient = new DefaultHttpClient();
        if (httpMethod == Constants.POST) 
            method = new HttpPost(url);        	
        if (httpMethod == Constants.PUT) 
            method = new HttpPut(url);       
        
        entity = new MultipartEntity();
        method.setEntity(entity);
    }
    
    
    public MultipartRequestHandler(String url, HttpEntityEnclosingRequestBase httpMethod )
    {
        httpclient = new DefaultHttpClient();
        method = httpMethod;
        entity = new MultipartEntity();
        method.setEntity(entity);
    }
    
    /**
     * add a file part to the MMP
     * @param f
     * @param name
     */
    public void addFilePart(File f, String name)
    {
        FileBody fileBody = new FileBody(f);
        entity.addPart(name, fileBody);
    }
    
    /**
     * add a file part to the MMP, using InputStream
     * Does not set contentLength, so not guaranteed to
     * work on all servers.
     * @param is
     * @param name
     */
    public void addFilePart(InputStream is, String name)
    {
    	InputStreamBody isBody = new InputStreamBody(is,name);
    	// InputStreamBody sets contentLength to -1, 
    	// and is not guaranteed to work on all servers.
    	entity.addPart(name, isBody);
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
     * execute the request
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public HttpResponse executeRequest() 
        throws ClientProtocolException, IOException
    {
        HttpResponse response = httpclient.execute(method);
        System.out.println("Response from MultipartRequestHandler.executeRequest: " + 
                response.getStatusLine());
        return response;
    }
     
}
