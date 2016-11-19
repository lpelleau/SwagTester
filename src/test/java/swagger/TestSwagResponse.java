package swagger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import net.pelleau.swagger.SwagResponse;
import net.pelleau.utils.SwagResponseAssert;

public class TestSwagResponse {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(TestSwagResponse.class);

	@Test
	public void testStatusCodeValues() throws UnirestException {
		long begin = System.currentTimeMillis();
		HttpResponse<String> response = Unirest.get("http://google.fr").asString();
		long elapsed = System.currentTimeMillis() - begin;

		SwagResponse swag = new SwagResponse(response, elapsed);

		SwagResponseAssert.assertStatusCodeEquals(200, swag);
	}

	// @Test // cancel because content may change.
	public void testBodyValues() throws UnirestException, IOException, URISyntaxException {
		long begin = System.currentTimeMillis();
		HttpResponse<String> response = Unirest.get("http://petstore.swagger.io/v2/pet/4").asString();
		long elapsed = System.currentTimeMillis() - begin;

		SwagResponse swag = new SwagResponse(response, elapsed);

		SwagResponse expected = new SwagResponse(elapsed);
		expected.setStatusCode(200);
		expected.setBody(Files
				.readAllLines(Paths
						.get(TestSwagResponse.class.getClassLoader().getResource("expected_result_1.json").toURI()))
				.stream().collect(Collectors.joining("\n")));

		SwagResponseAssert.assertEquals(expected, swag);
	}

	// @Test // cancel because content may change.
	public void testBodyValues2() throws UnirestException, IOException, URISyntaxException {
		long begin = System.currentTimeMillis();
		HttpResponse<String> response = Unirest.get("http://petstore.swagger.io/v2/pet/62").asString();
		long elapsed = System.currentTimeMillis() - begin;

		SwagResponse swag = new SwagResponse(response, elapsed);

		SwagResponse expected = new SwagResponse(elapsed);
		expected.setStatusCode(200);
		expected.setBody(Files
				.readAllLines(Paths
						.get(TestSwagResponse.class.getClassLoader().getResource("expected_result_2.json").toURI()))
				.stream().collect(Collectors.joining("\n")));

		SwagResponseAssert.assertEquals(expected, swag);
	}
}
