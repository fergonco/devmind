package org.fergonco.devmind.interpreter;

import org.apache.commons.lang3.StringUtils;
import org.fergonco.devmind.parser.Expression;

public class PredicateStatement implements Statement {

	private String predicateName;
	private Expression[] parameters;

	public PredicateStatement(String predicateName, Expression... parameters) {
		this.predicateName = predicateName;
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return predicateName + "(" + StringUtils.join(parameters) + ")";
	}
}
