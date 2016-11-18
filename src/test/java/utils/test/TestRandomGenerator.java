package utils.test;

import org.junit.Test;

import net.pelleau.utils.RandomGenerator;

public class TestRandomGenerator {

	@Test
	public void testDateTime() {

		System.out.println(RandomGenerator.getDateTime());

		System.out.println(RandomGenerator.getDateTime());

		System.out.println(RandomGenerator.getTodayDateTime());

	}

	@Test
	public void testSimpleValues() {
		System.out.println(RandomGenerator.getString(10));
		System.out.println(RandomGenerator.getString(10));
		System.out.println(RandomGenerator.getString(10));
		System.out.println(RandomGenerator.getString(10));

		System.out.println(RandomGenerator.getString(10, "azerty"));
		System.out.println(RandomGenerator.getString(10, "azerty"));
		System.out.println(RandomGenerator.getString(10, "azerty"));
		System.out.println(RandomGenerator.getString(10, "azerty"));
	}

}
