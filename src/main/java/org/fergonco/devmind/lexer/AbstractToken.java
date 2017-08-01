package org.fergonco.devmind.lexer;

public abstract class AbstractToken implements Token {

	private String text;
	private Token next = null;

	public AbstractToken(String text) {
		this.text = text;
	}

	@Override
	public String getText() {
		return text;
	}

	public void next(Token token) {
		this.next = token;
	}
	
	@Override
	public Token next() {
		return next;
	}

}
