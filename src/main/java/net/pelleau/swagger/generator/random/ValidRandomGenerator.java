package net.pelleau.swagger.generator.random;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;

final class ValidRandomGenerator implements RandomGenerator {

	private static ThreadLocalRandom rng = ThreadLocalRandom.current();

	ValidRandomGenerator() {
	}

	@Override
	public long getLong(long min, long max) {
		return rng.nextLong(min, max);
	}

	@Override
	public double getDouble(double min, double max) {
		return rng.nextDouble(min, max);
	}

	@Override
	public boolean getBool() {
		return rng.nextBoolean();
	}

	@Override
	public String getString(int minLength, int maxLength) {
		return RandomStringUtils.random((int) getLong(minLength, maxLength), 0, 0, true, false, null, rng);
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

	private String getNumericString(int length) {
		return RandomStringUtils.random(length, 0, 0, false, true, null, rng);
	}
}
