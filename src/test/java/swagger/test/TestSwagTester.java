package swagger.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import net.pelleau.swagger.SwagTester;

public class TestSwagTester {

	private static final String file = "petstore.json";

	SwagTester swagger;

	@Before
	public void initialize() {
		String pathFile = TestSwagTester.class.getClassLoader().getResource(file).getPath();
		swagger = new SwagTester(pathFile);
	}

	@Test
	public void testServerUp() {
		assertTrue(swagger.serverUpTest());
		assertTrue(swagger.serverUpTest(5000));
	}
}
