package net.pelleau.swagger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

public class SwagTester {

	private Swagger swagger;
	private Map<String, EntryPoint> entryPoints;

	public SwagTester(String pathToJsonFile) {
		swagger = new SwaggerParser().read(pathToJsonFile);

		getEntryPoints();
	}

	private void getEntryPoints() {
		entryPoints = new HashMap<>();

		swagger.getPaths().forEach((name, path) -> {
			EntryPoint ep = new EntryPointImpl(name, path);
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
		String host = "";
		boolean reachable = false;
		try {
			reachable = InetAddress.getByName(host).isReachable(milliseconds);
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
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
}