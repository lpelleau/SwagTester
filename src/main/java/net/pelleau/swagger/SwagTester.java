package net.pelleau.swagger;

import io.swagger.models.Swagger;
import io.swagger.models.parameters.AbstractSerializableParameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.parser.SwaggerParser;

public class SwagTester {

	private Swagger swagger;

	public SwagTester(String pathToJsonFile) {
		swagger = new SwaggerParser().read(pathToJsonFile);
	}

	public void test() {

		/*
		 * swagger.getPaths().entrySet().forEach(entry -> {
		 * System.out.println(entry.toString());
		 * entry.getValue().getOperations().forEach(o -> {
		 * o.getParameters().forEach(p -> { if
		 * (p.getClass().equals(PathParameter.class)) { try {
		 * AbstractSerializableParameter<PathParameter> asp =
		 * (AbstractSerializableParameter<PathParameter>) p;
		 * 
		 * System.out.println(asp.getType()); } catch (Exception e) {
		 * 
		 * } } }); }); ; });
		 */

	}

}
