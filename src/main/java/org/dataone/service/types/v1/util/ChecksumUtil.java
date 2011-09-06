/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.service.types.v1.util;

import java.io.IOException;
import java.io.InputStream;
import org.dataone.service.types.v1.Checksum;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author waltz
 */
public class ChecksumUtil {
   /**
     * return a checksum based on the input of the stream
     * @param is
     * @param algorithmAlgorithm
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static Checksum checksum(InputStream is, String checksumAlgorithm) throws NoSuchAlgorithmException, IOException {
        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance(checksumAlgorithm);
        int numRead;

        do {
            numRead = is.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        String csStr = getHex(complete.digest());
        Checksum checksum = new Checksum();
        checksum.setValue(csStr);
        checksum.setAlgorithm(checksumAlgorithm);
        return checksum;
    }

    /**
     * convert a byte array to a hex string
     */
    private static String getHex(byte[] raw) {
        final String HEXES = "0123456789ABCDEF";
        if (raw == null) {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

}
