package net.pelleau.swagger;

import io.swagger.parser.SwaggerParser;
import io.swagger.models.Swagger;

public class SwagTester {

	private Swagger swagger;

	public SwagTester(String pathToJsonFile) {
		swagger = new SwaggerParser().read(pathToJsonFile);
	}

	public String getHost() {
		return swagger.getHost();
	}

}
