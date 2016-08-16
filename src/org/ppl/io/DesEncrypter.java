package org.ppl.io;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DesEncrypter {
	Cipher ecipher;
	Cipher dcipher;

	public DesEncrypter() throws Exception {
		SecretKey key;

		String complex = new String("9#82jdkeo!2DcASg");
		byte[] keyBytes = complex.getBytes();
		key = new SecretKeySpec(keyBytes, "AES");

		ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] iv = { 2, 0, 1, 2, 6, 3, 8, 9, 2, 7, 3, 4, 1, 1, 9, 5 };
		IvParameterSpec ivspec = new IvParameterSpec(iv);

		ecipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
		dcipher.init(Cipher.DECRYPT_MODE, key, ivspec);
	}

	@SuppressWarnings("restriction")
	public String encrypt(String str) throws Exception {
		// Encode the string into bytes using utf-8
		byte[] utf8 = str.getBytes("UTF8");
		// Encrypt
		byte[] enc = ecipher.doFinal(utf8);
		// Encode bytes to base64 to get a string
		return new sun.misc.BASE64Encoder().encode(enc);
	}

	@SuppressWarnings("restriction")
	public String decrypt(String str) throws Exception {
		// Decode base64 to get bytes
		byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
		byte[] utf8 = dcipher.doFinal(dec);
		// Decode using utf-8
		return new String(utf8, "UTF8");
	}
}
