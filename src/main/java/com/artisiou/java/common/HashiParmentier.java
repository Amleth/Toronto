package com.artisiou.java.common;

import java.security.MessageDigest;
import java.util.Formatter;

public class HashiParmentier {

	public static String calculateHash(MessageDigest algorithm, byte[] data) throws Exception {
		algorithm.update(data);
		byte[] hash = algorithm.digest();

		return byteArray2Hex(hash);
	}

	private static String byteArray2Hex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String formattedHash = formatter.toString();
		formatter.close();

		return formattedHash;
	}
}
