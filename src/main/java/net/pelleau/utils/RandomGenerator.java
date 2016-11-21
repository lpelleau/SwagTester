package net.pelleau.utils;

public interface RandomGenerator {

	public static enum BoundType {
		MIN, MAX
	};

	/**
	 * @return a random int.
	 */
	int getInt();

	/**
	 * 
	 * @param max
	 *            the max bound.
	 * @return a random int between 0 (inclusive) and max (exclusive).
	 */
	int getInt(int max);

	/**
	 * @param min
	 *            the min bound.
	 * @param max
	 *            the max bound.
	 * @return a random int between min (inclusive) and max (inclusive).
	 */
	int getInt(int min, int max);

	/**
	 * @param bound
	 *            the limit value of your domain.
	 * @param type
	 *            the limit of bound you want. Min or Max.
	 * @return if type equals Min, returns a random integer between bound and
	 *         Max_Value. if type equals Max, returns a random integer between
	 *         Min_Value and bound.
	 */
	int getInt(int bound, BoundType type);

	/**
	 * @return a random long.
	 */
	long getLong();

	/**
	 * 
	 * @param max
	 *            the max bound.
	 * @return a random long between 0 (inclusive) and max (exclusive).
	 */
	long getLong(long max);

	/**
	 * @param min
	 *            the min bound.
	 * @param max
	 *            the max bound.
	 * @return a random long between min (inclusive) and max (inclusive).
	 */
	long getLong(long min, long max);

	/**
	 * @param bound
	 *            the limit value of your domain.
	 * @param type
	 *            the limit of bound you want. Min or Max.
	 * @return if type equals Min, returns a random long between bound and
	 *         Max_Value. if type equals Max, returns a random long between
	 *         Min_Value and bound.
	 */
	long getLong(long bound, BoundType type);

	/**
	 * @return a random float.
	 */
	float getFloat();

	/**
	 * @return a random double.
	 */
	double getDouble();

	/**
	 * @param max
	 *            the max bound.
	 * @return a random double between 0 (inclusive) and max (exclusive).
	 */
	double getDouble(double max);

	/**
	 * @param min
	 *            the minimum possible value.
	 * @param max
	 *            the maximum possible value.
	 * @return a random double between min and max.
	 */
	double getDouble(double min, double max);

	/**
	 * @param bound
	 *            the limit value of your domain.
	 * @param type
	 *            the limit of bound you want. Min or Max.
	 * @return if type equals Min, returns a random double between bound and
	 *         Max_Value. if type equals Max, returns a random double between
	 *         Min_Value and bound.
	 */
	double getDouble(double bound, BoundType type);

	/**
	 * @return a random boolean.
	 */
	boolean getBool();

	/**
	 * @param length
	 *            the length of the returned string.
	 * @return a random String of length size with only alphabetic characters.
	 */
	String getString(int length);

	/**
	 * @param minLength
	 *            the minimum length of the string.
	 * @param maxLength
	 *            the maximum length of the string.
	 * @return a random String with a length between minLength and maxLength.
	 */
	String getString(int minLength, int maxLength);

	/**
	 * @param length
	 *            the length of the returned string.
	 * @param charSet
	 *            the String containing the set of characters to use, may be
	 *            null.
	 * @return a random String of length size with the letters extracted from
	 *         the given charSet.
	 */
	String getString(int length, String charSet);

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
	String getString(int minLength, int maxLength, String charSet);

	/**
	 * @param length
	 *            the length of the returned numeric string.
	 * @return a random String with only length numerical characters.
	 */
	String getNumericString(int length);

	/**
	 * @return returns a random DateTime with "AAAA-MM-DDTHH:MM:SS.MMMZ" (ex:
	 *         "1970-01-01T00:00:00.001Z") format.
	 */
	String getDateTime();

	/**
	 * @return returns the current DateTime with "AAAA-MM-DDTHH:MM:SS.MMMZ" (ex:
	 *         "2016-11-18T15:25:15.195Z") format.
	 */
	String getTodayDateTime();

	/**
	 * @return returns a random Date with "AAAA-MM-DD" (ex: "1970-01-01")
	 *         format.
	 */
	String getDate();
}
