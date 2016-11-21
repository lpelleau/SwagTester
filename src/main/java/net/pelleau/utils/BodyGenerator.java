package net.pelleau.utils;

import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import io.swagger.models.ArrayModel;
import io.swagger.models.Model;
import io.swagger.models.Swagger;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;
import net.pelleau.swagger.methods.TestType;

public final class BodyGenerator {

	private BodyGenerator() {

	}

	private static String getInteger(RandomGenerator gen, String format) {
		String res = null;

		switch (format) {
		case "int32":
			res = String.valueOf(gen.getInt());
			break;

		case "int64":
			res = String.valueOf(gen.getLong());
			break;
		}

		return res;
	}

	private static String getNumber(RandomGenerator gen, String format) {
		String res = null;

		switch (format) {
		case "float":
			res = String.valueOf(gen.getFloat());
			break;

		case "double":
			res = String.valueOf(gen.getDouble());
			break;
		}

		return res;
	}

	private static JSONObject getRef(Swagger swagger, RandomGenerator gen, RefProperty property) {
		Model refT = getDefinition(swagger, property.get$ref());
		if (refT != null) {
			return fillType(swagger, gen, refT);
		}
		return null;
	}

	private static JSONArray getArray(Swagger swagger, RandomGenerator gen, ArrayProperty property) {
		JSONArray res = new JSONArray();

		if (property.getItems() instanceof RefProperty) {
			for (int i = gen.getInt(50); i >= 0; --i) {
				((JSONArray) res).put(getRef(swagger, gen, (RefProperty) property.getItems()));
			}

		} else {
			for (int i = gen.getInt(50); i >= 0; --i) {
				((JSONArray) res).put(fillProperty(swagger, gen, property.getItems()));
			}
		}

		return res;
	}

	private static String getString(RandomGenerator gen, String format) {
		String res = null;

		switch (format) {
		case "password":
		case "":
			res = gen.getString(gen.getInt(20));
			break;

		case "byte":
			res = new String(Base64.encodeBase64((gen.getString(gen.getInt(20)).getBytes())));
			break;

		case "binary":
			res = new String(gen.getString(gen.getInt(20)).getBytes());
			break;

		case "date":
			res = gen.getDate();
			break;

		case "date-time":
			res = gen.getDateTime();
			break;
		}

		return res;

	}

	public static Object fillProperty(Swagger swagger, RandomGenerator gen, Property property) {
		Object res = null;
		String type = property.getType() == null ? "" : property.getType();
		String format = property.getFormat() == null ? "" : property.getFormat();

		switch (type) {
		case "integer":
			res = getInteger(gen, format);
			break;

		case "number":
			res = getNumber(gen, format);
			break;

		case "boolean":
			res = String.valueOf(gen.getBool());
			break;

		case "string":
			res = getString(gen, format);
			break;

		case "ref":
			res = getRef(swagger, gen, (RefProperty) property);
			break;

		case "array":
			res = getArray(swagger, gen, (ArrayProperty) property);
			break;
		}
		return res;
	}

	private static Model getDefinition(Swagger swagger, String ref) {
		Matcher m = Pattern.compile("/([A-Za-z]+)$").matcher(ref);

		if (m.find()) {
			return swagger.getDefinitions().get(m.group(1));
		}
		return null;
	}

	private static JSONObject fillType(Swagger swagger, RandomGenerator gen, Model definition) {
		JSONObject res = new JSONObject();

		for (Entry<String, Property> entry : definition.getProperties().entrySet()) {
			res.put(entry.getKey(), fillProperty(swagger, gen, entry.getValue()));
		}

		return res;
	}

	public static Object fillBody(Swagger swagger, Model model, TestType type) {
		RandomGenerator gen = null;

		switch (type) {
		case VALID:
			gen = new ValidRandomGenerator();
			break;
		}

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
}
