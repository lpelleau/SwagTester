package net.pelleau.utils;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import io.swagger.models.Model;

public class RandomGenerator {

	private static Random rng = new Random();

	private RandomGenerator() {
	}

	/**
	 * @return a random int.
	 */
	public int getInt() {
		return rng.nextInt();
	}

	/**
	 * 
	 * @param max
	 *            the max bound.
	 * @return a random int between 0 (inclusive) and max (exclusive).
	 */
	public int getInt(int max) {
		return rng.nextInt(max);
	}

	/**
	 * @param min
	 *            the min bound.
	 * @param max
	 *            the max bound.
	 * @return a random int between min (inclusive) and max (exclusive).
	 */
	public int getInt(int min, int max) {
		return rng.nextInt((max - min) + 1) + min;
	}

	/**
	 * @param length
	 *            the length of the returned string.
	 * @return a random String of length size with only alphabetic characters.
	 */
	public String getString(int length) {
		return RandomStringUtils.randomAlphabetic(length);
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
	public String getString(int length, String charSet) {
		return RandomStringUtils.random(length, charSet);
	}

	public Object fillObject(Model model) {
		// TODO generate JSON object from Swagger Model and fill the values
		// inside with random values
		return null;
	}

}
