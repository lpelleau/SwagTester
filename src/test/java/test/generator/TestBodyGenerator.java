package test.generator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import io.swagger.models.ArrayModel;
import io.swagger.models.RefModel;
import io.swagger.models.properties.StringProperty;
import net.pelleau.swagger.SwagTester;
import net.pelleau.swagger.generator.BodyGenerator;
import net.pelleau.swagger.methods.TestType;
import swagger.TestSwagTester;

public class TestBodyGenerator {

	private static final String file = "petstore_modified.json";

	private SwagTester swagger;

	@Before
	public void initialize() {
		try {
			File pathFile = new File(TestSwagTester.class.getClassLoader().getResource(file).getPath());

			swagger = new SwagTester(pathFile.getAbsolutePath());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testFillBodyRef() {
		for (int i = 0; i < 100; i++) {
			RefModel ref = new RefModel();
			ref.set$ref("#/definitions/Pet");

			Object res = BodyGenerator.fillBody(swagger.getSwagger(), ref, TestType.VALID);

			assertNotNull(res);
			assertTrue(res instanceof JSONObject);

			JSONObject json = (JSONObject) res;

			assertTrue(json.has("id"));
			assertTrue(json.has("category"));
			assertTrue(json.has("name"));
			assertTrue(json.has("photoUrls"));
			assertTrue(json.has("tags"));
			assertTrue(json.has("status"));
		}
	}

	@Test
	public void testFillBodyArray() {
		for (int i = 0; i < 100; i++) {
			StringProperty stringProp = new StringProperty();
			stringProp.setType("string");
			stringProp.setFormat("");
			stringProp.setMinLength(10);

			ArrayModel array = new ArrayModel();
			array.setType("string");
			array.setItems(stringProp);

			Object res = BodyGenerator.fillBody(swagger.getSwagger(), array, TestType.VALID);

			assertNotNull(res);
			assertTrue(res instanceof JSONArray);

			JSONArray json = (JSONArray) res;

			json.forEach(elem -> assertTrue(elem instanceof String));
			json.forEach(elem -> assertTrue(((String) elem).length() >= 10));
		}
	}

}
