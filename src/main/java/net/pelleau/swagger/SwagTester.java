package net.pelleau.swagger;

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

	public void serverUpTest() {
		// TODO Auto-generated method stub

	}

	public void test() {
		// swagger.getPaths().entrySet().forEach(entry -> {
		// System.out.println(entry.toString());
		// entry.getValue().getOperations().forEach(o -> {
		// o.getParameters().forEach(p -> {
		// if (p.getClass().equals(PathParameter.class)) {
		// try {
		// AbstractSerializableParameter<PathParameter> asp =
		// (AbstractSerializableParameter<PathParameter>) p;
		// System.out.println(asp.getType());
		// } catch (Exception e) {
		// }
		// }
		// });
		// });
		// ;
		// });
	}

}