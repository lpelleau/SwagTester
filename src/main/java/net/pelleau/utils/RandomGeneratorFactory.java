package net.pelleau.utils;

import net.pelleau.swagger.methods.TestType;

public final class RandomGeneratorFactory {

	private RandomGeneratorFactory() {
	}

	private static ValidRandomGenerator valid;
	private static InvalidRandomGenerator invalid;
	//
	//

	public static RandomGenerator getRandomGenerator(TestType testType) {
		switch (testType) {
		case VALID:
		case TIMEOUT:
			return getValidRandomGenerator();

		case INVALID:
			return getInvalidRandomGenerator();

		case SCALLING:
			return getScallingRandomGenerator();

		case EXTREME_VALUES:
			return getExtremeRandomGenerator();
		default:
			return null;
		}
	}

	private static RandomGenerator getExtremeRandomGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	private static RandomGenerator getScallingRandomGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	private static RandomGenerator getInvalidRandomGenerator() {
		if (invalid == null) {
			invalid = new InvalidRandomGenerator();
		}
		return invalid;
	}

	private static RandomGenerator getValidRandomGenerator() {
		if (valid == null) {
			valid = new ValidRandomGenerator();
		}
		return valid;
	}

}
