package org.fergonco.devmind.interpreter;

import org.fergonco.devmind.lexer.Lexer;
import org.fergonco.devmind.lexer.LexerException;
import org.fergonco.devmind.lexer.Token;
import org.fergonco.devmind.parser.Parser;
import org.fergonco.devmind.parser.SyntaxException;

public class Interpreter {

	public Statement[] compile(String script) throws SyntaxException, LexerException {
		Lexer lexer = new Lexer(script);
		Token token = lexer.process();
		Parser parser = new Parser(token);
		return parser.parse();
	}


}
