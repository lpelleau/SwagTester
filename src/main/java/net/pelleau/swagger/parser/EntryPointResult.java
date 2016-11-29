package net.pelleau.swagger.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import io.swagger.models.HttpMethod;

public class EntryPointResult {
	private JsonNode data;

	public EntryPointResult(JsonNode value) {
		data = value;
	}

	public MethodResult getMethod(HttpMethod method) {
		String m = method.toString().toLowerCase();
		ArrayNode node = (ArrayNode) data.get(m);
		return node == null ? null : new MethodResult(node);
	}
}
