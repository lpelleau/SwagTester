package net.pelleau.utils;

import java.sql.Timestamp;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;

final class ValidRandomGenerator implements RandomGenerator {

	private static ThreadLocalRandom rng = ThreadLocalRandom.current();

	public ValidRandomGenerator() {
	}

	@Override
	public int getInt() {
		return rng.nextInt();
	}

	@Override
	public int getInt(int max) {
		return rng.nextInt(max);
	}

	@Override
	public int getInt(int min, int max) {
		return rng.nextInt(min, max);
	}

	@Override
	public long getLong() {
		return rng.nextLong();
	}

	@Override
	public long getLong(long max) {
		return rng.nextLong(max);
	}

	@Override
	public long getLong(long min, long max) {
		return rng.nextLong(min, max);
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
		return rng.nextDouble(max);
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
	public String getString(int length) {
		return RandomStringUtils.random(length, 0, 0, true, false, null, rng);
	}

	@Override
	public String getString(int minLength, int maxLength) {
		return RandomStringUtils.random(getInt(minLength, maxLength), 0, 0, true, false, null, rng);
	}

	@Override
	public String getString(int length, String charSet) {
		return RandomStringUtils.random(length, 0, 0, true, false, charSet.toCharArray(), rng);
	}

	@Override
	public String getString(int minLength, int maxLength, String charSet) {
		return RandomStringUtils.random(getInt(minLength, maxLength), 0, 0, true, false, charSet.toCharArray(), rng);
	}

	@Override
	public String getNumericString(int length) {
		return RandomStringUtils.random(length, 0, 0, false, true, null, rng);
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
		return new Timestamp(System.currentTimeMillis()).toInstant().toString();

	}
}
