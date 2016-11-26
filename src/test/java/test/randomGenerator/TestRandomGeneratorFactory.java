package test.randomGenerator;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import net.pelleau.swagger.generator.random.RandomGeneratorFactory;
import net.pelleau.swagger.methods.TestType;

public class TestRandomGeneratorFactory {

	@Test
	public void testValidGenerator() {
		assertNotNull(RandomGeneratorFactory.getRandomGenerator(TestType.VALID));
	}

	@Test
	public void testInvalidGenerator() {
		assertNotNull(RandomGeneratorFactory.getRandomGenerator(TestType.INVALID));
	}

	@Test
	public void testScalingGenerator() {
		assertNotNull(RandomGeneratorFactory.getRandomGenerator(TestType.SCALLING));
	}

	@Test
	public void testExtremeGenerator() {
		assertNotNull(RandomGeneratorFactory.getRandomGenerator(TestType.EXTREME_VALUES));
	}

	@Test
	public void testTimeoutGenerator() {
		assertNotNull(RandomGeneratorFactory.getRandomGenerator(TestType.TIMEOUT));
	}
}
