package net.pelleau.swagger.container;

import java.util.HashMap;
import java.util.Map;

import io.swagger.models.HttpMethod;
import net.pelleau.swagger.methods.TestType;

/**
 * Represents a http request.
 */
public class SwagRequest {

	private String url;

	private HttpMethod method;

	private TestType testType;

	private Map<String, String> pathParameters;
	private Map<String, String> headerParameters;
	private Map<String, Object> queryParameters;
	private Object bodyParameters;
	private Map<String, Object> formDataParameters;

	private boolean deprecated;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public TestType getTestType() {
		return testType;
	}

	public void setTestType(TestType testType) {
		this.testType = testType;
	}

	public Object getBodyParameters() {
		return bodyParameters;
	}

	public void setBodyParameters(Object bodyParameters) {
		this.bodyParameters = bodyParameters;
	}

	public Map<String, String> getPathParameters() {
		if (pathParameters == null) {
			pathParameters = new HashMap<String, String>();
		}
		return pathParameters;
	}

	public void setPathParameter(Map<String, String> pathParameters) {
		this.pathParameters = pathParameters;
	}

	public Map<String, String> getHeaderParameters() {
		if (headerParameters == null) {
			headerParameters = new HashMap<String, String>();
		}
		return headerParameters;
	}

	public void setHeaderParameter(Map<String, String> headerParameters) {
		this.headerParameters = headerParameters;
	}

	public Map<String, Object> getQueryParameters() {
		if (queryParameters == null) {
			queryParameters = new HashMap<String, Object>();
		}
		return queryParameters;
	}

	public void setQueryParameter(Map<String, Object> queryParameters) {
		this.queryParameters = queryParameters;
	}

	public Map<String, Object> getFormDataParameters() {
		if (formDataParameters == null) {
			formDataParameters = new HashMap<String, Object>();
		}
		return formDataParameters;
	}

	public void setDeprecated(boolean deprecated) {
		this.deprecated = deprecated;
	}

	public boolean isDeprecated() {
		return this.deprecated;
	}

	@Override
	public String toString() {
		return "SwagRequest [url=" + url + ", method=" + method + ", testType=" + testType + ", headerParameters="
				+ headerParameters + ", pathParameters=" + pathParameters + ", queryParameters=" + queryParameters
				+ ", bodyParameters=" + bodyParameters + ", formDataParameters=" + formDataParameters + "]";
	}
}
