/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.cn.batch.utils;

import java.security.SecureRandom;

/**
 * generate a random string for use in a Node Id, currently we
 * are generating Char + Int + Char + Int
 * in order to increase readability of ids  we eliminate the chars (0l1O)
 * rob nafh:  "I would eliminate just [0Ol1] because they are visually ambiguous, (and keep 'L' and 'o') "
 *
 * probability of collision = n nodes/50*8*50*8 (?)
 * if we run into problems in future we may change the format
 * in which this returned string is generated
 *
 * @author waltz
 */
public class NodeIdGenerator {
    static private SecureRandom r = new SecureRandom();
    static private String alphabet = "abcdefghijkmnopqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";
    static private  String numbers = "23456789";
    static public String generateId() {
        char randString[] = new char[4];
        randString[0] = alphabet.charAt(r.nextInt(alphabet.length()));
        randString[1] = numbers.charAt(r.nextInt(numbers.length()));
        randString[2] = alphabet.charAt(r.nextInt(alphabet.length()));
        randString[3] = numbers.charAt(r.nextInt(numbers.length()));
        return new String(randString);
    }
}
