package net.pelleau.swagger.generator.random;

import java.util.List;

public interface RandomGenerator {

	/**
	 * @param min
	 *            the min bound.
	 * @param max
	 *            the max bound.
	 * @return a random long between min (inclusive) and max (exclusive).
	 */
	long getLong(long min, long max);

	/**
	 * @param min
	 *            the minimum possible value.
	 * @param max
	 *            the maximum possible value.
	 * @return a random double between min (inclusive) and max (exclusive).
	 */
	double getDouble(double min, double max);

	/**
	 * @return a random boolean.
	 */
	boolean getBool();

	/**
	 * @param minLength
	 *            the minimum length of the string.
	 * @param maxLength
	 *            the maximum length of the string.
	 * @return a random String with a length between minLength (inclusive) and
	 *         maxLength (exclusive).
	 */
	String getString(int minLength, int maxLength);

	/**
	 * @return returns a random Data with "AAAA-MM-DD" (ex: "1970-01-01")
	 */
	String getDate();

	/**
	 * @return returns a random DateTime with "AAAA-MM-DDTHH:MM:SS.MMMZ" (ex:
	 *         "1970-01-01T00:00:00.001Z") format.
	 */
	String getDateTime();

	/**
	 * @param enums
	 *            the possible values.
	 * @return a value randomly picked form the enums.
	 */
	String getValue(List<String> enums);
}
