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

	protected abstract HttpMethod getHttpMethod();

	protected void genericTest(TestType testType) {
		try {
			String endPoint = swag.getHost() + (name.replace("{petId}", "4"));

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
				for (String app : operation.getConsumes()) {
					request.header("accept", app);
				}
			}

			HttpResponse<JsonNode> response = request.asJson();

			log.debug(response.getBody().toString());
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	public void validTest() {
		genericTest(TestType.VALID);
	}

	public void timeoutTest() {
		genericTest(TestType.TIMEOUT);
	}

	public void extremValuesTest() {
		genericTest(TestType.EXTREME_VALUES);
	}

	public void invalidTest() {
		genericTest(TestType.INVALID);
	}

	public void scalingTest() {
		genericTest(TestType.SCALLING);
	}
}
