package net.pelleau.swagger.generator.random;

import org.apache.commons.codec.binary.Base64;

public final class FormatGenerator {

	private FormatGenerator() {

	}

	public static Object getBoolean(RandomGenerator gen) {
		return gen.getBool();
	}

	public static Object getInteger(RandomGenerator gen, String format, long minConstraint, long maxConstraint) {
		if (format == null) {
			format = "int32";
		}
		switch (format) {
		case "int32":
		default:
			return (int) gen.getLong(minConstraint, maxConstraint);
		case "int64":
			return gen.getLong(minConstraint, maxConstraint);
		}
	}

	public static Object getNumber(RandomGenerator gen, String format, double minConstraint, double maxConstraint) {
		if (format == null) {
			format = "float";
		}
		switch (format) {
		case "float":
		default:
			return (float) gen.getDouble(minConstraint, maxConstraint);
		case "double":
			return gen.getDouble(minConstraint, maxConstraint);
		}
	}

	public static Object getString(RandomGenerator gen, String format, int minConstraint, int maxConstraint) {
		if (format == null) {
			format = "";
		}
		switch (format) {
		case "":
		case "password":
		default:
			return gen.getString(minConstraint, maxConstraint);
		case "byte":
			return Base64.encodeBase64((gen.getString(minConstraint, maxConstraint).getBytes()));
		case "binary":
			return gen.getString(minConstraint, maxConstraint).getBytes();
		case "date":
			return gen.getDate();
		case "date-time":
			return gen.getDateTime();
		}
	}
}
