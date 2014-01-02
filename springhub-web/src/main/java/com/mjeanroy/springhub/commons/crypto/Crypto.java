package com.mjeanroy.springhub.commons.crypto;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public abstract class Crypto {

	/** Class logger */
	private static final Logger log = LoggerFactory.getLogger(Crypto.class);

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
			log.error(ex.getMessage(), ex);
			return data;
		}
	}

	/**
	 * Encrypt string with AES encryption and URL encode result in UTF-8.
	 *
	 * @param data   Data to encrypt.
	 * @param salt   Salt use to encrypt data.
	 * @param secret Secret use for encryption.
	 * @return Encrypted data.
	 */
	public static String encryptAES_UTF8(String data, String salt, String secret) {
		return encryptAES_UTF8(salt + data, secret);
	}

	/**
	 * Encrypt string with AES encryption and URL encode result in UTF-8.
	 *
	 * @param data   Data to encrypt.
	 * @param secret Secret use for encryption.
	 * @return Encrypted data.
	 */
	public static String encryptAES_UTF8(String data, String secret) {
		try {
			return URLEncoder.encode(Crypto.encryptAES(data, secret), "UTF-8");
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return data;
		}
	}

	/**
	 * Encrypt string with AES encryption.
	 *
	 * @param data   Data to encrypt.
	 * @param salt   Salt use to encrypt data.
	 * @param secret Secret use for encryption.
	 * @return Encrypted data.
	 */
	public static String encryptAES(String data, String salt, String secret) {
		return Crypto.encryptAES(salt + data, secret);
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
			log.error(ex.getMessage(), ex);
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
			log.error(ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * Decript string encrypted with AES encryption. <br />
	 * String is first URL decoded using UTF-8 encoding and then decrypted using salt and secret.
	 *
	 * @param encrypted String to decrypt.
	 * @param salt      Salt used to encrypt string.
	 * @param secret    Secret used to encrypt string.
	 * @return Decrypted string.
	 */
	public static String decryptAES_UTF8(String encrypted, String salt, String secret) {
		try {
			String value = URLDecoder.decode(encrypted, "UTF-8");
			String decrypted = Crypto.decryptAES(value, secret);
			if (!decrypted.startsWith(salt)) {
				return null;
			}
			return decrypted.substring(salt.length());
		} catch (Exception ex) {
			return encrypted;
		}
	}

	/**
	 * Decript string encrypted with AES encryption. <br />
	 * String is first URL decoded using UTF-8 encoding and then decrypted using secret.
	 *
	 * @param encrypted String to decrypt.
	 * @param secret    Secret used to encrypt string.
	 * @return Decrypted string.
	 */
	public static String decryptAES_UTF8(String encrypted, String secret) {
		try {
			String value = URLDecoder.decode(encrypted, "UTF-8");
			return Crypto.decryptAES(value, secret);
		} catch (Exception ex) {
			return encrypted;
		}
	}
}
