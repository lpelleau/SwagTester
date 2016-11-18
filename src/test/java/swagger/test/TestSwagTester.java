package swagger.test;

import org.junit.Test;

import net.pelleau.swagger.SwagTester;

public class TestSwagTester {

	@Test
	public void testLib() {
		SwagTester test = new SwagTester(TestSwagTester.class.getClassLoader().getResource("petstore.json").getPath());

		System.out.println(test.getHost());

		test.test();
	}
}
