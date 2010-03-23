package org.dataone.service.types;

/**
 * The DataONE Type to represent a checksum and its algorithm.
 *
 * @author Matthew Jones
 */
public class Checksum 
{
    public static final int SHA_1 = 0;
    public static final int SHA_224 = 1;

    private int algorithm;
    private String value;
    
    /**
     * Construct a new Checksum with the given algorithm and value.
     * @param algorithm an integer code representing the algorithm used
     * @param value the String vlaue resulting from the checksum algorithm
     */
    public Checksum(int algorithm, String value) {
        this.algorithm = algorithm;
        this.value = value;
    }

    /**
     * @param algorithm the algorithm to set
     */
    public void setAlgorithm(int algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * @return the algorithm
     */
    public int getAlgorithm() {
        return algorithm;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
}
