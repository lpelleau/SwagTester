package net.pelleau.swagger.methods;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import io.swagger.models.Operation;
import net.pelleau.swagger.SwagTester;

public abstract class Method {

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

			System.out.println("Requesting : " + endPoint);

			HttpResponse<JsonNode> response = Unirest.get(endPoint).header("accept", "application/json").asJson();

			System.out.println(response.getStatus() + " : " + response.getStatusText());
			System.out.println(response.getBody());

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
