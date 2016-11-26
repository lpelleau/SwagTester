package swagger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pelleau.swagger.SwagTester;
import net.pelleau.swagger.methods.Method;
import net.pelleau.utils.SwagAssert;

public class TestSwagTester {

	// @SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(TestSwagTester.class);

	private static final String file = "petstore_modified.json";

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
	public void testSth() {
		// swagger.entryPoint("/pet").postMethod().validTest(); // Simple object
		SwagAssert.assertValid(swagger.entryPoint("/user/createWithArray").postMethod().validTest()); // Array
		// object
	}

	@Test
	public void testFull() {
		swagger.entryPoints().forEach((name, entry) -> {
			log.debug("Testing : " + entry.toString());

			// change the max loop value to test many times
			for (int i = 0; i <= 1; i++) {

				if (entry.getMethod() != null) {
					testMethod(entry.getMethod());
				}

				if (entry.headMethod() != null) {
					testMethod(entry.headMethod());
				}

				if (entry.postMethod() != null) {
					testMethod(entry.postMethod());
				}

				if (entry.putMethod() != null) {
					testMethod(entry.putMethod());
				}

				if (entry.patchMethod() != null) {
					testMethod(entry.patchMethod());
				}

				if (entry.optionsMethod() != null) {
					testMethod(entry.optionsMethod());
				}

				if (entry.deleteMethod() != null) {
					testMethod(entry.deleteMethod());
				}
			}
		});
	}

	@Test
	public void testOnlyGetMethod() {
		swagger.entryPoints().forEach((name, entry) -> {
			log.debug("Testing : " + entry.toString());
			if (entry.getMethod() != null) {
				testMethod(entry.getMethod());
			}
		});
	}

	@Test
	public void testOnlyPostMethod() {
		swagger.entryPoints().forEach((name, entry) -> {
			log.debug("Testing : " + entry.toString());
			if (entry.postMethod() != null) {
				testMethod(entry.postMethod());
			}
		});
	}

	@Test
	public void testOnlyPutMethod() {
		swagger.entryPoints().forEach((name, entry) -> {
			log.debug("Testing : " + entry.toString());
			if (entry.putMethod() != null) {
				testMethod(entry.putMethod());
			}
		});
	}

	@Test
	public void testOnlyDeleteMethod() {
		swagger.entryPoints().forEach((name, entry) -> {
			log.debug("Testing : " + entry.toString());
			if (entry.deleteMethod() != null) {
				testMethod(entry.deleteMethod());
			}
		});
	}

	private void testMethod(Method method) {
		testMethod(method, 1);
	}

	private void testMethod(Method method, int nbTest) {
		for (int i = 0; i < nbTest; i++) {
			executeTest(method);
		}
	}

	private void testMethodWithThread(Method method) throws InterruptedException {
		testMethodWithThread(method, 1);
	}

	private void testMethodWithThread(Method method, int nbTest) throws InterruptedException {
		testMethodWithThread(method, nbTest, 8);
	}

	private void testMethodWithThread(Method method, int nbTest, int nbThread) throws InterruptedException {
		ExecutorService threadPool = Executors.newFixedThreadPool(nbThread);
		for (int i = 0; i < nbTest; i++) {
			threadPool.submit(() -> {
				executeTest(method);
			});
		}
		threadPool.shutdown();
		threadPool.awaitTermination(1, TimeUnit.HOURS);
	}

	private void executeTest(Method method) {
		SwagAssert.assertValid(method.validTest());
		SwagAssert.assertValid(method.invalidTest());
		SwagAssert.assertValid(method.extremValuesTest());
		// SwagAssert.assertValid(method.scalingTest());
		// SwagAssert.assertValid(method.timeoutTest(1000));
	}
}
