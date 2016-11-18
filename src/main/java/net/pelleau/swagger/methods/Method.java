package net.pelleau.swagger.methods;

import io.swagger.models.Operation;

public abstract class Method {

	private Operation operation;

	public Method(Operation operation) {
		this.operation = operation;
	}

	public void validTest() {
		// TODO Auto-generated method stub

	}

	public void timeoutTest() {
		// TODO Auto-generated method stub

	}

	public void extremValuesTest() {
		// TODO Auto-generated method stub

	}

	public void invalidTest() {
		// TODO Auto-generated method stub

	}

	public void scalingTest() {
		// TODO Auto-generated method stub

	}
}
