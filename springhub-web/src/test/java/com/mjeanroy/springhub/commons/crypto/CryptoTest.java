package com.mjeanroy.springhub.commons.crypto;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

public class CryptoTest {

	@Test
	public void testMD5() throws Exception {
		String encrypted = Crypto.md5("foo");
		Assertions.assertThat(encrypted).isNotNull().isEqualTo("acbd18db4cc2f85cedef654fccc4a4d8");
	}

	@Test
	public void testSHA256() throws Exception {
		String encrypted = Crypto.sha256("foo");
		Assertions.assertThat(encrypted).isNotNull().isEqualTo("2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae");
	}

	@Test
	public void testEncryptAES() throws Exception {
		String secret = "ag1yxzwlmryjhp%o";
		String encrypted = Crypto.encryptAES("toto", secret);
		Assertions.assertThat(encrypted).isNotNull().isEqualTo("arl0OxSksKW0Eh8dE5cgbQ==");
	}

	@Test
	public void testDecryptAES() throws Exception {
		String secret = "ag1yxzwlmryjhp%o";
		String encrypted = Crypto.decryptAES("arl0OxSksKW0Eh8dE5cgbQ==", secret);
		Assertions.assertThat(encrypted).isNotNull().isEqualTo("toto");
	}

	@Test
	public void testBuildRandom() throws Exception {
		String random = Crypto.buildRandom();
		Assertions.assertThat(random).isNotNull().isNotEmpty();
	}
}
