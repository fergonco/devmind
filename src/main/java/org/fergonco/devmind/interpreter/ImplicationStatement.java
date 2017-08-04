package org.fergonco.devmind.interpreter;

import org.fergonco.devmind.parser.Expression;

public class ImplicationStatement extends TwoSideStatement implements Statement {
	public ImplicationStatement(Expression leftSide, Expression rightSide) {
		super(leftSide, rightSide);
	}

	@Override
	public String toString() {
		return leftSide + "->" + rightSide;
	}

}
