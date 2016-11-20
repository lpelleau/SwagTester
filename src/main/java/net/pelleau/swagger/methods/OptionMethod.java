package net.pelleau.swagger.methods;

import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import net.pelleau.swagger.SwagTester;

public class OptionMethod extends Method {
	public OptionMethod(Swagger swagger, SwagTester swag, String name, Operation operation) {
		super(swagger, swag, name, operation);
	}

	@Override
	protected HttpMethod getHttpMethod() {
		return HttpMethod.OPTIONS;
	}
}
