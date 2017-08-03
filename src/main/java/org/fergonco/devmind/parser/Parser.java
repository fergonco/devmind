package org.fergonco.devmind.parser;

import java.util.ArrayList;

import org.fergonco.devmind.interpreter.ConstantStatement;
import org.fergonco.devmind.interpreter.EqualityStatement;
import org.fergonco.devmind.interpreter.ImplicationStatement;
import org.fergonco.devmind.interpreter.PredicateStatement;
import org.fergonco.devmind.interpreter.ShowStatement;
import org.fergonco.devmind.interpreter.Statement;
import org.fergonco.devmind.interpreter.WhatStatement;
import org.fergonco.devmind.lexer.ClosedParenthesisToken;
import org.fergonco.devmind.lexer.ComaToken;
import org.fergonco.devmind.lexer.EqualsToken;
import org.fergonco.devmind.lexer.IDToken;
import org.fergonco.devmind.lexer.ImplicationToken;
import org.fergonco.devmind.lexer.OpenParenthesisToken;
import org.fergonco.devmind.lexer.ShowToken;
import org.fergonco.devmind.lexer.SingleValueToken;
import org.fergonco.devmind.lexer.Token;
import org.fergonco.devmind.lexer.WhatToken;

public class Parser {

	private Token currentToken;
	private Token lastConsumed;

	public Parser(Token token) {
		this.currentToken = token;
	}

	public Statement parse() throws SyntaxException {
		Statement ret = null;
		if (accept(WhatToken.class)) {
			ret = new WhatStatement();
		} else if (accept(ShowToken.class))	{
			String id = expect(IDToken.class).getText();
			ret = new ShowStatement(id);
		} else {
			Expression leftSide = expression();
			if (accept(EqualsToken.class)) {
				Expression rightSide = expression();
				ret = new EqualityStatement(leftSide, rightSide);
			} else if (accept(ImplicationToken.class)) {
				Expression rightSide = expression();
				ret = new ImplicationStatement(leftSide, rightSide);
			} else {
				if (leftSide instanceof Constant) {
					ret = new ConstantStatement(((Constant) leftSide).getId());
				} else if (leftSide instanceof Function) {
					Function predicateCall = (Function) leftSide;
					ret = new PredicateStatement(predicateCall);
				}
			}
		}

		return ret;

	}

	public Expression expression() throws SyntaxException {
		if (accept(SingleValueToken.class)) {
			return new Literal(lastConsumed.getText());
		} else {
			String id = expect(IDToken.class).getText();
			if (accept(OpenParenthesisToken.class)) {
				ArrayList<Expression> terms = new ArrayList<>();
				do {
					terms.add(expression());
					try {
						expect(ComaToken.class);
					} catch (SyntaxException e) {
						break;
					}
				} while (true);
				expect(ClosedParenthesisToken.class);
				Expression[] termArray = terms.toArray(new Expression[terms.size()]);
				return new Function(id, termArray);
			} else {
				if (Character.isUpperCase(id.charAt(0))) {
					return new Constant(id);
				} else {
					return new Literal(id);
				}
			}
		}
	}

	private boolean accept(Class<? extends Token> tokenClass) {
		if (currentToken != null && currentToken.getClass() == tokenClass) {
			consumeToken();
			return true;
		} else {
			return false;
		}
	}

	private Token expect(Class<? extends Token> expectedTokenClass) throws SyntaxException {
		if (currentToken != null && currentToken.getClass().equals(expectedTokenClass)) {
			consumeToken();
			return lastConsumed;
		} else {
			throw new SyntaxException(expectedTokenClass + " expected");
		}
	}

	private void consumeToken() {
		lastConsumed = currentToken;
		currentToken = currentToken.next();
	}

}
