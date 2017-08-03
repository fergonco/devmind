package org.fergonco.devmind.interpreter;

public class ConstantStatement implements Statement {

	private String constantValue;

	public ConstantStatement(String constantValue) {
		this.constantValue = constantValue;
	}

	@Override
	public String toString() {
		return constantValue; 
	}

	@Override
	public void execute(KnowledgeBase kb) {
		kb.constants.add(constantValue);
	}

}
