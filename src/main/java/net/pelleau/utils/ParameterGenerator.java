package net.pelleau.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.models.Swagger;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.SerializableParameter;
import io.swagger.models.properties.BooleanProperty;
import io.swagger.models.properties.DoubleProperty;
import io.swagger.models.properties.LongProperty;
import io.swagger.models.properties.StringProperty;
import net.pelleau.swagger.container.SwagRequest;
import net.pelleau.swagger.methods.TestType;

public final class ParameterGenerator {

	private static Logger log = LoggerFactory.getLogger(ParameterGenerator.class);

	private ParameterGenerator() {

	}

	public static void fillParameter(Swagger swagger, SwagRequest request, Parameter param, TestType testType) {

		if (param.getIn().equals("body")) {
			BodyParameter bodyParam = (BodyParameter) param;

			Object value = BodyGenerator.fillBody(swagger, bodyParam.getSchema(), testType);

			request.setBodyParameters(value);
		} else {
			SerializableParameter ser = (SerializableParameter) param;

			Object value = getParameterValue(ser, testType);

			switch (param.getIn()) {
			case "path":
				request.setUrl(request.getUrl().replaceAll("\\{" + param.getName() + "\\}", value.toString()));
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

	private static Object getParameterValue(SerializableParameter param, TestType testType) {
		RandomGenerator generator = RandomGeneratorFactory.getRandomGenerator(testType);

		switch (param.getType()) {
		case "string": {
			int min = 5;
			int max = 25;

			if (param.getMaxLength() != null) {
				max = param.getMaxLength();
			}

			if (param.getMinLength() != null) {
				min = param.getMinLength();
			}

			return generator.getString(min, max); // TODO use the generator
		}
		case "number": {
			double min = Double.MIN_VALUE;
			double max = Double.MAX_VALUE;

			if (param.getMinimum() != null) {
				min = param.getMinimum();
			}

			if (param.getMaximum() != null) {
				max = param.getMaximum();
			}

			return generator.getDouble(min, max);
		}
		case "integer": {
			int min = Integer.MIN_VALUE;
			int max = Integer.MAX_VALUE;

			if (param.getMinimum() != null) {
				min = param.getMinimum().intValue();
			}

			if (param.getMaximum() != null) {
				max = param.getMaximum().intValue();
			}

			return generator.getInt(min, max);
		}
		case "boolean":
			return generator.getBool();

		case "array":
			return "available";// getArray(generator, param);
		default:
			return null;
		}
	}

	private static String getArray(RandomGenerator generator, SerializableParameter param) {
		String res = "";
		String separator = "";
		switch (param.getCollectionFormat()) {
		default:
		case "csv":
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
		}

		switch (param.getItems().getType()) {
		case "string": {
			StringProperty property = (StringProperty) param.getItems();
			for (String val : property.getEnum()) {
				if (generator.getBool()) {
					res += val + separator;
				}
			}
			break;
		}

		case "number": {
			DoubleProperty property = (DoubleProperty) param.getItems();
			break;
		}

		case "integer": {
			LongProperty property = (LongProperty) param.getItems();
			break;
		}

		case "boolean": {
			BooleanProperty property = (BooleanProperty) param.getItems();
			break;
		}

		case "array": {
			break;
		}
		}
		return res;
	}
}
