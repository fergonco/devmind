package org.fergonco.devmind.lexer;

import java.util.ArrayList;

public class Lexer {

	public static final Token EQUALS = new EqualsToken();
	public static final Token CLOSED_PARENTHESIS = new ClosedParenthesisToken();
	public static final Token COMA = new ComaToken();
	public static final Token IMPLICATION = new ImplicationToken();
	public static final Token OPEN_PARENTHESIS = new OpenParenthesisToken();
	private String script;
	private int position;
	private ArrayList<Token> ret = new ArrayList<>();

	public Lexer(String script) {
		this.script = script;
	}

	public Token process() throws LexerException {
		position = 0;
		char[] chars = script.toCharArray();
		while (position < chars.length) {
			char character = chars[position];
			if (Character.isLetter(character)) {
				int start = position;
				while (position < chars.length && Character.isLetterOrDigit(chars[position])) {
					position++;
				}
				int end = position;
				position--; // move to the last position of token
				ret.add(new IDToken(script.substring(start, end)));
			} else if (character == '\"') {
				position++;
				int start = position;
				while (position < chars.length && chars[position] != '\"') {
					position++;
				}
				int end = position;
				ret.add(new SingleValueToken(script.substring(start, end)));
			} else if (character == '(') {
				ret.add(OPEN_PARENTHESIS);
			} else if (character == ')') {
				ret.add(CLOSED_PARENTHESIS);
			} else if (character == '=') {
				ret.add(EQUALS);
			} else if (character == ',') {
				ret.add(COMA);
			} else if (character == '-') {
				if (position + 1 < chars.length && chars[position + 1] == '>') {
					ret.add(IMPLICATION);
					position++;// move to last position of token
				} else {
					throw new LexerException("- must be followed by >");
				}
			} else if (Character.isWhitespace(character)) {
				// noop
			} else {
				throw new LexerException("Invalid character: " + character);
			}
			position++;
		}
		linkTokens();
		return ret.get(0);
	}

	private void linkTokens() {
		for (int i = 1; i < ret.size(); i++) {
			((AbstractToken) ret.get(i - 1)).next(ret.get(i));
		}
	}

}
