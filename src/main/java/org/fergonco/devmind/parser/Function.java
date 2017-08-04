package org.fergonco.devmind.parser;

import org.apache.commons.lang3.StringUtils;

public class Function implements Expression {

	private String id;
	private Expression[] parameters;

	public Function(String id, Expression... parameters) {
		this.id = id;
		this.parameters = parameters;
	}

	public Expression[] getParameters() {
		return parameters;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return id + "(" + StringUtils.join(parameters, ",") + ")";
	}

	public boolean same(Expression obj) {
		if (obj instanceof Function) {
			Function that = (Function) obj;
			if (this.id.equals(that.id) && this.parameters.length == that.parameters.length) {
				for (int i = 0; i < parameters.length; i++) {
					if (!this.parameters[i].equals(that.parameters[i])) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void accept(TerminalVisitor terminalVisitor) {
		terminalVisitor.visit(this);
		for (Expression expression : parameters) {
			expression.accept(terminalVisitor);
		}
	}

}
