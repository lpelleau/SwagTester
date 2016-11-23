package net.pelleau.swagger.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.stream.Collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.models.Swagger;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.SerializableParameter;
import net.pelleau.swagger.container.SwagRequest;
import net.pelleau.swagger.generator.random.FormatGenerator;
import net.pelleau.swagger.generator.random.RandomGenerator;
import net.pelleau.swagger.generator.random.RandomGeneratorFactory;
import net.pelleau.swagger.methods.TestType;

public final class ParameterGenerator {

	private static Logger log = LoggerFactory.getLogger(ParameterGenerator.class);

	private ParameterGenerator() {

	}

	public static void fillParameter(Swagger swagger, SwagRequest request, Parameter param, TestType testType) {
		if (param.getIn().equals("body")) {
			// Body case
			BodyParameter bodyParam = (BodyParameter) param;

			Object value = BodyGenerator.fillBody(swagger, bodyParam.getSchema(), testType);

			request.setBodyParameters(value);
		} else {
			// Other cases
			SerializableParameter ser = (SerializableParameter) param;

			Object value = getParameterValue(swagger, ser, testType);

			if (value != null) {
				switch (param.getIn()) {
				case "path":
					request.getPathParameters().put(param.getName(), value.toString());
					break;
				case "query":
					request.getQueryParameters().put(param.getName(), value);
					break;
				case "header":
					request.getHeaderParameters().put(param.getName(), value.toString());
					break;
				case "formData":
					request.getFormDataParameters().put(param.getName(), value);
					break;
				default:
					log.error("The IN value is incorrect. Expected : 'body', 'query', 'path' or 'formData'. Given : '"
							+ param.getIn() + "'.");
				}
			}
		}
	}

	private static Object getParameterValue(Swagger swagger, SerializableParameter param, TestType testType) {
		RandomGenerator generator = RandomGeneratorFactory.getRandomGenerator(testType);
		if (param.getRequired() || generator.getBool()) {
			switch (param.getType()) {
			case "string":
				return FormatGenerator.getString(generator, param.getFormat(),
						(param.getMinLength() != null ? param.getMinLength() : 1),
						(param.getMaxLength() != null ? param.getMaxLength() : 25));
			case "number":
				return FormatGenerator.getNumber(generator, param.getFormat(),
						(param.getMinLength() != null ? param.getMinLength() : Double.MIN_VALUE),
						(param.getMaxLength() != null ? param.getMaxLength() : Double.MAX_VALUE));
			case "integer":
				return FormatGenerator.getInteger(generator, param.getFormat(),
						(param.getMinLength() != null ? param.getMinLength() : Integer.MIN_VALUE),
						(param.getMaxLength() != null ? param.getMaxLength() : Integer.MAX_VALUE));
			case "boolean":
				return FormatGenerator.getBoolean(generator);
			case "array":
				return getArray(swagger, generator, param,
						(param.getMinItems() != null ? param.getMinItems() : (param.getRequired() ? 1 : 0)),
						(param.getMaxItems() != null ? param.getMaxItems() : 10));
			case "file":
				return getFile();
			default:
				throw new RuntimeException("This type is not supported (" + param.getType() + ").");
			}
		} else {
			return null;
		}
	}

	private static Object getArray(Swagger swagger, RandomGenerator generator, SerializableParameter param,
			int minItems, int maxItems) {
		// compute separator
		final String separator;
		switch (param.getCollectionFormat()) {
		case "csv":
		default:
			separator = ",";
			break;
		case "ssv":
			separator = " ";
			break;
		case "tsv":
			separator = "\\";
			break;
		case "pipes":
			separator = "|";
			break;
		case "multi":
			separator = "&" + param.getName() + "=";
		}

		int max = (int) generator.getDouble(minItems, maxItems);
		if (max < 0) {
			max = -max;
		}
		if (max > maxItems * 2) {
			max = maxItems * 2;
		}

		// get N values
		ArrayList<Object> array = new ArrayList<Object>();
		for (int i = 0; i < max; i++) {
			array.add(BodyGenerator.fillProperty(swagger, generator, param.getItems()));
		}

		if (array.size() == 0) {
			return null;
		}

		// TODO tester ca
		// me demande pas comment ca marche... j'ai pris ca là :
		// http://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/

		// return a String assembled with separator
		return array.stream().collect(Collector.of(() -> new StringJoiner(separator), (j, p) -> j.add(p.toString()),
				(j1, j2) -> j1.merge(j2), StringJoiner::toString));
	}

	private static File getFile() {
		return new File(ParameterGenerator.class.getClassLoader().getResource("random_image.png").getPath());
	}
}
