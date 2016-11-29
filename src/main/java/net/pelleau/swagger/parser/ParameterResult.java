package net.pelleau.swagger.parser;

import com.fasterxml.jackson.databind.JsonNode;

public class ParameterResult {
	private JsonNode data;
	private String in;
	private JsonNode param;

	public ParameterResult(JsonNode value) {
		data = value;
	}

	public String getIn() {
		if (in == null) {
			in = data.get("in").toString();
		}

		return in;
	}

	public JsonNode getData() {
		if (param == null) {
			param = data.get("data");
		}

		return param;
	}
}
