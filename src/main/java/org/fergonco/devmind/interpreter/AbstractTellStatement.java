package org.fergonco.devmind.interpreter;

public abstract class AbstractTellStatement implements Statement {

	@Override
	public String getOutput() {
		return null;
	}

	@Override
	public boolean tells() {
		return true;
	}
}
