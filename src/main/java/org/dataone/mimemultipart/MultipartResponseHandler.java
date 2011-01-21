/**
 * 
 */
package org.dataone.mimemultipart;

import java.io.*;
import java.util.*;

import org.apache.commons.io.IOUtils;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;

import javax.servlet.http.HttpServletRequest;

/**
 * @author berkley
 *
 */
public class MultipartResponseHandler
{
    private HttpServletRequest request;
    private File filePartFile;
    
    /**
     * constructor.  
     * @param request the servlet request to parse the input from
     * @param filePartFile a file handle to write any file contents to.  If
     * you are sure there are no file parts, this can be null.  If there are
     * more than one file parts, a counter will be appended to the name
     * of the file returned in the getParts hashtable.  
     */
    public MultipartResponseHandler(HttpServletRequest request, File filePartFile)
    {
        this.request = request;
        this.filePartFile = filePartFile;
    }
    
    /**
     * get the parts of the MMP
     * @return a hashtable with the name of each part hashed to the content
     * if the part is a file, a file handle will be returned.  If the part is
     * a param, the string value of the param will be returned.
     * @throws IOException 
     */
    public Hashtable<String, Object> getParts() throws IOException
    {
        Hashtable<String, Object> h = new Hashtable<String, Object>();
        MultipartParser parser = new MultipartParser(request, 999999999, false, false);
        Part part = parser.readNextPart();
        int count = 0;
        while(part != null)
        {
            String name = part.getName();
            if(part instanceof FilePart)
            {
                if(filePartFile == null)
                {
                    throw new IOException("MultipartResponseHandler.getParts: " +
                            "The multipart response has a file part in it so " +
                            "filePartFile cannot be null!");
                }
                File f = filePartFile;
                if(count > 0)
                {
                    f = new File(filePartFile.getAbsolutePath() + "-" + count);
                }
                
                FileOutputStream fos = new FileOutputStream(f);
                ((FilePart)part).writeTo(fos);
                fos.flush();
                fos.close();
                h.put(name, f);
                count++;
            }
            else
            {
                String value = ((ParamPart)part).getStringValue();
                h.put(name, value);
            }
            part = parser.readNextPart();
        }
        
        return h;
    }
}
