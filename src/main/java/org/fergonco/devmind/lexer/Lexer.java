package org.fergonco.devmind.lexer;

import java.util.ArrayList;

public class Lexer {

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
			if (matches(chars, position, "what")) {
				position += 4;
				ret.add(new WhatToken());
			} else if (Character.isLetter(character)) {
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
				ret.add(new OpenParenthesisToken());
			} else if (character == ')') {
				ret.add(new ClosedParenthesisToken());
			} else if (character == '=') {
				ret.add(new EqualsToken());
			} else if (character == ',') {
				ret.add(new ComaToken());
			} else if (character == '-') {
				if (position + 1 < chars.length && chars[position + 1] == '>') {
					ret.add(new ImplicationToken());
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

	private boolean matches(char[] chars, int pos, String str) {
		if (chars.length < pos + str.length()) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			if (chars[pos + i] != str.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	private void linkTokens() {
		for (int i = 1; i < ret.size(); i++) {
			((AbstractToken) ret.get(i - 1)).next(ret.get(i));
		}
	}

}
