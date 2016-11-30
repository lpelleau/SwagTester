package net.pelleau.swagger.parser;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class QueryParameterResult extends ParameterResult {
	private String name;
	private List<String> query;

	public QueryParameterResult(JsonNode value) {
		super(value);
	}

	public String getName() {
		if (name == null) {
			name = data.get("name").toString();
		}

		return name;
	}

	public List<String> getQueryParameter() {
		if (query == null) {
			query = new ArrayList<>();

			ArrayNode queries = (ArrayNode) data.get("data");

			queries.elements().forEachRemaining(e -> {
				query.add(e.toString());
			});
		}

		return query;
	}
}
