package net.pelleau.swagger.parser;

import com.fasterxml.jackson.databind.JsonNode;

public class PathParameterResult extends ParameterResult {
	private String name;
	private String path;

	public PathParameterResult(JsonNode value) {
		super(value);
	}

	public String getName() {
		if (name == null) {
			name = data.get("name").toString();
		}

		return name;
	}

	public String getPathParameter() {
		if (path == null) {
			path = data.get("data").toString();
		}

		return path;
	}
}
