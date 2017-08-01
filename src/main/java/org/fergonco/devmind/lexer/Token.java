package org.fergonco.devmind.lexer;

public interface Token {

	String getText();
	
	Token next();
}
