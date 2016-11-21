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

public final class BodyGenerator {

	private BodyGenerator() {

	}

	private static String getInteger(String format) {
		String res = null;

		switch (format) {
		case "int32":
			res = String.valueOf(RandomGenerator.getInt());
			break;

		case "int64":
			res = String.valueOf(RandomGenerator.getLong());
			break;
		}

		return res;
	}

	private static String getNumber(String format) {
		String res = null;

		switch (format) {
		case "float":
			res = String.valueOf(RandomGenerator.getFloat());
			break;

		case "double":
			res = String.valueOf(RandomGenerator.getDouble());
			break;
		}

		return res;
	}

	private static JSONObject getRef(Swagger swagger, RefProperty property) {
		Model refT = getDefinition(swagger, property.get$ref());
		if (refT != null) {
			return fillType(swagger, refT);
		}
		return null;
	}

	private static JSONArray getArray(Swagger swagger, ArrayProperty property) {
		JSONArray res = new JSONArray();

		if (property.getItems() instanceof RefProperty) {
			for (int i = RandomGenerator.getInt(50); i >= 0; --i) {
				((JSONArray) res).put(getRef(swagger, (RefProperty) property.getItems()));
			}

		} else {
			for (int i = RandomGenerator.getInt(50); i >= 0; --i) {
				((JSONArray) res).put(fillProperty(swagger, property.getItems()));
			}
		}

		return res;
	}

	private static String getString(String format) {
		String res = null;

		switch (format) {
		case "password":
		case "":
			res = RandomGenerator.getString(RandomGenerator.getInt(20));
			break;

		case "byte":
			res = new String(Base64.encodeBase64((RandomGenerator.getString(RandomGenerator.getInt(20)).getBytes())));
			break;

		case "binary":
			res = new String(RandomGenerator.getString(RandomGenerator.getInt(20)).getBytes());
			break;

		case "date":
			res = RandomGenerator.getDate();
			break;

		case "date-time":
			res = RandomGenerator.getDateTime();
			break;
		}

		return res;

	}

	public static Object fillProperty(Swagger swagger, Property property) {
		Object res = null;
		String type = property.getType() == null ? "" : property.getType();
		String format = property.getFormat() == null ? "" : property.getFormat();

		switch (type) {
		case "integer":
			res = getInteger(format);
			break;

		case "number":
			res = getNumber(format);
			break;

		case "boolean":
			res = String.valueOf(RandomGenerator.getBool());
			break;

		case "string":
			res = getString(format);
			break;

		case "ref":
			res = getRef(swagger, (RefProperty) property);
			break;

		case "array":
			res = getArray(swagger, (ArrayProperty) property);
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

	private static JSONObject fillType(Swagger swagger, Model definition) {
		JSONObject res = new JSONObject();

		for (Entry<String, Property> entry : definition.getProperties().entrySet()) {
			res.put(entry.getKey(), fillProperty(swagger, entry.getValue()));
		}

		return res;
	}

	public static Object fillBody(Swagger swagger, Model model) {
		if (model instanceof ArrayModel) {
			ArrayProperty property = new ArrayProperty();
			property.setItems(((ArrayModel) model).getItems());
			return getArray(swagger, property);

		} else {
			RefProperty property = new RefProperty();
			property.set$ref(model.getReference());
			return getRef(swagger, property);
		}
	}
}
