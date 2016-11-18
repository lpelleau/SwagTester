package net.pelleau.swagger.methods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import io.swagger.models.Operation;
import net.pelleau.swagger.SwagTester;

public abstract class Method {

	private static Logger log = LoggerFactory.getLogger(Method.class);

	private SwagTester swag;
	private String name;
	private Operation operation;

	public Method(SwagTester swag, String name, Operation operation) {
		this.swag = swag;
		this.name = name;
		this.operation = operation;
	}

	public void validTest() {

		try {
			String endPoint = swag.getHost() + (name.replace("{petId}", "4"));

			log.debug("Requesting : " + endPoint);

			HttpResponse<JsonNode> response = Unirest.get(endPoint).header("accept", "application/json").asJson();

			log.debug(response.getStatus() + " : " + response.getStatusText());
			log.debug(response.getBody().toString());

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void timeoutTest() {
		// TODO Auto-generated method stub

	}

	public void extremValuesTest() {
		// TODO Auto-generated method stub

	}

	public void invalidTest() {
		// TODO Auto-generated method stub

	}

	public void scalingTest() {
		// TODO Auto-generated method stub

	}
}
