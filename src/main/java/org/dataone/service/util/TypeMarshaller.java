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

package org.dataone.service.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import org.apache.log4j.Logger;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

/**
 *
 * @author rwaltz
 */
public class TypeMarshaller {

    static Logger logger = Logger.getLogger(TypeMarshaller.class.getName());

    public static File marshalTypeToFile(Object typeObject, String filenamePath) throws JiBXException, FileNotFoundException, IOException {
        File outputFile = new File(filenamePath);
        FileOutputStream typeOutput = new FileOutputStream(outputFile);
        marshalTypeToOutputStream(typeObject, typeOutput, null);
        try {
            typeOutput.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return outputFile;
    }
    
    public static void marshalTypeToOutputStream(Object typeObject, OutputStream os) throws JiBXException, IOException {
        marshalTypeToOutputStream(typeObject, os, null);
    }
    
    public static void marshalTypeToOutputStream(Object typeObject, OutputStream os, String styleSheet) throws JiBXException, IOException {
        IBindingFactory bfact = BindingDirectory.getFactory(typeObject.getClass());
        IMarshallingContext mctx = bfact.createMarshallingContext();
        mctx.startDocument("UTF-8", null, os);
        if (styleSheet != null) {
        	mctx.getXmlWriter().writePI("xml-stylesheet", "type=\"text/xsl\" href=\"" + styleSheet + "\"");
        }
        mctx.marshalDocument(typeObject);
    }
    
    public static <T> T unmarshalTypeFromFile(Class<T> domainClass, String filenamePath) throws IOException, InstantiationException, IllegalAccessException, JiBXException {
        IBindingFactory bfact = BindingDirectory.getFactory(domainClass);
        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
        Reader reader = new FileReader(filenamePath);
        T domainObject = (T) uctx.unmarshalDocument(reader);
        reader.close();
        return domainObject;
    }
    public static <T> T unmarshalTypeFromFile(Class<T> domainClass, File file) throws IOException, InstantiationException, IllegalAccessException, JiBXException {
        IBindingFactory bfact = BindingDirectory.getFactory(domainClass);
        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
        Reader reader = new FileReader(file);
        T domainObject = (T) uctx.unmarshalDocument(reader);
        reader.close();
        return domainObject;
    }
    public static <T> T unmarshalTypeFromStream(Class<T> domainClass, InputStream inputStream) throws IOException, InstantiationException, IllegalAccessException, JiBXException {
        IBindingFactory bfact = BindingDirectory.getFactory(domainClass);
        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
        T domainObject = (T) uctx.unmarshalDocument(inputStream, null);
        inputStream.close();
        return domainObject;
    }
}
