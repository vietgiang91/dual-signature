/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doantotnghiep;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.SecretKey;

/**
 *
 * @author vietg_000
 */
public class KeyGenerator {

    public KeyGenerator(String algorithm) throws IOException, NoSuchAlgorithmException {
        if (algorithm.equals("RSA")) {
            RSA_Algorithm rsa = new RSA_Algorithm(1024);
            BigInteger customer_privateKey = rsa.getD();
            BigInteger customer_publicKey = rsa.getE();
            BigInteger customer_N = rsa.getN();
            ObjectOutputStream oos = null;
            File file = null;

            file = new File("C://keys//customer_private.key");
            file.createNewFile();
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(customer_privateKey);
            oos.close();

            file = new File("C://keys//customer_public.key");
            file.createNewFile();
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(customer_publicKey);
            oos.close();

            file = new File("C://keys//customer_n.key");
            file.createNewFile();
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(customer_N);
            oos.close();

            RSA_Algorithm rsa1 = new RSA_Algorithm(1024);
            BigInteger bank_privateKey = rsa1.getD();
            BigInteger bank_publicKey = rsa1.getE();
            BigInteger bank_N = rsa1.getN();

            file = new File("C://keys//bank_private.key");
            file.createNewFile();
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(bank_privateKey);
            oos.close();

            file = new File("C://keys//bank_public.key");
            file.createNewFile();
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(bank_publicKey);
            oos.close();

            file = new File("C://keys//bank_n.key");
            file.createNewFile();
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(bank_N);
            oos.close();
        } else if (algorithm.equals("DES")) {
            SecretKey key;
            javax.crypto.KeyGenerator generator;
            generator = javax.crypto.KeyGenerator.getInstance("DES");
            generator.init(new SecureRandom());
            key = generator.generateKey();
            File file = new File("C://keys//key.key");
            file.createNewFile();
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
            os.writeObject(key);
            os.close();
        }
    }
}
