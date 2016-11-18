package swagger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pelleau.utils.RandomGenerator;

public class TestRandomGenerator {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(TestRandomGenerator.class);

	@BeforeClass
	public static void setSeed() {
		RandomGenerator.setSeed(12345678987654321l);
	}

	@Test
	public void testDateTime() {
		assertEquals("6261-16-27T1:54:34.596Z", RandomGenerator.getDateTime());
		assertEquals("7438-82-18T10:21:59.177Z", RandomGenerator.getDateTime());
		assertEquals("7121-77-02T13:34:47.535Z", RandomGenerator.getDateTime());
		assertEquals("8914-93-82T3:52:23.332Z", RandomGenerator.getDateTime());
	}

	@Test
	public void testSimpleValues() {
		assertFalse(RandomGenerator.getBool());
		assertFalse(RandomGenerator.getBool());
		assertTrue(RandomGenerator.getBool());
		assertTrue(RandomGenerator.getBool());

		assertEquals(787608502, RandomGenerator.getInt());
		assertEquals(-770768578, RandomGenerator.getInt());
		assertEquals(1033504252, RandomGenerator.getInt());
		assertEquals(-1236486315, RandomGenerator.getInt());

		assertEquals(36, RandomGenerator.getInt(100));
		assertEquals(99, RandomGenerator.getInt(100));
		assertEquals(87, RandomGenerator.getInt(100));
		assertEquals(16, RandomGenerator.getInt(100));

		assertEquals(13, RandomGenerator.getInt(10, 20));
		assertEquals(11, RandomGenerator.getInt(10, 20));
		assertEquals(19, RandomGenerator.getInt(10, 20));
		assertEquals(11, RandomGenerator.getInt(10, 20));

		assertEquals("5835", RandomGenerator.getNumericString(4));
		assertEquals("1995", RandomGenerator.getNumericString(4));
		assertEquals("0903", RandomGenerator.getNumericString(4));
		assertEquals("2717", RandomGenerator.getNumericString(4));

		assertEquals("ROJejAqXIb", RandomGenerator.getString(10));
		assertEquals("dipvJrhLMY", RandomGenerator.getString(10));
		assertEquals("RaGrWMLbjg", RandomGenerator.getString(10));
		assertEquals("mdLzdNZhMS", RandomGenerator.getString(10));

		assertEquals("aaarezaery", RandomGenerator.getString(10, "azerty"));
		assertEquals("rtztrertea", RandomGenerator.getString(10, "azerty"));
		assertEquals("yztazzytrr", RandomGenerator.getString(10, "azerty"));
		assertEquals("ezarzteeze", RandomGenerator.getString(10, "azerty"));
	}
}
