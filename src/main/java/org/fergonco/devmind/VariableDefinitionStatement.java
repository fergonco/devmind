package org.fergonco.devmind;

import org.fergonco.devmind.parser.Value;

public class VariableDefinitionStatement implements Statement {

	private String variable;
	private Value value;

	public VariableDefinitionStatement(String variable, Value value) {
		this.variable = variable;
		this.value = value;
	}

	@Override
	public String toString() {
		return variable + "=" + value;
	}
}
