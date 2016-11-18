package swagger.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pelleau.swagger.SwagTester;

public class TestSwagTester {

	private static Logger log = LoggerFactory.getLogger(TestSwagTester.class);

	private static final String file = "petstore.json";

	private static final String crapFile = "petstore_crap.json";

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
		// System.setProperty("debugParser", "true");

		try {
			String pathFile = new File(TestSwagTester.class.getClassLoader().getResource(file).getPath())
					.getAbsolutePath();
			swagger = new SwagTester(pathFile);
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
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void main() {
		swagger.entryPoints().forEach((name, ep) -> {
			log.debug(name);
		});

		swagger.entryPoint("/pet/{petId}").getMethod().validTest();
	}
}
