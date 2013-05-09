package com.mick8569.springhub.commons.crypto;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public abstract class Crypto {

	/** Class logger */
	private static final Logger LOG = LoggerFactory.getLogger(Crypto.class);

	/** MD5 Code */
	private static final String MD5 = "MD5";

	/** SHA-256 Code */
	private static final String SHA256 = "SHA-256";

	/** AES Code */
	private static final String AES = "AES";

	/**
	 * Encrypt string with md5 encrypter.
	 *
	 * @param str String to encrypt.
	 * @return Encrypted string.
	 */
	public static String md5(String str) {
		return crypt(str, MD5);
	}

	/**
	 * Encrypt string with SHA-256 encrypter.
	 *
	 * @param str String to encrypt.
	 * @return Encrypted string.
	 */
	public static String sha256(String str) {
		return crypt(str, SHA256);
	}

	/**
	 * Encrypt string with AES encryption.
	 *
	 * @param data   Data to encrypt.
	 * @param secret Secret use for encryption.
	 * @return Encrypted data.
	 */
	public static String encryptAES(String data, String secret) {
		try {
			Key key = generateAESKey(secret);
			Cipher c = Cipher.getInstance(AES);
			c.init(Cipher.ENCRYPT_MODE, key);
			byte[] encVal = c.doFinal(data.getBytes());
			return new String(Base64.encodeBase64(encVal));
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			return data;
		}
	}

	/**
	 * Decrypt string with AES encryption.
	 *
	 * @param encryptedData Data to decrypt.
	 * @param secret        Secret use for decryption.
	 * @return Decrypted data.
	 */
	public static String decryptAES(String encryptedData, String secret) {
		try {
			Key key = generateAESKey(secret);
			Cipher c = Cipher.getInstance(AES);
			c.init(Cipher.DECRYPT_MODE, key);
			byte[] decodedValue = Base64.decodeBase64(encryptedData.getBytes());
			byte[] decValue = c.doFinal(decodedValue);
			return new String(decValue);
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			return encryptedData;
		}
	}

	/**
	 * Generate AES key.
	 *
	 * @param secret Secret use to generate AES Key.
	 * @return Generated key.
	 * @throws Exception
	 */
	private static Key generateAESKey(String secret) throws Exception {
		return new SecretKeySpec(secret.getBytes(), AES);
	}

	/**
	 * Build random string.
	 *
	 * @return Random string.
	 */
	public static String buildRandom() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

	/**
	 * Encrypt string with specified algorithm.
	 *
	 * @param str       String to encrypt.
	 * @param algorithm Algorithm.
	 * @return Encrypted string.
	 */
	private static String crypt(String str, String algorithm) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(str.getBytes());

			byte byteData[] = md.digest();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException ex) {
			LOG.error(ex.getMessage(), ex);
			return null;
		}
	}
}
