package net.pelleau.utils;

import java.sql.Timestamp;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import io.swagger.models.Model;

public class RandomGenerator {

	private static Random rng = new Random();

	private RandomGenerator() {
	}

	public static void setSeed(long seed) {
		rng = new Random(seed);
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
		return rng.nextInt((max - min) + 1) + min;
	}

	public static double getDouble() {
		return rng.nextDouble();
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
	 * 
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
	 * 
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
