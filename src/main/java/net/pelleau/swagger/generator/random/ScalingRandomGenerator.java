package net.pelleau.swagger.generator.random;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;

final class ScalingRandomGenerator implements RandomGenerator {

	private static ThreadLocalRandom rng = ThreadLocalRandom.current();

	public ScalingRandomGenerator() {
	}

	@Override
	public long getLong(long min, long max) {
		return min;
	}

	@Override
	public double getDouble(double min, double max) {
		return min;
	}

	@Override
	public boolean getBool() {
		return false;
	}

	@Override
	public String getString(int minLength, int maxLength) {
		return RandomStringUtils.random(minLength, 0, 0, true, false, null, rng);
	}

	@Override
	public String getDate() {
		return "1990-01-01";
	}

	@Override
	public String getDateTime() {
		return "1990-01-01T00:00:00.001Z";
	}

	@Override
	public String getValue(List<String> enums) {
		return enums.get(0);
	}
}
