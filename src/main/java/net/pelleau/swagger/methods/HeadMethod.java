package net.pelleau.swagger.methods;

import io.swagger.models.Operation;
import net.pelleau.swagger.SwagTester;

public class HeadMethod extends Method {
	public HeadMethod(SwagTester swag, String name, Operation operation) {
		super(swag, name, operation);
	}
}
