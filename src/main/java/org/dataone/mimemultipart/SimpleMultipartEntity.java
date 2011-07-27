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
import java.util.Date;
import java.util.Vector;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.dataone.service.util.Constants;
import org.dataone.service.util.TypeMarshaller;
import org.jibx.runtime.JiBXException;

/**
 * @author berkley, nahf
 * A class to simplify creation of MIME multipart messages
 */
public class SimpleMultipartEntity extends MultipartEntity
{
	private Vector<String> tempfileNames = new Vector<String>();
    /**
     * add a file part to the MMP
     * @param name - the name assigned to the file part
     * @param file - the file to be put into the file part
     */
    public void addFilePart(String name, File file)
    {
// should let the processing method throw any exceptions.
//        if (!f.exists()) 
//        	throw new FileNotFoundException("File Not Found: " + f.getName());
//        if (f.isDirectory())
//        	throw new Exception("File cannot be a directory");
//        if (!f.canRead())
//        	throw new Exception("File not readable");
    	FileBody fileBody = new FileBody(file);
        addPart(name, fileBody);
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
			System.out.println("     bytes written: " + total);
			
		}
		
		FileBody fBody = new FileBody(outputFile);
		addPart(name, fBody);
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
    public void addFilePart(String name, Object serializableD1Object, Class type) 
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
            throw new RuntimeException("UTF-8 is not supported in MultipartRequestHandler.addParamPart: " + 
                    uee.getMessage());
        }
    }
    
    protected String getLastTempfile() {
    	return tempfileNames.lastElement();
    }
             
    
    private File generateTempFile()
    {
    	Date d = new Date();
		File tmpDir = new File(Constants.TEMP_DIR);
		File outputFile = new File(tmpDir, "mmp.output." + d.getTime());
		String afp = outputFile.getAbsolutePath();
		tempfileNames.add(afp);
		System.out.println("temp outputFile is: " + outputFile.getAbsolutePath());
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
