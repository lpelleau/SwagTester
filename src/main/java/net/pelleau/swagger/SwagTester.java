package net.pelleau.swagger;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import io.swagger.models.HttpMethod;
import io.swagger.models.Scheme;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import net.pelleau.swagger.container.SwagMetrics;
import net.pelleau.swagger.container.SwagRequest;
import net.pelleau.swagger.container.SwagResponse;
import net.pelleau.swagger.container.SwagTest;
import net.pelleau.swagger.methods.Method;
import net.pelleau.swagger.parser.PathParameterResult;
import net.pelleau.swagger.parser.QueryParameterResult;
import net.pelleau.swagger.parser.ResultsParser;

public class SwagTester {

	private static Logger log = LoggerFactory.getLogger(SwagTester.class);

	private static final int NB_THREADS = 32;

	private Swagger swagger;
	private ResultsParser parser;
	private Map<String, EntryPoint> entryPoints;

	private String host;

	public SwagTester(String pathToJsonFile, String pathToJsonResults) throws FileNotFoundException {
		createSwagger(pathToJsonFile);

		try {
			parser = new ResultsParser(pathToJsonResults);
		} catch (Exception e) {
			throw new FileNotFoundException("Error, unable to read the Json file (results) at : " + pathToJsonResults);
		}
	}

	public SwagTester(String pathToJsonFile) throws FileNotFoundException {
		createSwagger(pathToJsonFile);
	}

	private void createSwagger(String pathToJsonFile) throws FileNotFoundException {
		swagger = new SwaggerParser().read(pathToJsonFile);

		if (swagger != null) {

			// if no scheme is defined, use http by default
			host = (swagger.getSchemes().size() >= 1 ? swagger.getSchemes().get(0).name().toLowerCase() : "http")
					+ "://" + swagger.getHost() + swagger.getBasePath();

			log.debug("Host : " + host);

			getEntryPoints();

		} else {
			throw new FileNotFoundException("Error, unable to read the Json file at : " + pathToJsonFile);
		}
	}

	public boolean authenticate(String login, String password) {
		// TODO handle authentication
		return false;
	}

	private void getEntryPoints() {
		entryPoints = new HashMap<>();

		swagger.getPaths().forEach((name, path) -> {
			EntryPoint ep = new EntryPointImpl(swagger, this, name, path);
			entryPoints.put(name, ep);
		});
	}

	public List<SwagTest> sendRequests() {
		final List<SwagTest> result = new ArrayList<>();

		parser.getEntryPoints().forEach((path, ep) -> {

			Arrays.asList(HttpMethod.values()).forEach(m -> {

				if (ep.getMethod(m) != null) {
					ep.getMethod(m).getResults().forEach(res -> {
						SwagTest test = new SwagTest();
						test.setExpectedBody(res.getExpectedResult());

						SwagRequest r = new SwagRequest();
						r.setUrl(getHost() + path);
						r.setMethod(m);
						res.getParameters().forEach(param -> {

							if (param.getIn().equals("body")) {
								r.setBodyParameters(param.getData());

							} else if (param.getIn().equals("query")) {
								QueryParameterResult paramQuery = (QueryParameterResult) param;
								r.setQueryParameter(paramQuery.getQueryParameter());

							} else if (param.getIn().equals("path")) {
								PathParameterResult paramPath = (PathParameterResult) param;
								r.setPathParameter(paramPath.getPathParameter());
							}
						});

						test.setRequest(r);

						try {
							test.execute();

							SwagResponse resp = test.getResponse();
							result.add(test);
						} catch (UnirestException e) {
							e.printStackTrace();
						}
					});
				}

			});

		});

		return result;
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
	public boolean isServerUp() {
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

	public Swagger getSwagger() {
		return swagger;
	}

	public SwagMetrics runTests() {
		return runTests(1);
	}

	public SwagMetrics runTests(int nbTests) {
		SwagMetrics sm = new SwagMetrics();
		testFull(sm, nbTests);
		return sm;
	}

	private void testFull(SwagMetrics metrics, int nbTests) {
		entryPoints().forEach((name, entry) -> {
			if (entry.getMethod() != null) {
				testMethod(entry.getMethod(), metrics, nbTests, NB_THREADS);
			}

			if (entry.headMethod() != null) {
				testMethod(entry.headMethod(), metrics, nbTests, NB_THREADS);
			}

			if (entry.postMethod() != null) {
				testMethod(entry.postMethod(), metrics, nbTests, NB_THREADS);
			}

			if (entry.putMethod() != null) {
				testMethod(entry.putMethod(), metrics, nbTests, NB_THREADS);
			}

			if (entry.patchMethod() != null) {
				testMethod(entry.patchMethod(), metrics, nbTests, NB_THREADS);
			}

			if (entry.optionsMethod() != null) {
				testMethod(entry.optionsMethod(), metrics, nbTests, NB_THREADS);
			}

			if (entry.deleteMethod() != null) {
				testMethod(entry.deleteMethod(), metrics, nbTests, NB_THREADS);
			}
		});
	}

	private void testMethod(Method method, SwagMetrics metrics, int nbTest, int nbThread) {
		ExecutorService threadPool = Executors.newFixedThreadPool(nbThread);
		for (int i = 0; i < nbTest; i++) {
			threadPool.submit(() -> {
				executeTest(method, metrics);
			});
		}
		threadPool.shutdown();
		try {
			threadPool.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
	}

	private void executeTest(Method method, SwagMetrics metrics) {
		metrics.addResult(method.validTest());
		metrics.addResult(method.invalidTest());
		metrics.addResult(method.extremValuesTest());
		// SwagAssert.assertValid(method.scalingTest());
		// SwagAssert.assertValid(method.timeoutTest(1000));
	}
}