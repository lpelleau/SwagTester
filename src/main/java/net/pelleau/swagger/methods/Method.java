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
import io.swagger.models.parameters.Parameter;
import net.pelleau.swagger.SwagResponse;
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

	protected SwagResponse genericTest(TestType testType) {
		try {

			// TODO create SwagTest

			// TODO create SwagRequest

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

			// TODO create ExpectedResponse

			log.debug("Requesting : " + endPoint);

			// TODO give the filled SwagTest to a method which launch the web
			// request to the server
			// TODO in this method, fill the SwagResponse

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

			// TODO Returns the filled SwagTest

			SwagResponse retval = new SwagResponse(response, elapsed);

			log.debug(response.getBody().toString());

			return retval;
		} catch (UnirestException e) {
			e.printStackTrace();
			return null;
		}
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
