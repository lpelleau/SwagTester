package swagger;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pelleau.swagger.SwagTester;
import net.pelleau.swagger.parser.ResultsParser;

public class TestResultsParser {
	private static Logger log = LoggerFactory.getLogger(TestResultsParser.class);

	private static final String file = "petstore_modified.json";
	private static final String resultsFile = "results.json";

	private SwagTester swagger;

	private ResultsParser parser;

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
			File pathFile = new File(TestResultsParser.class.getClassLoader().getResource(file).getPath());
			File pathFileRes = new File(TestResultsParser.class.getClassLoader().getResource(resultsFile).getPath());

			swagger = new SwagTester(pathFile.getAbsolutePath(), pathFileRes.getAbsolutePath());
			parser = new ResultsParser(pathFileRes.getAbsolutePath());
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
