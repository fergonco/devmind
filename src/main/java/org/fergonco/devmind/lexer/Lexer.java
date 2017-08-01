package org.fergonco.devmind.lexer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fergonco.devmind.SyntaxException;

public class Lexer {

	public static final Token END_STATEMENT = new EndStatementToken();
	public static final Token EQUALS = new EqualsToken();
	public static final Token CLOSED_PARENTHESIS = new ClosedParenthesisToken();
	public static final Token COMA = new ComaToken();
	public static final Token OPEN_PARENTHESIS = new OpenParenthesisToken();
	private String script;
	private int position;
	private ArrayList<Token> ret = new ArrayList<>();
	private Pattern spaces = Pattern.compile("^\\s*");
	private Pattern EOF = Pattern.compile("^\\Z");
	private Pattern idPattern = Pattern.compile("^\\w+");
	private Pattern untilComa = Pattern.compile("^[^\"]*");

	public Lexer(String script) {
		this.script = script;
	}

	public Token process() throws SyntaxException {
		while (position < script.length()) {
			accept(spaces);
			identifier();
			accept(spaces);
			if (accept("(") != null) {
				ret.add(OPEN_PARENTHESIS);
				identifier();
				expect(")");
				ret.add(CLOSED_PARENTHESIS);
			}
			accept(spaces);
			if (accept("=") != null) {
				ret.add(EQUALS);
				value();
			}
			accept(spaces);
			expect(EOF);
			ret.add(END_STATEMENT);
		}

		linkTokens();

		return ret.get(0);
	}

	private void linkTokens() {
		for (int i = 1; i < ret.size(); i++) {
			((AbstractToken) ret.get(i - 1)).next(ret.get(i));
		}
	}

	private void value() throws SyntaxException {
		accept(spaces);
		if (accept("\"") != null) {
			ret.add(new SingleValueToken(expect(untilComa)));
			expect("\"");
		} else if (accept("(") != null) {
			ret.add(OPEN_PARENTHESIS);
			boolean first = true;
			while (accept(")") == null) {
				if (!first) {
					expect(",");
					ret.add(COMA);
				} else {
					first = false;
				}
				value();
			}
			ret.add(CLOSED_PARENTHESIS);
		} else {
			String id = expect(idPattern);
			if (Character.isUpperCase(id.charAt(0))) {
				ret.add(new IDValueToken(id));
			} else {
				ret.add(new SingleValueToken(id));
			}
		}
	}

	private void identifier() throws SyntaxException {
		ret.add(new IDToken(expect(idPattern)));
	}

	private String expect(String str) throws SyntaxException {
		String errMessage = str + " expected at position " + position;
		if (position + str.length() > script.length()) {
			throw new SyntaxException(errMessage);
		}
		for (int i = 0; i < str.length(); i++) {
			if (script.charAt(position + i) != str.charAt(i)) {
				throw new SyntaxException(errMessage);
			}
		}
		position += str.length();
		return str;
	}

	private String expect(Pattern pattern) throws SyntaxException {
		Matcher matcher = pattern.matcher(script.substring(position));
		if (matcher.find()) {
			String ret = matcher.group();
			position += ret.length();
			return ret;
		} else {
			throw new SyntaxException(pattern.pattern() + " expected at position " + position);
		}
	}

	private String accept(String str) {
		try {
			return expect(str);
		} catch (SyntaxException e) {
			return null;
		}
	}

	private String accept(Pattern pattern) {
		try {
			return expect(pattern);
		} catch (SyntaxException e) {
			return null;
		}
	}

}
