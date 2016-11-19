package net.pelleau.swagger;

import java.util.HashMap;
import java.util.Map;

import io.swagger.models.HttpMethod;
import io.swagger.models.Path;
import net.pelleau.swagger.methods.DeleteMethod;
import net.pelleau.swagger.methods.GetMethod;
import net.pelleau.swagger.methods.HeadMethod;
import net.pelleau.swagger.methods.Method;
import net.pelleau.swagger.methods.OptionMethod;
import net.pelleau.swagger.methods.PatchMethod;
import net.pelleau.swagger.methods.PostMethod;
import net.pelleau.swagger.methods.PutMethod;

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
			Method method = null;

			if (path.getOperationMap().get(httpMethod) != null) {
				switch (httpMethod) {
				case GET:
					method = new GetMethod(swag, name, path.getOperationMap().get(httpMethod));
					break;
				case DELETE:
					method = new DeleteMethod(swag, name, path.getOperationMap().get(httpMethod));
					break;
				case HEAD:
					method = new HeadMethod(swag, name, path.getOperationMap().get(httpMethod));
					break;
				case OPTIONS:
					method = new OptionMethod(swag, name, path.getOperationMap().get(httpMethod));
					break;
				case PATCH:
					method = new PatchMethod(swag, name, path.getOperationMap().get(httpMethod));
					break;
				case POST:
					method = new PostMethod(swag, name, path.getOperationMap().get(httpMethod));
					break;
				case PUT:
					method = new PutMethod(swag, name, path.getOperationMap().get(httpMethod));
					break;
				}
			}

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

	@Override
	public String toString() {
		return "EntryPointImpl [name=" + name + ", getMethod()=" + getMethod() + ", postMethod()=" + postMethod()
				+ ", putMethod()=" + putMethod() + ", patchMethod()=" + patchMethod() + ", deleteMethod()="
				+ deleteMethod() + ", headMethod()=" + headMethod() + ", optionsMethod()=" + optionsMethod() + "]";
	}

}
