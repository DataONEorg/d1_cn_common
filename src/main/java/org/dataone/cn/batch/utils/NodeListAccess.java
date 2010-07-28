/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.cn.batch.utils;

import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.dataone.service.types.NodeList;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

/**
 *
 * @author rwaltz
 */
public class NodeListAccess {
    static Logger logger = Logger.getLogger(NodeListAccess.class.getName());
    private NodeList nodeList = null;
    private final File nodeListFile;
    static private IBindingFactory bfact = null;
    static private IUnmarshallingContext uctx = null;
    static private IMarshallingContext mctx = null;
    static {
        try {
            
            bfact = BindingDirectory.getFactory(org.dataone.service.types.NodeList.class);
            uctx = bfact.createUnmarshallingContext();
            mctx = bfact.createMarshallingContext();
        } catch (JiBXException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
    public NodeListAccess(String nodeListFileLocation) throws FileNotFoundException, JiBXException, IOException {
        // XXX read in the node list, for right now its on the filesystem, soon it will be
        // in metacat
        nodeListFile = new File(nodeListFileLocation);
        InputStream inputStream = new FileInputStream(nodeListFile);
        try {
            nodeList = (NodeList) uctx.unmarshalDocument(inputStream, null);

        } finally {
            inputStream.close();
        }
    }
    public void persistNodeListToFileSystem() throws JiBXException, FileNotFoundException {
        IBindingFactory bfact = BindingDirectory.getFactory(org.dataone.service.types.SystemMetadata.class);


        FileOutputStream nodeListFileOutputStream = new FileOutputStream(nodeListFile);

        mctx.marshalDocument(this.nodeList, "UTF-8", null, nodeListFileOutputStream);
        try {
            nodeListFileOutputStream.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage(),ex);
        }
    }
    public NodeList getNodeList() {
        return nodeList;
    }

    public void setNodeList(NodeList nodeList) {
        this.nodeList = nodeList;
    }

}
