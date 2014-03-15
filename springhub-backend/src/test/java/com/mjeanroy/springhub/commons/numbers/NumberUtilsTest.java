package com.mjeanroy.springhub.commons.numbers;

import org.junit.Test;

import static com.mjeanroy.springhub.commons.numbers.NumberUtils.doubleValue;
import static com.mjeanroy.springhub.commons.numbers.NumberUtils.floatValue;
import static com.mjeanroy.springhub.commons.numbers.NumberUtils.intValue;
import static com.mjeanroy.springhub.commons.numbers.NumberUtils.longValue;
import static com.mjeanroy.springhub.commons.numbers.NumberUtils.shortValue;
import static org.fest.assertions.api.Assertions.assertThat;

public class NumberUtilsTest {

	@Test
	public void longValue_should_return_long() {
		// GIVEN
		Number number = 10L;

		// WHEN
		long value = longValue(number);

		// THEN
		assertThat(value).isExactlyInstanceOf(Long.class).isEqualTo(number.longValue());
	}

	@Test
	public void longValue_should_return_default_value_when_number_is_null() {
		// GIVEN
		Number number = null;
		long defaultValue = 5L;

		// WHEN
		long value = longValue(number, defaultValue);

		// THEN
		assertThat(value).isExactlyInstanceOf(Long.class).isEqualTo(defaultValue);
	}

	@Test
	public void longValue_should_return_zero_when_number_is_null() {
		// GIVEN
		Number number = null;

		// WHEN
		long value = longValue(number);

		// THEN
		assertThat(value).isExactlyInstanceOf(Long.class).isZero();
	}

	@Test
	public void shortValue_should_return_short() {
		// GIVEN
		Number number = 10;

		// WHEN
		short value = shortValue(number);

		// THEN
		assertThat(value).isExactlyInstanceOf(Short.class).isEqualTo(number.shortValue());
	}

	@Test
	public void shortValue_should_return_default_value_when_number_is_null() {
		// GIVEN
		Number number = null;
		short defaultValue = 5;

		// WHEN
		short value = shortValue(number, defaultValue);

		// THEN
		assertThat(value).isExactlyInstanceOf(Short.class).isEqualTo(defaultValue);
	}

	@Test
	public void shortValue_should_return_zero_when_number_is_null() {
		// GIVEN
		Number number = null;

		// WHEN
		short value = shortValue(number);

		// THEN
		assertThat(value).isExactlyInstanceOf(Short.class).isZero();
	}

	@Test
	public void intValue_should_return_int() {
		// GIVEN
		Number number = 10;

		// WHEN
		int value = intValue(number);

		// THEN
		assertThat(value).isExactlyInstanceOf(Integer.class).isEqualTo(number.intValue());
	}

	@Test
	public void intValue_should_return_default_value_when_number_is_null() {
		// GIVEN
		Number number = null;
		int defaultValue = 5;

		// WHEN
		int value = intValue(number, defaultValue);

		// THEN
		assertThat(value).isExactlyInstanceOf(Integer.class).isEqualTo(defaultValue);
	}

	@Test
	public void intValue_should_return_zero_when_number_is_null() {
		// GIVEN
		Number number = null;

		// WHEN
		int value = intValue(number);

		// THEN
		assertThat(value).isExactlyInstanceOf(Integer.class).isZero();
	}

	@Test
	public void doubleValue_should_return_double() {
		// GIVEN
		Number number = 10D;

		// WHEN
		double value = doubleValue(number);

		// THEN
		assertThat(value).isExactlyInstanceOf(Double.class).isEqualTo(number.doubleValue());
	}

	@Test
	public void doubleValue_should_return_default_value_when_number_is_null() {
		// GIVEN
		Number number = null;
		double defaultValue = 5;

		// WHEN
		double value = doubleValue(number, defaultValue);

		// THEN
		assertThat(value).isExactlyInstanceOf(Double.class).isEqualTo(defaultValue);
	}

	@Test
	public void doubleValue_should_return_zero_when_number_is_null() {
		// GIVEN
		Number number = null;

		// WHEN
		double value = doubleValue(number);

		// THEN
		assertThat(value).isExactlyInstanceOf(Double.class).isZero();
	}

	@Test
	public void floatValue_should_return_double() {
		// GIVEN
		Number number = 10f;

		// WHEN
		float value = floatValue(number);

		// THEN
		assertThat(value).isExactlyInstanceOf(Float.class).isEqualTo(number.floatValue());
	}

	@Test
	public void floatValue_should_return_default_value_when_number_is_null() {
		// GIVEN
		Number number = null;
		float defaultValue = 5;

		// WHEN
		float value = floatValue(number, defaultValue);

		// THEN
		assertThat(value).isExactlyInstanceOf(Float.class).isEqualTo(defaultValue);
	}

	@Test
	public void floatValue_should_return_zero_when_number_is_null() {
		// GIVEN
		Number number = null;

		// WHEN
		float value = floatValue(number);

		// THEN
		assertThat(value).isExactlyInstanceOf(Float.class).isZero();
	}
}
