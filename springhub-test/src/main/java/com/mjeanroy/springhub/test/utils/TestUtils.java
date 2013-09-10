package com.mjeanroy.springhub.test.utils;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TestUtils {

	private TestUtils() {
	}

	public static File copy(String filename) throws Exception {
		URL url = TestUtils.class.getResource("/images/" + filename);
		URI uri = url.toURI();
		File file = (new File(uri));

		File dest = new File("/tmp/" + filename);
		org.apache.commons.io.FileUtils.copyFile(file, dest);
		return dest;
	}

	public static void delete(File file) throws Exception {
		if (file.exists()) {
			org.apache.commons.io.FileUtils.deleteQuietly(file);
		}
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
