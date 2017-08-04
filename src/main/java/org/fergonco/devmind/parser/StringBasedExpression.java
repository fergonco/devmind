package org.fergonco.devmind.parser;

public abstract class StringBasedExpression implements Expression {

	protected String value;

	public StringBasedExpression(String value) {
		this.value = value;
	}

	@Override
	public boolean same(Expression obj) {
		if (obj.getClass().equals(this.getClass())) {
			return ((StringBasedExpression) obj).value.equals(this.value);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

}
