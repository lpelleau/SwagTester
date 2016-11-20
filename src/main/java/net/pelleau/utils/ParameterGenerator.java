package net.pelleau.utils;

import io.swagger.models.parameters.FormParameter;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import net.pelleau.swagger.SwagRequest;
import net.pelleau.swagger.methods.TestType;
import net.pelleau.utils.RandomGenerator.BoundType;

public final class ParameterGenerator {

	private ParameterGenerator() {

	}

	private static int stringDefaultMinLength = 3;
	private static int stringDefaultMaxLength = 15;

	// TODO take account of the optional values like Maximum,
	// Minimum, MaxLength
	// cf : http://swagger.io/specification/#parameterObject

	public static void fillPathParameter(SwagRequest request, PathParameter pathParam, TestType testType) {
		if (testType == TestType.VALID) {
			switch (pathParam.getType()) {
			case "string": {
				int min = stringDefaultMinLength;
				int max = stringDefaultMaxLength;

				if (pathParam.getMaxLength() != null) {
					max = pathParam.getMaxLength();
				}

				if (pathParam.getMinLength() != null) {
					min = pathParam.getMinLength();
				}

				request.setUrl(request.getUrl().replaceAll("\\{" + pathParam.getName() + "\\}",
						RandomGenerator.getString(min, max)));
				break;
			}
			case "number": {
				double min = Double.MIN_VALUE;
				double max = Double.MAX_VALUE;

				if (pathParam.getMinimum() != null) {
					min = pathParam.getMinimum();
				}

				if (pathParam.getMaximum() != null) {
					max = pathParam.getMaximum();
				}

				request.setUrl(request.getUrl().replaceAll("\\{" + pathParam.getName() + "\\}",
						String.valueOf(RandomGenerator.getDouble(min, max))));
				break;
			}
			case "integer": {
				int min = Integer.MIN_VALUE;
				int max = Integer.MAX_VALUE;

				if (pathParam.getMinimum() != null) {
					min = pathParam.getMinimum().intValue();
				}

				if (pathParam.getMaximum() != null) {
					max = pathParam.getMaximum().intValue();
				}

				request.setUrl(request.getUrl().replaceAll("\\{" + pathParam.getName() + "\\}",
						String.valueOf(RandomGenerator.getInt(min, max))));
				break;
			}
			case "boolean": {
				request.setUrl(request.getUrl().replaceAll("\\{" + pathParam.getName() + "\\}",
						String.valueOf(RandomGenerator.getBool())));
				break;
			}
			case "array":
				// TODO
				break;
			}
		} else if (testType == TestType.INVALID) {
			switch (pathParam.getType()) {
			case "string": {
				int min = stringDefaultMinLength;
				int max = stringDefaultMaxLength;

				if (pathParam.getMaxLength() != null) {
					max = pathParam.getMaxLength() * 2;
				}

				if (pathParam.getMinLength() != null) {
					min = pathParam.getMinLength() / 2;
				}

				request.setUrl(request.getUrl().replaceAll("\\{" + pathParam.getName() + "\\}",
						RandomGenerator.getString(min, max)));
				break;
			}
			case "number": {
				if (pathParam.getMinimum() != null) {
					// between -inf, min
					request.setUrl(request.getUrl().replaceAll("\\{" + pathParam.getName() + "\\}",
							String.valueOf(RandomGenerator.getDouble(pathParam.getMinimum(), BoundType.MAX))));
				} else if (pathParam.getMaximum() != null) {
					// between max, +inf
					request.setUrl(request.getUrl().replaceAll("\\{" + pathParam.getName() + "\\}",
							String.valueOf(RandomGenerator.getDouble(pathParam.getMaximum(), BoundType.MIN))));
				} else {
					// full random
					request.setUrl(request.getUrl().replaceAll("\\{" + pathParam.getName() + "\\}",
							String.valueOf(RandomGenerator.getDouble())));
				}
				break;
			}
			case "integer": {
				if (pathParam.getMinimum() != null) {
					// between -inf, min
					request.setUrl(request.getUrl().replaceAll("\\{" + pathParam.getName() + "\\}",
							String.valueOf(RandomGenerator.getInt(pathParam.getMinimum().intValue(), BoundType.MAX))));
				} else if (pathParam.getMaximum() != null) {
					// between max, +inf
					request.setUrl(request.getUrl().replaceAll("\\{" + pathParam.getName() + "\\}",
							String.valueOf(RandomGenerator.getInt(pathParam.getMaximum().intValue(), BoundType.MIN))));
				} else {
					// full random
					request.setUrl(request.getUrl().replaceAll("\\{" + pathParam.getName() + "\\}",
							String.valueOf(RandomGenerator.getInt())));
				}
				break;
			}
			case "boolean": {
				request.setUrl(request.getUrl().replaceAll("\\{" + pathParam.getName() + "\\}",
						String.valueOf(RandomGenerator.getBool())));
				break;
			}
			case "array":
				// TODO
				break;
			}
		} else if (testType == TestType.SCALLING) {
			switch (pathParam.getType()) {
			case "string": {
				int min = stringDefaultMinLength;
				int max = stringDefaultMaxLength;

				if (pathParam.getMaxLength() != null) {
					max = pathParam.getMaxLength() * 2;
				}

				if (pathParam.getMinLength() != null) {
					min = pathParam.getMinLength() / 2;
				}

				request.setUrl(request.getUrl().replaceAll("\\{" + pathParam.getName() + "\\}",
						RandomGenerator.getString(min, max)));
				break;
			}
			case "number": {
				double value = 0;

				if (pathParam.getMinimum() != null) {
					value = pathParam.getMinimum() + 1;
				} else if (pathParam.getMaximum() != null) {
					value = pathParam.getMaximum() - 1;
				}

				request.setUrl(request.getUrl().replaceAll("\\{" + pathParam.getName() + "\\}", String.valueOf(value)));

				break;
			}
			case "integer": {
				int value = 0;

				if (pathParam.getMinimum() != null) {
					value = pathParam.getMinimum().intValue() + 1;
				} else if (pathParam.getMaximum() != null) {
					value = pathParam.getMaximum().intValue() - 1;
				}

				request.setUrl(request.getUrl().replaceAll("\\{" + pathParam.getName() + "\\}", String.valueOf(value)));
				break;
			}
			case "boolean": {
				request.setUrl(
						request.getUrl().replaceAll("\\{" + pathParam.getName() + "\\}", Boolean.toString(true)));
				break;
			}
			case "array":
				// TODO
				break;
			}
		}
		// TODO the others testType
	}

