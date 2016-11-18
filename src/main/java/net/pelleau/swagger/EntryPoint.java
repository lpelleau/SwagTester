package net.pelleau.swagger;

import net.pelleau.swagger.methods.Method;

public interface EntryPoint {
	public Method getMethod();

	public Method postMethod();

	public Method putMethod();

	public Method patchMethod();

	public Method deleteMethod();

	public Method headMethod();

	public Method optionsMethod();
}
