package demo;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;

import net.pelleau.swagger.SwagTester;
import swagger.TestResultsParser;
import test.swagger.TestSwagTester;

public class DemoExpected {

	private static final String fileName = "petstore_modified.json";

	private static final String expectedFileName = "results.json";

	/**
	 * Montre l'utilisation de la librairie en chargeant un ficher avec des
	 * résultats précis attendus.
	 */
	@Test
	public void demoExpected() throws FileNotFoundException {
		File pathFile = new File(TestSwagTester.class.getClassLoader().getResource(fileName).getPath());
		File pathFileExpected = new File(
				TestResultsParser.class.getClassLoader().getResource(expectedFileName).getPath());

		SwagTester swagger = new SwagTester(pathFile.getAbsolutePath(), pathFileExpected.getAbsolutePath());

		if (swagger.isServerUp()) {
			System.out.println("SERVER ONLINE");

			swagger.sendRequests().forEach(test -> {
				System.out.print(test.getRequest().getUrl() + " : ");
				System.out.println(test.isValid() ? "AS EXPECTED" : "NOT AS EXPECTED");
			});

		} else {
			System.out.println("SERVER OFFLINE");
		}
	}
}
