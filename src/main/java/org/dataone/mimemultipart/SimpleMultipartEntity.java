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
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dataone.service.Constants;

/**
 * @author berkley
 * A class to simplify creation of MIME multipart messages
 */
public class SimpleMultipartEntity extends MultipartEntity
{
        
    /**
     * add a file part to the MMP
     * @param f
     * @param name
     * @throws Exception 
     */
    public void addFilePart(File f, String name) throws Exception
    {
        if (!f.exists()) 
        	throw new Exception("File Not Found: " + f.getName());
        if (f.isDirectory())
        	throw new Exception("File cannot be a directory");
        if (!f.canRead())
        	throw new Exception("File not readable");
    	FileBody fileBody = new FileBody(f);
        addPart(name, fileBody);
    }
    
    /**
     * add a file part to the MMP, using InputStream
     * This method writes the contents of the stream to 
     * a temp file and sends it.  
     * @param is
     * @param name
     */
    public void addFilePart(InputStream is, String name)
    {
		File outputFile = generateTempFile();
		try {
			FileOutputStream os = new FileOutputStream(outputFile);	
			// transfer input stream to temp file
			IOUtils.copy(is, os);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FileBody fBody = new FileBody(outputFile);
		addPart(name, fBody);
    }
   
    /**
     * This method generates a temp files for sending, containing
     * the value parameter.  Encoding is in UTF-8.
     * @param name
     * @param value
     */
    public void addFilePart(String name, String value)
    {	
    	File outputFile = generateTempFile();
    	try {
    		FileOutputStream os = new FileOutputStream(outputFile);
    		OutputStreamWriter osw = new OutputStreamWriter(os,"UTF-8");
    		osw.write(value);
    		osw.flush();
    		osw.close();
    	} catch (FileNotFoundException e) {
    		// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    	FileBody fileBody = new FileBody(outputFile);
        addPart(name, fileBody);
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
            addPart(name, new StringBody(value, Charset.forName("UTF-8")));
        }
        catch(UnsupportedEncodingException uee)
        {
            throw new RuntimeException("UTF-8 is not supported in MultipartRequestHandler.addParamPart: " + 
                    uee.getMessage());
        }
    }
             
    
    private static File generateTempFile()
    {
    	Date d = new Date();
		File tmpDir = new File(Constants.TEMP_DIR);
		File outputFile = new File(tmpDir, "mmp.output." + d.getTime());
		System.out.println("temp outputFile is: " + outputFile.getAbsolutePath());
		return outputFile;
    }
    
}
