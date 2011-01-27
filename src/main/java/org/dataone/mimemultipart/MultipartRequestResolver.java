/**
 * 
 */
package org.dataone.mimemultipart;

import java.io.*;
import java.util.*;


import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * DataOne conversion of HttpServletRequests into a DataONE MultipartRequest via
 *  <a href="http://jakarta.apache.org/commons/fileupload">Jakarta Commons FileUpload</a>
 * 1.2 or above. Inspired by <a href="http://static.springsource.org/spring/docs/3.0.x/javadoc-api/org/springframework/web/multipart/MultipartResolver.html">MultipartResolver</a>
 * but almost entirely rewritten by DataONE.
 *
 * <p>Provides "maxUploadSize", "maxMemorySize" and "tmpUploadDir" settings as
 * bean properties. See corresponding
 * ServletFileUpload / DiskFileItemFactory properties ("sizeMax", "sizeThreshold",
 * ) for details in terms of defaults and accepted values.
 *
 * <p>Saves temporary files to the temporary directory.
 *
 * @author Robert P. Waltz (DataONE)
 * @author Trevor D. Cook (Original)
 * @author Juergen Hoeller (Original)
 * @since 29.09.2003
 * @see #CommonsMultipartResolver(ServletContext)
 * @see org.springframework.web.portlet.multipart.CommonsPortletMultipartResolver
 * @see org.apache.commons.fileupload.servlet.ServletFileUpload
 * @see org.apache.commons.fileupload.disk.DiskFileItemFactory
 */
public class MultipartRequestResolver
{
    Logger logger = Logger.getLogger(MultipartRequestResolver.class.getName());
    private DiskFileItemFactory factory;
    private org.apache.commons.fileupload.servlet.ServletFileUpload upload;
    final static int SIZE = 16384;
    /**
     * constructor.  
     * @param filePartFile a Directory path to write any file contents to.
     *
     */
    public MultipartRequestResolver()
    {
        // Create a factory for disk-based file items
        this.factory = new DiskFileItemFactory();

        // Create a new file upload handler
        this.upload = new ServletFileUpload(this.factory);

    }
    public MultipartRequestResolver(String tmpUploadDir)
    {
        this.factory = new DiskFileItemFactory();
        this.factory.setRepository(new File(tmpUploadDir));
        this.upload = new ServletFileUpload(factory);
    }
    public MultipartRequestResolver(String tmpUploadDir, int maxUploadSize)
    {
        this.factory = new DiskFileItemFactory();

        // Set factory constraints
        this.factory.setRepository(new File(tmpUploadDir));

        // Create a new file upload handler
        this.upload = new ServletFileUpload(factory);

        // Set overall request size constraint
        this.upload.setSizeMax(maxUploadSize);

    }
    public MultipartRequestResolver(String tmpUploadDir, int maxUploadSize, int maxMemorySize)
    {
        this.factory = new DiskFileItemFactory();

        // Set factory constraints
        this.factory.setSizeThreshold(maxMemorySize);
        this.factory.setRepository(new File(tmpUploadDir));

        // Create a new file upload handler
        this.upload = new ServletFileUpload(factory);

        // Set overall request size constraint
        this.upload.setSizeMax(maxUploadSize);

    }
    /**
     * get the parts of a
     *
     * @throws IOException 
     */
    public MultipartRequest resolveMultipart(HttpServletRequest request) throws IOException, FileUploadException
    {
        Map<String, List<String>> mpParams = new HashMap<String, List<String>>();
        Map<String, File> mpFiles = new HashMap<String, File>();
        MultipartRequest multipartRequest = new MultipartRequest(request, mpFiles, mpParams);
        if (!this.upload.isMultipartContent(request)) {
            return multipartRequest;
        }
        List /* FileItem */ items = upload.parseRequest(request);
        Iterator iter = items.iterator();
        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();

            if (item.isFormField()) {
                String name = item.getFieldName();
                String value = item.getString();
                if (mpParams.containsKey(name)) {
                    mpParams.get(name).add(value);
                } else {
                    List values = new ArrayList();
                    values.add(value);
                    mpParams.put(name, values);
                }
            } else {
               // processUploadedFile(item);
                String fileKey = getOriginalFilename(item);
                if (item instanceof DiskFileItem) {
                    DiskFileItem diskItem = (DiskFileItem)item;
                  mpFiles.put(fileKey, diskItem.getStoreLocation());
                } else if (item.isInMemory()){
                    File fileItem = new File (this.factory.getRepository().getAbsolutePath() + fileKey);
                    if (fileItem.exists()) {
                        fileItem.delete();
                    }
                    fileItem.createNewFile();
                    FileOutputStream fileItemOutput = new FileOutputStream(fileItem);
                    InputStream inputStream = item.getInputStream();
                    byte[] barray = new byte[SIZE];
                    int nRead = 0;

                    while ((nRead = inputStream.read(barray, 0, SIZE)) != -1) {
                    fileItemOutput.write(barray, 0, nRead);
                    }
                fileItemOutput.flush();
                fileItemOutput.close();
                inputStream.close();
                mpFiles.put(fileKey, fileItem);
                } else {
                    throw new FileUploadException("unable to determine file location of Multipart Form named: " +item.getName());
                }
            }
        }
        return multipartRequest;
    }
 /**
 * From the MultipartFile Class implementation for Jakarta Commons FileUpload.
 *<a href="http://static.springsource.org/spring/docs/3.0.x/javadoc-api/org/springframework/web/multipart/commons/CommonsMultipartFile.html"> CommonsMultipartFile </a>
 *
 * @author Trevor D. Cook
 * @author Juergen Hoeller
 * @since 29.09.2003
 */
    private String getOriginalFilename(FileItem fileItem) {
            String filename = fileItem.getName();
            if (filename == null) {
                    // Should never happen.
                    return "";
            }
            // check for Unix-style path
            int pos = filename.lastIndexOf("/");
            if (pos == -1) {
                    // check for Windows-style path
                    pos = filename.lastIndexOf("\\");
            }
            if (pos != -1)  {
                    // any sort of path separator found
                    return filename.substring(pos + 1);
            }
            else {
                    // plain name
                    return filename;
            }
    }
}
