package net.pelleau.swagger;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a testCase
 */
public class SwagTest {

	private SwagRequest request;
	private SwagResponse response;

	private List<SwagResponse> expectedValues;

	public SwagTest(SwagRequest request, SwagResponse response) {
		this.request = request;
		this.response = response;
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

	public SwagResponse getResponse() {
		return response;
	}

	public SwagRequest getRequest() {
		return request;
	}

}
