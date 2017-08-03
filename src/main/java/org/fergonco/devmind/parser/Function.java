package org.fergonco.devmind.parser;

import org.apache.commons.lang3.StringUtils;

public class Function implements Expression {

	private String id;
	private Expression[] parameters;

	public Function(String id, Expression... parameters) {
		this.id = id;
		this.parameters = parameters;
	}

	public Expression[] getParameters() {
		return parameters;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return id + "(" + StringUtils.join(parameters) + ")";
	}
}
