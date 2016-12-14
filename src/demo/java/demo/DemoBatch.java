package demo;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;

import net.pelleau.swagger.SwagTester;
import net.pelleau.swagger.container.SwagMetrics;
import test.swagger.TestSwagTester;

public class DemoBatch {

	private static final String fileName = "petstore_modified.json";

	/**
	 * Montre l'utilisation standard de la librairie sur cas réel. 10 tests de
	 * chaque type sur tout les entrypoints.
	 */
	@Test
	public void demoBatch() throws FileNotFoundException {
		File pathFile = new File(TestSwagTester.class.getClassLoader().getResource(fileName).getPath());

		SwagTester swagger = new SwagTester(pathFile.getAbsolutePath());

		if (swagger.isServerUp()) {
			System.out.println("SERVER ONLINE");

			SwagMetrics metrics = swagger.runTests(10);

			System.out.println("NB TESTS : " + metrics.getResults().size());
			System.out.println("NB SUCCESSFUL : " + metrics.getSuccessfulTests().size());
			System.out.println("NB FAILED : " + metrics.getFailedTests().size());
			System.out.println();

			System.out.println("MINIMUM : " + metrics.getMinExecTime());
			System.out.println("MAXIMUM : " + metrics.getMaxExecTime());
			System.out.println("AVERAGE : " + metrics.getAvgExecTime());
			System.out.println();

			metrics.getStats().forEach((type, stat) -> {
				System.out.println(type + " : " + stat);
			});

		} else {
			System.out.println("SERVER OFFLINE");
		}
	}
}
