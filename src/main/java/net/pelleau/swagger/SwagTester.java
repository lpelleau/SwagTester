package net.pelleau.swagger;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import io.swagger.models.Scheme;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

public class SwagTester {

	private Swagger swagger;
	private Map<String, EntryPoint> entryPoints;

	private String host;

	public SwagTester(String pathToJsonFile) {
		swagger = new SwaggerParser().read(pathToJsonFile);

		host = swagger.getSchemes().get(0).name().toLowerCase() + "://" + swagger.getHost() + swagger.getBasePath();

		System.out.println(host);

		getEntryPoints();
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
	 * @param milliseconds
	 *            - Time in milliseconds before timeout
	 * @return true if the server is UP
	 */
	public boolean serverUpTest(int milliseconds) {
		boolean reachable = true;
		// reachable =
		// InetAddress.getByName(swagger.getHost()).isReachable(milliseconds);

		String url = "://" + swagger.getHost() + swagger.getBasePath();
		for (Scheme schemes : swagger.getSchemes()) {
			try {
				URL currentUrl = new URL(schemes.toValue() + url);

				HttpURLConnection urlConnect = (HttpURLConnection) currentUrl.openConnection();

				@SuppressWarnings("unused")
				Object objData = urlConnect.getContent();
			} catch (Exception e) {
				reachable = false;
			}
		}

		return reachable;
	}

	/**
	 * Test if the server is UP with a simple Ping.<br>
	 * Timeout of 5 seconds.
	 * 
	 * @return true if the server is UP
	 */
	public boolean serverUpTest() {
		return serverUpTest(5000);
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