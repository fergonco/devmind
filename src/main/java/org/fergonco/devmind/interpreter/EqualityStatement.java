package org.fergonco.devmind.interpreter;

import org.fergonco.devmind.parser.Expression;

public class EqualityStatement implements Statement {

	private Expression leftSide;
	private Expression rightSide;

	public EqualityStatement(Expression leftSide, Expression rightSide) {
		super();
		this.leftSide = leftSide;
		this.rightSide = rightSide;
	}

	@Override
	public String toString() {
		return leftSide + "=" + rightSide;
	}
}
