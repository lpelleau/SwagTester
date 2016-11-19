package net.pelleau.swagger.methods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.FormParameter;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import net.pelleau.swagger.SwagRequest;
import net.pelleau.swagger.SwagResponse;
import net.pelleau.swagger.SwagTest;
import net.pelleau.swagger.SwagTester;
import net.pelleau.utils.RandomGenerator;

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

	protected abstract HttpMethod getHttpMethod();

	protected SwagTest genericTest2(TestType testType) {

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

				// TODO take account of the optional values like Maximum,
				// Minimum, MaxLength
				// cf : http://swagger.io/specification/#parameterObject
				case "path": {
					PathParameter pathParam = (PathParameter) param;

					switch (pathParam.getType()) {
					case "string": {
						request.setUrl(request.getUrl().replaceAll("\\{" + pathParam.getName() + "\\}",
								RandomGenerator.getString(10)));
						break;
					}
					case "number": {
						request.setUrl(request.getUrl().replaceAll("\\{" + pathParam.getName() + "\\}",
								String.valueOf(RandomGenerator.getDouble())));
						break;
					}
					case "integer": {
						request.setUrl(request.getUrl().replaceAll("\\{" + pathParam.getName() + "\\}",
								String.valueOf(RandomGenerator.getInt(1000))));
						break;
					}
					case "boolean": {
						request.setUrl(request.getUrl().replaceAll("\\{" + pathParam.getName() + "\\}",
								String.valueOf(RandomGenerator.getBool())));
						break;
					}
					case "array":
						// TODO
						break;
					}
					break;
				}
				case "query": {
					QueryParameter queryParam = (QueryParameter) param;

					switch (queryParam.getType()) {
					case "string": {
						request.getQueryParameters().put(queryParam.getName(), RandomGenerator.getString(10));
						break;
					}
					case "number": {
						request.getQueryParameters().put(queryParam.getName(),
								String.valueOf(RandomGenerator.getDouble()));
						break;
					}
					case "integer": {
						request.getQueryParameters().put(queryParam.getName(),
								String.valueOf(RandomGenerator.getInt(1000)));
						break;
					}
					case "boolean": {
						request.getQueryParameters().put(queryParam.getName(),
								String.valueOf(RandomGenerator.getBool()));
						break;
					}
					case "array":
						// TODO
						break;
					}
					break;
				}
				case "header": {
					HeaderParameter headerParam = (HeaderParameter) param;

					switch (headerParam.getType()) {
					case "string": {
						request.getHeaderParameters().put(headerParam.getName(), RandomGenerator.getString(10));
						break;
					}
					case "number": {
						request.getHeaderParameters().put(headerParam.getName(),
								String.valueOf(RandomGenerator.getDouble()));
						break;
					}
					case "integer": {
						request.getHeaderParameters().put(headerParam.getName(),
								String.valueOf(RandomGenerator.getInt(1000)));
						break;
					}
					case "boolean": {
						request.getHeaderParameters().put(headerParam.getName(),
								String.valueOf(RandomGenerator.getBool()));
						break;
					}
					case "array":
						// TODO
						break;
					}
					break;
				}
				case "formData": {
					FormParameter formParam = (FormParameter) param;

					switch (formParam.getType()) {
					case "string": {
						request.getFormDataParameters().put(formParam.getName(), RandomGenerator.getString(10));
						break;
					}
					case "number": {
						request.getFormDataParameters().put(formParam.getName(),
								String.valueOf(RandomGenerator.getDouble()));
						break;
					}
					case "integer": {
						request.getFormDataParameters().put(formParam.getName(),
								String.valueOf(RandomGenerator.getInt(1000)));
						break;
					}
					case "boolean": {
						request.getFormDataParameters().put(formParam.getName(),
								String.valueOf(RandomGenerator.getBool()));
						break;
					}
					case "array":
						// TODO
						break;
					}

					break;
				}
				case "body": {
					BodyParameter bodyParam = (BodyParameter) param;

					request.setBodyParameters(RandomGenerator.fillObject(bodyParam.getSchema()));

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
		if (!testCase.getExpectedValues().stream().anyMatch(resp -> {
			return resp.getStatusCode() == 200;
		})) {
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

	protected SwagResponse genericTest(TestType testType) {
		try {
			String endPoint = swag.getHost();

			String params[] = name.split("/");
			for (int i = 0; i < params.length; ++i) {

				switch (testType) {
				default:
				case VALID:
					// If the parameter contains the characters "id", We
					// consider the API require an integer
					if (name.contains("Id") || name.contains("id")) {
						int id = RandomGenerator.getInt(Integer.MAX_VALUE);
						params[i] = params[i].replaceAll("\\{[a-zA-x]+\\}", String.valueOf(id));

					} else {
						String str = RandomGenerator.getString(RandomGenerator.getInt(200));
						params[i] = params[i].replaceAll("\\{[a-zA-x]+\\}", str);
					}
					break;

				case EXTREME_VALUES:
					// If the parameter contains the characters "id", We
					// consider the API require an integer
					if (name.contains("Id") || name.contains("id")) {
						int id = 0;
						if (RandomGenerator.getInt(2) == 0) {
							id = Integer.MAX_VALUE;
						}
						params[i] = params[i].replaceAll("\\{[a-zA-x]+\\}", String.valueOf(id));

					} else {
						String str = "";
						if (RandomGenerator.getInt(2) == 0) {
							str = RandomGenerator.getString(RandomGenerator.getInt(500));
						}
						params[i] = params[i].replaceAll("\\{[a-zA-x]+\\}", str);
					}
					break;

				case INVALID:
					String str = null;

					if (RandomGenerator.getInt(2) == 0) {
						str = RandomGenerator.getString(RandomGenerator.getInt(500));

					} else {
						str = String.valueOf(RandomGenerator.getInt(Integer.MAX_VALUE) * -1);
					}

					params[i] = params[i].replaceAll("\\{[a-zA-x]+\\}", str);
					break;
				}

				endPoint += params[i] + "/";
			}

			if (operation.getParameters() != null) {
				for (Parameter param : operation.getParameters()) {
					if (param.getIn().equals("query")) {
						// TODO Generate val with expected (or not) type
						String val = "";
						endPoint += val;
					}
				}
			}

			log.debug("Requesting : " + endPoint);

			HttpRequest request = null;

			switch (getHttpMethod()) {
			case GET:
				request = Unirest.get(endPoint);
				break;
			case POST:
				request = Unirest.post(endPoint);
				break;
			case PUT:
				request = Unirest.put(endPoint);
				break;
			case DELETE:
				request = Unirest.delete(endPoint);
				break;
			case HEAD:
				request = Unirest.head(endPoint);
				break;
			case OPTIONS:
				request = Unirest.options(endPoint);
				break;
			case PATCH:
				request = Unirest.patch(endPoint);
				break;
			}

			if (operation.getConsumes() != null) {
				for (String app : operation.getProduces()) {
					request.header("accept", app);
				}
			}

			if (operation.getParameters() != null) {
				for (Parameter param : operation.getParameters()) {
					if (param.getIn().equals("header")) {
						// TODO Generate val with expected (or not) type
						String val = "";
						request.header(param.getName(), val);
					}
				}
			}

			// TODO Insert body here
			if (operation.getParameters() != null) {
				for (Parameter param : operation.getParameters()) {
					if (param.getIn().equals("body")) {
						// TODO Generate val with expected (or not) type
						// get Schema -> is array ? -> get ref -> fill with
						// Random
					}
				}
			}

			long begin = System.currentTimeMillis();

			HttpResponse<JsonNode> response = request.asJson();

			long elapsed = System.currentTimeMillis() - begin;

			SwagResponse retval = new SwagResponse(response, elapsed);

			log.debug(response.getBody().toString());

			return retval;
		} catch (UnirestException e) {
			e.printStackTrace();
			return null;
		}
	}

	public SwagTest validTest2() {
		return genericTest2(TestType.VALID);
	}

	public SwagResponse validTest() {
		return genericTest(TestType.VALID);
	}

	public SwagResponse timeoutTest(long milliseconds) {
		// TODO Insert timeout here
		return genericTest(TestType.TIMEOUT);
	}

	public SwagResponse extremValuesTest() {
		return genericTest(TestType.EXTREME_VALUES);
	}

	public SwagResponse invalidTest() {
		return genericTest(TestType.INVALID);
	}

	public SwagResponse scalingTest() {
		// TODO Insert threads here
		return genericTest(TestType.SCALLING);
	}
}
