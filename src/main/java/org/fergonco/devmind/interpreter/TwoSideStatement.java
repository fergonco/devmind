package org.fergonco.devmind.interpreter;

import org.fergonco.devmind.parser.Expression;

public class TwoSideStatement extends AbstractTellStatement implements Statement {

	protected Expression leftSide;
	protected Expression rightSide;

	public TwoSideStatement(Expression leftSide, Expression rightSide) {
		this.leftSide = leftSide;
		this.rightSide = rightSide;
	}

	@Override
	public void execute(KnowledgeBase kb) throws KBRuntimeException {
		kb.registerReferences(this, leftSide);
		kb.registerReferences(this, rightSide);
	}

	@Override
	public Expression[] getExpressions() {
		return new Expression[] { leftSide, rightSide };
	}

}
