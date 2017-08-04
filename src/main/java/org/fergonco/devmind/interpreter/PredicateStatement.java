package org.fergonco.devmind.interpreter;

import org.apache.commons.lang3.StringUtils;
import org.fergonco.devmind.parser.Expression;
import org.fergonco.devmind.parser.Function;

public class PredicateStatement extends AbstractTellStatement implements Statement {

	private Function predicateCall;

	public PredicateStatement(Function predicateCall) {
		this.predicateCall = predicateCall;
	}

	@Override
	public String toString() {
		return predicateCall.getId() + "(" + StringUtils.join(predicateCall.getParameters(), ",") + ")";
	}

	@Override
	public void execute(KnowledgeBase kb) throws KBRuntimeException {
		for (Expression parameter : predicateCall.getParameters()) {
			kb.registerReferences(this, parameter);
		}
	}

	@Override
	public Expression[] getExpressions() {
		return new Expression[] { predicateCall };
	}

}
