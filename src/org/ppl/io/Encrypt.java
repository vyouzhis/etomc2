package org.ppl.io;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.apache.commons.codec.binary.Base64;

public class Encrypt {
	public static Encrypt config = null;

	public static Encrypt getInstance() {

		if (config == null) {
			config = new Encrypt();
		}

		return config;

	}

	public String MD5(String md5) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
		}
		return null;
	}

	public long Crc32(String input) {
		if (input == null || input.length() == 0) {
			return 0;
		}
		// get bytes from string
		byte bytes[] = input.getBytes();

		Checksum checksum = new CRC32();

		// update the current checksum with the specified array of bytes
		checksum.update(bytes, 0, bytes.length);

		// get the current checksum value
		long checksumValue = checksum.getValue();

		// System.out.println("CRC32 checksum for input string is: " +
		// checksumValue);
		return checksumValue;
	}

	public String Base64_Encode(String encode) {
		// Base64 en = new Base64();
		// byte[] encodedBytes =
		// System.out.println("encodedBytes " + new String(encodedBytes));

		return null;
	}

	public String Base64_Decode(String encodedBytes) {
		byte[] decodedBytes = Base64.decodeBase64(encodedBytes);

		return new String(decodedBytes);

	}

	public String toHex(String arg) {
		try {
			return String.format("%040x",
					new BigInteger(1, arg.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public String hexToString(String hex) {
		return new String(new BigInteger(hex, 16).toByteArray());
	}
}
