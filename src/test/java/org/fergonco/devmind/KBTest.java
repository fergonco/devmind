package org.fergonco.devmind;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.fergonco.devmind.interpreter.EqualityStatement;
import org.fergonco.devmind.interpreter.Interpreter;
import org.fergonco.devmind.interpreter.PredicateStatement;
import org.fergonco.devmind.interpreter.Statement;
import org.fergonco.devmind.lexer.LexerException;
import org.fergonco.devmind.parser.Constant;
import org.fergonco.devmind.parser.Function;
import org.fergonco.devmind.parser.SyntaxException;
import org.junit.Test;

public class KBTest {

	@Test
	public void constants() throws SyntaxException, LexerException {
		Interpreter i = new Interpreter();
		i.run("P");
		i.run("A");
		i.run("cool(A)");
		String[] constants = i.getConstants();
		assertEquals(2, constants.length);
		assertTrue(contains(constants, "P"));
		assertTrue(contains(constants, "A"));
	}

	@Test
	public void constantReferences() throws SyntaxException, LexerException {
		Interpreter i = new Interpreter();
		i.run("P");
		i.run("A");
		i.run("cool(A)");
		i.run("nice(B)");
		i.run("parent(C) = A");
		i.run("parent(C, A)");
		i.run("parts(B) = parts(A)");
		Statement[] statements = i.getReferences("A");
		assertEquals(4, statements.length);
		assertTrue(contains(statements, new PredicateStatement(new Function("cool", new Constant("A")))));
		assertTrue(contains(statements,
				new PredicateStatement(new Function("parent", new Constant("C"), new Constant("A")))));
		assertTrue(contains(statements,
				new EqualityStatement(new Function("parent", new Constant("C")), new Constant("A"))));
		assertTrue(contains(statements, new EqualityStatement(new Function("parts", new Constant("B")),
				new Function("parts", new Constant("A")))));
	}

	@Test
	public void runAskWhat() throws SyntaxException, LexerException {
		Interpreter i = new Interpreter();
		i.run("A");
		String output = i.run("what");
		assertTrue(output.contains("A"));
	}

	@Test
	public void runAskRefs() throws SyntaxException, LexerException {
		Interpreter i = new Interpreter();
		i.run("A");
		i.run("nice(B)");
		i.run("parent(C) = A");
		i.run("uses(ui(A), B)");
		String output = i.run("show A");
		assertTrue(output.contains("parent(C)=A"));
		assertTrue(output.contains("uses(ui(A),B)"));
		assertTrue(!output.contains("nice(B)"));
	}

	private boolean contains(Object[] array, Object element) {
		for (Object test : array) {
			if (test.toString().equals(element.toString())) {
				return true;
			}
		}
		return false;
	}
}
