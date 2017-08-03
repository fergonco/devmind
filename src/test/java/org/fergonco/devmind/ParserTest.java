package org.fergonco.devmind;

import org.fergonco.devmind.interpreter.ConstantStatement;
import org.fergonco.devmind.interpreter.EqualityStatement;
import org.fergonco.devmind.interpreter.Interpreter;
import org.fergonco.devmind.interpreter.Statement;
import org.fergonco.devmind.lexer.LexerException;
import org.fergonco.devmind.parser.Constant;
import org.fergonco.devmind.parser.Expression;
import org.fergonco.devmind.parser.Function;
import org.fergonco.devmind.parser.Literal;
import org.fergonco.devmind.parser.SyntaxException;
import org.junit.Test;

import junit.framework.TestCase;

public class ParserTest extends TestCase {

	@Test
	public void testConstant() throws SyntaxException, LexerException {
		Interpreter compiler = new Interpreter();
		Statement[] statements = compiler.compile("Project");
		assertTrue(statements.length == 1);
		checkEquals(statements[0], new ConstantStatement("Project"));
	}

	@Test
	public void testVariable() throws SyntaxException, LexerException {
		Interpreter compiler = new Interpreter();
		Statement[] statements = compiler.compile("P = Project");
		assertTrue(statements.length == 1);
		checkEquals(statements[0], new EqualityStatement(new Constant("P"), new Constant("Project")));
	}

	@Test
	public void testFunction() throws SyntaxException, LexerException {
		Interpreter compiler = new Interpreter();
		String text = "We dont know yet";
		Statement[] statements = compiler.compile("aim(P) = \"" + text + "\"");
		assertTrue(statements.length == 1);
		checkEquals(statements[0],
				new EqualityStatement(new Function("aim", new Expression[] { new Constant("P") }), new Literal(text)));
	}

	private void checkEquals(Statement statement, Statement variableStatement) {
		assertTrue(statement + " vs " + variableStatement, statement.toString().equals(variableStatement.toString()));
	}
}
