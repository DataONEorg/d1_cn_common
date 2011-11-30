/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.service.util;

import java.math.BigInteger;
import org.jibx.runtime.JiBXException;


/**
 *
 * @author waltz
 */
public class BigIntegerMarshaller {

    static final BigInteger compareLimit = new BigInteger("0");
    /*
     * convert a BigInteger to a String
     * The BigInteger must be greater or equal to 0
     *
     * @param bigInt
     * @return String
     * @throws JiBXException
     *
     */
    public static String serializeBigInteger(BigInteger bigInt) throws JiBXException {
        if (bigInt.compareTo(compareLimit) >= 0) {
            return bigInt.toString();
        } else {
            throw new JiBXException("unsignedLong must be greater than or equal to 0");
        }

    }


    /**
     * convert a String to a BigInteger
     * The String must be greater or equal to 0
     * 
     * @param bigIntValue
     * @return BigInteger
     * @throws JiBXException
     */
    public static BigInteger deserializeBigInteger(String bigIntValue) throws JiBXException {

    	if (bigIntValue == null)
    		throw new JiBXException("serialized value cannot be null");
    	BigInteger bigInt = new BigInteger(bigIntValue);
        if (bigInt.compareTo(compareLimit) >= 0) {
            return bigInt;
        } else {
            throw new JiBXException("unsignedLong must be greater than or equal to 0");
        }
    }
}
