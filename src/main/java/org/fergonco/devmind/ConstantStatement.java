package org.fergonco.devmind;

public class ConstantStatement implements Statement {

	private String constantValue;

	public ConstantStatement(String constantValue) {
		this.constantValue = constantValue;
	}

	@Override
	public String toString() {
		return constantValue; 
	}
}
