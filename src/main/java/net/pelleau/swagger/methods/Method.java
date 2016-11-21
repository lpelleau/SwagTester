package net.pelleau.swagger.methods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.exceptions.UnirestException;

import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.FormParameter;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import net.pelleau.swagger.SwagRequest;
import net.pelleau.swagger.SwagResponse;
import net.pelleau.swagger.SwagTest;
import net.pelleau.swagger.SwagTester;
import net.pelleau.utils.BodyGenerator;
import net.pelleau.utils.ParameterGenerator;
import net.pelleau.utils.RandomGenerator;

public abstract class Method {

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

		// add http accept header
		if (operation.getProduces() != null && !operation.getProduces().isEmpty()) {
			operation.getProduces().forEach(pro -> {
				request.getHeaderParameters().put("accept", pro);
			});
		}

		// add endPoint to the url
		request.setUrl(request.getUrl() + name);

		// add parameters
		if (operation.getParameters() != null && !operation.getParameters().isEmpty()) {
			operation.getParameters().forEach(param -> {
				switch (param.getIn()) {

				case "path": {
					PathParameter pathParam = (PathParameter) param;

					ParameterGenerator.fillPathParameter(request, pathParam, testType);

					break;
				}
				case "query": {
					QueryParameter queryParam = (QueryParameter) param;

					ParameterGenerator.fillQueryParameter(request, queryParam, testType);

					break;
				}
				case "header": {
					HeaderParameter headerParam = (HeaderParameter) param;

					ParameterGenerator.fillHeaderParameter(request, headerParam, testType);

					break;
				}
				case "formData": {
					FormParameter formParam = (FormParameter) param;

					ParameterGenerator.fillFormParameter(request, formParam, testType);

					break;
				}
				case "body": {
					BodyParameter bodyParam = (BodyParameter) param;

					Object body = BodyGenerator.fillBody(swagger, bodyParam.getSchema());
					request.setBodyParameters(body);

					break;
				}
				default: {
					log.error("The IN value is incorrect. Expected : 'body', 'query', 'path' or 'formData'. Given : '"
							+ param.getIn() + "'.");
				}
				}
			});
		}

		// generate expected responses
		operation.getResponses().forEach((code, value) -> {
			// TODO deal with "default" possible statusCode
			if (!code.equals("default")) {

				SwagResponse expected = new SwagResponse();

				expected.setStatusCode(Integer.valueOf(code));

				// TODO try to check the description value

				// TODO deal with value.getSchema() when the response have a
				// body part

				testCase.getExpectedValues().add(expected);
			}
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
			return null;
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
