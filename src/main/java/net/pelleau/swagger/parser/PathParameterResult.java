package net.pelleau.swagger.parser;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class PathParameterResult extends ParameterResult {
	private String name;
	private Map<String, String> path;

	public PathParameterResult(JsonNode value) {
		super(value);
	}

	public String getName() {
		if (name == null) {
			name = data.get("name").textValue();
		}

		return name;
	}

	public Map<String, String> getPathParameter() {
		if (path == null) {
			path = new HashMap<>();
			path.put(getName(), data.findValue("data").textValue());
		}

		return path;
	}
}
