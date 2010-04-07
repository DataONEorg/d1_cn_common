/**
 * Copyright 2010 Regents of the University of California and the
 *                National Center for Ecological Analysis and Synthesis
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

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
