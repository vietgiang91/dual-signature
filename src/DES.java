import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


public class DES {
	private static final String KEY_ALGORITHM = "DES";
	private static final String CIPHER_ALGORITHM="DES/ECB/PKCS5Padding";
	
	// 产生密钥
	private static SecretKey keyGenerator(String key) throws Exception {
		byte input[] = HexString2Bytes(key);
		DESKeySpec desKey = new DESKeySpec(input);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		SecretKey secureKey = keyFactory.generateSecret(desKey);
		return secureKey;
	}
	
	private static int parse(char c) {
		if (c >= 'a')
			return (c - 'a' + 10) & 0x0f;
		if (c >= 'A')
			return (c - 'A' + 10) & 0x0f;
		return (c - '0') & 0x0f;
	}
	
	// 将十六进制转换为字节数组
	private static byte[] HexString2Bytes(String str) {
		byte[] b = new byte[str.length() / 2];
		int j = 0;
		char c0, c1;
		
		for (int i = 0; i < b.length; ++i) {
			c0 = str.charAt(j++);
			c1 = str.charAt(j++);
			b[i] = (byte)((parse(c0) << 4) | parse(c1));
		}
		return b;
	}
	
	// DES加密
	public static byte[] encrypt(String str, String key) throws Exception {
		Key deskey = keyGenerator(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		SecureRandom random = new SecureRandom();
		cipher.init(Cipher.ENCRYPT_MODE, deskey, random);
		byte[] results = cipher.doFinal(str.getBytes());
		return results;
	}
	
	// DES解密
	public static byte[] decrypt(byte[] str, String key) throws Exception {
		Key deskey = keyGenerator(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		SecureRandom random = new SecureRandom();
		cipher.init(Cipher.DECRYPT_MODE, deskey, random);
		return cipher.doFinal(str);
	}
	
	public static void main(String[] args) throws Exception {
		String str = "NGUYEN VIET GIANG";
		System.out.println("PlainText:" + str);
		
		String key = "ABCDefabDCBA0123";
		byte[] c = encrypt(str, key);
		
		System.out.println("Cipher:" + c.toString());
		System.out.println("Plaintext:" + new String(decrypt(c, key)));
	}
}