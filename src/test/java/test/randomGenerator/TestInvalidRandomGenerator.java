package test.randomGenerator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import net.pelleau.swagger.generator.random.RandomGenerator;
import net.pelleau.swagger.generator.random.RandomGeneratorFactory;
import net.pelleau.swagger.methods.TestType;

public class TestInvalidRandomGenerator {

	private RandomGenerator invalid;

	@Before
	public void initialize() {
		invalid = RandomGeneratorFactory.getRandomGenerator(TestType.INVALID);
	}

	@Test
	public void testString() {
		for (int i = 0; i < 10000; i++) {
			String str = invalid.getString(10, 100);
			assertTrue(str.length() < 10 || str.length() >= 100);
		}
	}

	@Test
	public void testInt() {
		for (int i = 0; i < 10000; i++) {
			long lg = invalid.getLong(100, 1000);
			assertTrue(lg < 100 || lg >= 1000);
		}
	}

	@Test
	public void testInt2() {
		for (int i = 0; i < 10000; i++) {
			long lg = invalid.getLong(-1000, -100);
			assertTrue(lg < -1000 || lg >= -100);
		}
	}

	@Test
	public void testInt3() {
		for (int i = 0; i < 10000; i++) {
			long lg = invalid.getLong(-100, 100);
			assertTrue(lg < -100 || lg >= 100);
		}
	}

	@Test
	public void testDouble() {
		for (int i = 0; i < 10000; i++) {
			double dl = invalid.getDouble(100, 1000);
			assertTrue(dl < 100 || dl >= 1000);
		}
	}

	@Test
	public void testDouble2() {
		for (int i = 0; i < 10000; i++) {
			double dl = invalid.getDouble(-1000, -100);
			assertTrue(dl < -1000 || dl >= -100);
		}
	}

	@Test
	public void testDouble3() {
		for (int i = 0; i < 10000; i++) {
			double dl = invalid.getDouble(-100, 100);
			assertTrue(dl < -100 || dl >= 100);
		}
	}

	@Test
	public void testDate() {
		Pattern datePattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
		for (int i = 0; i < 10000; i++) {
			String date = invalid.getDate();
			assertTrue(datePattern.matcher(date).find());
		}
	}

	@Test
	public void testDateTime() {
		Pattern datePattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$");
		for (int i = 0; i < 10000; i++) {
			String date = invalid.getDateTime();
			assertTrue(datePattern.matcher(date).find());
		}
	}

	@Test
	public void testValue() {
		ArrayList<String> list = new ArrayList<String>();

		list.add("aaaa");
		list.add("bbbb");
		list.add("cccc");
		list.add("dddd");

		for (int i = 0; i < 10000; i++) {
			assertFalse(list.contains(invalid.getValue(list)));
		}
	}

}
