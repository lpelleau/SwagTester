package net.pelleau.utils;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;

final class InvalidRandomGenerator implements RandomGenerator {

	private static ThreadLocalRandom rng = ThreadLocalRandom.current();

	/**
	 * 
	 * @param min
	 *            min (inclusive) bound.
	 * @param max
	 *            max (exclusive) bound.
	 * @return a random int between min and max-1.
	 */
	private static int rangeInt(int min, int max) {
		// TODO
		return min;
	}

	private static long rangeLong(long min, long max) {
		// TODO
		return min;
	}

	private static double rangeDouble(double min, double max) {
		// TODO
		return min;
	}

	public InvalidRandomGenerator() {
	}

	@Override
	public int getInt() {
		return rng.nextInt();
	}

	@Override
	public int getInt(int max) {
		if (rng.nextBoolean()) {
			return rangeInt(Integer.MIN_VALUE, 0);
		} else {
			return rangeInt(max, Integer.MAX_VALUE);
		}
	}

	@Override
	public int getInt(int min, int max) {
		if (rng.nextBoolean()) {
			return rangeInt(Integer.MIN_VALUE, min);
		} else {
			return rangeInt(max, Integer.MAX_VALUE);
		}
	}

	@Override
	public long getLong() {
		return rng.nextLong();
	}

	@Override
	public long getLong(long max) {
		if (rng.nextBoolean()) {
			return rangeLong(Long.MIN_VALUE, 0);
		} else {
			return rangeLong(max, Long.MAX_VALUE);
		}
	}

	@Override
	public long getLong(long min, long max) {
		if (rng.nextBoolean()) {
			return rangeLong(Long.MIN_VALUE, min);
		} else {
			return rangeLong(max, Long.MAX_VALUE);
		}
	}

	@Override
	public float getFloat() {
		return rng.nextFloat();
	}

	@Override
	public double getDouble() {
		return rng.nextDouble();
	}

	@Override
	public double getDouble(double max) {
		if (rng.nextBoolean()) {
			return rangeDouble(Double.MIN_VALUE, 0);
		} else {
			return rangeDouble(max, Double.MAX_VALUE);
		}
	}

	@Override
	public double getDouble(double min, double max) {
		if (rng.nextBoolean()) {
			return rangeDouble(Double.MIN_VALUE, min);
		} else {
			return rangeDouble(max, Double.MAX_VALUE);
		}
	}

	@Override
	public boolean getBool() {
		return rng.nextBoolean();
	}

	@Override
	public String getString(int length) {
		if (rng.nextBoolean()) {
			return RandomStringUtils.random(rangeInt(0, length), 0, 0, true, false, null, rng);
		} else {
			return RandomStringUtils.random(rangeInt(length + 1, 250), 0, 0, true, false, null, rng);
		}
	}

	@Override
	public String getString(int minLength, int maxLength) {
		if (rng.nextBoolean()) {
			return RandomStringUtils.random(rangeInt(0, minLength), 0, 0, true, false, null, rng);
		} else {
			return RandomStringUtils.random(rangeInt(maxLength, 250), 0, 0, true, false, null, rng);
		}
	}

	@Override
	public String getString(int length, String charSet) {
		if (rng.nextBoolean()) {
			return RandomStringUtils.random(rangeInt(0, length), 0, 0, true, false, charSet.toCharArray(), rng);
		} else {
			return RandomStringUtils.random(rangeInt(length + 1, 250), 0, 0, true, false, charSet.toCharArray(), rng);
		}
	}

	@Override
	public String getString(int minLength, int maxLength, String charSet) {
		if (rng.nextBoolean()) {
			return RandomStringUtils.random(rangeInt(0, minLength), 0, 0, true, false, charSet.toCharArray(), rng);
		} else {
			return RandomStringUtils.random(rangeInt(maxLength, 250), 0, 0, true, false, charSet.toCharArray(), rng);
		}
	}

	@Override
	public String getNumericString(int length) {
		if (rng.nextBoolean()) {
			return RandomStringUtils.random(rangeInt(0, length), 0, 0, false, true, null, rng);
		} else {
			return RandomStringUtils.random(rangeInt(length + 1, 250), 0, 0, false, true, null, rng);
		}
	}

	@Override
	public String getDate() {
		return getNumericString(4) + "-" + getNumericString(2) + "-" + getNumericString(2);
	}

	@Override
	public String getDateTime() {
		return getNumericString(4) + "-" + getNumericString(2) + "-" + getNumericString(2) + "T" + rng.nextInt(25) + ":"
				+ rng.nextInt(61) + ":" + rng.nextInt(61) + "." + getNumericString(3) + "Z";
	}

	@Override
	public String getTodayDateTime() {
		return getDateTime();

	}
}
