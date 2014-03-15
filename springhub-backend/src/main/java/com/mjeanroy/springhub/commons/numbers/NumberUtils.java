package com.mjeanroy.springhub.commons.numbers;

public final class NumberUtils {

	private NumberUtils() {
	}

	/**
	 * Get long value of number, zero is returned if number is null.
	 *
	 * @param number Number to get long value.
	 * @return Long value.
	 */
	public static long longValue(Number number) {
		return longValue(number, 0L);
	}

	/**
	 * Get long value of number, default value is returned if number is null.
	 *
	 * @param number       Number to get long value.
	 * @param defaultValue Default value to return if number is null.
	 * @return Long value.
	 */
	public static long longValue(Number number, long defaultValue) {
		return number == null ? defaultValue : number.longValue();
	}

	/**
	 * Get short value of number, zero is returned if number is null.
	 *
	 * @param number Number to get short value.
	 * @return Short value.
	 */
	public static short shortValue(Number number) {
		return shortValue(number, (short) 0);
	}

	/**
	 * Get short value of number, default value is returned if number is null.
	 *
	 * @param number       Number to get short value.
	 * @param defaultValue Default value to return if number is null.
	 * @return Short value.
	 */
	public static short shortValue(Number number, short defaultValue) {
		return number == null ? defaultValue : number.shortValue();
	}

	/**
	 * Get int value of number, zero is returned if number is null.
	 *
	 * @param number Number to get int value.
	 * @return Short value.
	 */
	public static int intValue(Number number) {
		return number == null ? 0 : number.intValue();
	}

	/**
	 * Get int value of number, default value is returned if number is null.
	 *
	 * @param number       Number to get int value.
	 * @param defaultValue Default value to return if number is null.
	 * @return Short value.
	 */
	public static int intValue(Number number, int defaultValue) {
		return number == null ? defaultValue : number.intValue();
	}

	/**
	 * Get double value of number, zero is returned if number is null.
	 *
	 * @param number Number to get double value.
	 * @return Short value.
	 */
	public static double doubleValue(Number number) {
		return doubleValue(number, 0D);
	}

	/**
	 * Get double value of number, zero is returned if number is null.
	 *
	 * @param number       Number to get double value.
	 * @param defaultValue Default value to return if number is null.
	 * @return Short value.
	 */
	public static double doubleValue(Number number, double defaultValue) {
		return number == null ? defaultValue : number.doubleValue();
	}

	/**
	 * Get float value of number, zero is returned if number is null.
	 *
	 * @param number Number to get float value.
	 * @return Short value.
	 */
	public static float floatValue(Number number) {
		return floatValue(number, 0f);
	}

	/**
	 * Get float value of number, zero is returned if number is null.
	 *
	 * @param number       Number to get float value.
	 * @param defaultValue Default value to return if number is null.
	 * @return Short value.
	 */
	public static float floatValue(Number number, float defaultValue) {
		return number == null ? defaultValue : number.floatValue();
	}
}
