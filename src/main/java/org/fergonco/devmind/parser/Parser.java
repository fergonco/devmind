package org.fergonco.devmind.parser;

import java.util.ArrayList;

import org.fergonco.devmind.interpreter.ConstantStatement;
import org.fergonco.devmind.interpreter.EqualityStatement;
import org.fergonco.devmind.interpreter.ImplicationStatement;
import org.fergonco.devmind.interpreter.PredicateStatement;
import org.fergonco.devmind.interpreter.Statement;
import org.fergonco.devmind.lexer.ClosedParenthesisToken;
import org.fergonco.devmind.lexer.ComaToken;
import org.fergonco.devmind.lexer.EqualsToken;
import org.fergonco.devmind.lexer.IDToken;
import org.fergonco.devmind.lexer.ImplicationToken;
import org.fergonco.devmind.lexer.OpenParenthesisToken;
import org.fergonco.devmind.lexer.SingleValueToken;
import org.fergonco.devmind.lexer.Token;

public class Parser {

	private Token currentToken;
	private Token lastConsumed;

	public Parser(Token token) {
		this.currentToken = token;
	}

	public Statement[] parse() throws SyntaxException {
		ArrayList<Statement> statements = new ArrayList<>();
		Expression leftSide = expression();
		if (accept(EqualsToken.class)) {
			Expression rightSide = expression();
			statements.add(new EqualityStatement(leftSide, rightSide));
		} else if (accept(ImplicationToken.class)) {
			Expression rightSide = expression();
			statements.add(new ImplicationStatement(leftSide, rightSide));
		} else {
			if (leftSide instanceof Constant) {
				statements.add(new ConstantStatement(((Constant) leftSide).getId()));
			} else if (leftSide instanceof Function) {
				Function predicateCall = (Function) leftSide;
				statements.add(new PredicateStatement(predicateCall));
			}
		}

		return statements.toArray(new Statement[statements.size()]);
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
