package net.pelleau.swagger;

import java.util.HashMap;
import java.util.Map;

import io.swagger.models.HttpMethod;
import io.swagger.models.Path;
import net.pelleau.swagger.methods.GetMethod;
import net.pelleau.swagger.methods.Method;

class EntryPointImpl implements EntryPoint {

	private SwagTester swag;
	private String name;
	private Path path;
	Map<HttpMethod, Method> methods;

	public EntryPointImpl(SwagTester swag, String name, Path path) {
		this.swag = swag;
		this.name = name;
		this.path = path;
		methods = new HashMap<>();
	}

	private Method getMethod(HttpMethod httpMethod) {
		if (!methods.containsKey(httpMethod)) {
			Method method = new GetMethod(swag, name, path.getOperationMap().get(httpMethod));
			methods.put(httpMethod, method);
		}

		return methods.get(httpMethod);
	}

	@Override
	public Method getMethod() {
		return getMethod(HttpMethod.GET);
	}

	@Override
	public Method postMethod() {
		return getMethod(HttpMethod.POST);
	}

	@Override
	public Method putMethod() {
		return getMethod(HttpMethod.PUT);
	}

	@Override
	public Method patchMethod() {
		return getMethod(HttpMethod.PATCH);
	}

	@Override
	public Method deleteMethod() {
		return getMethod(HttpMethod.DELETE);
	}

	@Override
	public Method headMethod() {
		return getMethod(HttpMethod.HEAD);
	}

	@Override
	public Method optionsMethod() {
		return getMethod(HttpMethod.OPTIONS);
	}

}
