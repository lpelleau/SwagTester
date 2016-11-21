package net.pelleau.swagger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

/**
 * Represents a testCase
 */
public class SwagTest {

	private SwagRequest request;
	private SwagResponse response;

	private List<SwagResponse> expectedValues;

	public SwagTest() {

	}

	public SwagTest(SwagRequest request, SwagResponse response) {
		this.request = request;
		this.response = response;
	}

	public void execute() throws UnirestException {

		if (request != null && request.getUrl() != null) {

			HttpRequest call = null;

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
			}

			if (!request.getQueryParameters().isEmpty()) {
				call.queryString(request.getQueryParameters());
			}

			Map<String, String> headers = request.getHeaderParameters();

			if (call instanceof HttpRequestWithBody) {
				HttpRequestWithBody complexCall = (HttpRequestWithBody) call;

				if (request.getBodyParameters() != null) {
					headers.put("Content-Type", "application/json");
					complexCall.body(request.getBodyParameters().toString());
				} else if (!request.getFormDataParameters().isEmpty()) {
					headers.put("Content-Type", "multipart/form-data");
					complexCall.fields(request.getFormDataParameters());
				}

			}

			call.headers(headers);

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

		for (SwagResponse current : expectedValues) {
			boolean valid = current.getStatusCode() == response.getStatusCode();

			if (valid && current.getStatusText() != null) {
				valid &= current.getStatusText().equals(response.getStatusText());
			}

			if (valid && current.getHeader() != null) {
				valid &= current.getHeader().equals(response.getHeader());
			}

			if (valid && current.getBody() != null) {
				valid &= current.getBody().equals(response.getBody());
			}

			if (valid) {
				return valid;
			}
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

	@Override
	public String toString() {
		return "SwagTest [request=" + request + ", response=" + response + ", expectedValues=" + expectedValues + "]";
	}
}
