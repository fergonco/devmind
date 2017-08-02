package org.fergonco.devmind;

public class ImplicationStatement implements Statement {

	private String value;
	private String predicateName;
	private String[] terms;

	public ImplicationStatement(String value, String predicateName, String[] terms) {
		this.value = value;
		this.predicateName = predicateName;
		this.terms = terms;
	}

}
