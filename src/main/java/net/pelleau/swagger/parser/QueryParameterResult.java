package net.pelleau.swagger.parser;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class QueryParameterResult extends ParameterResult {
	private String name;
	private Map<String, Object> query;

	public QueryParameterResult(JsonNode value) {
		super(value);
	}

	public String getName() {
		if (name == null) {
			name = data.get("name").textValue();
		}

		return name;
	}

	public Map<String, Object> getQueryParameter() {
		if (query == null) {
			query = new HashMap<>();

			ArrayNode queries = (ArrayNode) data.get("data");

			queries.elements().forEachRemaining(e -> {
				query.put(getName(), e.asText());
			});
		}

		return query;
	}
}
