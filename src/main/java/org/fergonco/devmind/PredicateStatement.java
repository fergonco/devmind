package org.fergonco.devmind;

import org.fergonco.devmind.parser.Value;

public class PredicateStatement implements Statement {

	private String predicate;
	private String term;
	private Value value;

	public PredicateStatement(String predicate, String term, Value value) {
		this.predicate = predicate;
		this.term = term;
		this.value = value;
	}

	@Override
	public String toString() {
		return predicate + "(" + term + ") = " + value;
	}
}
