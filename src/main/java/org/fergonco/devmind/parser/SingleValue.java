package org.fergonco.devmind.parser;

public class SingleValue implements Value {

	private String value;

	public SingleValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "\"" + value + "\"";
	}

}
