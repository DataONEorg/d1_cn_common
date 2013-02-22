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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.dataone.service.util.TypeMarshaller;
import org.jibx.runtime.JiBXException;

/**
 * @author berkley, nahf
 * A class to simplify creation of MIME multipart messages
 */
public class SimpleMultipartEntity extends MultipartEntity
{
	private static Log log = LogFactory.getLog(SimpleMultipartEntity.class);
	private Vector<File> tempFiles = new Vector<File>();
 
	private String mmpDescription = "";
	
	
	
	/**
	 * Returns a concise description of the existing file parts, as comma-separated
	 * key-value pairs.  Values for file parts are its filename and size.
	 * @return
	 */
	public String getDescription() {
		return mmpDescription;
	}
	
	/**
     * add a file part to the MMP
     * @param name - the name assigned to the file part
     * @param file - the file to be put into the file part
     */
    public void addFilePart(String name, File file)
    {
    	FileBody fileBody = new FileBody(file);
        addPart(name, fileBody);
        mmpDescription += String.format("FilePart:%s = %s (%d bytes); ", 
        		name, file.getAbsoluteFile(), file.length());
    }
    
    /**
     * add a file part to the MMP, using InputStream
     * This method writes the contents of the stream to 
     * a temp file and sends it.  
     * @param name - the name assigned to the file part
     * @param is - the inputStream
     * @throws IOException 
     */
    public void addFilePart(String name, InputStream is) throws IOException
    {
		File outputFile = generateTempFile();
		FileOutputStream os = null;
		int total = 0;
		try {
			os = new FileOutputStream(outputFile);
			// transfer input stream to temp file
			byte[] bytebuffer = new byte[4096];
			
			
			int num = is.read(bytebuffer);
			while (num != -1) {
				os.write(bytebuffer, 0, num);
				total += num;
				num = is.read(bytebuffer);
			}
		} finally {
			os.flush();
			os.close();
			log.debug("     bytes written: " + total);
			
		}
		
		FileBody fBody = new FileBody(outputFile);
		addPart(name, fBody);
		
        mmpDescription += String.format("FilePart:%s = %s (%d bytes); ", 
        		name, outputFile.getAbsoluteFile(), outputFile.length());
    }
    
    /**
     * The DataONE object (serializable datatype) passed in
     * is serialized as the specified type to file.  Then added
     * as a file part.
     * 
     * @param name
     * @param serializableD1Object - the dataone serializable object to add 
     * @param type - class of the serializable dataone datatype
     * @throws IOException 
     * @throws JiBXException 
     */
    public void addFilePart(String name, Object serializableD1Object) 
    throws IOException, JiBXException
    {
    	// create temp file
    	File outputFile = generateTempFile();
    
    	FileOutputStream fileOut = new FileOutputStream(outputFile);
    	
        TypeMarshaller.marshalTypeToOutputStream(serializableD1Object, fileOut);
    	fileOut.flush();
    	fileOut.close();
    	
    	// create the new file body
    	FileBody fBody = new FileBody(outputFile);
		addPart(name, fBody);
		
        mmpDescription += String.format("FilePart:%s = %s (%d bytes); ", 
        		name, outputFile.getAbsoluteFile(), outputFile.length());
    }

    @Deprecated
    public void addFilePart(String name, Object serializableD1Object, Class type) 
    throws IOException, JiBXException
    {
    	addFilePart(name, serializableD1Object);
    }
    
    
    /**
     * This method generates a temp files for sending, containing
     * the value parameter.  Encoding is in UTF-8.
     * @param name - the name assigned to the file part
     * @param value - the String to be passed as a file part
     * @throws IOException 
     */
    public void addFilePart(String name, String value) throws IOException
    {	
    	File outputFile = generateTempFile();
    	OutputStreamWriter osw = null;
    	try {
    		FileOutputStream os = new FileOutputStream(outputFile);
    		osw = new OutputStreamWriter(os,"UTF-8");
    		osw.write(value);
    	} finally {
    		osw.flush();
    		osw.close();
		}   	
    	FileBody fileBody = new FileBody(outputFile);
        addPart(name, fileBody);
        
        mmpDescription += String.format("FilePart:%s = %s (%d bytes); ", 
        		name, outputFile.getAbsoluteFile(), outputFile.length());
    }

    
    /**
     * add a param part to the MMP
     * @param name - the name (key) assigned to the param part
     * @param value - the value associated to the name 
     */
    public void addParamPart(String name, String value)
    {
        try
        {
            addPart(name, new StringBody(value, Charset.forName("UTF-8")));
        }
        catch(UnsupportedEncodingException uee)
        {
            throw new RuntimeException("UTF-8 is not supported in SimpleMultipartEntity.addParamPart: " + 
                    uee.getMessage());
        }
        
        mmpDescription += String.format("ParamPart:%s = %s; ", name, value);
    }
    
    protected String getLastTempfile() {
    	return tempFiles.lastElement().getAbsolutePath();
    }
             
    
    private File generateTempFile() throws IOException
    {                
		File outputFile = File.createTempFile("mmp.output.", null);

		tempFiles.add(outputFile);
		log.info("temp outputFile is: " + outputFile.getAbsolutePath());
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
    	for (File file: tempFiles) {
    		if (file.exists()) {
    			if (!file.delete()) {
    				areAllFilesGone = false;
    				log.warn("failed to delete temp file: " + file.getAbsolutePath());
    			}
    		}
    	}
    	return areAllFilesGone;
    }
    
}
