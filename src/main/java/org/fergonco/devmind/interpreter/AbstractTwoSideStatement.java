package org.fergonco.devmind.interpreter;

import org.fergonco.devmind.parser.Expression;

public class AbstractTwoSideStatement extends AbstractTellStatement implements Statement {

	protected Expression leftSide;
	protected Expression rightSide;

	public AbstractTwoSideStatement(Expression leftSide, Expression rightSide) {
		this.leftSide = leftSide;
		this.rightSide = rightSide;
	}

	@Override
	public void execute(KnowledgeBase kb) {
		kb.registerReferences(this, leftSide);
		kb.registerReferences(this, rightSide);
	}

}
