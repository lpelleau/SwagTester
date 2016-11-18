package swagger.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.pelleau.swagger.SwagTester;

public class TestSwagTester {

	@Test
	public void main() {
		String pathFile = TestSwagTester.class.getClassLoader().getResource("petstore.json").getPath();

		SwagTester test = new SwagTester(pathFile);

		// assertTrue(test.serverUpTest());

		test.entryPoints().forEach((name, ep) -> {
			System.out.println(name);
		});

		test.entryPoint("/pet/{petId}").getMethod().validTest();
	}
}
