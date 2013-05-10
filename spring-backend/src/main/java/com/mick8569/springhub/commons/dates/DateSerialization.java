package com.mick8569.springhub.commons.dates;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateSerialization {

	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private DateSerialization() {
	}

	public static String serializeDateTime(Date date) {
		return serializeDateTime(date, DEFAULT_DATE_TIME_FORMAT);
	}

	public static String serializeDate(Date date) {
		return serializeDateTime(date, DEFAULT_DATE_FORMAT);
	}

	public static Date deserializeDateTime(String date) {
		return deserializeDateTime(date, DEFAULT_DATE_TIME_FORMAT);
	}

	public static String serializeDateTime(Date date, String datePattern) {
		DateFormat df = new SimpleDateFormat(datePattern);
		return df.format(date);
	}

	public static Date deserializeDateTime(String date, String datePattern) {
		DateFormat df = new SimpleDateFormat(datePattern);
		try {
			return df.parse(date);
		} catch (ParseException ex) {
			return null;
		}
	}
}
