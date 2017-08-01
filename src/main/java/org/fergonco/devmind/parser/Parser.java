package org.fergonco.devmind.parser;

import java.util.ArrayList;

import org.fergonco.devmind.PredicateStatement;
import org.fergonco.devmind.Statement;
import org.fergonco.devmind.SyntaxException;
import org.fergonco.devmind.VariableStatement;
import org.fergonco.devmind.lexer.ClosedParenthesisToken;
import org.fergonco.devmind.lexer.EqualsToken;
import org.fergonco.devmind.lexer.IDToken;
import org.fergonco.devmind.lexer.IDValueToken;
import org.fergonco.devmind.lexer.Lexer;
import org.fergonco.devmind.lexer.OpenParenthesisToken;
import org.fergonco.devmind.lexer.SingleValueToken;
import org.fergonco.devmind.lexer.Token;

public class Parser {

	private Token currentToken;

	public Parser(Token token) {
		this.currentToken = token;
	}

	public Statement[] parse(Token token) throws SyntaxException {
		ArrayList<Statement> statements = new ArrayList<>();
		Token lookahead = token.next();
		if (lookahead == Lexer.OPEN_PARENTHESIS) {
			statements.add(predicate());
		} else if (lookahead == Lexer.EQUALS) {
			statements.add(variable());
		}

		return statements.toArray(new Statement[statements.size()]);
	}

	private Statement variable() throws SyntaxException {
		String predicate = consume(IDToken.class).getText();
		consume(EqualsToken.class);
		Value value = value();

		return new VariableStatement(predicate, value);
	}

	private Statement predicate() throws SyntaxException {
		String predicate = consume(IDToken.class).getText();
		consume(OpenParenthesisToken.class);
		String term = consume(IDToken.class).getText();
		consume(ClosedParenthesisToken.class);
		consume(EqualsToken.class);
		Value value = value();

		return new PredicateStatement(predicate, term, value);
	}

	private Value value() throws SyntaxException {
		try {
			String singleValue = consume(SingleValueToken.class).getText();
			return new SingleValue(singleValue);
		} catch (SyntaxException e) {
		}
		try {
			String idValue = consume(IDValueToken.class).getText();
			return new IDValue(idValue);
		} catch (SyntaxException e) {
		}
		try {
			consume(Lexer.OPEN_PARENTHESIS.getClass());
			ArrayList<Value> values = new ArrayList<>();
			try {
				while (true) {
					values.add(value());
				}
			} catch (SyntaxException v) {
				consume(Lexer.CLOSED_PARENTHESIS.getClass());
			}
			return new ArrayValue(values.toArray(new Value[values.size()]));
		} catch (SyntaxException f) {
			throw new SyntaxException("single value or multiple values between parentheses expected");
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
