package net.pelleau.swagger;

import java.util.HashMap;
import java.util.Map;

import io.swagger.models.HttpMethod;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import net.pelleau.swagger.methods.DeleteMethod;
import net.pelleau.swagger.methods.GetMethod;
import net.pelleau.swagger.methods.HeadMethod;
import net.pelleau.swagger.methods.Method;
import net.pelleau.swagger.methods.OptionMethod;
import net.pelleau.swagger.methods.PatchMethod;
import net.pelleau.swagger.methods.PostMethod;
import net.pelleau.swagger.methods.PutMethod;

class EntryPointImpl implements EntryPoint {

	private Swagger swagger;
	private SwagTester swag;
	private String name;
	private Path path;
	Map<HttpMethod, Method> methods;

	public EntryPointImpl(Swagger swagger, SwagTester swag, String name, Path path) {
		this.swagger = swagger;
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
					method = new GetMethod(swagger, swag, name, path.getOperationMap().get(httpMethod));
					break;
				case DELETE:
					method = new DeleteMethod(swagger, swag, name, path.getOperationMap().get(httpMethod));
					break;
				case HEAD:
					method = new HeadMethod(swagger, swag, name, path.getOperationMap().get(httpMethod));
					break;
				case OPTIONS:
					method = new OptionMethod(swagger, swag, name, path.getOperationMap().get(httpMethod));
					break;
				case PATCH:
					method = new PatchMethod(swagger, swag, name, path.getOperationMap().get(httpMethod));
					break;
				case POST:
					method = new PostMethod(swagger, swag, name, path.getOperationMap().get(httpMethod));
					break;
				case PUT:
					method = new PutMethod(swagger, swag, name, path.getOperationMap().get(httpMethod));
					break;
				default:
					throw new RuntimeException("This HttpMethod is not supported");
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
