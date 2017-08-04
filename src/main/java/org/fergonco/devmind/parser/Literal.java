package org.fergonco.devmind.parser;

public class Literal extends StringBasedExpression implements Expression {

	public Literal(String value) {
		super(value);
	}

	@Override
	public void accept(TerminalVisitor terminalVisitor) {
		terminalVisitor.visit(this);
	}

}