	public static void fillFormParameter(SwagRequest request, FormParameter formParam, TestType testType) {
		switch (formParam.getType()) {
		case "string": {
			int min = stringDefaultMinLength;
			int max = stringDefaultMaxLength;

			if (formParam.getMaxLength() != null) {
				max = formParam.getMaxLength();
			}

			if (formParam.getMinLength() != null) {
				min = formParam.getMinLength();
			}

			request.getFormDataParameters().put(formParam.getName(), RandomGenerator.getString(min, max));
			break;
		}
		case "number": {
			double min = Double.MIN_VALUE;
			double max = Double.MAX_VALUE;

			if (formParam.getMinimum() != null) {
				min = formParam.getMinimum();
			}

			if (formParam.getMaximum() != null) {
				max = formParam.getMaximum();
			}

			request.getFormDataParameters().put(formParam.getName(),
					String.valueOf(RandomGenerator.getDouble(min, max)));
			break;
		}
		case "integer": {
			int min = Integer.MIN_VALUE;
			int max = Integer.MAX_VALUE;

			if (formParam.getMinimum() != null) {
				min = formParam.getMinimum().intValue();
			}

			if (formParam.getMaximum() != null) {
				max = formParam.getMaximum().intValue();
			}

			request.getFormDataParameters().put(formParam.getName(), String.valueOf(RandomGenerator.getInt(min, max)));
			break;
		}
		case "boolean": {
			request.getFormDataParameters().put(formParam.getName(), String.valueOf(RandomGenerator.getBool()));
			break;
		}
		case "array": {
			// TODO
			break;
		}
		case "file": {
			// TODO
			break;
		}
		}
	}

	public static void fillHeaderParameter(SwagRequest request, HeaderParameter headerParam, TestType testType) {
		switch (headerParam.getType()) {
		case "string": {
			int min = stringDefaultMinLength;
			int max = stringDefaultMaxLength;

			if (headerParam.getMaxLength() != null) {
				max = headerParam.getMaxLength();
			}

			if (headerParam.getMinLength() != null) {
				min = headerParam.getMinLength();
			}

			request.getHeaderParameters().put(headerParam.getName(), RandomGenerator.getString(min, max));
			break;
		}
		case "number": {
			double min = Double.MIN_VALUE;
			double max = Double.MAX_VALUE;

			if (headerParam.getMinimum() != null) {
				min = headerParam.getMinimum();
			}

			if (headerParam.getMaximum() != null) {
				max = headerParam.getMaximum();
			}

			request.getHeaderParameters().put(headerParam.getName(),
					String.valueOf(RandomGenerator.getDouble(min, max)));
			break;
		}
		case "integer": {
			int min = Integer.MIN_VALUE;
			int max = Integer.MAX_VALUE;

			if (headerParam.getMinimum() != null) {
				min = headerParam.getMinimum().intValue();
			}

			if (headerParam.getMaximum() != null) {
				max = headerParam.getMaximum().intValue();
			}

			request.getHeaderParameters().put(headerParam.getName(), String.valueOf(RandomGenerator.getInt(min, max)));
			break;
		}
		case "boolean": {
			request.getHeaderParameters().put(headerParam.getName(), String.valueOf(RandomGenerator.getBool()));
			break;
		}
		case "array":
			// TODO
			break;
		}
	}

	public static void fillQueryParameter(SwagRequest request, QueryParameter queryParam, TestType testType) {
		switch (queryParam.getType()) {
		case "string": {
			request.getQueryParameters().put(queryParam.getName(), RandomGenerator.getString(10));
			break;
		}
		case "number": {
			request.getQueryParameters().put(queryParam.getName(), String.valueOf(RandomGenerator.getDouble()));
			break;
		}
		case "integer": {
			request.getQueryParameters().put(queryParam.getName(), String.valueOf(RandomGenerator.getInt(1000)));
			break;
		}
		case "boolean": {
			request.getQueryParameters().put(queryParam.getName(), String.valueOf(RandomGenerator.getBool()));
			break;
		}
		case "array":
			// TODO
			break;
		}
	}
}
