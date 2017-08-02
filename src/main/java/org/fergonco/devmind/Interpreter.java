package org.fergonco.devmind;

import org.fergonco.devmind.lexer.Lexer;
import org.fergonco.devmind.lexer.Token;
import org.fergonco.devmind.parser.Parser;

public class Interpreter {

	public Statement[] compile(String script) throws SyntaxException {
		Lexer lexer = new Lexer(script);
		Token token = lexer.process();
		Parser parser = new Parser(token);
		return parser.parse();
	}


}
