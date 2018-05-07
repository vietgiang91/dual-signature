package doantotnghiep;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.management.openmbean.InvalidKeyException;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package doantotnghiep;
//
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.ObjectInputStream;
//import javax.crypto.Cipher;
//import javax.crypto.SecretKey;
//
///**
// *
// * @author vietg_000
// */
//public class DES {
//    public static final String SECRET_KEY_FILE = "C:/keys/secret.key";
//    public static final String ALGORITHM = "DES";
//    private static KeyGenerator keyGen;
//    
//     public static boolean areKeysPresent(){
//        File secretKeyFile = new File(SECRET_KEY_FILE);
//        
//        if(secretKeyFile.exists())
//        {
//            return true;
//        }
//        return false;
//    }
//     public static byte[] encrypt(String message, SecretKey key)
//     {
//         byte[] cipherText = null;
//         try{
//             Cipher cipher = Cipher.getInstance(ALGORITHM);
//             cipher.init(Cipher.ENCRYPT_MODE, key);
//             cipherText = cipher.doFinal(message.getBytes());
//         }
//         catch(Exception e)
//         {
//             e.printStackTrace();
//         }
//         return cipherText;
//     }
//     
//     public static String decrypt(byte[] text, SecretKey key)
//    {
//        byte[] decryptedText = null;
//        try{
//            final Cipher cipher = Cipher.getInstance(ALGORITHM);
//            
//            cipher.init(Cipher.DECRYPT_MODE, key);
//            decryptedText = cipher.doFinal(text);
//        } catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        return new String(decryptedText);
//    }
//     public static String bytesToString(byte[] encrypted){
//        String test = "";
//        for (byte b : encrypted)
//        {
//            test += Byte.toString(b);
//        }
//        return test;
//    }
//     
//     public static void main(String[] args)
//     {
//         try{
//            if (!areKeysPresent())
//            {
//                KeyGenerator keyGen = new KeyGenerator("DES");
//            }
//            final String originalText = "giang";
//            ObjectInputStream inputStream = null;
//    
//            inputStream = new ObjectInputStream(new FileInputStream(SECRET_KEY_FILE));
//            final SecretKey secretKey = (SecretKey) inputStream.readObject();
//            final byte[] cipherText = encrypt(originalText, secretKey);
//            final String plainText = decrypt(cipherText, secretKey);
//
//            
//            System.out.println("Original: " +originalText);
//            System.out.println("Encrypted: "+cipherText.toString());
//            System.out.println("Decrypted: " +plainText);
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//     }
//}
public class DES {

    

//    private void generateKey() throws NoSuchAlgorithmException, IOException {
//        SecretKey key;
//        KeyGenerator generator;
//        generator = KeyGenerator.getInstance("DES");
//        generator.init(new SecureRandom());
//        key = generator.generateKey();
//        File file = new File("E:\\key.key");
//        file.createNewFile();
//        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
//        os.writeObject(key);
//        os.close();
//    }

    public String encrypt(String message, SecretKey key) throws IllegalBlockSizeException,
            BadPaddingException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            UnsupportedEncodingException, java.security.InvalidKeyException {
        // Get a cipher object.
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        // Gets the raw bytes to encrypt, UTF8 is needed for
        // having a standard character set
        byte[] stringBytes = message.getBytes("UTF8");

        // encrypt using the cypher
        byte[] raw = cipher.doFinal(stringBytes);

        // converts to base64 for easier display.
//        BASE64Encoder encoder = new BASE64Encoder();
//        String base64 = encoder.encode(raw);
        
        Encoder encoder = Base64.getEncoder();
        String base64 = encoder.encodeToString(raw);
        return base64;
    }

    public String decrypt(String encrypted, SecretKey key) throws InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, IOException, java.security.InvalidKeyException {

        // Get a cipher object.
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        //decode the BASE64 coded message
        Decoder decoder = Base64.getDecoder();
        byte[] raw = decoder.decode(encrypted);

        //decode the message
        byte[] stringBytes = cipher.doFinal(raw);

        //converts the decoded message to a String
        String clear = new String(stringBytes, "UTF8");
        return clear;
    }
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, java.security.InvalidKeyException
    {
        DES des = new DES();
        KeyGenerator keyGen = new KeyGenerator("DES");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("C://keys//key.key"));
        SecretKey key = (SecretKey) ois.readObject();
        ois.close();
        ois = new ObjectInputStream(new FileInputStream("C://keys//Messege_Merchant_To_Bank.txt"));
        String mess = (String)ois.readObject();
        String[] strArr = mess.split("@");
        String message = strArr[0];
        String cipher = des.encrypt(message, key);
        String PlaintText = des.decrypt(cipher, key);
        System.out.println(cipher);
        System.out.println(PlaintText);
        System.out.println(message.length());
        System.out.println(PlaintText);
    }
}