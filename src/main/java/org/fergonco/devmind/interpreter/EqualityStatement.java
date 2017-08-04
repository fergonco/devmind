package org.fergonco.devmind.interpreter;

import org.fergonco.devmind.parser.Constant;
import org.fergonco.devmind.parser.Expression;

public class EqualityStatement extends TwoSideStatement implements Statement {

	public EqualityStatement(Expression leftSide, Expression rightSide) {
		super(leftSide, rightSide);
	}

	@Override
	public void execute(KnowledgeBase kb) throws KBRuntimeException {
		String constantDefinition = null;
		if (leftSide instanceof Constant) {
			constantDefinition = ((Constant) leftSide).getId();
		} else if (rightSide instanceof Constant) {
			constantDefinition = ((Constant) rightSide).getId();
		}
		if (constantDefinition != null && !kb.askConstant(constantDefinition)) {
			kb.addConstant(constantDefinition);
		}
		super.execute(kb);
	}

	@Override
	public String toString() {
		return leftSide + "=" + rightSide;
	}
}
