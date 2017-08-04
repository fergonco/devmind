package org.fergonco.devmind.interpreter;

import org.fergonco.devmind.parser.Expression;

public interface Statement {

	void execute(KnowledgeBase kb) throws KBRuntimeException;

	String getOutput();

	boolean tells();

	Expression[] getExpressions();

}
