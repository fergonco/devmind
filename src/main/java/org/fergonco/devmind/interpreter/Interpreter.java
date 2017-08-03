package org.fergonco.devmind.interpreter;

import org.fergonco.devmind.lexer.Lexer;
import org.fergonco.devmind.lexer.LexerException;
import org.fergonco.devmind.lexer.Token;
import org.fergonco.devmind.parser.Parser;
import org.fergonco.devmind.parser.SyntaxException;

public class Interpreter {

	private KnowledgeBase kb = new KnowledgeBase();

	public Statement[] compile(String script) throws SyntaxException, LexerException {
		Lexer lexer = new Lexer(script);
		Token token = lexer.process();
		Parser parser = new Parser(token);
		return parser.parse();
	}

	public void run(String script) throws SyntaxException, LexerException {
		Statement[] statements = compile(script);
		for (Statement statement : statements) {
			statement.execute(kb);
		}
	}

	public String[] getConstants() {
		return kb.getConstants();
	}

	public Statement[] getReferences(String constantName) {
		return kb.getReferences(constantName);
	}
}
