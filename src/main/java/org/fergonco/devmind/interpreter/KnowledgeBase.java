package org.fergonco.devmind.interpreter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.fergonco.devmind.parser.Constant;
import org.fergonco.devmind.parser.Expression;
import org.fergonco.devmind.parser.Function;

public class KnowledgeBase {

	ArrayList<String> constants = new ArrayList<>();
	HashMap<String, ArrayList<Statement>> constantStatements = new HashMap<>();

	void registerReferences(Statement containingStatement, Expression parameter) {
		List<String> constants = getConstants(parameter);
		for (String constant : constants) {
			ArrayList<Statement> statements = constantStatements.get(constant);
			if (statements == null) {
				statements = new ArrayList<>();
				constantStatements.put(constant, statements);
			}
			statements.add(containingStatement);
		}
	}

	private List<String> getConstants(Expression expression) {
		if (expression instanceof Constant) {
			return Collections.singletonList(((Constant) expression).getId());
		} else if (expression instanceof Function) {
			List<String> ret = new ArrayList<>();
			Expression[] parameters = ((Function) expression).getParameters();
			for (Expression parameter : parameters) {
				ret.addAll(getConstants(parameter));
			}
			return ret;
		} else {
			return Collections.emptyList();
		}
	}

	public String[] askConstants() {
		return constants.toArray(new String[constants.size()]);
	}

	public Statement[] askReferences(String constantName) {
		ArrayList<Statement> ret = constantStatements.get(constantName);
		return ret.toArray(new Statement[ret.size()]);
	}

}
