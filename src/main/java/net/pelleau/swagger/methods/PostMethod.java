package net.pelleau.swagger.methods;

import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import net.pelleau.swagger.SwagTester;

public class PostMethod extends Method {
	public PostMethod(SwagTester swag, String name, Operation operation) {
		super(swag, name, operation);
	}

	@Override
	protected HttpMethod getHttpMethod() {
		return HttpMethod.POST;
	}
}
