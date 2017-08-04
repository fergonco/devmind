package org.fergonco.devmind.interpreter;

import org.fergonco.devmind.parser.Constant;
import org.fergonco.devmind.parser.Expression;

public class ConstantStatement extends AbstractTellStatement implements Statement {

	private Constant constantExpr;

	public ConstantStatement(Constant constantExpr) {
		this.constantExpr = constantExpr;
	}

	@Override
	public String toString() {
		return constantExpr.getId();
	}

	@Override
	public void execute(KnowledgeBase kb) throws KBRuntimeException {
		kb.addConstant(constantExpr.getId());
	}

	@Override
	public Expression[] getExpressions() {
		return new Expression[] { constantExpr };
	}

}
