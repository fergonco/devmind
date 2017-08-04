package org.fergonco.devmind.parser;

public interface Expression {

	boolean same(Expression exp);

	void accept(TerminalVisitor terminalVisitor);
}
