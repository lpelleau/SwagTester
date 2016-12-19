package swagger;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pelleau.swagger.SwagTester;

public class TestResultsParser {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(TestResultsParser.class);

	private static final String file = "petstore_modified.json";
	private static final String resultsFile = "results.json";

	private SwagTester swagger;

	@Before
	public void initialize() {
		try {
			File pathFile = new File(TestResultsParser.class.getClassLoader().getResource(file).getPath());
			File pathFileRes = new File(TestResultsParser.class.getClassLoader().getResource(resultsFile).getPath());

			swagger = new SwagTester(pathFile.getAbsolutePath(), pathFileRes.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testFull() {
		// parser.getEntryPoints().get("/pet").getMethod(HttpMethod.GET).getResults().get(0).getParameters().get(0).getData();

		swagger.sendRequests().forEach(r -> {
			System.out.println(r.isValid());
		});
	}
}
