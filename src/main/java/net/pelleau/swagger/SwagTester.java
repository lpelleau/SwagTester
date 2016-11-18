package net.pelleau.swagger;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import io.swagger.models.Scheme;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

public class SwagTester {

	private static Logger log = LoggerFactory.getLogger(SwagTester.class);

	private Swagger swagger;
	private Map<String, EntryPoint> entryPoints;

	private String host;

	public SwagTester(String pathToJsonFile) throws Exception {
		swagger = new SwaggerParser().read(pathToJsonFile);

		if (swagger != null) {

			host = swagger.getSchemes().get(0).name().toLowerCase() + "://" + swagger.getHost() + swagger.getBasePath();

			log.debug("Host : " + host);

			getEntryPoints();

		} else {
			throw new Exception("Error, unable to read the Json file at : " + pathToJsonFile);
		}
	}

	private void getEntryPoints() {
		entryPoints = new HashMap<>();

		swagger.getPaths().forEach((name, path) -> {
			EntryPoint ep = new EntryPointImpl(this, name, path);
			entryPoints.put(name, ep);
		});
	}

	public Map<String, EntryPoint> entryPoints() {
		return entryPoints;
	}

	public EntryPoint entryPoint(String name) {
		return entryPoints.get(name);
	}

	/**
	 * Test if the server is UP with a simple Ping.
	 * 
	 * @return true if the server is UP
	 */
	public boolean serverUpTest() {
		boolean reachable = true;

		String url = "://" + swagger.getHost();
		for (Scheme schemes : swagger.getSchemes()) {
			try {
				log.debug(schemes.toValue() + url);

				HttpResponse<String> response = Unirest.head(schemes.toValue() + url).asString();

				if (response.getStatus() != 200) {
					reachable = false;
				}
			} catch (UnirestException e) {
				reachable = false;
			}
		}

		return reachable;
	}

	/**
	 * Return the base URL of the current server.
	 * 
	 * @return
	 */
	public String getHost() {
		return host;
	}
}