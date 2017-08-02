package org.fergonco.devmind.parser;

import java.util.ArrayList;

import org.fergonco.devmind.ConstantStatement;
import org.fergonco.devmind.FunctionDefinitionStatement;
import org.fergonco.devmind.ImplicationStatement;
import org.fergonco.devmind.PredicateStatement;
import org.fergonco.devmind.Statement;
import org.fergonco.devmind.SyntaxException;
import org.fergonco.devmind.VariableDefinitionStatement;
import org.fergonco.devmind.lexer.ClosedParenthesisToken;
import org.fergonco.devmind.lexer.EqualsToken;
import org.fergonco.devmind.lexer.IDToken;
import org.fergonco.devmind.lexer.Lexer;
import org.fergonco.devmind.lexer.OpenParenthesisToken;
import org.fergonco.devmind.lexer.SingleValueToken;
import org.fergonco.devmind.lexer.Token;

public class Parser {

	private Token currentToken;

	public Parser(Token token) {
		this.currentToken = token;
	}

	public Statement[] parse() throws SyntaxException {
		ArrayList<Statement> statements = new ArrayList<>();
		try {
			statements.add(implication());
		} catch (SyntaxException e) {
			Token lookahead = currentToken.next();
			if (lookahead == Lexer.OPEN_PARENTHESIS) {
				statements.add(functionDefinitionOrPredicate());
			} else if (lookahead == Lexer.EQUALS) {
				statements.add(variableDefinition());
			} else {
				statements.add(axiom());
			}
		}

		return statements.toArray(new Statement[statements.size()]);
	}

	private ImplicationStatement implication() throws SyntaxException {
		String value = consume(SingleValueToken.class).getText();
		consume(Lexer.IMPLICATION.getClass());
		FunctionOrPredicateCall fop = functionOrPredicateCall();
		return new ImplicationStatement(value, fop.functionName, fop.terms);
	}

	private Statement axiom() throws SyntaxException {
		String constantName = consume(IDToken.class).getText();

		return new ConstantStatement(constantName);
	}

	private Statement variableDefinition() throws SyntaxException {
		String variableName = consume(IDToken.class).getText();
		consume(EqualsToken.class);
		Value value = value();

		return new VariableDefinitionStatement(variableName, value);
	}

	private Statement functionDefinitionOrPredicate() throws SyntaxException {
		FunctionOrPredicateCall fop = functionOrPredicateCall();
		if (currentToken == Lexer.EQUALS) {
			consume(EqualsToken.class);
			Value value = value();
			return new FunctionDefinitionStatement(fop.functionName, fop.terms, value);
		} else {
			return new PredicateStatement(fop.functionName, fop.terms);

		}
	}

	private FunctionOrPredicateCall functionOrPredicateCall() throws SyntaxException {
		String functionName = consume(IDToken.class).getText();
		consume(OpenParenthesisToken.class);
		ArrayList<String> terms = new ArrayList<>();
		terms.add(consume(IDToken.class).getText());
		try {
			while (true) {
				consume(Lexer.COMA.getClass());
				terms.add(consume(IDToken.class).getText());
			}
		} catch (SyntaxException e) {
		}
		String[] termArray = terms.toArray(new String[terms.size()]);
		consume(ClosedParenthesisToken.class);
		return new FunctionOrPredicateCall(functionName, termArray);
	}

	private class FunctionOrPredicateCall {
		private String functionName;
		private String[] terms;

		public FunctionOrPredicateCall(String functionName, String[] terms) {
			this.functionName = functionName;
			this.terms = terms;
		}

	}

	private Value value() throws SyntaxException {
		try {
			String singleValue = consume(SingleValueToken.class).getText();
			return new SingleValue(singleValue);
		} catch (SyntaxException e) {
		}
		try {
			String idValue = consume(IDToken.class).getText();
			return new IDValue(idValue);
		} catch (SyntaxException e) {
			throw new SyntaxException("single value or reference expected");
		}
	}

	private Token consume(Class<? extends Token> expectedTokenClass) throws SyntaxException {
		if (currentToken.getClass().equals(expectedTokenClass)) {
			Token consumed = currentToken;
			currentToken = currentToken.next();
			return consumed;
		} else {
			throw new SyntaxException(expectedTokenClass + " expected");
		}
	}

}
