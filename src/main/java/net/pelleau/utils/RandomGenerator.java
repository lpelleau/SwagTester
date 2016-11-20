package net.pelleau.utils;

import java.sql.Timestamp;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;

import io.swagger.models.Model;

public final class RandomGenerator {

	private static ThreadLocalRandom rng = ThreadLocalRandom.current();

	public static enum BoundType {
		MIN, MAX
	};

	private RandomGenerator() {
	}

	/**
	 * @return a random int.
	 */
	public static int getInt() {
		return rng.nextInt();
	}

	/**
	 * 
	 * @param max
	 *            the max bound.
	 * @return a random int between 0 (inclusive) and max (exclusive).
	 */
	public static int getInt(int max) {
		return rng.nextInt(max);
	}

	/**
	 * @param min
	 *            the min bound.
	 * @param max
	 *            the max bound.
	 * @return a random int between min (inclusive) and max (inclusive).
	 */
	public static int getInt(int min, int max) {
		return rng.nextInt(min, max);
	}

	/**
	 * @param bound
	 *            the limit value of your domain.
	 * @param type
	 *            the limit of bound you want. Min or Max.
	 * @return if type equals Min, returns a random integer between bound and
	 *         Max_Value. if type equals Max, returns a random integer between
	 *         Min_Value and bound.
	 */
	public static int getInt(int bound, BoundType type) {
		if (type == BoundType.MIN) {
			return rng.nextInt(bound, Integer.MAX_VALUE);
		} else {
			return rng.nextInt(Integer.MIN_VALUE, bound);
		}
	}

	/**
	 * @return a random double.
	 */
	public static double getDouble() {
		return rng.nextDouble();
	}

	/**
	 * @param max
	 *            the max bound.
	 * @return a random double between 0 (inclusive) and max (exclusive).
	 */
	public static double getDouble(double max) {
		return rng.nextDouble(max);
	}

	/**
	 * @param min
	 *            the minimum possible value.
	 * @param max
	 *            the maximum possible value.
	 * @return a random double between min and max.
	 */
	public static double getDouble(double min, double max) {
		return rng.nextDouble(min, max);
	}

	/**
	 * @param bound
	 *            the limit value of your domain.
	 * @param type
	 *            the limit of bound you want. Min or Max.
	 * @return if type equals Min, returns a random double between bound and
	 *         Max_Value. if type equals Max, returns a random double between
	 *         Min_Value and bound.
	 */
	public static double getDouble(double bound, BoundType type) {
		if (type == BoundType.MIN) {
			return rng.nextDouble(bound, Double.MAX_VALUE);
		} else {
			return rng.nextDouble(Double.MIN_VALUE, bound);
		}
	}

	/**
	 * @return a random boolean.
	 */
	public static boolean getBool() {
		return rng.nextBoolean();
	}

	/**
	 * @param length
	 *            the length of the returned string.
	 * @return a random String of length size with only alphabetic characters.
	 */
	public static String getString(int length) {
		return RandomStringUtils.random(length, 0, 0, true, false, null, rng);
	}

	/**
	 * @param minLength
	 *            the minimum length of the string.
	 * @param maxLength
	 *            the maximum length of the string.
	 * @return a random String with a length between minLength and maxLength.
	 */
	public static String getString(int minLength, int maxLength) {
		return RandomStringUtils.random(getInt(minLength, maxLength), 0, 0, true, false, null, rng);
	}

	/**
	 * @param length
	 *            the length of the returned string.
	 * @param charSet
	 *            the String containing the set of characters to use, may be
	 *            null.
	 * @return a random String of length size with the letters extracted from
	 *         the given charSet.
	 */
	public static String getString(int length, String charSet) {
		return RandomStringUtils.random(length, 0, 0, true, false, charSet.toCharArray(), rng);
	}

	/**
	 * @param minLength
	 *            the minimum length of the string.
	 * @param maxLength
	 *            the maximum length of the string.
	 * @param charSet
	 *            the String containing the set of characters to use, may be
	 *            null.
	 * @return a random String with a length between minLength and maxLength
	 *         with the letters extracted from the given charSet.
	 */
	public static String getString(int minLength, int maxLength, String charSet) {
		return RandomStringUtils.random(getInt(minLength, maxLength), 0, 0, true, false, charSet.toCharArray(), rng);
	}

	/**
	 * @param length
	 *            the length of the returned numeric string.
	 * @return a random String with only length numerical characters.
	 */
	public static String getNumericString(int length) {
		return RandomStringUtils.random(length, 0, 0, false, true, null, rng);
	}

	/**
	 * Not implemented yet !
	 */
	public static String fillObject(Model model) {
		// TODO generate JSON object from Swagger Model and fill the values
		// inside with random values
		return null;
	}

	/**
	 * @return returns a random DateTime with "AAAA-MM-DDTHH:MM:SS.MMMZ" (ex:
	 *         "1970-01-01T00:00:00.001Z") format.
	 */
	public static String getDateTime() {
		return getNumericString(4) + "-" + getNumericString(2) + "-" + getNumericString(2) + "T" + rng.nextInt(25) + ":"
				+ rng.nextInt(61) + ":" + rng.nextInt(61) + "." + getNumericString(3) + "Z";
	}

	/**
	 * @return returns the current DateTime with "AAAA-MM-DDTHH:MM:SS.MMMZ" (ex:
	 *         "2016-11-18T15:25:15.195Z") format.
	 */
	public static String getTodayDateTime() {
		return new Timestamp(System.currentTimeMillis()).toInstant().toString();

	}
}
