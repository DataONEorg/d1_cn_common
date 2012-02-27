/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.cn.servlet.http;

import java.io.*;
import javax.servlet.*;

/**
 * A custom servlet output stream that stores its data in a buffer,
 * rather than sending it directly to the client.
 *
 * @author Eric M. Burke
 */
public class BufferedServletOutputStream extends ServletOutputStream {
    // the actual buffer
    private ByteArrayOutputStream bos = new ByteArrayOutputStream( );

    /**
     * @return the contents of the buffer.
     */
    public byte[] getBuffer( ) {
        return this.bos.toByteArray( );
    }

    /**
     * This method must be defined for custom servlet output streams.
     */
    @Override
    public void write(int data) throws IOException {
        this.bos.write(data);
    }
    /**
     * This method must be defined for custom servlet output streams.
     */
    @Override
    public void write(byte[] b) throws IOException {
        this.bos.write(b);
    }
    /**
     * This method must be defined for custom servlet output streams.
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException {

        this.bos.write(b,off,len);
    }
    // BufferedHttpResponseWrapper calls this method
    public void reset( ) {
        this.bos.reset( );
    }

    // BufferedHttpResponseWrapper calls this method
    public void setBufferSize(int size) {
        // no way to resize an existing ByteArrayOutputStream
        this.bos = new ByteArrayOutputStream(size);
    }
}