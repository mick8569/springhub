package com.mjeanroy.springhub.commons.crypto;

import static com.mjeanroy.springhub.commons.crypto.Crypto.buildRandom;
import static com.mjeanroy.springhub.commons.crypto.Crypto.decryptAES;
import static com.mjeanroy.springhub.commons.crypto.Crypto.encryptAES;
import static com.mjeanroy.springhub.commons.crypto.Crypto.generateAlphaNumericRandom;
import static com.mjeanroy.springhub.commons.crypto.Crypto.md5;
import static com.mjeanroy.springhub.commons.crypto.Crypto.sha256;
import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class CryptoTest {

	@Test
	public void testMD5() {
		// GIVEN
		String decrypted = "foo";

		// WHEN
		String encrypted = md5(decrypted);

		// THEN
		assertThat(encrypted).isNotNull().isEqualTo("acbd18db4cc2f85cedef654fccc4a4d8");
	}

	@Test
	public void testSHA256() {
		// GIVEN
		String decrypted = "foo";

		// WHEN
		String encrypted = sha256(decrypted);

		// THEN
		assertThat(encrypted).isNotNull().isEqualTo("2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae");
	}

	@Test
	public void testEncryptAES() {
		// GIVEN
		String secret = "ag1yxzwlmryjhp%o";
		String decrypted = "toto";

		// WHEN
		String encrypted = encryptAES(decrypted, secret);

		// THEN
		assertThat(encrypted).isNotNull().isEqualTo("arl0OxSksKW0Eh8dE5cgbQ==");
	}

	@Test
	public void testDecryptAES() {
		// GIVEN
		String secret = "ag1yxzwlmryjhp%o";
		String encrypted = "arl0OxSksKW0Eh8dE5cgbQ==";

		// WHEN
		String decrypted = decryptAES(encrypted, secret);

		// THEN
		assertThat(decrypted).isNotNull().isEqualTo("toto");
	}

	@Test
	public void testBuildRandom() {
		// WHEN
		String random = buildRandom();

		// THEN
		assertThat(random).isNotNull().isNotEmpty();
	}

	@Test
	public void testBuildRandomAlphaNumericString() {
		// WHEN
		String random = generateAlphaNumericRandom();

		// THEN
		assertThat(random).isNotNull().hasSize(32).matches("^[a-z0-9]+$");
	}

	@Test
	public void testBuildRandomAlphaNumericStringWithSize() {
		// GIVEN
		int size = 20;

		// WHEN
		String random = generateAlphaNumericRandom(size);

		// THEN
		assertThat(random).isNotNull().hasSize(size).matches("^[a-z0-9]+$");
	}
}
