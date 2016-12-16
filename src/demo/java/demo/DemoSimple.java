package demo;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;

import net.pelleau.swagger.EntryPoint;
import net.pelleau.swagger.SwagTester;
import net.pelleau.swagger.container.SwagTest;
import test.swagger.TestSwagTester;

public class DemoSimple {

	private static final String fileName = "petstore_simple.json";

	/**
	 * Montre le process g�n�ral pour un cas concret
	 */
	@Test
	public void simpleDemo() throws FileNotFoundException {
		File pathFile = new File(TestSwagTester.class.getClassLoader().getResource(fileName).getPath());

		SwagTester swagger = new SwagTester(pathFile.getAbsolutePath());

		if (swagger.isServerUp()) {
			System.out.println("SERVER ONLINE");

			swagger.entryPoints().forEach((name, ep) -> {
				System.out.println(name);
				System.out.println("\t" + ep);
				System.out.println();
			});

			EntryPoint pet = swagger.entryPoint("/pet");

			SwagTest vTest = pet.putMethod().validTest();

			System.out.println("VALID TEST");
			System.out.println(vTest.getRequest());
			System.out.println(vTest.getExpectedValues());
			System.out.println(vTest.getResponse());
			System.out.println(vTest.isValid());

			System.out.println();

			SwagTest iTest = pet.putMethod().invalidTest();

			System.out.println("INVALID TEST");
			System.out.println(iTest.getRequest());
			System.out.println(iTest.getExpectedValues());
			System.out.println(iTest.getResponse());
			System.out.println(iTest.isValid());

		} else {
			System.out.println("SERVER OFFLINE");
		}
	}

}
