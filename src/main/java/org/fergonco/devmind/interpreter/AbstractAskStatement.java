package org.fergonco.devmind.interpreter;

import org.fergonco.devmind.parser.Expression;

public abstract class AbstractAskStatement implements Statement {

	private String output;

	protected void setOutput(String output) {
		this.output = output;
	}

	@Override
	public String getOutput() {
		return output;
	}

	@Override
	public boolean tells() {
		return false;
	}

	@Override
	public Expression[] getExpressions() {
		return new Expression[0];
	}

}
