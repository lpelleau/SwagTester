package net.pelleau.swagger.container;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

/**
 * Represents a testCase
 */
public class SwagTest {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(SwagTest.class);

	private SwagRequest request;
	private SwagResponse response;

	private List<SwagResponse> expectedValues;

	private JsonNode expectedBody;

	public SwagTest() {

	}

	public SwagTest(SwagRequest request, SwagResponse response) {
		this.request = request;
		this.response = response;
	}

	public void execute() throws UnirestException {
		if (request != null && request.getUrl() != null) {
			BaseRequest call = null;

			// generate request with
			switch (request.getMethod()) {
			case GET:
				call = Unirest.get(request.getUrl());
				break;
			case POST:
				call = Unirest.post(request.getUrl());
				break;
			case PUT:
				call = Unirest.put(request.getUrl());
				break;
			case DELETE:
				call = Unirest.delete(request.getUrl());
				break;
			case HEAD:
				call = Unirest.head(request.getUrl());
				break;
			case OPTIONS:
				call = Unirest.options(request.getUrl());
				break;
			case PATCH:
				call = Unirest.patch(request.getUrl());
				break;
			default:
				throw new RuntimeException("This HttpRequest is not supported.");
			}

			// apply path parameters
			if (!request.getPathParameters().isEmpty()) {
				for (Entry<String, String> entry : request.getPathParameters().entrySet()) {
					call = ((HttpRequest) call).routeParam(entry.getKey(), entry.getValue());
				}
			}

			// apply header parameters
			if (!request.getHeaderParameters().isEmpty()) {
				call = ((HttpRequest) call).headers(request.getHeaderParameters());
			}

			// apply query parameters
			if (!request.getQueryParameters().isEmpty()) {
				call = ((HttpRequest) call).queryString(request.getQueryParameters());
			}

			// apply body or form parameters
			if (call instanceof HttpRequestWithBody) {
				if (request.getBodyParameters() != null) {
					if (!((HttpRequest) call).getHeaders().containsKey("Content-Type")) {
						call = ((HttpRequest) call).header("Content-Type", "application/json");
					}
					call = ((HttpRequestWithBody) call).body(request.getBodyParameters().toString());
				} else if (!request.getFormDataParameters().isEmpty()) {
					call = ((HttpRequestWithBody) call).fields(request.getFormDataParameters());
				}
			}

			// call the web Api with responseTime
			long begin = System.currentTimeMillis();
			HttpResponse<String> input = call.asString();
			long elapsed = System.currentTimeMillis() - begin;

			response = new SwagResponse(input, elapsed);
		}
	}

	/**
	 * Test if the current Test succeed. eq if the response have every setted
	 * data of one of the expected values. For exemple, if you put only a 200
	 * statusCode on a expected Responses, if the response has a 200 statusCode,
	 * the test if valid. If you add a body part to the expected Response, it
	 * will be tested too.
	 * 
	 * @return whether or not the test succeed.
	 */
	public boolean isValid() {
		if (response == null) {
			return false;
		}

		boolean valid = true;

		if (expectedValues != null) {
			for (SwagResponse current : expectedValues) {
				valid = current.getStatusCode() == response.getStatusCode();

				if (valid && current.getStatusText() != null) {
					valid &= current.getStatusText().equals(response.getStatusText());
				}

				if (valid && current.getHeader() != null) {
					valid &= current.getHeader().equals(response.getHeader());
				}

				if (valid && current.getBody() != null) {
					// TODO try to have a regex or a matching tool to have joker
					// char in case we can't predict the answer
					valid &= current.getBody().equals(response.getBody());
				}
			}
		}

		if (getExpectedBody() != null) {
			valid &= response.getBody().equals(getExpectedBody().toString());
		}

		if (valid) {
			return valid;
		}

		if (request.isDeprecated()) {
			throw new RuntimeException("The test fail but this entrypoint is deprecated.");
		}

		return false;
	}

	/**
	 * The list of every possible valid response. Usage :
	 * "[SwagTestObject].getExpectedValues().add([SwagResponseObject]);"
	 * 
	 * @return
	 */
	public List<SwagResponse> getExpectedValues() {
		if (expectedValues == null) {
			expectedValues = new ArrayList<SwagResponse>();
		}
		return expectedValues;
	}

	public void setRequest(SwagRequest request) {
		this.request = request;
	}

	public SwagResponse getResponse() {
		return response;
	}

	public SwagRequest getRequest() {
		return request;
	}

	public void setExpectedBody(JsonNode value) {
		expectedBody = value;
	}

	public JsonNode getExpectedBody() {
		return expectedBody;
	}

	@Override
	public String toString() {
		return "SwagTest [request=" + request + ", response=" + response + ", expectedValues=" + expectedValues + "]";
	}
}
