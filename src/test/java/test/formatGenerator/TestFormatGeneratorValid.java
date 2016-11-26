package test.formatGenerator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.pelleau.swagger.generator.random.FormatGenerator;
import net.pelleau.swagger.generator.random.RandomGenerator;
import net.pelleau.swagger.generator.random.RandomGeneratorFactory;
import net.pelleau.swagger.methods.TestType;

public class TestFormatGeneratorValid {

	@Test
	public void testGetInt32() {
		for (TestType type : TestType.values()) {
			RandomGenerator gen = RandomGeneratorFactory.getRandomGenerator(type);
			for (int i = 0; i < 1000; i++) {
				Object integer = FormatGenerator.getInteger(gen, "int32", 10, 100);
				assertTrue(integer instanceof Integer);
			}
		}
	}

	@Test
	public void testGetInt64() {
		for (TestType type : TestType.values()) {
			RandomGenerator gen = RandomGeneratorFactory.getRandomGenerator(type);
			for (int i = 0; i < 1000; i++) {
				Object integer = FormatGenerator.getInteger(gen, "int64", 10, 100);
				assertTrue(integer instanceof Long);
			}
		}
	}

	@Test
	public void testGetIntNull() {
		for (TestType type : TestType.values()) {
			RandomGenerator gen = RandomGeneratorFactory.getRandomGenerator(type);
			for (int i = 0; i < 1000; i++) {
				Object integer = FormatGenerator.getInteger(gen, null, 10, 100);
				assertTrue(integer instanceof Integer);
			}
		}
	}

	@Test
	public void testGetIntOther() {
		for (TestType type : TestType.values()) {
			RandomGenerator gen = RandomGeneratorFactory.getRandomGenerator(type);
			for (int i = 0; i < 1000; i++) {
				Object integer = FormatGenerator.getInteger(gen, "other", 10, 100);
				assertTrue(integer instanceof Integer);
			}
		}
	}

	@Test
	public void testGetNumberFloat() {
		for (TestType type : TestType.values()) {
			RandomGenerator gen = RandomGeneratorFactory.getRandomGenerator(type);
			for (int i = 0; i < 1000; i++) {
				Object number = FormatGenerator.getNumber(gen, "float", 10, 100);
				assertTrue(number instanceof Float);
			}
		}
	}

	@Test
	public void testGetNumberDouble() {
		for (TestType type : TestType.values()) {
			RandomGenerator gen = RandomGeneratorFactory.getRandomGenerator(type);
			for (int i = 0; i < 1000; i++) {
				Object number = FormatGenerator.getNumber(gen, "double", 10, 100);
				assertTrue(number instanceof Double);
			}
		}
	}

	@Test
	public void testGetNumberNull() {
		for (TestType type : TestType.values()) {
			RandomGenerator gen = RandomGeneratorFactory.getRandomGenerator(type);
			for (int i = 0; i < 1000; i++) {
				Object number = FormatGenerator.getNumber(gen, null, 10, 100);
				assertTrue(number instanceof Float);
			}
		}
	}

	@Test
	public void testGetNumberOther() {
		for (TestType type : TestType.values()) {
			RandomGenerator gen = RandomGeneratorFactory.getRandomGenerator(type);
			for (int i = 0; i < 1000; i++) {
				Object number = FormatGenerator.getNumber(gen, "other", 10, 100);
				assertTrue(number instanceof Float);
			}
		}
	}

	@Test
	public void testGetStringSimple() {
		for (TestType type : TestType.values()) {
			RandomGenerator gen = RandomGeneratorFactory.getRandomGenerator(type);
			for (int i = 0; i < 1000; i++) {
				Object str = FormatGenerator.getString(gen, "", 10, 20);
				assertTrue(str instanceof String);
			}
		}
	}

	@Test
	public void testGetStringPassword() {
		for (TestType type : TestType.values()) {
			RandomGenerator gen = RandomGeneratorFactory.getRandomGenerator(type);
			for (int i = 0; i < 1000; i++) {
				Object str = FormatGenerator.getString(gen, "password", 10, 20);
				assertTrue(str instanceof String);
			}
		}
	}

	@Test
	public void testGetStringByte() {
		for (TestType type : TestType.values()) {
			RandomGenerator gen = RandomGeneratorFactory.getRandomGenerator(type);
			for (int i = 0; i < 1000; i++) {
				Object str = FormatGenerator.getString(gen, "byte", 10, 20);
				assertTrue(str instanceof byte[]);
			}
		}
	}

	@Test
	public void testGetStringBinary() {
		for (TestType type : TestType.values()) {
			RandomGenerator gen = RandomGeneratorFactory.getRandomGenerator(type);
			for (int i = 0; i < 1000; i++) {
				Object str = FormatGenerator.getString(gen, "binary", 10, 20);
				assertTrue(str instanceof byte[]);
			}
		}
	}

	@Test
	public void testGetStringDate() {
		for (TestType type : TestType.values()) {
			RandomGenerator gen = RandomGeneratorFactory.getRandomGenerator(type);
			for (int i = 0; i < 1000; i++) {
				Object str = FormatGenerator.getString(gen, "date", 10, 20);
				assertTrue(str instanceof String);
			}
		}
	}

	@Test
	public void testGetStringDateTime() {
		for (TestType type : TestType.values()) {
			RandomGenerator gen = RandomGeneratorFactory.getRandomGenerator(type);
			for (int i = 0; i < 1000; i++) {
				Object str = FormatGenerator.getString(gen, "date-time", 10, 20);
				assertTrue(str instanceof String);
			}
		}
	}

	@Test
	public void testGetStringNull() {
		for (TestType type : TestType.values()) {
			RandomGenerator gen = RandomGeneratorFactory.getRandomGenerator(type);
			for (int i = 0; i < 1000; i++) {
				Object str = FormatGenerator.getString(gen, null, 10, 20);
				assertTrue(str instanceof String);
			}
		}
	}

	@Test
	public void testGetStringOther() {
		for (TestType type : TestType.values()) {
			RandomGenerator gen = RandomGeneratorFactory.getRandomGenerator(type);
			for (int i = 0; i < 1000; i++) {
				Object str = FormatGenerator.getString(gen, "other", 10, 20);
				assertTrue(str instanceof String);
			}
		}
	}

}
