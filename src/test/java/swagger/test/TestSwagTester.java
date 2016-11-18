package swagger.test;

import org.junit.Test;

import net.pelleau.swagger.SwagTester;

public class TestSwagTester {

	@Test
	public void testLib() {
		String pathFile = TestSwagTester.class.getClassLoader().getResource("petstore.json").getPath();
		SwagTester test = new SwagTester(pathFile);
	}
}
