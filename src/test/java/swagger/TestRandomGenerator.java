package swagger;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pelleau.utils.RandomGenerator;

public class TestRandomGenerator {

	private static Logger log = LoggerFactory.getLogger(TestRandomGenerator.class);

	@Test
	public void testDateTime() {
		log.debug(RandomGenerator.getDateTime());

		log.debug(RandomGenerator.getDateTime());

		log.debug(RandomGenerator.getTodayDateTime());
	}

	@Test
	public void testSimpleValues() {
		log.debug("" + RandomGenerator.getBool());
		log.debug("" + RandomGenerator.getBool());
		log.debug("" + RandomGenerator.getBool());
		log.debug("" + RandomGenerator.getBool());

		log.debug("" + RandomGenerator.getInt());
		log.debug("" + RandomGenerator.getInt());
		log.debug("" + RandomGenerator.getInt());
		log.debug("" + RandomGenerator.getInt());

		log.debug("" + RandomGenerator.getInt(100));
		log.debug("" + RandomGenerator.getInt(100));
		log.debug("" + RandomGenerator.getInt(100));
		log.debug("" + RandomGenerator.getInt(100));

		log.debug("" + RandomGenerator.getInt(10, 20));
		log.debug("" + RandomGenerator.getInt(10, 20));
		log.debug("" + RandomGenerator.getInt(10, 20));
		log.debug("" + RandomGenerator.getInt(10, 20));

		log.debug(RandomGenerator.getString(10));
		log.debug(RandomGenerator.getString(10));
		log.debug(RandomGenerator.getString(10));
		log.debug(RandomGenerator.getString(10));

		log.debug(RandomGenerator.getString(10, "azerty"));
		log.debug(RandomGenerator.getString(10, "azerty"));
		log.debug(RandomGenerator.getString(10, "azerty"));
		log.debug(RandomGenerator.getString(10, "azerty"));
	}
}
