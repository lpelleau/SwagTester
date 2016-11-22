package net.pelleau.swagger.methods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.exceptions.UnirestException;

import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import net.pelleau.swagger.SwagTester;
import net.pelleau.swagger.container.SwagRequest;
import net.pelleau.swagger.container.SwagResponse;
import net.pelleau.swagger.container.SwagTest;
import net.pelleau.swagger.generator.ParameterGenerator;

public abstract class Method {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(Method.class);

	private Swagger swagger;
	private SwagTester swag;
	private String name;
	private Operation operation;

	public Method(Swagger swagger, SwagTester swag, String name, Operation operation) {
		this.swagger = swagger;
		this.swag = swag;
		this.name = name;
		this.operation = operation;
	}

	protected abstract HttpMethod getHttpMethod();

	protected SwagTest genericTest(TestType testType) {

		// create empty testCase
		SwagTest testCase = new SwagTest();

		// create empty request
		SwagRequest request = new SwagRequest();

		// assign the request to the testCase
		testCase.setRequest(request);

		// fill request
		request.setTestType(testType);
		request.setUrl(swag.getHost());
		request.setMethod(getHttpMethod());

		request.setDeprecated((operation.isDeprecated() != null ? operation.isDeprecated() : false));

		// add http accept header
		if (operation.getProduces() != null && !operation.getProduces().isEmpty()) {
			operation.getProduces().forEach(pro -> request.getHeaderParameters().put("accept", pro));
		}

		// add http content-type header
		if (operation.getConsumes() != null && !operation.getConsumes().isEmpty()) {
			// TODO may ignore application/xml
			// TODO may limit this to one entry
			operation.getProduces().forEach(con -> request.getHeaderParameters().put("Content-Type", con));
		}

		// add endPoint to the url
		request.setUrl(request.getUrl() + name);

		// add parameters
		if (operation.getParameters() != null && !operation.getParameters().isEmpty()) {
			operation.getParameters()
					.forEach(param -> ParameterGenerator.fillParameter(swagger, request, param, testType));
		}

		// generate expected responses
		operation.getResponses().forEach((code, value) -> {

			String stringCode = (code.equals("default") ? "200" : code);

			SwagResponse expected = new SwagResponse();

			expected.setStatusCode(Integer.valueOf(stringCode));

			// TODO try to check the description value

			// TODO deal with value.getSchema() when the response have a
			// body part

			testCase.getExpectedValues().add(expected);

		});

		// add the default 200 statusCode if needed
		if (!testCase.getExpectedValues().stream().anyMatch(resp -> resp.getStatusCode() == 200)) {
			SwagResponse default200Response = new SwagResponse();
			default200Response.setStatusCode(200);
			testCase.getExpectedValues().add(default200Response);
		}

		// call the web api with the given parameters in request object
		try {
			testCase.execute();

			return testCase;
		} catch (UnirestException e) {
			throw new RuntimeException("Something went wrong calling the web API.");
		}
	}

	public SwagTest validTest() {
		return genericTest(TestType.VALID);
	}

	public SwagTest timeoutTest(long milliseconds) {
		// TODO Insert timeout here
		return genericTest(TestType.TIMEOUT);
	}

	public SwagTest extremValuesTest() {
		return genericTest(TestType.EXTREME_VALUES);
	}

	public SwagTest invalidTest() {
		return genericTest(TestType.INVALID);
	}

	public SwagTest scalingTest() {
		// TODO Insert threads here
		return genericTest(TestType.SCALLING);
	}

	@Override
	public String toString() {
		return "Method [name=" + name + ", operation=" + operation.getOperationId() + "]";
	}

}
