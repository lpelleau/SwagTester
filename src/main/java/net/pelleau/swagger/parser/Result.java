package net.pelleau.swagger.parser;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class Result {
	private JsonNode data;

	private List<String> produces;
	private List<ParameterResult> parameters;
	private JsonNode expected;

	public Result(JsonNode value) {
		data = value;
	}

	public List<String> getProduces() {
		if (produces == null) {
			produces = new ArrayList<>();

			ArrayNode prod = ((ArrayNode) data.get("produce"));

			prod.elements().forEachRemaining(e -> {
				produces.add(e.toString());
			});
		}

		return produces;
	}

	public List<ParameterResult> getParameters() {
		if (parameters == null) {
			parameters = new ArrayList<>();

			ArrayNode params = ((ArrayNode) data.get("parameters"));

			params.elements().forEachRemaining(p -> {

				ParameterResult param = null;

				String in = p.findValue("in").textValue();

				switch (in) {
				case "query":
					param = new QueryParameterResult(p);
					break;
				case "path":
					param = new PathParameterResult(p);
					break;
				case "header":
					param = new HeaderParameterResult(p);
					break;
				default:
					param = new ParameterResult(p);
				}

				parameters.add(param);
			});
		}

		return parameters;
	}

	public JsonNode getExpectedResult() {
		if (expected == null) {
			expected = data.get("expected");
		}

		return expected;
	}
}
