package org.fergonco.devmind.interpreter;

import org.apache.commons.lang3.StringUtils;
import org.fergonco.devmind.parser.Expression;
import org.fergonco.devmind.parser.Function;

public class PredicateStatement implements Statement {

	private Function predicateCall;

	public PredicateStatement(Function predicateCall) {
		this.predicateCall = predicateCall;
	}

	@Override
	public String toString() {
		return predicateCall.getId() + "(" + StringUtils.join(predicateCall.getParameters()) + ")";
	}

	@Override
	public void execute(KnowledgeBase kb) {
		for (Expression parameter : predicateCall.getParameters()) {
			kb.registerReferences(this, parameter);
		}
	}
}
