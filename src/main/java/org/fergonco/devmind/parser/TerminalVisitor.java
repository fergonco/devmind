package org.fergonco.devmind.parser;

public interface TerminalVisitor {
	
	void visit(Constant constant);

	void visit(Function function);

	void visit(Literal literal);
}