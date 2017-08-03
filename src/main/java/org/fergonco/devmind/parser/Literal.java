package org.fergonco.devmind.parser;

public class Literal implements Expression {

	private String value;

	public Literal(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "\"" + value + "\"";
	}

}
