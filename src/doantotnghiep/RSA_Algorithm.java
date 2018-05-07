/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doantotnghiep;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 * @author vietg_000
 */
public class RSA_Algorithm {

    private BigInteger n, d, e;
    private int bitlen = 1024;


    public RSA_Algorithm(BigInteger newn, BigInteger newe) {
        n = newn;
        e = newe;
    }

    public RSA_Algorithm(int bits) {
        bitlen = bits;
        SecureRandom r = new SecureRandom();
        BigInteger p = new BigInteger(bitlen / 2, 100, r);
        BigInteger q = new BigInteger(bitlen / 2, 100, r);
        n = p.multiply(q);
        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        e = new BigInteger("3");
        while (m.gcd(e).intValue() > 1) {
            e = e.add(new BigInteger("2"));
        }
        d = e.modInverse(m);
    }


    public synchronized String encrypt(String message, BigInteger e, BigInteger n) {
        return (new BigInteger(message.getBytes())).modPow(e, n).toString();
    }

    public synchronized BigInteger encrypt(BigInteger message, BigInteger e, BigInteger n) {
        return message.modPow(e, n);
    }

    public synchronized String decrypt(String message, BigInteger d, BigInteger n) {
        return new String((new BigInteger(message)).modPow(d, n).toByteArray());
    }

    public synchronized BigInteger decrypt(BigInteger message, BigInteger d, BigInteger n) {
        return message.modPow(d, n);
    }

    public synchronized void generateKeys() {
        SecureRandom r = new SecureRandom();
        BigInteger p = new BigInteger(bitlen / 2, 100, r);
        BigInteger q = new BigInteger(bitlen / 2, 100, r);
        n = p.multiply(q);
        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        e = new BigInteger("3");
        while (m.gcd(e).intValue() > 1) {
            e = e.add(new BigInteger("2"));
        }
        d = e.modInverse(m);
    }

    public synchronized BigInteger getN() {
        return n;
    }

    public synchronized BigInteger getE() {
        return e;
    }

    public synchronized BigInteger getD() {
        return d;
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        RSA_Algorithm rsa = new RSA_Algorithm(1024);
        BigInteger bank_public = null;
        BigInteger bank_private = null;
        BigInteger bank_n = null;
        KeyGenerator keyGen = new KeyGenerator("RSA");
        
        try {
            ObjectInputStream inputStream = null;
            inputStream = new ObjectInputStream(new FileInputStream("C://keys//bank_public.key"));
            bank_public = (BigInteger) inputStream.readObject();
            inputStream = new ObjectInputStream(new FileInputStream("C://keys//bank_n.key"));
            bank_n = (BigInteger) inputStream.readObject();
            inputStream = new ObjectInputStream(new FileInputStream("C://keys//bank_private.key"));
            bank_private = (BigInteger) inputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String Encrypted = rsa.encrypt("Nguyen Viet Giang", bank_public, bank_n);
        String Decrypted = rsa.decrypt(Encrypted, bank_private, bank_n);
        System.out.println(Encrypted);
        System.out.println(Decrypted);
    }
}
