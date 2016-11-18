package net.pelleau.swagger;

import net.pelleau.swagger.methods.HttpMethod;

public interface EntryPoint {
	public HttpMethod get();

	public HttpMethod post();

	public HttpMethod put();

	public HttpMethod patch();

	public HttpMethod delete();

	public HttpMethod head();

	public HttpMethod options();
}
