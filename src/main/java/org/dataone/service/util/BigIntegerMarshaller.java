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

    static final BigInteger minLimit = new BigInteger("0");
    static final BigInteger maxLimit = new BigInteger("18446744073709551615");
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
        if ((bigInt.compareTo(minLimit) >= 0) && (bigInt.compareTo(maxLimit) <= 0)) {
            return bigInt.toString();
        } else {
            throw new JiBXException("unsignedLong must be greater than or equal to 0 and less than or equal to 18446744073709551615");
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
        if ((bigInt.compareTo(minLimit) >= 0) && (bigInt.compareTo(maxLimit) <= 0)) {
            return bigInt;
        } else {
            throw new JiBXException("unsignedLong must be greater than or equal to 0 and less than or equal to 18446744073709551615");
        }
    }
}
