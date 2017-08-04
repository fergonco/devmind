package org.fergonco.devmind;

import static junit.framework.Assert.fail;

import org.fergonco.devmind.interpreter.ConstantAlreadyExistsException;
import org.fergonco.devmind.interpreter.ConstantNotFoundException;
import org.fergonco.devmind.interpreter.Interpreter;
import org.fergonco.devmind.interpreter.KBRuntimeException;
import org.fergonco.devmind.lexer.LexerException;
import org.fergonco.devmind.parser.SyntaxException;
import org.junit.Test;

public class RuntimeTest {

	@Test
	public void testExistingConstant() throws SyntaxException, LexerException, KBRuntimeException {
		Interpreter i = new Interpreter();
		i.run("A");
		try {
			i.run("A");
			fail();
		} catch (ConstantAlreadyExistsException e) {
		}
	}

	@Test
	public void testRefToNonExistingConstant() throws SyntaxException, LexerException, KBRuntimeException {
		Interpreter i = new Interpreter();
		i.run("A");
		i.run("nice(A)");
		try {
			i.run("nice(B)");
			fail();
		} catch (ConstantNotFoundException e) {
		}
	}
}
