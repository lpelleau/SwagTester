package net.pelleau.swagger.generator;

import java.util.Map.Entry;

import org.apache.commons.lang.math.RandomUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.models.ArrayModel;
import io.swagger.models.Model;
import io.swagger.models.Swagger;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.BaseIntegerProperty;
import io.swagger.models.properties.DateProperty;
import io.swagger.models.properties.DateTimeProperty;
import io.swagger.models.properties.DecimalProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;
import io.swagger.models.properties.StringProperty;
import net.pelleau.swagger.generator.random.FormatGenerator;
import net.pelleau.swagger.generator.random.RandomGenerator;
import net.pelleau.swagger.generator.random.RandomGeneratorFactory;
import net.pelleau.swagger.methods.TestType;

public final class BodyGenerator {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(BodyGenerator.class);

	private BodyGenerator() {

	}

	public static Object fillBody(Swagger swagger, Model model, TestType type) {
		RandomGenerator gen = RandomGeneratorFactory.getRandomGenerator(type);

		if (model instanceof ArrayModel) {
			ArrayProperty property = new ArrayProperty();
			property.setItems(((ArrayModel) model).getItems());
			return getArray(swagger, gen, property);
		} else {
			RefProperty property = new RefProperty();
			property.set$ref(model.getReference());
			return getRef(swagger, gen, property);
		}
	}

	private static JSONArray getArray(Swagger swagger, RandomGenerator gen, ArrayProperty property) {
		JSONArray res = new JSONArray();

		int max = 1 + RandomUtils.nextInt(10);

		if (property.getItems() instanceof RefProperty) {
			for (int i = 0; i <= max; i++) {
				res.put(getRef(swagger, gen, (RefProperty) property.getItems()));
			}

		} else {
			for (int i = 0; i <= max; i++) {
				res.put(fillProperty(swagger, gen, property.getItems()));
			}
		}

		return res;
	}

	private static JSONObject getRef(Swagger swagger, RandomGenerator gen, RefProperty property) {
		Model refT = getDefinition(swagger, property.get$ref());
		if (refT != null) {
			return fillType(swagger, gen, refT);
		} else {
			// return null;
			throw new RuntimeException("Can't found this reference : " + property.get$ref());
		}
	}

	// XXX old way (by Loic)
	// private static Pattern refPattern = Pattern.compile("#/([A-Za-z]+)$");
	//
	// private static Model getDefinition(Swagger swagger, String ref) {
	// log.error(ref);
	// Matcher m = refPattern.matcher(ref);
	// if (refPattern.matcher(ref).matches()) {
	// return swagger.getDefinitions().get(m.group());
	// } else {
	// // return null;
	// throw new RuntimeException("Can't found this definition.");
	// }
	// }

	private static Model getDefinition(Swagger swagger, String ref) {
		String defName = ref.substring(ref.lastIndexOf("/") + 1);
		return swagger.getDefinitions().get(defName);
	}

	private static JSONObject fillType(Swagger swagger, RandomGenerator gen, Model definition) {
		JSONObject res = new JSONObject();

		for (Entry<String, Property> entry : definition.getProperties().entrySet()) {
			res.put(entry.getKey(), fillProperty(swagger, gen, entry.getValue()));
		}

		return res;
	}

	public static Object fillProperty(Swagger swagger, RandomGenerator gen, Property property) {
		switch (property.getType()) {
		case "string":
			if (property instanceof StringProperty) {
				StringProperty stringProp = (StringProperty) property;
				if (stringProp.getEnum() != null && !stringProp.getEnum().isEmpty()) {
					return gen.getValue(stringProp.getEnum());
				} else {
					return FormatGenerator.getString(gen, stringProp.getFormat(),
							(stringProp.getMinLength() != null ? stringProp.getMinLength() : 1),
							(stringProp.getMaxLength() != null ? stringProp.getMaxLength() : 25));
				}
			} else if (property instanceof DateTimeProperty) {
				DateTimeProperty dateTimeProp = (DateTimeProperty) property;
				if (dateTimeProp.getEnum() != null && !dateTimeProp.getEnum().isEmpty()) {
					return gen.getValue(dateTimeProp.getEnum());
				} else {
					return FormatGenerator.getString(gen, dateTimeProp.getFormat(), 0, 0);
				}
			} else if (property instanceof DateProperty) {
				DateProperty dateProp = (DateProperty) property;
				if (dateProp.getEnum() != null && !dateProp.getEnum().isEmpty()) {
					return gen.getValue(dateProp.getEnum());
				} else {
					return FormatGenerator.getString(gen, dateProp.getFormat(), 0, 0);
				}
			} else {
				throw new RuntimeException(
						"This String format is not supported : " + property.getClass().getSimpleName());
			}
		case "integer":
			BaseIntegerProperty intProp = (BaseIntegerProperty) property;
			return FormatGenerator.getInteger(gen, intProp.getFormat(),
					(intProp.getMinimum() != null ? intProp.getMinimum().intValue() : Integer.MIN_VALUE),
					(intProp.getMaximum() != null ? intProp.getMaximum().intValue() : Integer.MAX_VALUE));
		case "number":
			DecimalProperty numberProp = (DecimalProperty) property;
			return FormatGenerator.getNumber(gen, numberProp.getFormat(),
					(numberProp.getMinimum() != null ? numberProp.getMinimum() : Double.MIN_VALUE),
					(numberProp.getMaximum() != null ? numberProp.getMaximum() : Double.MAX_VALUE));
		case "boolean":
			return FormatGenerator.getBoolean(gen);
		case "ref":
			return getRef(swagger, gen, (RefProperty) property);
		case "array":
			return getArray(swagger, gen, (ArrayProperty) property);
		default:
			throw new RuntimeException("This type is not supported.");
		}
	}
}
