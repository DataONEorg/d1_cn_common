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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dataone.cn.batch.utils;

import java.io.ByteArrayOutputStream;
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
        IBindingFactory bfact = BindingDirectory.getFactory(typeObject.getClass());

        IMarshallingContext mctx = bfact.createMarshallingContext();
        File outputFile = new File(filenamePath);
        FileOutputStream typeOutput = new FileOutputStream(outputFile);

        mctx.marshalDocument(typeObject, "UTF-8", null, typeOutput);
        try {
            typeOutput.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return outputFile;
    }
    // Not to be used with large objects! Other than ObjectList or NodeList, its probably ok to use
    // with most other Dataone Types.  Possible that SystemMetadata objects may become
    // too large at some point as well.
    //
    // We may wish to throw exceptions based on the class of the object being marshalled
    // in the future
    public static OutputStream marshalTypeToOutputStream(Object typeObject) throws JiBXException, FileNotFoundException, IOException {
        IBindingFactory bfact = BindingDirectory.getFactory(typeObject.getClass());

        IMarshallingContext mctx = bfact.createMarshallingContext();
        ByteArrayOutputStream typeOutput = new ByteArrayOutputStream();

        mctx.marshalDocument(typeObject, "UTF-8", null, typeOutput);

        return typeOutput;
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
