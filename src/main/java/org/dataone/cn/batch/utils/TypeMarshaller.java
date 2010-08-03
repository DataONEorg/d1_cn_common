/**
 * This work was created by participants in the DataONE project, and is jointly
 * copyrighted by participating institutions in DataONE. For more information on
 * DataONE, see our web site at http://dataone.org.
 *
 *   Copyright ${year}
 *
 * This work is Licensed under the Apache License, Version 2.0 (the "License");
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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

    public static <T> T unmarshalTypeFromFile(Class<T> domainClass, String filenamePath) throws IOException, InstantiationException, IllegalAccessException {
        Reader reader = null;
        T domainObject = null;
        try {
            IBindingFactory bfact = BindingDirectory.getFactory(domainClass);
            IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            domainObject = domainClass.newInstance();
            reader = new FileReader(filenamePath);

            domainObject = (T) uctx.unmarshalDocument(reader);
        } catch (FileNotFoundException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (JiBXException ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            reader.close();
        }
        return domainObject;
    }
}
