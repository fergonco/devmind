package org.fergonco.devmind.interpreter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.fergonco.devmind.parser.Constant;
import org.fergonco.devmind.parser.Expression;
import org.fergonco.devmind.parser.Function;
import org.fergonco.devmind.parser.Literal;
import org.fergonco.devmind.parser.TerminalVisitor;

public class KnowledgeBase {

	private ArrayList<String> constants = new ArrayList<>();
	private HashMap<String, ArrayList<Statement>> constantStatements = new HashMap<>();

	void registerReferences(Statement containingStatement, Expression parameter) throws ConstantNotFoundException {
		List<String> constants = getConstants(parameter);
		for (String constant : constants) {
			if (!this.constants.contains(constant)) {
				throw new ConstantNotFoundException(constant);
			}
			ArrayList<Statement> statements = constantStatements.get(constant);
			if (statements == null) {
				statements = new ArrayList<>();
				constantStatements.put(constant, statements);
			}
			statements.add(containingStatement);
		}
	}

	private List<String> getConstants(Expression expression) {
		List<String> ret = new ArrayList<>();
		expression.accept(new TerminalVisitor() {

			@Override
			public void visit(Literal literal) {
				// noop
			}

			@Override
			public void visit(Function function) {
				// noop
			}

			@Override
			public void visit(Constant constant) {
				ret.add(constant.getId());
			}
		});
		return ret;
	}

	public void addConstant(String constant) throws ConstantAlreadyExistsException {
		if (constants.contains(constant)) {
			throw new ConstantAlreadyExistsException(constant);
		} else {
			constants.add(constant);
		}
	}

	public String[] getConstants() {
		return constants.toArray(new String[constants.size()]);
	}

	public Statement[] getReferences(String constantName) {
		ArrayList<Statement> ret = constantStatements.get(constantName);
		return ret.toArray(new Statement[ret.size()]);
	}

	public String[] getSymbols() {
		HashSet<String> ret = new HashSet<>();
		ret.addAll(constants);
		Collection<ArrayList<Statement>> statements = constantStatements.values();
		for (ArrayList<Statement> arrayList : statements) {
			for (Statement statement : arrayList) {
				List<String> symbols = getAllSymbols(statement);
				ret.addAll(symbols);
			}
		}

		return ret.toArray(new String[ret.size()]);
	}

	private List<String> getAllSymbols(Statement statement) {
		List<String> ret = new ArrayList<>();
		Expression[] expressions = statement.getExpressions();
		for (Expression expression : expressions) {
			ret.addAll(getAllSymbols(expression));
		}

		return ret;
	}

	private List<String> getAllSymbols(Expression expression) {
		List<String> ret = new ArrayList<>();
		expression.accept(new TerminalVisitor() {

			@Override
			public void visit(Literal literal) {
				// noop
			}

			@Override
			public void visit(Function function) {
				ret.add(function.getId());
			}

			@Override
			public void visit(Constant constant) {
				ret.add(constant.getId());
			}
		});
		return ret;
	}
}