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
	public void AskConstants() throws SyntaxException, LexerException {
		Interpreter i = new Interpreter();
		i.tell("P");
		i.tell("A");
		i.tell("cool(A)");
		String[] constants = i.askConstants();
		assertEquals(2, constants.length);
		assertTrue(contains(constants, "P"));
		assertTrue(contains(constants, "A"));
	}

	@Test
	public void AskReferencesOfConstant() throws SyntaxException, LexerException {
		Interpreter i = new Interpreter();
		i.tell("P");
		i.tell("A");
		i.tell("cool(A)");
		i.tell("nice(B)");
		i.tell("parent(C) = A");
		i.tell("parent(C, A)");
		i.tell("parts(B) = parts(A)");
		Statement[] statements = i.askReferences("A");
		assertEquals(4, statements.length);
		assertTrue(contains(statements, new PredicateStatement(new Function("cool", new Constant("A")))));
		assertTrue(contains(statements, new PredicateStatement(new Function("parent", new Constant("C"), new Constant("A")))));
		assertTrue(contains(statements,
				new EqualityStatement(new Function("parent", new Constant("C")), new Constant("A"))));
		assertTrue(contains(statements, new EqualityStatement(new Function("parts", new Constant("B")),
				new Function("parts", new Constant("A")))));
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
