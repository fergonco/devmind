package org.fergonco.devmind;

import org.apache.commons.lang3.StringUtils;
import org.fergonco.devmind.parser.Value;

public class FunctionDefinitionStatement implements Statement {

	private String functionName;
	private String[] terms;
	private Value value;

	public FunctionDefinitionStatement(String functionName, String[] terms, Value value) {
		this.functionName = functionName;
		this.terms = terms;
		this.value = value;
	}

	@Override
	public String toString() {
		return functionName + "(" + StringUtils.join(terms, ",") + ") = " + value;
	}
}
