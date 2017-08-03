package org.fergonco.devmind.parser;

public class Constant implements Expression {

	private String value;

	public Constant(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public String getId() {
		return value;
	}

}
