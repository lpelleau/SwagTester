package test.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import io.swagger.models.RefModel;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.FormParameter;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import net.pelleau.swagger.SwagTester;
import net.pelleau.swagger.container.SwagRequest;
import net.pelleau.swagger.generator.ParameterGenerator;
import net.pelleau.swagger.methods.TestType;
import swagger.TestSwagTester;

public class TestParameterGenerator {

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
	public void testFillQueryStringParameter() {
		for (int i = 0; i < 100; i++) {
			SwagRequest request = new SwagRequest();

			QueryParameter param = new QueryParameter();
			param.setIn("query");
			param.setType("string");
			param.setName("testParam");
			param.setRequired(true);

			ParameterGenerator.fillParameter(swagger.getSwagger(), request, param, TestType.VALID);

			assertNotNull(request.getQueryParameters());
			assertEquals(1, request.getQueryParameters().size());
			assertTrue(request.getQueryParameters().containsKey("testParam"));
			assertTrue(request.getQueryParameters().get("testParam") instanceof String);
		}
	}

	@Test
	public void testFillQueryIntParameter() {
		for (int i = 0; i < 100; i++) {
			SwagRequest request = new SwagRequest();

			QueryParameter param = new QueryParameter();
			param.setIn("query");
			param.setType("integer");
			param.setName("testParam");
			param.setRequired(true);

			ParameterGenerator.fillParameter(swagger.getSwagger(), request, param, TestType.VALID);

			assertNotNull(request.getQueryParameters());
			assertEquals(1, request.getQueryParameters().size());
			assertTrue(request.getQueryParameters().containsKey("testParam"));
			assertTrue(request.getQueryParameters().get("testParam") instanceof Integer);
		}
	}

	@Test
	public void testFillQueryInt32Parameter() {
		for (int i = 0; i < 100; i++) {
			SwagRequest request = new SwagRequest();

			QueryParameter param = new QueryParameter();
			param.setIn("query");
			param.setType("integer");
			param.setFormat("int32");
			param.setName("testParam");
			param.setRequired(true);

			ParameterGenerator.fillParameter(swagger.getSwagger(), request, param, TestType.VALID);

			assertNotNull(request.getQueryParameters());
			assertEquals(1, request.getQueryParameters().size());
			assertTrue(request.getQueryParameters().containsKey("testParam"));
			assertTrue(request.getQueryParameters().get("testParam") instanceof Integer);
		}
	}

	@Test
	public void testFillQueryInt64Parameter() {
		for (int i = 0; i < 100; i++) {
			SwagRequest request = new SwagRequest();

			QueryParameter param = new QueryParameter();
			param.setIn("query");
			param.setType("integer");
			param.setFormat("int64");
			param.setName("testParam");
			param.setRequired(true);

			ParameterGenerator.fillParameter(swagger.getSwagger(), request, param, TestType.VALID);

			assertNotNull(request.getQueryParameters());
			assertEquals(1, request.getQueryParameters().size());
			assertTrue(request.getQueryParameters().containsKey("testParam"));
			assertTrue(request.getQueryParameters().get("testParam") instanceof Long);
		}
	}

	@Test
	public void testFillHeaderStringParameter() {
		for (int i = 0; i < 100; i++) {
			SwagRequest request = new SwagRequest();

			HeaderParameter param = new HeaderParameter();
			param.setIn("header");
			param.setType("string");
			param.setFormat("password");
			param.setName("testParam");
			param.setRequired(true);

			ParameterGenerator.fillParameter(swagger.getSwagger(), request, param, TestType.VALID);

			assertNotNull(request.getHeaderParameters());
			assertEquals(1, request.getHeaderParameters().size());
			assertTrue(request.getHeaderParameters().containsKey("testParam"));
			assertTrue(request.getHeaderParameters().get("testParam") instanceof String);
		}
	}

	@Test
	public void testFillFormdataStringParameter() {
		for (int i = 0; i < 100; i++) {
			SwagRequest request = new SwagRequest();

			FormParameter param = new FormParameter();
			param.setIn("formData");
			param.setType("string");
			param.setFormat("date");
			param.setName("testParam");
			param.setRequired(true);

			ParameterGenerator.fillParameter(swagger.getSwagger(), request, param, TestType.VALID);

			assertNotNull(request.getFormDataParameters());
			assertEquals(1, request.getFormDataParameters().size());
			assertTrue(request.getFormDataParameters().containsKey("testParam"));
			assertTrue(request.getFormDataParameters().get("testParam") instanceof String);
		}
	}

	@Test
	public void testFillPathStringParameter() {
		for (int i = 0; i < 100; i++) {
			SwagRequest request = new SwagRequest();

			PathParameter param = new PathParameter();
			param.setIn("path");
			param.setType("string");
			param.setFormat("date");
			param.setName("testParam");
			param.setRequired(true);

			ParameterGenerator.fillParameter(swagger.getSwagger(), request, param, TestType.VALID);

			assertNotNull(request.getPathParameters());
			assertEquals(1, request.getPathParameters().size());
			assertTrue(request.getPathParameters().containsKey("testParam"));
			assertTrue(request.getPathParameters().get("testParam") instanceof String);
		}
	}

	@Test
	public void testFillBodyStringParameter() {
		for (int i = 0; i < 100; i++) {
			SwagRequest request = new SwagRequest();

			RefModel ref = new RefModel();
			ref.set$ref("#/definitions/Pet");

			BodyParameter param = new BodyParameter();
			param.setIn("body");
			param.setName("testParam");
			param.setSchema(ref);
			param.setRequired(true);

			ParameterGenerator.fillParameter(swagger.getSwagger(), request, param, TestType.VALID);

			assertNotNull(request.getBodyParameters());
		}
	}

}
