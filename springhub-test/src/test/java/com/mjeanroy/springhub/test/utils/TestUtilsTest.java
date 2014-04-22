package com.mjeanroy.springhub.test.utils;

import static org.apache.commons.io.FileUtils.deleteQuietly;
import static org.apache.commons.io.FileUtils.forceMkdir;
import static org.apache.commons.io.FileUtils.getTempDirectoryPath;
import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;

import org.junit.Test;

public class TestUtilsTest {

	@Test
	public void test_copy() {
		// GIVEN
		String filename = "logback-test.xml";

		// WHEN
		File result = TestUtils.copy(filename);

		// THEN
		assertThat(result).exists();
		assertThat(result.getAbsolutePath()).isEqualTo(getTempDirectoryPath() + "/" + filename);
		deleteQuietly(result);
	}

	@Test
	public void test_copy_to() throws Exception {
		// GIVEN
		String to = "/foo";
		forceMkdir(new File(getTempDirectoryPath() + to));
		String filename = "logback-test.xml";

		// WHEN
		File result = TestUtils.copyTo(filename, to);

		// THEN
		assertThat(result).exists();
		assertThat(result.getAbsolutePath()).isEqualTo(getTempDirectoryPath() + to + "/" + filename);
		deleteQuietly(result);
	}
}
