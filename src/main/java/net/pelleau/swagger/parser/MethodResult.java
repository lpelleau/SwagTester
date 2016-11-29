package net.pelleau.swagger.parser;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class MethodResult {
	private ArrayNode data;
	private List<Result> results;

	public MethodResult(ArrayNode value) {
		data = value;
	}

	public List<Result> getResults() {
		if (results == null) {
			results = new ArrayList<>();

			data.elements().forEachRemaining(e -> {
				results.add(new Result(e));
			});
		}
		return results;
	}
}
