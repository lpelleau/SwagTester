package net.pelleau.swagger.generator.random;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;

final class InvalidRandomGenerator implements RandomGenerator {

	private static ThreadLocalRandom rng = ThreadLocalRandom.current();

	public InvalidRandomGenerator() {
	}

	@Override
	public long getLong(long min, long max) {
		if (rng.nextBoolean()) {
			return rng.nextLong(Long.MIN_VALUE, min);
		} else {
			return rng.nextLong(max, Long.MAX_VALUE);
		}
	}

	@Override
	public double getDouble(double min, double max) {
		if (rng.nextBoolean()) {
			return rng.nextDouble(-Double.MAX_VALUE, min);
		} else {
			return rng.nextDouble(max, Double.MAX_VALUE);
		}
	}

	@Override
	public boolean getBool() {
		return rng.nextBoolean();
	}

	@Override
	public String getString(int minLength, int maxLength) {
		if (rng.nextBoolean() && minLength > 1) {
			return RandomStringUtils.random((int) rng.nextLong(1, minLength), 0, 0, true, false, null, rng);
		} else {
			return RandomStringUtils.random((int) rng.nextLong(maxLength, 250), 0, 0, true, false, null, rng);
		}
	}

	@Override
	public String getDate() {
		return getNumericString(4) + "-" + getNumericString(2) + "-" + getNumericString(2);
	}

	@Override
	public String getDateTime() {
		return getNumericString(4) + "-" + getNumericString(2) + "-" + getNumericString(2) + "T"
				+ String.format("%02d", rng.nextInt(25)) + ":" + String.format("%02d", rng.nextInt(61)) + ":"
				+ String.format("%02d", rng.nextInt(61)) + "." + getNumericString(3) + "Z";
	}

	private String getNumericString(int length) {
		return RandomStringUtils.random(length, 0, 0, false, true, null, rng);
	}

	@Override
	public String getValue(List<String> enums) {
		String curr;
		do {
			curr = RandomStringUtils.random(enums.get(0).length(), 0, 0, true, false, null, rng);
		} while (enums.contains(curr));
		return curr;
	}
}
