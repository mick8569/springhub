package com.mick8569.springhub.commons.dates;

import org.fest.assertions.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateSerializationTest {

	@Test
	public void test_serializeDateTime() throws Exception {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, 2012);
		cal.set(Calendar.MONTH, Calendar.JULY);
		cal.set(Calendar.DAY_OF_MONTH, 10);
		cal.set(Calendar.HOUR_OF_DAY, 15);
		cal.set(Calendar.MINUTE, 2);
		cal.set(Calendar.SECOND, 30);

		String strDate = DateSerialization.serializeDateTime(cal.getTime());
		Assertions.assertThat(strDate).isNotNull().isEqualTo("2012-07-10 15:02:30");

		strDate = DateSerialization.serializeDateTime(cal.getTime(), "yyyy-MM-dd");
		Assertions.assertThat(strDate).isNotNull().isEqualTo("2012-07-10");
		Assert.assertEquals("2012-07-10", strDate);
	}

	@Test
	public void test_deserializeDateTime() throws Exception {
		Date date = DateSerialization.deserializeDateTime("2012-07-10 15:02:30");
		Assertions.assertThat(date).isNotNull()
				.isWithinYear(2012)
				.isWithinMonth(7)
				.isWithinDayOfMonth(10)
				.isWithinHourOfDay(15)
				.isWithinMinute(2)
				.isWithinSecond(30);

		date = DateSerialization.deserializeDateTime("2012-07-10", "yyyy-MM-dd");
		Assertions.assertThat(date).isNotNull()
				.isWithinYear(2012)
				.isWithinMonth(7)
				.isWithinDayOfMonth(10);
	}
}
