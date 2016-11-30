package net.pelleau.swagger.parser;

import com.fasterxml.jackson.databind.JsonNode;

public class HeaderParameterResult extends ParameterResult {
	private String name;
	private String header;

	public HeaderParameterResult(JsonNode value) {
		super(value);
	}

	public String getName() {
		if (name == null) {
			name = data.get("name").toString();
		}

		return name;
	}

	public String getHeaderParameter() {
		if (header == null) {
			header = data.get("data").toString();
		}

		return header;
	}
}
