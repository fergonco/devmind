package org.fergonco.devmind;

import org.fergonco.devmind.interpreter.ConstantStatement;
import org.fergonco.devmind.interpreter.EqualityStatement;
import org.fergonco.devmind.interpreter.Interpreter;
import org.fergonco.devmind.interpreter.PredicateStatement;
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
		check("Project", new ConstantStatement("Project"));
	}

	private void check(String script, Statement statement) throws SyntaxException, LexerException {
		Interpreter interpreter = new Interpreter();
		Statement result = interpreter.compile(script);
		assertTrue(result + " vs " + statement, result.toString().equals(statement.toString()));
	}

	@Test
	public void testPredicate() throws SyntaxException, LexerException {
		check("nice(Project)", new PredicateStatement(new Function("nice", new Constant("Project"))));
	}

	@Test
	public void testPredicateParameters() throws SyntaxException, LexerException {
		check("nice(ui(Project))",
				new PredicateStatement(new Function("nice", new Function("ui", new Constant("Project")))));
		check("nice(\"holidays\")", new PredicateStatement(new Function("nice", new Literal("holidays"))));
	}

	@Test
	public void testEquality() throws SyntaxException, LexerException {
		check("P = Project", new EqualityStatement(new Constant("P"), new Constant("Project")));
		check("P = ui(Project, \"mobile\")", new EqualityStatement(new Constant("P"),
				new Function("ui", new Constant("Project"), new Literal("mobile"))));
		check("aim(P) = \"We dont know yet\"", new EqualityStatement(
				new Function("aim", new Expression[] { new Constant("P") }), new Literal("We dont know yet")));
	}
}
