package org.fergonco.devmind;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.io.IOException;

import org.fergonco.devmind.interpreter.EqualityStatement;
import org.fergonco.devmind.interpreter.Interpreter;
import org.fergonco.devmind.interpreter.KBRuntimeException;
import org.fergonco.devmind.interpreter.PredicateStatement;
import org.fergonco.devmind.interpreter.Statement;
import org.fergonco.devmind.lexer.LexerException;
import org.fergonco.devmind.parser.Constant;
import org.fergonco.devmind.parser.Function;
import org.fergonco.devmind.parser.SyntaxException;
import org.junit.Test;

public class KBTest {

	@Test
	public void constants() throws SyntaxException, LexerException, KBRuntimeException {
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
	public void constantReferences() throws SyntaxException, LexerException, KBRuntimeException {
		Interpreter i = new Interpreter();
		i.run("A");
		i.run("B");
		i.run("C");
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
	public void runAskWhat() throws SyntaxException, LexerException, KBRuntimeException {
		Interpreter i = new Interpreter();
		i.run("A");
		String output = i.run("what");
		assertTrue(output.contains("A"));
	}

	@Test
	public void runShow() throws SyntaxException, LexerException, KBRuntimeException {
		Interpreter i = new Interpreter();
		i.run("A");
		i.run("B");
		i.run("C");
		i.run("nice(B)");
		i.run("parent(C) = A");
		i.run("uses(ui(A), B)");
		String output = i.run("show A");
		assertTrue(output.contains("parent(C)=A"));
		assertTrue(output.contains("uses(ui(A),B)"));
		assertTrue(!output.contains("nice(B)"));
	}

	@Test
	public void runShowNoRefs() throws SyntaxException, LexerException, KBRuntimeException {
		Interpreter i = new Interpreter();
		i.run("A");
		i.run("B");
		i.run("nice(B)");
		String output = i.run("show A");
		assertEquals("", output.trim());
	}

	@Test
	public void getAllSymbols() throws SyntaxException, LexerException, KBRuntimeException {
		Interpreter i = new Interpreter();
		i.run("A");
		i.run("B");
		i.run("C");
		i.run("nice(B)");
		i.run("nice(C)");
		i.run("parent(C) = A");
		i.run("uses(ui(A), B)");
		String[] symbols = i.getSymbols();
		assertEquals(7, symbols.length);
		contains(symbols, "A");
		contains(symbols, "B");
		contains(symbols, "C");
		contains(symbols, "nice");
		contains(symbols, "parent");
		contains(symbols, "uses");
		contains(symbols, "ui");
	}

	@Test
	public void loadSelfKB() throws SyntaxException, LexerException, KBRuntimeException, IOException {
		Interpreter i = new Interpreter();
		String output = i.commmandLineRun("what");
		assertTrue(output.contains("Project"));
		i = new Interpreter();
		String output2 = i.commmandLineRun("what");
		assertEquals(output, output2);
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
