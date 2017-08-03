package org.fergonco.devmind.interpreter;

import org.fergonco.devmind.parser.Expression;

public class EqualityStatement extends AbstractTwoSideStatement implements Statement {

	public EqualityStatement(Expression leftSide, Expression rightSide) {
		super(leftSide,rightSide);
	}

	@Override
	public String toString() {
		return leftSide + "=" + rightSide;
	}
}
