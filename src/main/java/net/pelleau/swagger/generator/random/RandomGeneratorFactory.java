package net.pelleau.swagger.generator.random;

import net.pelleau.swagger.methods.TestType;

public final class RandomGeneratorFactory {

	private RandomGeneratorFactory() {
	}

	private static ValidRandomGenerator valid;
	private static InvalidRandomGenerator invalid;
	private static ScalingRandomGenerator scaling;
	private static ExtremeRandomGenerator extreme;

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
			throw new RuntimeException("This TestType is not supported.");
		}
	}

	private static RandomGenerator getExtremeRandomGenerator() {
		if (extreme == null) {
			extreme = new ExtremeRandomGenerator();
		}
		return extreme;
	}

	private static RandomGenerator getScallingRandomGenerator() {
		if (scaling == null) {
			scaling = new ScalingRandomGenerator();
		}
		return scaling;
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
