package org.fergonco.devmind;

import org.fergonco.devmind.Interpreter;
import org.fergonco.devmind.FunctionDefinitionStatement;
import org.fergonco.devmind.Statement;
import org.fergonco.devmind.SyntaxException;
import org.fergonco.devmind.VariableDefinitionStatement;
import org.fergonco.devmind.parser.IDValue;
import org.fergonco.devmind.parser.SingleValue;
import org.junit.Test;

import junit.framework.TestCase;

public class ParserTest extends TestCase {

	@Test
	public void testConstant() throws SyntaxException {
		Interpreter compiler = new Interpreter();
		Statement[] statements = compiler.compile("Project");
		assertTrue(statements.length == 1);
		checkEquals(statements[0], new ConstantStatement("Project"));
	}

	@Test
	public void testVariable() throws SyntaxException {
		Interpreter compiler = new Interpreter();
		Statement[] statements = compiler.compile("P = Project");
		assertTrue(statements.length == 1);
		checkEquals(statements[0], new VariableDefinitionStatement("P", new IDValue("Project")));
	}

	@Test
	public void testPredicate() throws SyntaxException {
		Interpreter compiler = new Interpreter();
		String text = "We dont know yet";
		Statement[] statements = compiler.compile("aim(P) = \"" + text + "\"");
		assertTrue(statements.length == 1);
		checkEquals(statements[0], new FunctionDefinitionStatement("aim", new String[] { "P" }, new SingleValue(text)));
	}

	private void checkEquals(Statement statement, Statement variableStatement) {
		assertTrue(statement + " vs " + variableStatement, statement.toString().equals(variableStatement.toString()));
	}
}
