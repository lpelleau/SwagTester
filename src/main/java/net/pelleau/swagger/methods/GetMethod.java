package net.pelleau.swagger.methods;

import io.swagger.models.Operation;
import net.pelleau.swagger.SwagTester;

public class GetMethod extends Method {
	public GetMethod(SwagTester swag, String name, Operation operation) {
		super(swag, name, operation);
	}
}
