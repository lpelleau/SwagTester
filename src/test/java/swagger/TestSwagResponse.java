package swagger;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import net.pelleau.swagger.SwagResponse;

public class TestSwagResponse {

	private static Logger log = LoggerFactory.getLogger(TestSwagResponse.class);

	@Test
	public void testResponseValues() throws UnirestException {
		long begin = System.currentTimeMillis();
		HttpResponse<String> response = Unirest.get("http://google.fr").asString();
		long elapsed = System.currentTimeMillis() - begin;

		SwagResponse swag = new SwagResponse(response, elapsed);

		assertEquals(swag.getStatusCode(), 200);

		log.debug("" + swag.getResponseTime());
	}
}
