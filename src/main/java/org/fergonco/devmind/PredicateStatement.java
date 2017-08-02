package org.fergonco.devmind;

public class PredicateStatement implements Statement {

	private String predicateName;
	private String[] termArray;

	public PredicateStatement(String predicateName, String[] termArray) {
		this.predicateName = predicateName;
		this.termArray = termArray;
	}

}
