package swagger;

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

	@Test
	public void testBodyValues() throws UnirestException {
		long begin = System.currentTimeMillis();
		HttpResponse<String> response = Unirest.get("http://petstore.swagger.io/v2/pet/4").asString();
		long elapsed = System.currentTimeMillis() - begin;

		SwagResponse swag = new SwagResponse(response, elapsed);

		SwagResponse expected = new SwagResponse(elapsed);
		expected.setBody(
				"{\"id\":4,\"category\":{\"id\":0,\"name\":\"test\"},\"name\":\"doggie\",\"photoUrls\":[\"string\"],\"tags\":[{\"id\":0,\"name\":\"string\"}],\"status\":\"available\"}");

		SwagResponseAssert.assertEquals(expected, swag);
	}
}
