package org.fergonco.devmind.parser;

public class ArrayValue implements Value {

	private Value[] values;

	public ArrayValue(Value[] values) {
		this.values = values;
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		ret.append("(");
		for (Value value : values) {
			ret.append(value).append(",");
		}
		ret.append(")");
		return ret.toString();
	}
}
