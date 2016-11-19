package net.pelleau.swagger;

import com.mashape.unirest.http.HttpResponse;

/**
 * Represents a http response.
 */
public class SwagResponse {

	private int statusCode;
	private String statusText;

	private String header;
	private String body;

	private long responseTime;

	public SwagResponse() {

	}

	public SwagResponse(long responseTime) {
		this.responseTime = responseTime;
	}

	public SwagResponse(HttpResponse<?> response, long responseTime) {
		this.responseTime = responseTime;
		this.statusCode = response.getStatus();
		this.statusText = response.getStatusText();
		this.header = response.getHeaders().toString();
		this.body = response.getBody().toString();
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	@Override
	public String toString() {
		return "SwagResponse [statusCode=" + statusCode + ", statusText=" + statusText + ", header=" + header
				+ ", body=" + body + ", responseTime=" + responseTime + "]";
	}

}
