package swagger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pelleau.swagger.SwagTester;
import net.pelleau.utils.SwagAssert;

public class TestSwagTester {

	// @SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(TestSwagTester.class);

	private static final String file = "petstore.json";

	private static final String crapFile = "petstore_crap.json";

	private static final String unknownFile = "unknown.json";

	private SwagTester swagger;

	@BeforeClass
	public static void setLog() {
		// avoid annoying log when requesting false website.
		java.util.logging.Logger.getLogger("org.apache.http.wire").setLevel(java.util.logging.Level.FINEST);
		java.util.logging.Logger.getLogger("org.apache.http.headers").setLevel(java.util.logging.Level.FINEST);
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
		System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "ERROR");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "ERROR");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "ERROR");
	}

	@Before
	public void initialize() {
		try {
			File pathFile = new File(TestSwagTester.class.getClassLoader().getResource(file).getPath());

			swagger = new SwagTester(pathFile.getAbsolutePath());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testServerUp() {
		assertTrue(swagger.isServerUp());
	}

	@Test
	public void testServerDown() {
		try {
			String crapPathFile = new File(TestSwagTester.class.getClassLoader().getResource(crapFile).getPath())
					.getAbsolutePath();

			SwagTester crapSwagger = new SwagTester(crapPathFile);

			assertFalse(crapSwagger.isServerUp());
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		}
	}

	@Test(expected = FileNotFoundException.class)
	public void testFileNotFound() throws FileNotFoundException {
		new SwagTester(unknownFile);
	}

	@Test
	public void testFull() {
		swagger.entryPoints().forEach((name, entry) -> {
			log.debug("Testing : " + entry.toString());

			// change the max loop value to test many times
			for (int i = 0; i <= 1; i++) {

				if (entry.getMethod() != null) {
					SwagAssert.assertValid(entry.getMethod().validTest());
					SwagAssert.assertValid(entry.getMethod().invalidTest());
					SwagAssert.assertValid(entry.getMethod().scalingTest());
					// SwagAssert.assertValid(entry.getMethod().extremValuesTest());
					// SwagAssert.assertValid(entry.getMethod().timeoutTest(1000));
				}

				if (entry.headMethod() != null) {
					SwagAssert.assertValid(entry.headMethod().validTest());
					SwagAssert.assertValid(entry.headMethod().invalidTest());
					SwagAssert.assertValid(entry.headMethod().scalingTest());
					// SwagAssert.assertValid(entry.headMethod().extremValuesTest());
					// SwagAssert.assertValid(entry.headMethod().timeoutTest(1000));
				}

				if (entry.postMethod() != null) {
					// SwagAssert.assertValid(entry.postMethod().validTest());
					// SwagAssert.assertValid(entry.postMethod().invalidTest());
					// SwagAssert.assertValid(entry.postMethod().scalingTest());
					// SwagAssert.assertValid(entry.postMethod().extremValuesTest());
					// SwagAssert.assertValid(entry.postMethod().timeoutTest(1000));
				}

				if (entry.putMethod() != null) {
					// SwagAssert.assertValid(entry.putMethod().validTest());
					// SwagAssert.assertValid(entry.putMethod().invalidTest());
					// SwagAssert.assertValid(entry.putMethod().scalingTest());
					// SwagAssert.assertValid(entry.putMethod().extremValuesTest());
					// SwagAssert.assertValid(entry.putMethod().timeoutTest(1000));
				}

				if (entry.patchMethod() != null) {
					// SwagAssert.assertValid(entry.patchMethod().validTest());
					// SwagAssert.assertValid(entry.patchMethod().invalidTest());
					// SwagAssert.assertValid(entry.patchMethod().scalingTest());
					// SwagAssert.assertValid(entry.patchMethod().extremValuesTest());
					// SwagAssert.assertValid(entry.patchMethod().timeoutTest(1000));
				}

				if (entry.optionsMethod() != null) {
					// SwagAssert.assertValid(entry.optionsMethod().validTest());
					// SwagAssert.assertValid(entry.optionsMethod().invalidTest());
					// SwagAssert.assertValid(entry.optionsMethod().scalingTest());
					// SwagAssert.assertValid(entry.optionsMethod().extremValuesTest());
					// SwagAssert.assertValid(entry.optionsMethod().timeoutTest(1000));
				}

				if (entry.deleteMethod() != null) {
					// SwagAssert.assertValid(entry.deleteMethod().validTest());
					// SwagAssert.assertValid(entry.deleteMethod().invalidTest());
					// SwagAssert.assertValid(entry.deleteMethod().scalingTest());
					// SwagAssert.assertValid(entry.deleteMethod().extremValuesTest());
					// SwagAssert.assertValid(entry.deleteMethod().timeoutTest(1000));
				}
			}
		});
	}
}
