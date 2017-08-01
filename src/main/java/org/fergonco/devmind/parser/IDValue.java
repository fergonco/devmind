package org.fergonco.devmind.parser;

public class IDValue implements Value {

	private String value;

	public IDValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
