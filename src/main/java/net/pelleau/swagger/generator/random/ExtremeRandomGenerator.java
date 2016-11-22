package net.pelleau.swagger.generator.random;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;

final class ExtremeRandomGenerator implements RandomGenerator {

	private static ThreadLocalRandom rng = ThreadLocalRandom.current();

	public ExtremeRandomGenerator() {
	}

	@Override
	public long getLong(long min, long max) {
		if (rng.nextBoolean()) {
			return min;
		} else {
			return max - 1;
		}
	}

	@Override
	public double getDouble(double min, double max) {
		if (rng.nextBoolean()) {
			return min;
		} else {
			return max - 1;
		}
	}

	@Override
	public boolean getBool() {
		return rng.nextBoolean();
	}

	@Override
	public String getString(int minLength, int maxLength) {
		if (rng.nextBoolean()) {
			return RandomStringUtils.random(minLength, 0, 0, true, false, null, rng);
		} else {
			return RandomStringUtils.random(maxLength - 1, 0, 0, true, false, null, rng);
		}
	}

	@Override
	public String getDate() {
		switch (rng.nextInt(4)) {
		default:
		case 0:
			return "1000-01-01";
		case 1:
			return "2000-12-31";
		case 2:
			return "3000-05-15";
		case 3:
			return "2015-02-29";
		}
	}

	@Override
	public String getDateTime() {
		switch (rng.nextInt(4)) {
		default:
		case 0:
			return "1000-01-01T00:00:00.001Z";
		case 1:
			return "1999-12-31T23:59:59.999Z";
		case 2:
			return "3000-05-15T23:59:59.999Z";
		case 3:
			return "2015-02-29T10:10:10.100Z";
		}
	}
}
