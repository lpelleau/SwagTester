package net.pelleau.swagger;

import io.swagger.models.HttpMethod;
import net.pelleau.swagger.methods.TestType;

/**
 * Represents a http request.
 */
public class SwagRequest {

	private String url;

	private HttpMethod method;

	private TestType testType;

	private String headerParameters;
	private String queryParameters;
	private String bodyParameters;
	private String dataFormParameters;

}
