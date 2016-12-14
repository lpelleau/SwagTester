package demo;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;

import net.pelleau.swagger.EntryPoint;
import net.pelleau.swagger.SwagTester;
import net.pelleau.swagger.container.SwagTest;
import test.swagger.TestSwagTester;

public class DemoGen {

	private static final String fileName = "petstore_simple.json";

	/**
	 * Permet de voir les possibilités des générateurs aléatoires et de voir la
	 * prise en compte des valeurs du fichier json.
	 */
	@Test
	public void possibilitiesOfRandomGenerator() throws FileNotFoundException {
		File pathFile = new File(TestSwagTester.class.getClassLoader().getResource(fileName).getPath());

		SwagTester swagger = new SwagTester(pathFile.getAbsolutePath());

		EntryPoint pet = swagger.entryPoint("/demo/{pathValue}");

		SwagTest vTest = pet.postMethod().validTest();
		System.out.println("VALID DATA");
		System.out.println(vTest.getRequest());
		System.out.println();

		SwagTest iTest = pet.postMethod().invalidTest();
		System.out.println("INVALID DATA");
		System.out.println(iTest.getRequest());
		System.out.println();

		SwagTest xTest = pet.postMethod().extremValuesTest();
		System.out.println("EXTREME DATA");
		System.out.println(xTest.getRequest());
		System.out.println();
	}

}
