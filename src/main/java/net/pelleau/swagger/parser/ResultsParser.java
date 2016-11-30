package net.pelleau.swagger.parser;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.util.Json;

public class ResultsParser {
	private Map<String, EntryPointResult> entryPoints;

	private JsonNode root;

	public ResultsParser(String location) throws Exception {
		String data;
		location = location.replaceAll("\\\\", "/");
		if (location.toLowerCase().startsWith("http")) {
			data = RemoteUrl.urlToString(location, null);
		} else {
			final String fileScheme = "file://";
			Path path;
			if (location.toLowerCase().startsWith(fileScheme)) {
				path = Paths.get(URI.create(location));
			} else {
				path = Paths.get(location);
			}
			if (Files.exists(path)) {
				data = FileUtils.readFileToString(path.toFile(), "UTF-8");
			} else {
				data = ClasspathHelper.loadFileFromClasspath(location);
			}
		}
		if (data.trim().startsWith("{")) {
			ObjectMapper mapper = Json.mapper();
			root = mapper.readTree(data);
		}
		// else {
		// root = DeserializationUtils.readYamlTree(data);
		// }
	}

	public Map<String, EntryPointResult> getEntryPoints() {
		if (entryPoints == null) {
			entryPoints = new HashMap<>();

			root.fields().forEachRemaining(e -> {
				entryPoints.put(e.getKey(), new EntryPointResult(e.getValue()));
			});
		}

		return entryPoints;
	}
}
