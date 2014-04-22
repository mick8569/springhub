package com.mjeanroy.springhub.test.utils;

import static org.apache.commons.io.FileUtils.copyFile;
import static org.apache.commons.io.FileUtils.deleteQuietly;
import static org.apache.commons.io.FileUtils.getTempDirectoryPath;
import static org.apache.commons.io.FilenameUtils.normalize;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class TestUtils {

	private TestUtils() {
	}

	/**
	 * Copy file from classpath to temporary directory.
	 *
	 * @param filename Filename, related to classpath.
	 * @return Result file (stored in temporary directory).
	 */
	public static File copy(String filename) {
		return copyTo(filename, "");
	}

	/**
	 * Copy file from classpath to temporary directory.
	 *
	 * @param filename Filename, related to classpath.
	 * @param targetDirectory Target directory, related to temporary directory.
	 * @return Result file (stored in temporary directory).
	 */
	public static File copyTo(String filename, String targetDirectory) {
		try {
			String from = "";
			if (!filename.startsWith("/")) {
				from = "/";
			}

			URL url = TestUtils.class.getResource(from + filename);
			URI uri = url.toURI();
			File file = (new File(uri));

			String tmpPath = getTempDirectoryPath();
			if (!tmpPath.endsWith("/")) {
				tmpPath += "/";
			}

			String targetPath = tmpPath + targetDirectory;
			if (!targetPath.endsWith("/")) {
				targetPath += "/";
			}

			String targetFile = normalize(targetPath + filename);
			File dest = new File(targetFile);
			copyFile(file, dest);
			return dest;
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void delete(File file) throws Exception {
		deleteQuietly(file);
	}

	public static Date getDate(int year, int month, int dayOfMonth, int hour, int minutes, int seconds) {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minutes);
		cal.set(Calendar.SECOND, seconds);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
}
