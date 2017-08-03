package org.fergonco.devmind.interpreter;

public abstract class AbstractAskStatement implements Statement {

	private String output;

	protected void setOutput(String output) {
		this.output = output;
	}

	@Override
	public String getOutput() {
		return output;
	}

}
