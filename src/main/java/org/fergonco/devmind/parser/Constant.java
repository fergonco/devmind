package org.fergonco.devmind.parser;

public class Constant extends StringBasedExpression implements Expression {

	public Constant(String value) {
		super(value);
	}

	public String getId() {
		return value;
	}

	@Override
	public void accept(TerminalVisitor terminalVisitor) {
		terminalVisitor.visit(this);
	}
}
