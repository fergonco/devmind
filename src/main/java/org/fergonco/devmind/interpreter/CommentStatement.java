package org.fergonco.devmind.interpreter;

import org.fergonco.devmind.parser.Expression;

public class CommentStatement extends AbstractTellStatement implements Statement {

	private String text;

	public CommentStatement(String text) {
		this.text = text;
	}

	@Override
	public void execute(KnowledgeBase kb) throws KBRuntimeException {
	}

	@Override
	public Expression[] getExpressions() {
		return new Expression[0];
	}

	@Override
	public String toString() {
		return text;
	}
}
