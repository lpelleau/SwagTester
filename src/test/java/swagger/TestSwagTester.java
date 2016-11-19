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

import net.pelleau.swagger.SwagResponse;
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
		assertTrue(swagger.serverUpTest());
	}

	@Test
	public void testServerDown() {
		try {
			String crapPathFile = new File(TestSwagTester.class.getClassLoader().getResource(crapFile).getPath())
					.getAbsolutePath();

			SwagTester crapSwagger = new SwagTester(crapPathFile);

			assertFalse(crapSwagger.serverUpTest());
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		}
	}

	@Test(expected = FileNotFoundException.class)
	public void testFileNotFound() throws FileNotFoundException {
		new SwagTester(unknownFile);
	}

	@Test
	public void testGetPet() {
		SwagResponse response = swagger.entryPoint("/pet/{petId}").getMethod().validTest();

		log.debug(response.toString());
	}

	@Test
	public void testGeneric2() {
		SwagAssert.assertValid(swagger.entryPoint("/pet/{petId}").getMethod().validTest2());
	}

	@Test
	public void testFullGeneric2() {

		swagger.entryPoints().forEach((name, entry) -> {
			log.debug("Testing : " + entry.toString());

			// change the max loop value to test many times
			for (int i = 0; i <= 1; i++) {

				if (entry.getMethod() != null) {
					SwagAssert.assertValid(entry.getMethod().validTest2());
				}

				if (entry.headMethod() != null) {
					SwagAssert.assertValid(entry.headMethod().validTest2());
				}

				if (entry.postMethod() != null) {
					// SwagAssert.assertValid(entry.postMethod().validTest2());
				}

				if (entry.putMethod() != null) {
					// SwagAssert.assertValid(entry.putMethod().validTest2());
				}

				if (entry.patchMethod() != null) {
					// SwagAssert.assertValid(entry.patchMethod().validTest2());
				}

				if (entry.optionsMethod() != null) {
					// SwagAssert.assertValid(entry.optionsMethod().validTest2());
				}

				if (entry.deleteMethod() != null) {
					// SwagAssert.assertValid(entry.deleteMethod().validTest2());
				}

			}

		});

	}
}
