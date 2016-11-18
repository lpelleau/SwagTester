package net.pelleau.swagger;

import java.util.ArrayList;
import java.util.List;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

public class SwagTester {

	private Swagger swagger;
	private List<EntryPoint> entryPoints;

	public SwagTester(String pathToJsonFile) {
		swagger = new SwaggerParser().read(pathToJsonFile);

		getEntryPoints();
	}

	private void getEntryPoints() {
		entryPoints = new ArrayList<>();
		// TODO Auto-generated method stub
	}

	public List<EntryPoint> entryPoints() {
		return entryPoints;
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